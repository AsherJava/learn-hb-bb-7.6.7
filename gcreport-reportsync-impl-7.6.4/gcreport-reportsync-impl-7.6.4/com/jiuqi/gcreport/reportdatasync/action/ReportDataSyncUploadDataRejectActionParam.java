/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.reportdatasync.action;

class ReportDataSyncUploadDataRejectActionParam {
    private String orgCode;
    private String taskTitle;
    private String taskId;
    private String periodStr;
    private String selectAdjust;
    private String rejectMsg;
    private String orgType;

    ReportDataSyncUploadDataRejectActionParam() {
    }

    public String getOrgCode() {
        return this.orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getTaskTitle() {
        return this.taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public String getPeriodStr() {
        return this.periodStr;
    }

    public void setPeriodStr(String periodStr) {
        this.periodStr = periodStr;
    }

    public String getRejectMsg() {
        return this.rejectMsg;
    }

    public void setRejectMsg(String rejectMsg) {
        this.rejectMsg = rejectMsg;
    }

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getOrgType() {
        return this.orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public String getSelectAdjust() {
        return this.selectAdjust;
    }

    public void setSelectAdjust(String selectAdjust) {
        this.selectAdjust = selectAdjust;
    }
}

