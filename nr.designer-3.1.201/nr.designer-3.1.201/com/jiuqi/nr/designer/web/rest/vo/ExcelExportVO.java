/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.designer.web.rest.vo;

import java.io.Serializable;

public class ExcelExportVO
implements Serializable {
    private static final long serialVersionUID = 1L;
    String[] formKeys;
    String viewType;
    String schemeId;
    String formulaSchemeId;
    String showForm;
    String efdcScheme;
    String downLoadKey;
    String taskKey;

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public String getDownLoadKey() {
        return this.downLoadKey;
    }

    public void setDownLoadKey(String downLoadKey) {
        this.downLoadKey = downLoadKey;
    }

    public String getEfdcScheme() {
        return this.efdcScheme;
    }

    public void setEfdcScheme(String efdcScheme) {
        this.efdcScheme = efdcScheme;
    }

    public String getShowForm() {
        return this.showForm;
    }

    public void setShowForm(String showForm) {
        this.showForm = showForm;
    }

    public String getFormulaSchemeId() {
        return this.formulaSchemeId;
    }

    public void setFormulaSchemeId(String formulaSchemeId) {
        this.formulaSchemeId = formulaSchemeId;
    }

    public String[] getFormKeys() {
        return this.formKeys;
    }

    public void setFormKeys(String[] formKeys) {
        this.formKeys = formKeys;
    }

    public String getViewType() {
        return this.viewType;
    }

    public void setViewType(String viewType) {
        this.viewType = viewType;
    }

    public String getSchemeId() {
        return this.schemeId;
    }

    public void setSchemeId(String schemeId) {
        this.schemeId = schemeId;
    }
}

