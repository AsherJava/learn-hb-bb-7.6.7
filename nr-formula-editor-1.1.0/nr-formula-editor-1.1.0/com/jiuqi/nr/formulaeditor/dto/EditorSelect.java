/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.formulaeditor.dto;

import com.jiuqi.nr.formulaeditor.dto.EditObject;
import com.jiuqi.nr.formulaeditor.dto.ParamObj;

public class EditorSelect {
    private EditObject editObject;
    private ParamObj paramObj;

    public EditObject getEditObject() {
        return this.editObject;
    }

    public void setEditObject(EditObject editObject) {
        this.editObject = editObject;
    }

    public ParamObj getParamObj() {
        return this.paramObj;
    }

    public void setParamObj(ParamObj paramObj) {
        this.paramObj = paramObj;
    }
}

