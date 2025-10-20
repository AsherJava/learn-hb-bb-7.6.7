/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.conversion.common;

import java.util.List;

public class GcConversionWorkPaperEnv {
    private boolean isSegment;
    private String rateSchemeCode;
    private String taskId;
    private String schemeId;
    private String formId;
    private String orgId;
    private String orgTypeId;
    private String orgType;
    private List<String> fieldKeys;
    private String periodStr;
    private String orgVersionType;
    private String beforeCurrencyCode;
    private String selectAdjustCode;
    private Boolean isFloat;

    public Boolean getFloat() {
        return this.isFloat;
    }

    public void setFloat(Boolean aFloat) {
        this.isFloat = aFloat;
    }

    public boolean getIsSegment() {
        return this.isSegment;
    }

    public void setIsSegment(boolean isSegment) {
        this.isSegment = isSegment;
    }

    public String getBeforeCurrencyCode() {
        return this.beforeCurrencyCode;
    }

    public void setBeforeCurrencyCode(String beforeCurrencyCode) {
        this.beforeCurrencyCode = beforeCurrencyCode;
    }

    public String getOrgVersionType() {
        return this.orgVersionType;
    }

    public void setOrgVersionType(String orgVersionType) {
        this.orgVersionType = orgVersionType;
    }

    public String getPeriodStr() {
        return this.periodStr;
    }

    public void setPeriodStr(String periodStr) {
        this.periodStr = periodStr;
    }

    public List<String> getFieldKeys() {
        return this.fieldKeys;
    }

    public void setFieldKeys(List<String> fieldKeys) {
        this.fieldKeys = fieldKeys;
    }

    public boolean isSegment() {
        return this.isSegment;
    }

    public void setSegment(boolean segment) {
        this.isSegment = segment;
    }

    public String getRateSchemeCode() {
        return this.rateSchemeCode;
    }

    public void setRateSchemeCode(String rateSchemeCode) {
        this.rateSchemeCode = rateSchemeCode;
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

    public String getOrgId() {
        return this.orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getOrgType() {
        return this.orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public String getOrgTypeId() {
        return this.orgTypeId;
    }

    public void setOrgTypeId(String orgTypeId) {
        this.orgTypeId = orgTypeId;
    }

    public String getSelectAdjustCode() {
        return this.selectAdjustCode;
    }

    public void setSelectAdjustCode(String selectAdjustCode) {
        this.selectAdjustCode = selectAdjustCode;
    }
}

