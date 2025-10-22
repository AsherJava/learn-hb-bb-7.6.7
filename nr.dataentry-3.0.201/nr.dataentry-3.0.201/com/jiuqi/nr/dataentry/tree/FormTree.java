/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.annotation.JsonSerialize
 *  com.jiuqi.np.util.tree.Tree
 */
package com.jiuqi.nr.dataentry.tree;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jiuqi.np.util.tree.Tree;
import com.jiuqi.nr.dataentry.tree.FormTreeItem;
import com.jiuqi.nr.dataentry.tree.TreeJsonSerializer;

@JsonSerialize(using=TreeJsonSerializer.class)
public class FormTree {
    private Tree<FormTreeItem> tree;

    public Tree<FormTreeItem> getTree() {
        return this.tree;
    }

    public void setTree(Tree<FormTreeItem> tree) {
        this.tree = tree;
    }
}

