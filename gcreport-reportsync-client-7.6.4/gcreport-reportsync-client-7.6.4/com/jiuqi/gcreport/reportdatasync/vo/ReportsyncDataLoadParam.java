/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.reportdatasync.vo;

public class ReportsyncDataLoadParam {
    private String periodStr;
    private String adjustCode;
    private String taskCode;
    private String loadStatus;
    private String loadMsg;
    private String id;

    public String getPeriodStr() {
        return this.periodStr;
    }

    public void setPeriodStr(String periodStr) {
        this.periodStr = periodStr;
    }

    public String getTaskCode() {
        return this.taskCode;
    }

    public void setTaskCode(String taskCode) {
        this.taskCode = taskCode;
    }

    public String getLoadStatus() {
        return this.loadStatus;
    }

    public void setLoadStatus(String loadStatus) {
        this.loadStatus = loadStatus;
    }

    public String getLoadMsg() {
        return this.loadMsg;
    }

    public void setLoadMsg(String loadMsg) {
        this.loadMsg = loadMsg;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAdjustCode() {
        return this.adjustCode;
    }

    public void setAdjustCode(String adjustCode) {
        this.adjustCode = adjustCode;
    }
}

