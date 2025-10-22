/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.nr.common.itree.INode
 *  com.jiuqi.nr.common.itree.ITree
 */
package com.jiuqi.nr.entity.component.tree.service.impl;

import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.common.itree.INode;
import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.entity.bo.EntitySearchBO;
import com.jiuqi.nr.entity.common.NodeType;
import com.jiuqi.nr.entity.component.tree.service.EntityTreeService;
import com.jiuqi.nr.entity.component.tree.vo.TreeNode;
import com.jiuqi.nr.entity.component.tree.vo.TreeParam;
import com.jiuqi.nr.entity.config.IsolatingBaseDataConfig;
import com.jiuqi.nr.entity.internal.service.AdapterService;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.model.IEntityGroup;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

public class EntityTreeServiceImpl
implements EntityTreeService {
    private static final Logger logger = LoggerFactory.getLogger(EntityTreeServiceImpl.class);
    private static final Integer SHOW_ALL = 1;
    private static final Integer DIMENSION_MODE = 2;
    private static final Integer CALIBRE_MODE = 3;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private AdapterService adapterService;
    @Autowired
    private IsolatingBaseDataConfig isolatingBaseData;

    @Override
    public List<ITree<TreeNode>> initTree(TreeParam treeParam) {
        this.filterExistSelectedKeys(treeParam);
        return this.buildRootNodes(true, treeParam);
    }

    private void filterExistSelectedKeys(TreeParam treeParam) {
        List<String> selectedKeys = treeParam.getSelectedKeys();
        if (!CollectionUtils.isEmpty(selectedKeys)) {
            ArrayList<String> filterExistKeys = new ArrayList<String>(selectedKeys.size());
            for (String selectedKey : selectedKeys) {
                if (!this.existEntity(selectedKey)) continue;
                filterExistKeys.add(selectedKey);
            }
            treeParam.setSelectedKeys(filterExistKeys);
        }
    }

    private boolean existEntity(String selectedKey) {
        return this.entityMetaService.queryEntity(selectedKey) != null;
    }

    private List<ITree<TreeNode>> buildRootNodes(boolean expandFirstNode, TreeParam treeParam) {
        ArrayList<ITree<TreeNode>> nodes = new ArrayList<ITree<TreeNode>>();
        List<String> pathKeys = this.getAllPathKeys(treeParam.getSelectedKeys());
        List<IEntityGroup> rootEntityGroups = this.entityMetaService.getRootEntityGroups();
        int flag = 0;
        if (!CollectionUtils.isEmpty(rootEntityGroups) && DIMENSION_MODE.equals(treeParam.getShowContent())) {
            rootEntityGroups.sort(Comparator.comparing(IEntityGroup::getId).reversed());
        }
        for (IEntityGroup group : rootEntityGroups) {
            TreeNode treeNode = TreeNode.copyFromGroup(group);
            ITree root = new ITree((INode)treeNode);
            nodes.add((ITree<TreeNode>)root);
            List<ITree<TreeNode>> childes = this.getChildrenGroups(treeNode.getKey(), pathKeys, treeParam.getShowContent());
            if (flag == 0 && expandFirstNode) {
                if (CollectionUtils.isEmpty(childes)) {
                    childes = this.getChildrenEntities(group.getId(), treeParam.getShowContent(), treeParam.isCheckBaseTree());
                }
                if (!CollectionUtils.isEmpty(childes)) {
                    for (ITree<TreeNode> iTreeNode : childes) {
                        root.appendChild(iTreeNode);
                    }
                    root.setExpanded(true);
                }
            }
            ++flag;
        }
        return nodes;
    }

    private List<String> getAllPathKeys(List<String> selectedKeys) {
        ArrayList<String> pathKeys = new ArrayList<String>();
        for (String selectedKey : selectedKeys) {
            List<String> path = this.getNodePath(selectedKey);
            if (CollectionUtils.isEmpty(path)) continue;
            pathKeys.addAll(path);
        }
        return pathKeys;
    }

    @Override
    public List<ITree<TreeNode>> getChildrenNodes(TreeParam treeParam) {
        this.filterExistSelectedKeys(treeParam);
        List<String> allPathKeys = this.getAllPathKeys(treeParam.getSelectedKeys());
        List<ITree<TreeNode>> groupNodes = this.getChildrenGroups(treeParam.getGroupKey(), allPathKeys, treeParam.getShowContent());
        List<ITree<TreeNode>> entityNodes = this.getChildrenEntities(treeParam.getGroupKey(), treeParam.getShowContent(), treeParam.isCheckBaseTree());
        for (ITree<TreeNode> node : groupNodes) {
            if (!allPathKeys.contains(node.getKey())) continue;
            node.setChecked(true);
        }
        for (ITree<TreeNode> entityNode : entityNodes) {
            if (!treeParam.getSelectedKeys().contains(entityNode.getKey())) continue;
            entityNode.setChecked(true);
        }
        groupNodes.addAll(entityNodes);
        return groupNodes;
    }

    @Override
    public List<TreeNode> searchNodes(TreeParam treeParam) {
        Integer showContent = treeParam.getShowContent();
        List<IEntityDefine> entityDefines = new ArrayList<IEntityDefine>();
        if (SHOW_ALL.equals(showContent) || DIMENSION_MODE.equals(showContent)) {
            EntitySearchBO bo = new EntitySearchBO();
            bo.setKeyWords(treeParam.getKeyWords());
            List<IEntityDefine> defines = this.entityMetaService.fuzzySearchEntity(bo);
            entityDefines.addAll(defines);
        } else {
            EntitySearchBO bo = new EntitySearchBO();
            bo.setKeyWords(treeParam.getKeyWords());
            bo.setCalibre(1);
            entityDefines = this.entityMetaService.fuzzySearchEntity(bo);
        }
        return entityDefines.stream().map(TreeNode::copyFromEntity).collect(Collectors.toList());
    }

    @Override
    public List<ITree<TreeNode>> locationTreeNode(TreeParam treeParam) {
        List<String> path = this.getNodePath(treeParam.getLocationKey());
        List<ITree<TreeNode>> iTrees = this.buildRootNodes(false, treeParam);
        if (!this.existEntity(treeParam.getLocationKey())) {
            return iTrees;
        }
        List<String> allPathKeys = this.getAllPathKeys(treeParam.getSelectedKeys());
        for (ITree<TreeNode> iTree : iTrees) {
            this.findNode(path, 0, iTree, treeParam, allPathKeys);
        }
        return iTrees;
    }

    private List<String> getNodePath(String key) {
        List<String> queryPath = this.entityMetaService.getPath(key);
        IEntityDefine entityDefine = this.entityMetaService.queryEntity(key);
        if (entityDefine.getIncludeSubTreeEntity() == 0) {
            IEntityDefine baseTreeEntity = this.entityMetaService.queryBaseTreeEntityBySubTreeEntityId(key);
            queryPath.add(baseTreeEntity.getId());
        }
        return queryPath;
    }

    @Override
    public List<String> getAllChildrenNodes(TreeParam treeParam) {
        ArrayList<String> nodes = new ArrayList<String>(16);
        this.loopChildren(treeParam, nodes);
        return nodes;
    }

    private void loopChildren(TreeParam treeParam, List<String> keys) {
        List<ITree<TreeNode>> childrenNodes = this.getChildrenNodes(treeParam);
        if (!CollectionUtils.isEmpty(childrenNodes)) {
            for (ITree<TreeNode> node : childrenNodes) {
                if (NodeType.GROUP.equals((Object)((TreeNode)node.getData()).getNodeType())) {
                    treeParam.setGroupKey(((TreeNode)node.getData()).getKey());
                    this.loopChildren(treeParam, keys);
                    continue;
                }
                keys.add(node.getKey());
            }
        }
    }

    private void findNode(List<String> path, int index, ITree<TreeNode> node, TreeParam treeParam, List<String> allPathKeys) {
        if (node.getKey().equals(path.get(index))) {
            node.setExpanded(true);
            node.setChecked(true);
            try {
                this.expandGroup(path, index, node, treeParam, allPathKeys);
                this.expandAndCheckNode(node, path, index, allPathKeys, treeParam);
            }
            catch (JQException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    private void expandAndCheckNode(ITree<TreeNode> node, List<String> path, int index, List<String> allPathKeys, TreeParam treeParam) {
        List<ITree<TreeNode>> childrenEntities = this.getChildrenEntities(node.getKey(), treeParam.getShowContent(), treeParam.isCheckBaseTree());
        if (!CollectionUtils.isEmpty(childrenEntities)) {
            for (ITree<TreeNode> childEntity : childrenEntities) {
                if (childEntity.getKey().equals(treeParam.getLocationKey())) {
                    childEntity.setSelected(true);
                    childEntity.setChecked(true);
                }
                if (treeParam.getSelectedKeys().contains(childEntity.getKey()) || allPathKeys.contains(childEntity.getKey())) {
                    childEntity.setChecked(true);
                }
                node.appendChild(childEntity);
                int indexTemp = index;
                if (childEntity.isLeaf() || indexTemp == path.size() - 1) continue;
                childEntity.setExpanded(true);
                this.expandAndCheckNode(childEntity, path, ++indexTemp, allPathKeys, treeParam);
            }
        }
    }

    private void expandGroup(List<String> path, int index, ITree<TreeNode> node, TreeParam treeParam, List<String> allPathKeys) throws JQException {
        List<ITree<TreeNode>> childrenGroups = this.getChildrenGroups(node.getKey(), allPathKeys, treeParam.getShowContent());
        if (!CollectionUtils.isEmpty(childrenGroups)) {
            for (ITree<TreeNode> childrenGroup : childrenGroups) {
                int indexTemp = index;
                node.appendChild(childrenGroup);
                if (indexTemp == path.size() - 1) continue;
                this.findNode(path, ++indexTemp, childrenGroup, treeParam, allPathKeys);
            }
        }
    }

    private List<ITree<TreeNode>> getChildrenEntities(String groupId, Integer showContent, boolean checkBaseTree) {
        ArrayList<ITree<TreeNode>> nodes = new ArrayList<ITree<TreeNode>>();
        List<IEntityDefine> entities = null;
        entities = this.adapterService.judgementGroupId(groupId) ? this.entityMetaService.getEntitiesInGroup(groupId) : this.entityMetaService.getSubTreeEntities(groupId);
        if (!CollectionUtils.isEmpty(entities)) {
            List filterList = entities.stream().filter(e -> {
                if (DIMENSION_MODE.equals(showContent)) {
                    return this.isDimension((IEntityDefine)e) && (e.getIsolation() == 0 || e.getIsolation() > 0 && this.isolatingBaseData.isEnableIsolation());
                }
                if (CALIBRE_MODE.equals(showContent)) {
                    return this.isCalibre((IEntityDefine)e);
                }
                return true;
            }).collect(Collectors.toList());
            for (IEntityDefine entity : filterList) {
                String[] stringArray;
                boolean isSubTree;
                TreeNode iNode = TreeNode.copyFromEntity(entity);
                ITree iTree = new ITree((INode)iNode);
                boolean bl = isSubTree = entity.getIncludeSubTreeEntity() == 0;
                if (!isSubTree) {
                    isSubTree = CollectionUtils.isEmpty(this.entityMetaService.getSubTreeEntities(entity.getId()));
                }
                if (isSubTree) {
                    iTree.setLeaf(true);
                } else {
                    iNode.setNodeType(checkBaseTree ? NodeType.BASE_NODE : NodeType.GROUP);
                }
                if (isSubTree || checkBaseTree) {
                    String[] stringArray2 = new String[1];
                    stringArray = stringArray2;
                    stringArray2[0] = "#icon-56_ZT_A_NR_zhuti1";
                } else {
                    stringArray = null;
                }
                iTree.setIcons(stringArray);
                iTree.setTitle(TreeNode.buildEntityTitle(entity.getTitle(), entity.getCode()));
                nodes.add((ITree<TreeNode>)iTree);
            }
        }
        return nodes;
    }

    private boolean isDimension(IEntityDefine entityDefine) {
        return entityDefine.getDimensionFlag() == 1;
    }

    private boolean isCalibre(IEntityDefine entityDefine) {
        return "BASE".equals(entityDefine.getCategory());
    }

    private List<ITree<TreeNode>> getChildrenGroups(String groupKey, List<String> allPathKeys, Integer showContent) {
        ArrayList<ITree<TreeNode>> tree = new ArrayList<ITree<TreeNode>>();
        if (!this.adapterService.judgementGroupId(groupKey)) {
            return tree;
        }
        List<IEntityGroup> childrenEntityGroups = this.entityMetaService.getChildrenGroup(groupKey);
        if (!CollectionUtils.isEmpty(childrenEntityGroups)) {
            for (IEntityGroup group : childrenEntityGroups) {
                List<IEntityDefine> entities;
                TreeNode inode = TreeNode.copyFromGroup(group);
                ITree iTree = new ITree((INode)inode);
                List<IEntityGroup> groups = this.entityMetaService.getChildrenGroup(group.getId());
                iTree.setLeaf(CollectionUtils.isEmpty(groups));
                if (iTree.isLeaf() && !CollectionUtils.isEmpty(entities = this.entityMetaService.getEntitiesInGroup(group.getId()))) {
                    if (DIMENSION_MODE.equals(showContent)) {
                        iTree.setLeaf(entities.stream().noneMatch(this::isDimension));
                    } else {
                        iTree.setLeaf(false);
                    }
                }
                if (allPathKeys.contains(iTree.getKey())) {
                    iTree.setChecked(true);
                }
                tree.add((ITree<TreeNode>)iTree);
            }
        }
        return tree;
    }
}

