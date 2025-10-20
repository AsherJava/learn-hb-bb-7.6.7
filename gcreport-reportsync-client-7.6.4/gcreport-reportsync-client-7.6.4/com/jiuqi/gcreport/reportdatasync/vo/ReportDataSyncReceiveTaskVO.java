/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.reportdatasync.vo;

public class ReportDataSyncReceiveTaskVO {
    private String id;
    private String taskTitle;
    private String taskId;
    private String taskCode;
    private String syncVersion;
    private String syncParamAttachId;
    private String syncDesAttachId;
    private String syncDesAttachTitle;
    private String receiveType;
    private String xfTime;
    private String syncTime;
    private Integer syncStatus;
    private Integer available;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTaskTitle() {
        return this.taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getSyncParamAttachId() {
        return this.syncParamAttachId;
    }

    public void setSyncParamAttachId(String syncParamAttachId) {
        this.syncParamAttachId = syncParamAttachId;
    }

    public String getSyncDesAttachId() {
        return this.syncDesAttachId;
    }

    public void setSyncDesAttachId(String syncDesAttachId) {
        this.syncDesAttachId = syncDesAttachId;
    }

    public String getXfTime() {
        return this.xfTime;
    }

    public void setXfTime(String xfTime) {
        this.xfTime = xfTime;
    }

    public String getSyncTime() {
        return this.syncTime;
    }

    public void setSyncTime(String syncTime) {
        this.syncTime = syncTime;
    }

    public String getSyncVersion() {
        return this.syncVersion;
    }

    public void setSyncVersion(String syncVersion) {
        this.syncVersion = syncVersion;
    }

    public String getSyncDesAttachTitle() {
        return this.syncDesAttachTitle;
    }

    public void setSyncDesAttachTitle(String syncDesAttachTitle) {
        this.syncDesAttachTitle = syncDesAttachTitle;
    }

    public String getTaskCode() {
        return this.taskCode;
    }

    public void setTaskCode(String taskCode) {
        this.taskCode = taskCode;
    }

    public String getReceiveType() {
        return this.receiveType;
    }

    public void setReceiveType(String receiveType) {
        this.receiveType = receiveType;
    }

    public Integer getSyncStatus() {
        return this.syncStatus;
    }

    public void setSyncStatus(Integer syncStatus) {
        this.syncStatus = syncStatus;
    }

    public Integer getAvailable() {
        return this.available;
    }

    public void setAvailable(Integer available) {
        this.available = available;
    }
}

