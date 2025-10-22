/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.inputdata.dto;

public class MergeCalcFilterCondition {
    private String orgId;
    private String orgType;
    private String taskId;
    private String schemeId;
    private String periodStr;
    private String currency;
    private String selectAdjustCode;

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

    public String getPeriodStr() {
        return this.periodStr;
    }

    public void setPeriodStr(String periodStr) {
        this.periodStr = periodStr;
    }

    public String getCurrency() {
        return this.currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getSelectAdjustCode() {
        return this.selectAdjustCode;
    }

    public void setSelectAdjustCode(String selectAdjustCode) {
        this.selectAdjustCode = selectAdjustCode;
    }

    public String toString() {
        return "MergeCalcFilterCondition{orgId='" + this.orgId + '\'' + ", orgType='" + this.orgType + '\'' + ", taskId='" + this.taskId + '\'' + ", schemeId='" + this.schemeId + '\'' + ", periodStr='" + this.periodStr + '\'' + ", currency='" + this.currency + '\'' + ", adjustCode='" + this.selectAdjustCode + '\'' + '}';
    }
}

