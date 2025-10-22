/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.annotation.JsonSerialize
 *  com.jiuqi.np.util.tree.Tree
 */
package com.jiuqi.nr.designer.web.facade.formuladesigner;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jiuqi.np.util.tree.Tree;
import com.jiuqi.nr.designer.web.facade.formuladesigner.FormTreeItem;
import com.jiuqi.nr.designer.web.facade.formuladesigner.FormulaDesingerTreeJsonSerializer;

@JsonSerialize(using=FormulaDesingerTreeJsonSerializer.class)
public class FormTree {
    private Tree<FormTreeItem> tree;

    public Tree<FormTreeItem> getTree() {
        return this.tree;
    }

    public void setTree(Tree<FormTreeItem> tree) {
        this.tree = tree;
    }
}

