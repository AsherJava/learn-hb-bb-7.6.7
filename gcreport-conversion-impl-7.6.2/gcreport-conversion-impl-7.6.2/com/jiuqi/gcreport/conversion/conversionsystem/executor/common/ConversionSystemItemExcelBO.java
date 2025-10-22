/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.conversion.conversionsystem.executor.common;

public class ConversionSystemItemExcelBO {
    private String taskId;
    private String schemeId;
    private String formId;
    private String regionId;
    private String indexId;
    private String rateTypeTitle;
    private String rateFormula;

    public ConversionSystemItemExcelBO(String taskId, String schemeId, String formId, String regionId, String indexId, String rateTypeTitle, String rateFormula) {
        this.taskId = taskId;
        this.schemeId = schemeId;
        this.formId = formId;
        this.regionId = regionId;
        this.indexId = indexId;
        this.rateTypeTitle = rateTypeTitle;
        this.rateFormula = rateFormula;
    }

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getSchemeId() {
        return this.schemeId;
    }

    public void setSchemeId(String schemeId) {
        this.schemeId = schemeId;
    }

    public String getFormId() {
        return this.formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public String getIndexId() {
        return this.indexId;
    }

    public void setIndexId(String indexId) {
        this.indexId = indexId;
    }

    public String getRateTypeTitle() {
        return this.rateTypeTitle;
    }

    public void setRateTypeTitle(String rateTypeTitle) {
        this.rateTypeTitle = rateTypeTitle;
    }

    public String getRateFormula() {
        return this.rateFormula;
    }

    public void setRateFormula(String rateFormula) {
        this.rateFormula = rateFormula;
    }

    public String getRegionId() {
        return this.regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }
}

