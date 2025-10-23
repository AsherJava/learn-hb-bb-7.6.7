/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.zb.scheme.service.impl;

import com.jiuqi.nr.zb.scheme.common.NodeType;
import com.jiuqi.nr.zb.scheme.core.ZbGroup;
import com.jiuqi.nr.zb.scheme.core.ZbInfo;
import com.jiuqi.nr.zb.scheme.core.ZbScheme;
import com.jiuqi.nr.zb.scheme.core.ZbSchemeGroup;
import com.jiuqi.nr.zb.scheme.exception.ZbSchemeException;
import com.jiuqi.nr.zb.scheme.internal.tree.INode;
import com.jiuqi.nr.zb.scheme.internal.tree.ITree;
import com.jiuqi.nr.zb.scheme.internal.tree.TreeNodeQueryParam;
import com.jiuqi.nr.zb.scheme.internal.tree.ZbGroupNode;
import com.jiuqi.nr.zb.scheme.internal.tree.ZbInfoNode;
import com.jiuqi.nr.zb.scheme.internal.tree.ZbSchemeGroupNode;
import com.jiuqi.nr.zb.scheme.internal.tree.ZbSchemeNode;
import com.jiuqi.nr.zb.scheme.service.IZbSchemeService;
import com.jiuqi.nr.zb.scheme.service.IZbSchemeTreeService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class ZbSchemeTreeServiceImpl
implements IZbSchemeTreeService {
    @Autowired
    private IZbSchemeService zbSchemeService;
    private static final int GROUP_TYPE = NodeType.ZB_SCHEME_GROUP.getValue() | NodeType.ZB_GROUP.getValue();

    private List<ITree<INode>> getTreeListByType(NodeType type, TreeNodeQueryParam param) {
        if (type == null) {
            return Collections.emptyList();
        }
        switch (type) {
            case ZB_SCHEME_GROUP: {
                return this.getSchemeGroupTreeList(param);
            }
            case ZB_SCHEME: {
                return this.getSchemeTreeList(param);
            }
            case ZB_GROUP: {
                return this.getZbGroupTreeList(param);
            }
            case ZB_INFO: {
                return this.getZbTreeList(param);
            }
        }
        return Collections.emptyList();
    }

    private List<ITree<INode>> getZbTreeList(TreeNodeQueryParam treeNodeQueryParam) {
        List<ITree<INode>> treeList = this.getZbGroupTreeList(treeNodeQueryParam);
        String versionKey = treeNodeQueryParam.getVersionKey();
        List<ZbInfo> zbInfos = this.zbSchemeService.listZbInfoByVersion(versionKey);
        Stream<ITree> stream = zbInfos.stream().map(zbInfo -> {
            ITree<ZbInfoNode> tree = new ITree<ZbInfoNode>(new ZbInfoNode((ZbInfo)zbInfo));
            tree.setLeaf(true);
            tree.setIcons(new String[]{"#icon14_SHU_A_NW_tubiaozhongji"});
            return tree;
        });
        treeList.addAll(stream.collect(Collectors.toList()));
        return treeList;
    }

    private List<ITree<INode>> getZbGroupTreeList(TreeNodeQueryParam treeNodeQueryParam) {
        String schemeKey = treeNodeQueryParam.getSchemeKey();
        String versionKey = treeNodeQueryParam.getVersionKey();
        Assert.notNull((Object)schemeKey, "The schemeKey must not be null");
        Assert.notNull((Object)versionKey, "The versionKey must not be null");
        List<ZbGroup> list = this.zbSchemeService.listZbGroupByVersion(versionKey);
        Stream<ITree> stream = list.stream().map(d -> {
            ITree<ZbGroupNode> tree = new ITree<ZbGroupNode>(new ZbGroupNode((ZbGroup)d));
            tree.setLeaf(true);
            tree.setIcons(new String[]{"#icon14_SHU_A_NW_tubiaokezhankai"});
            return tree;
        });
        return stream.collect(Collectors.toList());
    }

    private List<ITree<INode>> getSchemeTreeList(TreeNodeQueryParam treeNodeQueryParam) {
        List<ITree<INode>> treeList = this.getSchemeGroupTreeList(treeNodeQueryParam);
        this.zbSchemeService.listAllZbScheme().forEach(d -> treeList.add(new ITree<ZbSchemeNode>(new ZbSchemeNode((ZbScheme)d))));
        return treeList;
    }

    private List<ITree<INode>> getSchemeGroupTreeList(TreeNodeQueryParam treeNodeQueryParam) {
        List<ZbSchemeGroup> list = this.zbSchemeService.listAllZbSchemeGroup();
        return list.stream().map(d -> new ITree<ZbSchemeGroupNode>(new ZbSchemeGroupNode((ZbSchemeGroup)d))).collect(Collectors.toList());
    }

    @Override
    public List<ITree<INode>> queryZbSchemeGroupTree(TreeNodeQueryParam queryParam) {
        if (queryParam == null || queryParam.getType() == null) {
            return Collections.emptyList();
        }
        NodeType type = queryParam.getType();
        List<ITree<INode>> treeList = this.getTreeListByType(type, queryParam);
        ZbSchemeGroupNode rootNode = new ZbSchemeGroupNode();
        rootNode.setKey("00000000-0000-0000-0000-000000000000");
        rootNode.setTitle("\u5168\u90e8\u6307\u6807\u4f53\u7cfb");
        ITree<INode> root = new ITree<INode>(rootNode);
        return this.buildTree(root, treeList, queryParam);
    }

    @Override
    public List<ITree<INode>> queryZbGroupTree(TreeNodeQueryParam queryParam) {
        if (queryParam == null || queryParam.getType() == null) {
            return Collections.emptyList();
        }
        NodeType type = queryParam.getType();
        List<ITree<INode>> treeList = this.getTreeListByType(type, queryParam);
        String schemeKey = queryParam.getSchemeKey();
        ZbScheme zbScheme = this.zbSchemeService.getZbScheme(schemeKey);
        if (zbScheme == null) {
            throw new ZbSchemeException(String.format("\u6307\u6807\u4f53\u7cfb[%s]\u4e0d\u5b58\u5728", schemeKey));
        }
        ZbGroupNode rootNode = new ZbGroupNode();
        rootNode.setKey("00000000-0000-0000-0000-000000000000");
        rootNode.setTitle(zbScheme.getTitle());
        ITree<INode> root = new ITree<INode>(rootNode);
        return this.buildTree(root, treeList, queryParam);
    }

    private List<ITree<INode>> buildTree(ITree<INode> root, List<ITree<INode>> treeList, TreeNodeQueryParam queryParam) {
        LinkedHashMap<String, ITree<INode>> treeMap = new LinkedHashMap<String, ITree<INode>>(treeList.size());
        for (ITree<INode> tree : treeList) {
            treeMap.put(tree.getKey(), tree);
        }
        String keyword = null;
        if (StringUtils.hasLength(queryParam.getKeyword())) {
            keyword = queryParam.getKeyword().toUpperCase();
        }
        HashSet<String> keywordSet = new HashSet<String>();
        Predicate<INode> disabledPredicate = queryParam.getNodeDisabled();
        Predicate<INode> filterPredicate = queryParam.getNodeFilter();
        Iterator iterator = treeMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = iterator.next();
            ITree tree = (ITree)entry.getValue();
            Object node = tree.getData();
            ITree<INode> parent = treeMap.getOrDefault(node.getParentKey(), root);
            if (!filterPredicate.test((INode)node) || parent == null) {
                iterator.remove();
                continue;
            }
            tree.setDisabled(disabledPredicate.test((INode)node));
            if (keyword != null) {
                if (node.getTitle().contains(keyword)) {
                    keywordSet.add(node.getKey());
                }
                if (node.getNodeType() == NodeType.ZB_INFO && node.getCode().contains(keyword)) {
                    keywordSet.add(node.getKey());
                }
            }
            parent.appendChild(tree);
            parent.setLeaf(false);
            if (tree.getData().getNodeType() != NodeType.ZB_GROUP) continue;
            this.setGroupPos(parent.getChildren());
        }
        if (queryParam.isRemoveEmptyGroup()) {
            this.pruning(root.getChildren(), queryParam, keywordSet);
        }
        if (keyword != null) {
            return this.keywordList(treeMap, keywordSet);
        }
        String location = queryParam.getLocation();
        if (treeMap.containsKey(location)) {
            this.locationTree(treeMap, location, location, queryParam.isCheckChildren());
        } else {
            root.setExpanded(true);
            root.setSelected(true);
        }
        return Collections.singletonList(root);
    }

    private List<ITree<INode>> keywordList(Map<String, ITree<INode>> treeMap, Set<String> keywordSet) {
        Iterator<Map.Entry<String, ITree<INode>>> iterator = treeMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, ITree<INode>> entry = iterator.next();
            ITree<INode> tree = entry.getValue();
            if (keywordSet.contains(tree.getKey())) continue;
            iterator.remove();
        }
        return new ArrayList<ITree<INode>>(treeMap.values());
    }

    private void pruning(List<ITree<INode>> trees, TreeNodeQueryParam queryParam, Set<String> keywordSet) {
        if (CollectionUtils.isEmpty(trees)) {
            return;
        }
        Iterator<ITree<INode>> iterator = trees.iterator();
        while (iterator.hasNext()) {
            ITree<INode> tree = iterator.next();
            if ((tree.getData().getNodeType().getValue() & GROUP_TYPE) == 0) continue;
            if (tree.hasChildren()) {
                this.pruning(tree.getChildren(), queryParam, keywordSet);
                continue;
            }
            iterator.remove();
            keywordSet.remove(tree.getKey());
        }
    }

    private void setGroupPos(List<ITree<INode>> children) {
        for (int i = 0; i < children.size(); ++i) {
            ZbGroupNode node = (ZbGroupNode)children.get(i).getData();
            node.setFirst(i == 0);
            node.setLast(i == children.size() - 1);
        }
    }

    private void locationTree(Map<String, ITree<INode>> treeMap, String current, String location, boolean checkChildren) {
        while (treeMap.containsKey(current)) {
            ITree<INode> parent;
            ITree<INode> tree = treeMap.get(current);
            if (tree.getKey().equals(location)) {
                tree.setSelected(!tree.isDisabled());
            }
            if ((parent = tree.getParent()) != null) {
                parent.setExpanded(true);
                current = parent.getKey();
                continue;
            }
            current = null;
        }
    }

    private void locationTree(ITree<INode> root, String location, boolean checkChildren) {
        if (root == null) {
            return;
        }
        ITree<INode> parent = root.getParent();
        if (parent != null) {
            parent.setExpanded(true);
        }
        if (location.equals(root.getKey())) {
            root.setChecked(!root.isDisabled());
            root.setSelected(true);
            if (checkChildren) {
                this.checkChildren(root);
            }
        }
        this.locationTree(root.getParent(), location, checkChildren);
    }

    private void checkChildren(ITree<INode> root) {
        if (root.hasChildren()) {
            root.setExpanded(true);
            List<ITree<INode>> children = root.getChildren();
            for (ITree<INode> child : children) {
                child.setChecked(true);
                this.checkChildren(child);
            }
        }
    }

    @Override
    public List<ITree<INode>> filterZbInfo(TreeNodeQueryParam queryParam) {
        NodeType type = queryParam.getType();
        if (type == null) {
            queryParam.setType(NodeType.ZB_INFO);
        }
        return this.queryZbGroupTree(queryParam);
    }
}

