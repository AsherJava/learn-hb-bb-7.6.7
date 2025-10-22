/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.reportdatasync.vo;

public class ReportDataSyncReceiveLogVO {
    private String id;
    private String taskTitle;
    private String taskId;
    private String taskCode;
    private String syncVersion;
    private String xfTime;
    private String syncTime;
    private Integer syncStatus;
    private String syncUserId;
    private String syncUserName;
    private String compareFileId;
    private String receiveType;
    private String receiveTaskId;
    private Integer available;

    public Integer getAvailable() {
        return this.available;
    }

    public void setAvailable(Integer available) {
        this.available = available;
    }

    public String getReceiveType() {
        return this.receiveType;
    }

    public void setReceiveType(String receiveType) {
        this.receiveType = receiveType;
    }

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

    public Integer getSyncStatus() {
        return this.syncStatus;
    }

    public void setSyncStatus(Integer syncStatus) {
        this.syncStatus = syncStatus;
    }

    public String getSyncUserId() {
        return this.syncUserId;
    }

    public void setSyncUserId(String syncUserId) {
        this.syncUserId = syncUserId;
    }

    public String getSyncUserName() {
        return this.syncUserName;
    }

    public void setSyncUserName(String syncUserName) {
        this.syncUserName = syncUserName;
    }

    public String getCompareFileId() {
        return this.compareFileId;
    }

    public void setCompareFileId(String compareFileId) {
        this.compareFileId = compareFileId;
    }

    public String getSyncVersion() {
        return this.syncVersion;
    }

    public void setSyncVersion(String syncVersion) {
        this.syncVersion = syncVersion;
    }

    public String getTaskCode() {
        return this.taskCode;
    }

    public void setTaskCode(String taskCode) {
        this.taskCode = taskCode;
    }

    public String getReceiveTaskId() {
        return this.receiveTaskId;
    }

    public void setReceiveTaskId(String receiveTaskId) {
        this.receiveTaskId = receiveTaskId;
    }
}

