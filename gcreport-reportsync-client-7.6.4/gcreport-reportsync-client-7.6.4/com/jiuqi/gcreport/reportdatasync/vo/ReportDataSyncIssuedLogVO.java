/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.reportdatasync.vo;

public class ReportDataSyncIssuedLogVO {
    private String id;
    private String syncVersion;
    private String taskTitle;
    private String taskId;
    private String taskCode;
    private String syncParamAttachId;
    private String syncDesAttachId;
    private String syncDesAttachTitle;
    private String paramStatus;
    private String paramStatusTitle;
    private Integer syncStatus;
    private Integer syncSuccess;
    private Integer syncFailed;
    private Integer notSync;
    private String syncStatusTitle;
    private String syncTime;
    private String syncUserId;
    private String syncUserName;
    private Boolean showProgress;
    private Double progress;
    private String content;

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

    public Integer getSyncStatus() {
        return this.syncStatus;
    }

    public void setSyncStatus(Integer syncStatus) {
        this.syncStatus = syncStatus;
    }

    public String getSyncStatusTitle() {
        return this.syncStatusTitle;
    }

    public void setSyncStatusTitle(String syncStatusTitle) {
        this.syncStatusTitle = syncStatusTitle;
    }

    public String getSyncTime() {
        return this.syncTime;
    }

    public void setSyncTime(String syncTime) {
        this.syncTime = syncTime;
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

    public String getParamStatus() {
        return this.paramStatus;
    }

    public void setParamStatus(String paramStatus) {
        this.paramStatus = paramStatus;
    }

    public String getParamStatusTitle() {
        return this.paramStatusTitle;
    }

    public void setParamStatusTitle(String paramStatusTitle) {
        this.paramStatusTitle = paramStatusTitle;
    }

    public Integer getSyncSuccess() {
        return this.syncSuccess;
    }

    public void setSyncSuccess(Integer syncSuccess) {
        this.syncSuccess = syncSuccess;
    }

    public Integer getSyncFailed() {
        return this.syncFailed;
    }

    public void setSyncFailed(Integer syncFailed) {
        this.syncFailed = syncFailed;
    }

    public Integer getNotSync() {
        return this.notSync;
    }

    public void setNotSync(Integer notSync) {
        this.notSync = notSync;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

