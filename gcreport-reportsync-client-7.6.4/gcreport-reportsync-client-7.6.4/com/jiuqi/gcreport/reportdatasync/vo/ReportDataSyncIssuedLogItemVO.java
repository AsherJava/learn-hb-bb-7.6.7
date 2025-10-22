/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.reportdatasync.vo;

public class ReportDataSyncIssuedLogItemVO {
    private String index;
    private String id;
    private String xfTaskId;
    private String orgTitle;
    private Boolean status;
    private Boolean notSynchronized;
    private String msg;
    private String syncTime;
    private String syncUserName;
    private String taskTitle;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getXfTaskId() {
        return this.xfTaskId;
    }

    public void setXfTaskId(String xfTaskId) {
        this.xfTaskId = xfTaskId;
    }

    public String getOrgTitle() {
        return this.orgTitle;
    }

    public void setOrgTitle(String orgTitle) {
        this.orgTitle = orgTitle;
    }

    public Boolean getStatus() {
        return this.status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getSyncTime() {
        return this.syncTime;
    }

    public void setSyncTime(String syncTime) {
        this.syncTime = syncTime;
    }

    public String getSyncUserName() {
        return this.syncUserName;
    }

    public void setSyncUserName(String syncUserName) {
        this.syncUserName = syncUserName;
    }

    public String getIndex() {
        return this.index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getTaskTitle() {
        return this.taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public Boolean getNotSynchronized() {
        return this.notSynchronized;
    }

    public void setNotSynchronized(Boolean notSynchronized) {
        this.notSynchronized = notSynchronized;
    }
}

