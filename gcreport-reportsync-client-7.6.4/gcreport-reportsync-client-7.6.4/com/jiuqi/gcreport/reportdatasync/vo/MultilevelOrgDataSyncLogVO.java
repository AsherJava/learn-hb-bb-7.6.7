/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.reportdatasync.vo;

public class MultilevelOrgDataSyncLogVO {
    private String syncLogId;
    private String periodStr;
    private String periodValue;
    private String taskId;
    private String taskTitle;
    private String syncDataAttachId;
    private String uploadUsername;
    private String uploadUserId;
    private String uploadTime;
    private String uploadStatus;
    private String uploadMsg;
    private String loadStatus;
    private String loadMsg;
    private String orgType;
    private String orgTypeTitle;
    private Boolean showProgress;
    private Double progress;

    public String getSyncLogId() {
        return this.syncLogId;
    }

    public void setSyncLogId(String syncLogId) {
        this.syncLogId = syncLogId;
    }

    public String getPeriodStr() {
        return this.periodStr;
    }

    public void setPeriodStr(String periodStr) {
        this.periodStr = periodStr;
    }

    public String getPeriodValue() {
        return this.periodValue;
    }

    public void setPeriodValue(String periodValue) {
        this.periodValue = periodValue;
    }

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskTitle() {
        return this.taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public String getSyncDataAttachId() {
        return this.syncDataAttachId;
    }

    public void setSyncDataAttachId(String syncDataAttachId) {
        this.syncDataAttachId = syncDataAttachId;
    }

    public String getUploadUsername() {
        return this.uploadUsername;
    }

    public void setUploadUsername(String uploadUsername) {
        this.uploadUsername = uploadUsername;
    }

    public String getUploadUserId() {
        return this.uploadUserId;
    }

    public void setUploadUserId(String uploadUserId) {
        this.uploadUserId = uploadUserId;
    }

    public String getUploadTime() {
        return this.uploadTime;
    }

    public void setUploadTime(String uploadTime) {
        this.uploadTime = uploadTime;
    }

    public String getUploadStatus() {
        return this.uploadStatus;
    }

    public void setUploadStatus(String uploadStatus) {
        this.uploadStatus = uploadStatus;
    }

    public String getUploadMsg() {
        return this.uploadMsg;
    }

    public void setUploadMsg(String uploadMsg) {
        this.uploadMsg = uploadMsg;
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

    public String getOrgType() {
        return this.orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public String getOrgTypeTitle() {
        return this.orgTypeTitle;
    }

    public void setOrgTypeTitle(String orgTypeTitle) {
        this.orgTypeTitle = orgTypeTitle;
    }

    public Boolean getShowProgress() {
        return this.showProgress;
    }

    public void setShowProgress(Boolean showProgress) {
        this.showProgress = showProgress;
    }

    public Double getProgress() {
        return this.progress;
    }

    public void setProgress(Double progress) {
        this.progress = progress;
    }
}

