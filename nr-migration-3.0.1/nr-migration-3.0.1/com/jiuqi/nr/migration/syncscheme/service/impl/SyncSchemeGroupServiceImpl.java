/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.np.definition.common.UUIDUtils
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.migration.syncscheme.service.impl;

import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.np.definition.common.UUIDUtils;
import com.jiuqi.nr.migration.syncscheme.bean.SyncSchemeGroup;
import com.jiuqi.nr.migration.syncscheme.dao.ISyncSchemeGroupDao;
import com.jiuqi.nr.migration.syncscheme.exception.SyncSchemeException;
import com.jiuqi.nr.migration.syncscheme.service.ISyncSchemeGroupService;
import com.jiuqi.nr.migration.syncscheme.tree.ISyncSchemeGroupTreeService;
import com.jiuqi.nr.migration.syncscheme.tree.SyncSchemeGroupTreeNode;
import com.jiuqi.nr.migration.syncscheme.tree.TreeNode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Service
@Transactional(rollbackFor={Exception.class})
public class SyncSchemeGroupServiceImpl
implements ISyncSchemeGroupService,
ISyncSchemeGroupTreeService {
    @Autowired
    private ISyncSchemeGroupDao syncSchemeGroupDao;

    @Override
    public void check(boolean isUpdate, SyncSchemeGroup group) {
        if (isUpdate) {
            List<SyncSchemeGroup> childrens = this.getByParent(group.getParent());
            for (SyncSchemeGroup children : childrens) {
                if (!children.getTitle().equals(group.getTitle()) || children.getKey().equals(group.getKey())) continue;
                throw new SyncSchemeException("\u6570\u636e\u540c\u6b65\u65b9\u6848\u5206\u7ec4\u6807\u9898\u91cd\u590d\uff1a" + group.getTitle());
            }
        } else {
            SyncSchemeGroup groupFind = this.syncSchemeGroupDao.getByTitleAndGroup(group.getTitle(), group.getParent());
            if (groupFind != null) {
                throw new SyncSchemeException("\u6570\u636e\u540c\u6b65\u65b9\u6848\u5206\u7ec4\u6807\u9898\u91cd\u590d\uff1a" + group.getTitle());
            }
        }
    }

    @Override
    public void add(SyncSchemeGroup group) throws SyncSchemeException {
        this.check(false, group);
        group.setKey(UUIDUtils.getKey());
        group.setUpdateTime(new Date().getTime());
        group.setOrder(OrderGenerator.newOrder());
        this.syncSchemeGroupDao.add(group);
    }

    @Override
    public void update(SyncSchemeGroup group) throws SyncSchemeException {
        this.check(true, group);
        this.syncSchemeGroupDao.update(group);
    }

    @Override
    public void delete(String key) throws SyncSchemeException {
        List<SyncSchemeGroup> childrens = this.getByParent(key);
        if (!CollectionUtils.isEmpty(childrens)) {
            throw new SyncSchemeException(String.format("\u540c\u6b65\u65b9\u6848\u5206\u7ec4[%s]\u4e0b\u6709\u5b50\u5206\u7ec4\uff0c\u65e0\u6cd5\u5220\u9664", key));
        }
        this.syncSchemeGroupDao.delete(key);
    }

    @Override
    public SyncSchemeGroup getByKey(String key) {
        return this.syncSchemeGroupDao.get(key);
    }

    @Override
    public void moveUp(String key) throws SyncSchemeException {
        SyncSchemeGroup group = this.syncSchemeGroupDao.get(key);
        List<SyncSchemeGroup> groups = this.syncSchemeGroupDao.getByParent(group.getParent());
        int index = groups.indexOf(group);
        if (index == -1) {
            throw new SyncSchemeException(String.format("\u540c\u6b65\u65b9\u6848\u5206\u7ec4[%s]\u4e0d\u5b58\u5728", key));
        }
        if (index == 0) {
            throw new SyncSchemeException("\u5df2\u7ecf\u662f\u7b2c\u4e00\u4e2a\uff0c\u65e0\u6cd5\u4e0a\u79fb");
        }
        SyncSchemeGroup beforeGroup = groups.get(index - 1);
        String tempOrder = group.getOrder();
        group.setOrder(beforeGroup.getOrder());
        beforeGroup.setOrder(tempOrder);
        this.syncSchemeGroupDao.update(beforeGroup);
        this.syncSchemeGroupDao.update(group);
    }

    @Override
    public void moveDown(String key) throws SyncSchemeException {
        SyncSchemeGroup group = this.syncSchemeGroupDao.get(key);
        List<SyncSchemeGroup> groups = this.syncSchemeGroupDao.getByParent(group.getParent());
        int index = groups.indexOf(group);
        if (index == -1) {
            throw new SyncSchemeException(String.format("\u540c\u6b65\u65b9\u6848\u5206\u7ec4[%s]\u4e0d\u5b58\u5728", group.getKey()));
        }
        if (index == groups.size() - 1) {
            throw new SyncSchemeException("\u5df2\u7ecf\u662f\u6700\u540e\u4e00\u4e2a\uff0c\u65e0\u6cd5\u4e0b\u79fb");
        }
        SyncSchemeGroup afterGroup = groups.get(index + 1);
        String tempOrder = group.getOrder();
        group.setOrder(afterGroup.getOrder());
        afterGroup.setOrder(tempOrder);
        this.syncSchemeGroupDao.update(afterGroup);
        this.syncSchemeGroupDao.update(group);
    }

    @Override
    public List<SyncSchemeGroup> getByParent(String parentKey) {
        return this.syncSchemeGroupDao.getByParent(parentKey);
    }

    @Override
    public List<TreeNode<SyncSchemeGroupTreeNode>> getRoot() {
        ArrayList<TreeNode<SyncSchemeGroupTreeNode>> roots = new ArrayList<TreeNode<SyncSchemeGroupTreeNode>>();
        TreeNode<SyncSchemeGroupTreeNode> root = this.buildVirtualRootNode();
        root.setSelected(true);
        root.setExpand(true);
        root.setChildren(this.getChildren(null));
        roots.add(root);
        return roots;
    }

    @Override
    public List<TreeNode<SyncSchemeGroupTreeNode>> getChildren(String parentKey) {
        String parentTitle = "\u5168\u90e8\u6570\u636e\u540c\u6b65\u65b9\u6848";
        if (parentKey != null) {
            parentTitle = this.getByKey(parentKey).getTitle();
        }
        return this.getChildren(parentKey, parentTitle);
    }

    @Override
    public List<TreeNode<SyncSchemeGroupTreeNode>> location(String key) {
        ArrayList<TreeNode<SyncSchemeGroupTreeNode>> nodes = new ArrayList<TreeNode<SyncSchemeGroupTreeNode>>();
        if (key == null) {
            TreeNode<SyncSchemeGroupTreeNode> rootNode = this.buildVirtualRootNode();
            rootNode.setSelected(true);
            nodes.add(rootNode);
        } else {
            SyncSchemeGroup group = this.getByKey(key);
            if (group == null) {
                throw new SyncSchemeException(String.format("\u540c\u6b65\u65b9\u6848\u5206\u7ec4[%s]\u4e0d\u5b58\u5728", key));
            }
            ArrayList<SyncSchemeGroup> path = new ArrayList<SyncSchemeGroup>();
            path.add(group);
            String parentKey = group.getParent();
            while (parentKey != null) {
                SyncSchemeGroup parentGroup = this.getByKey(parentKey);
                if (parentGroup == null) {
                    throw new SyncSchemeException(String.format("\u540c\u6b65\u65b9\u6848\u5206\u7ec4[%s]\u4e0d\u5b58\u5728", key));
                }
                path.add(parentGroup);
                parentKey = parentGroup.getParent();
            }
            Collections.reverse(path);
            TreeNode<SyncSchemeGroupTreeNode> rootNode = this.buildVirtualRootNode();
            rootNode.setExpand(true);
            nodes.add(rootNode);
            List<TreeNode<Object>> childrens = this.getChildren(null);
            rootNode.setChildren(childrens);
            for (int index = 0; index < path.size(); ++index) {
                SyncSchemeGroup subGroup = (SyncSchemeGroup)path.get(index);
                TreeNode subGroupNode = Objects.requireNonNull(childrens).stream().filter(child -> child.getKey().equals(subGroup.getKey())).findFirst().orElseThrow(() -> new SyncSchemeException(String.format("\u540c\u6b65\u65b9\u6848\u5206\u7ec4[%s]\u4e0d\u5b58\u5728", subGroup.getKey())));
                if (index != path.size() - 1) {
                    childrens = this.getChildren(subGroup.getKey(), subGroup.getTitle());
                    subGroupNode.setChildren(childrens);
                    subGroupNode.setExpand(true);
                    continue;
                }
                subGroupNode.setSelected(true);
            }
        }
        return nodes;
    }

    private List<TreeNode<SyncSchemeGroupTreeNode>> getChildren(String parentKey, String parentTitle) {
        List<SyncSchemeGroup> groups = this.getByParent(parentKey);
        if (!CollectionUtils.isEmpty(groups)) {
            ArrayList<TreeNode<SyncSchemeGroupTreeNode>> childrens = new ArrayList<TreeNode<SyncSchemeGroupTreeNode>>();
            for (int i = 0; i < groups.size(); ++i) {
                childrens.add(this.buildTreeNode(groups.get(i), parentTitle, i == 0, i == groups.size() - 1));
            }
            return childrens;
        }
        return null;
    }

    private TreeNode<SyncSchemeGroupTreeNode> buildVirtualRootNode() {
        SyncSchemeGroup rootGroup = new SyncSchemeGroup();
        rootGroup.setKey("00000000-0000-0000-0000-000000000000");
        rootGroup.setTitle("\u5168\u90e8\u6570\u636e\u540c\u6b65\u65b9\u6848");
        rootGroup.setParent(null);
        return this.buildTreeNode(rootGroup, null, false, false);
    }

    private TreeNode<SyncSchemeGroupTreeNode> buildTreeNode(SyncSchemeGroup group, String parentTitle, boolean isFirst, boolean isLast) {
        SyncSchemeGroupTreeNode nodeData = new SyncSchemeGroupTreeNode(group.getKey(), group.getTitle(), group.getParent(), parentTitle, group.getOrder());
        nodeData.setFirst(isFirst);
        nodeData.setLast(isLast);
        TreeNode<SyncSchemeGroupTreeNode> node = new TreeNode<SyncSchemeGroupTreeNode>(nodeData);
        node.setKey(group.getKey());
        node.setTitle(group.getTitle());
        node.setParent(group.getParent());
        node.setIcon("#icon-folder");
        return node;
    }
}

