/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.formulaeditor.dto;

import com.jiuqi.nr.formulaeditor.dto.EditObject;
import com.jiuqi.nr.formulaeditor.dto.FormulaObj;

public class EditorFormula {
    private EditObject editObject;
    private FormulaObj formulaObj;

    public EditObject getEditObject() {
        return this.editObject;
    }

    public void setEditObject(EditObject editObject) {
        this.editObject = editObject;
    }

    public FormulaObj getFormulaObj() {
        return this.formulaObj;
    }

    public void setFormulaObj(FormulaObj formulaObj) {
        this.formulaObj = formulaObj;
    }
}

