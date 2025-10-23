/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.summary.utils;

import com.jiuqi.nr.summary.vo.vue2.TreeNode;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.util.CollectionUtils;

public class ITreeConvert {
    public static TreeNode fromTreeNode(com.jiuqi.nr.summary.tree.core.TreeNode treeNode) {
        TreeNode treeNode1 = new TreeNode();
        treeNode1.setKey(treeNode.getKey());
        treeNode1.setCode(treeNode.getCode());
        treeNode1.setTitle(treeNode.getTitle());
        treeNode1.setLeaf(treeNode.isLeaf());
        treeNode1.setIcons(treeNode.getIcon());
        treeNode1.setExpanded(treeNode.isExpand());
        treeNode1.setChecked(treeNode.isChecked());
        treeNode1.setSelected(treeNode.isSelected());
        treeNode1.setDisabled(treeNode.isDisabled());
        treeNode1.setNoDrag(treeNode.isDraggable());
        List<com.jiuqi.nr.summary.tree.core.TreeNode> children = treeNode.getChildren();
        if (!CollectionUtils.isEmpty(children)) {
            List<TreeNode> children1 = children.stream().map(ITreeConvert::fromTreeNode).collect(Collectors.toList());
            treeNode1.setChildren(children1);
        }
        treeNode1.setData(treeNode.getData());
        return treeNode1;
    }
}

