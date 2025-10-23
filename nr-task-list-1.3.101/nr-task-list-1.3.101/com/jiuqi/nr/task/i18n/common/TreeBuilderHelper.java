/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.facade.IMetaGroup
 *  com.jiuqi.np.definition.facade.IMetaItem
 *  com.jiuqi.nr.task.api.tree.TreeConfig
 *  com.jiuqi.nr.task.api.tree.TreeData
 *  com.jiuqi.nr.task.api.tree.UITreeNode
 *  com.jiuqi.xlib.utils.StringUtil
 */
package com.jiuqi.nr.task.i18n.common;

import com.jiuqi.np.definition.facade.IMetaGroup;
import com.jiuqi.np.definition.facade.IMetaItem;
import com.jiuqi.nr.task.api.tree.TreeConfig;
import com.jiuqi.nr.task.api.tree.TreeData;
import com.jiuqi.nr.task.api.tree.UITreeNode;
import com.jiuqi.xlib.utils.StringUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

public class TreeBuilderHelper {
    public static List<UITreeNode<TreeData>> buildTree(List<UITreeNode<TreeData>> nodes, TreeConfig treeConfig) {
        if (CollectionUtils.isEmpty(nodes)) {
            return Collections.emptyList();
        }
        LinkedHashMap<String, UITreeNode<TreeData>> treeMapTemp = new LinkedHashMap<String, UITreeNode<TreeData>>();
        nodes.stream().sorted(treeConfig.getComparator()).forEachOrdered(e -> treeMapTemp.put(null != e.getKey() ? e.getKey() : "root", (UITreeNode<TreeData>)e));
        UITreeNode rootNode = new UITreeNode();
        for (UITreeNode node : treeMapTemp.values()) {
            if (null == node) continue;
            String parent = node.getParent();
            if (treeMapTemp.containsKey(parent)) {
                UITreeNode parentNode = (UITreeNode)treeMapTemp.get(parent);
                if (null == parentNode) continue;
                TreeBuilderHelper.addChild((UITreeNode<TreeData>)parentNode, (UITreeNode<TreeData>)node, treeConfig, treeMapTemp);
                continue;
            }
            TreeBuilderHelper.addChild((UITreeNode<TreeData>)rootNode, (UITreeNode<TreeData>)node);
            if (!treeConfig.isSelected(node.getKey())) continue;
            node.setSelected(true);
        }
        return rootNode.getChildren();
    }

    public static void addChild(UITreeNode<TreeData> parentNode, UITreeNode<TreeData> node, TreeConfig treeConfig, Map<String, UITreeNode<TreeData>> treeMapTemp) {
        TreeBuilderHelper.addChild(parentNode, node);
        if (treeConfig.isSelected(node.getKey())) {
            TreeBuilderHelper.nodeSelect(node, treeMapTemp);
        }
        parentNode.setLeaf(false);
    }

    public static void nodeSelect(UITreeNode<TreeData> node, Map<String, UITreeNode<TreeData>> treeMapTemp) {
        node.setSelected(true);
        TreeBuilderHelper.nodeExpand(node, treeMapTemp);
    }

    public static void addChild(UITreeNode<TreeData> parentNode, UITreeNode<TreeData> node) {
        ArrayList<UITreeNode<TreeData>> children = parentNode.getChildren();
        if (null == children) {
            children = new ArrayList<UITreeNode<TreeData>>();
            children.add(node);
            parentNode.setChildren(children);
        } else {
            children.add(node);
            children.sort(new TreeConfig().getComparator());
        }
    }

    public static void nodeExpand(UITreeNode<TreeData> node, Map<String, UITreeNode<TreeData>> treeMapTemp) {
        node.setExpand(true);
        String parent = node.getParent();
        UITreeNode<TreeData> parentNode = new UITreeNode<TreeData>();
        if (treeMapTemp.containsKey(parent)) {
            parentNode = treeMapTemp.get(parent);
            parentNode.setExpand(true);
        }
        if (parentNode != null && StringUtils.hasText(parentNode.getParent())) {
            TreeBuilderHelper.nodeExpand(parentNode, treeMapTemp);
        }
    }

    public static UITreeNode<TreeData> buildNode(IMetaGroup group, boolean isGroupTree) {
        UITreeNode treeNode = new UITreeNode();
        treeNode.setKey(group.getKey());
        treeNode.setTitle(group.getTitle());
        if (StringUtil.isEmpty((String)group.getParentKey())) {
            if (isGroupTree) {
                treeNode.setParent("I18N_FORM_GROUP_TREE_ROOT");
            } else {
                treeNode.setParent("I18N_FORM_TREE_ROOT");
            }
        } else {
            treeNode.setParent(group.getParentKey());
        }
        treeNode.setOrder(group.getOrder());
        return treeNode;
    }

    public static UITreeNode<TreeData> buildNode(IMetaItem item, String parentKey) {
        UITreeNode treeNode = new UITreeNode();
        treeNode.setKey(item.getKey());
        treeNode.setParent(parentKey);
        treeNode.setTitle(item.getTitle());
        treeNode.setOrder(item.getOrder());
        return treeNode;
    }
}

