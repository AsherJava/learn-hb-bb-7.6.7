/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.reportdatasync.vo;

public class ReportDataSyncUploadLogVO {
    private String id;
    private String syncVersion;
    private String uploadSchemeId;
    private String periodStr;
    private String periodValue;
    private String taskId;
    private String taskTitle;
    private String taskCode;
    private String syncDataAttachId;
    private String uploadUsername;
    private String uploadUserId;
    private String uploadTime;
    private String uploadStatus;
    private String uploadMsg;
    private String loadStatus;
    private String loadMsg;
    private Boolean showProgress;
    private Double progress;
    private String orgType;
    private String orgTypeTitle;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSyncVersion() {
        return this.syncVersion;
    }

    public void setSyncVersion(String syncVersion) {
        this.syncVersion = syncVersion;
    }

    public String getUploadSchemeId() {
        return this.uploadSchemeId;
    }

    public void setUploadSchemeId(String uploadSchemeId) {
        this.uploadSchemeId = uploadSchemeId;
    }

    public String getPeriodStr() {
        return this.periodStr;
    }

    public void setPeriodStr(String periodStr) {
        this.periodStr = periodStr;
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

    public String getTaskCode() {
        return this.taskCode;
    }

    public void setTaskCode(String taskCode) {
        this.taskCode = taskCode;
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

    public String getPeriodValue() {
        return this.periodValue;
    }

    public void setPeriodValue(String periodValue) {
        this.periodValue = periodValue;
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
}

