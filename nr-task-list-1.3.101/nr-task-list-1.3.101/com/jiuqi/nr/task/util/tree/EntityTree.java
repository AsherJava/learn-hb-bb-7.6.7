/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.annotation.JsonSerialize
 *  com.jiuqi.nr.common.itree.INode
 *  com.jiuqi.nr.common.itree.ITree
 *  com.jiuqi.nr.entity.component.tree.vo.TreeNode
 */
package com.jiuqi.nr.task.util.tree;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jiuqi.nr.common.itree.INode;
import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.entity.component.tree.vo.TreeNode;
import com.jiuqi.nr.task.util.tree.EntityTreeSerializer;
import java.util.List;

@JsonSerialize(using=EntityTreeSerializer.class)
public class EntityTree
extends ITree<TreeNode> {
    public EntityTree(ITree tree) {
        this.setTitle(tree.getTitle());
        this.setKey(tree.getKey());
        this.setLeaf(tree.isLeaf());
        this.setChecked(tree.isChecked());
        this.setExpanded(tree.isExpanded());
        this.setSelected(tree.isSelected());
        this.setLevel(tree.getLevel());
        this.setIcons(tree.getIcons());
        this.setDisabled(tree.isDisabled());
        this.setData((INode)((TreeNode)tree.getData()));
    }

    public <T extends ITree<TreeNode>> void setChildren1(List<T> children) {
        super.setChildren(children);
    }
}

