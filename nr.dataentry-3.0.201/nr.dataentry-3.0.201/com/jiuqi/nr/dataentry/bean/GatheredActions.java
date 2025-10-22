/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.annotation.JsonSerialize
 *  com.jiuqi.np.util.tree.Tree
 */
package com.jiuqi.nr.dataentry.bean;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jiuqi.np.util.tree.Tree;
import com.jiuqi.nr.dataentry.gather.ActionItem;
import com.jiuqi.nr.dataentry.gather.TreeJsonSerializer;

@JsonSerialize(using=TreeJsonSerializer.class)
public class GatheredActions {
    private Tree<ActionItem> tree;

    public Tree<ActionItem> getNode() {
        return this.tree;
    }

    public void setNode(Tree<ActionItem> node) {
        this.tree = node;
    }
}

