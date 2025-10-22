/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.designer.web.facade;

import com.jiuqi.nr.designer.web.facade.FormulaObj;

public class UnitSelectorObj {
    private String taskkey;
    private String formSchemeKey;
    private String runType;
    private String unit;
    private String formulaSchemeKey;
    private String currentFormId;
    private FormulaObj[] formulaObjs;

    public FormulaObj[] getFormulaObjs() {
        return this.formulaObjs;
    }

    public void setFormulaObjs(FormulaObj[] formulaObjs) {
        this.formulaObjs = formulaObjs;
    }

    public String getUnit() {
        return this.unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getFormulaSchemeKey() {
        return this.formulaSchemeKey;
    }

    public void setFormulaSchemeKey(String formulaSchemeKey) {
        this.formulaSchemeKey = formulaSchemeKey;
    }

    public String getCurrentFormId() {
        return this.currentFormId;
    }

    public void setCurrentFormId(String currentFormId) {
        this.currentFormId = currentFormId;
    }

    public String getTaskkey() {
        return this.taskkey;
    }

    public void setTaskkey(String taskkey) {
        this.taskkey = taskkey;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public String getRunType() {
        return this.runType;
    }

    public void setRunType(String runType) {
        this.runType = runType;
    }
}

