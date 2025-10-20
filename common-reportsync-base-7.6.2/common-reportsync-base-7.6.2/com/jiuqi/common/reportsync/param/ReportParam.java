/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.common.reportsync.param;

import java.util.List;

public class ReportParam {
    private String taskId;
    private List<String> schemeIds;
    private List<String> formKeys;
    private List<String> formulaSchemes;
    private List<String> efdcSchemes;

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public List<String> getSchemeIds() {
        return this.schemeIds;
    }

    public void setSchemeIds(List<String> schemeIds) {
        this.schemeIds = schemeIds;
    }

    public List<String> getFormulaSchemes() {
        return this.formulaSchemes;
    }

    public void setFormulaSchemes(List<String> formulaSchemes) {
        this.formulaSchemes = formulaSchemes;
    }

    public List<String> getEfdcSchemes() {
        return this.efdcSchemes;
    }

    public void setEfdcSchemes(List<String> efdcSchemes) {
        this.efdcSchemes = efdcSchemes;
    }

    public List<String> getFormKeys() {
        return this.formKeys;
    }

    public void setFormKeys(List<String> formKeys) {
        this.formKeys = formKeys;
    }
}

