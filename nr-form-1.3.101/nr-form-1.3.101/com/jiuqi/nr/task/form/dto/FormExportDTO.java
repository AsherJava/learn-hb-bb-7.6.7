/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.form.dto;

import java.io.Serializable;
import java.util.List;

public class FormExportDTO
implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<String> formKeys;
    private String viewType;
    private String schemeId;
    private String formulaSchemeId;
    private String efdcScheme;
    private String showForm;
    private String downLoadKey;
    private String taskKey;
    private String fileName;

    public List<String> getFormKeys() {
        return this.formKeys;
    }

    public void setFormKeys(List<String> formKeys) {
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

    public String getFormulaSchemeId() {
        return this.formulaSchemeId;
    }

    public void setFormulaSchemeId(String formulaSchemeId) {
        this.formulaSchemeId = formulaSchemeId;
    }

    public String getShowForm() {
        return this.showForm;
    }

    public void setShowForm(String showForm) {
        this.showForm = showForm;
    }

    public String getEfdcScheme() {
        return this.efdcScheme;
    }

    public void setEfdcScheme(String efdcScheme) {
        this.efdcScheme = efdcScheme;
    }

    public String getDownLoadKey() {
        return this.downLoadKey;
    }

    public void setDownLoadKey(String downLoadKey) {
        this.downLoadKey = downLoadKey;
    }

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}

