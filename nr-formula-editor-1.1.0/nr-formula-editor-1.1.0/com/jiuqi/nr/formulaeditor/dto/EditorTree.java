/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.formulaeditor.dto;

import com.jiuqi.nr.formulaeditor.dto.EditObject;
import com.jiuqi.nr.formulaeditor.vo.TreeObj;

public class EditorTree {
    private EditObject editObject;
    private TreeObj treeObj;

    public EditObject getEditObject() {
        return this.editObject;
    }

    public void setEditObject(EditObject editObject) {
        this.editObject = editObject;
    }

    public TreeObj getTreeObj() {
        return this.treeObj;
    }

    public void setTreeObj(TreeObj treeObj) {
        this.treeObj = treeObj;
    }
}

