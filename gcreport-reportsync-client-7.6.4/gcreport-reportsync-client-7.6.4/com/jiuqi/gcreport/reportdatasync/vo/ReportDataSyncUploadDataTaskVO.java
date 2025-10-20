/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.reportdatasync.vo;

import java.util.List;

public class ReportDataSyncUploadDataTaskVO {
    private String id;
    private String syncVersion;
    private String taskId;
    private List<String> returnUnitCodes;
    private String taskTitle;
    private String taskCode;
    private String periodStr;
    private String periodValue;
    private String adjustCode;
    private String orgCode;
    private String orgTitle;
    private String unitCodes;
    private String uploadTime;
    private String uploadUsername;
    private String uploadUserId;
    private String status;
    private String syncTime;
    private String syncLogInfo;
    private String rejectMsg;
    private String syncDataAttachId;
    private String mappingSchemeId;
    private String mappingSchemeTitle;
    private Boolean showProgress;
    private Double progress;
    private Boolean executeUpload = Boolean.FALSE;
    private Boolean allowForceUpload = Boolean.FALSE;
    private Boolean applicationMode;
    private List<String> headCodes;
    private List<String> headTitles;

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

    public String getPeriodStr() {
        return this.periodStr;
    }

    public void setPeriodStr(String periodStr) {
        this.periodStr = periodStr;
    }

    public String getOrgTitle() {
        return this.orgTitle;
    }

    public void setOrgTitle(String orgTitle) {
        this.orgTitle = orgTitle;
    }

    public String getUploadTime() {
        return this.uploadTime;
    }

    public void setUploadTime(String uploadTime) {
        this.uploadTime = uploadTime;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSyncTime() {
        return this.syncTime;
    }

    public void setSyncTime(String syncTime) {
        this.syncTime = syncTime;
    }

    public String getSyncLogInfo() {
        return this.syncLogInfo;
    }

    public void setSyncLogInfo(String syncLogInfo) {
        this.syncLogInfo = syncLogInfo;
    }

    public String getRejectMsg() {
        return this.rejectMsg;
    }

    public void setRejectMsg(String rejectMsg) {
        this.rejectMsg = rejectMsg;
    }

    public String getSyncDataAttachId() {
        return this.syncDataAttachId;
    }

    public void setSyncDataAttachId(String syncDataAttachId) {
        this.syncDataAttachId = syncDataAttachId;
    }

    public String getOrgCode() {
        return this.orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getTaskCode() {
        return this.taskCode;
    }

    public void setTaskCode(String taskCode) {
        this.taskCode = taskCode;
    }

    public String getPeriodValue() {
        return this.periodValue;
    }

    public void setPeriodValue(String periodValue) {
        this.periodValue = periodValue;
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

    public String getMappingSchemeId() {
        return this.mappingSchemeId;
    }

    public void setMappingSchemeId(String mappingSchemeId) {
        this.mappingSchemeId = mappingSchemeId;
    }

    public String getMappingSchemeTitle() {
        return this.mappingSchemeTitle;
    }

    public void setMappingSchemeTitle(String mappingSchemeTitle) {
        this.mappingSchemeTitle = mappingSchemeTitle;
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

    public Boolean getExecuteUpload() {
        return this.executeUpload;
    }

    public void setExecuteUpload(Boolean executeUpload) {
        this.executeUpload = executeUpload;
    }

    public Boolean getAllowForceUpload() {
        return this.allowForceUpload;
    }

    public void setAllowForceUpload(Boolean allowForceUpload) {
        this.allowForceUpload = allowForceUpload;
    }

    public Boolean getApplicationMode() {
        return this.applicationMode;
    }

    public void setApplicationMode(Boolean applicationMode) {
        this.applicationMode = applicationMode;
    }

    public List<String> getHeadCodes() {
        return this.headCodes;
    }

    public void setHeadCodes(List<String> headCodes) {
        this.headCodes = headCodes;
    }

    public List<String> getHeadTitles() {
        return this.headTitles;
    }

    public void setHeadTitles(List<String> headTitles) {
        this.headTitles = headTitles;
    }

    public String getAdjustCode() {
        return this.adjustCode;
    }

    public void setAdjustCode(String adjustCode) {
        this.adjustCode = adjustCode;
    }

    public List<String> getReturnUnitCodes() {
        return this.returnUnitCodes;
    }

    public void setReturnUnitCodes(List<String> returnUnitCodes) {
        this.returnUnitCodes = returnUnitCodes;
    }

    public String getUnitCodes() {
        return this.unitCodes;
    }

    public void setUnitCodes(String unitCodes) {
        this.unitCodes = unitCodes;
    }
}

