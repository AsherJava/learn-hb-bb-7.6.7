/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataentry.bean;

public class DataEntryInitParam {
    private String functionCode;
    private String taskKey;
    private String periodInfo;
    private String catchePeriodInfo;
    private String formSchemeKey;

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public String getCatchePeriodInfo() {
        return this.catchePeriodInfo;
    }

    public void setCatchePeriodInfo(String catchePeriodInfo) {
        this.catchePeriodInfo = catchePeriodInfo;
    }

    public String getFunctionCode() {
        return this.functionCode;
    }

    public void setFunctionCode(String functionCode) {
        this.functionCode = functionCode;
    }

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public String getPeriodInfo() {
        return this.periodInfo;
    }

    public void setPeriodInfo(String periodInfo) {
        this.periodInfo = periodInfo;
    }
}

