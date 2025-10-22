/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.logic.api.param;

import com.jiuqi.nr.data.logic.facade.param.input.ActionEnum;

public class CheckExeContext {
    private String batchId;
    private ActionEnum actionType;
    private String formSchemeKey;
    private String formulaSchemeKey;

    public ActionEnum getActionType() {
        return this.actionType;
    }

    public void setActionType(ActionEnum actionType) {
        this.actionType = actionType;
    }

    public String getBatchId() {
        return this.batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public String getFormulaSchemeKey() {
        return this.formulaSchemeKey;
    }

    public void setFormulaSchemeKey(String formulaSchemeKey) {
        this.formulaSchemeKey = formulaSchemeKey;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }
}

