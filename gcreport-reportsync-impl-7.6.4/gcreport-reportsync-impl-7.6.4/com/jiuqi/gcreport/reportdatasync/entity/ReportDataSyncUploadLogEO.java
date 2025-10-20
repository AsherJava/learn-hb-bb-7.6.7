/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn$DBType
 *  com.jiuqi.gcreport.definition.impl.anno.DBTable
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 */
package com.jiuqi.gcreport.reportdatasync.entity;

import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import java.util.Date;

@DBTable(name="GC_DATASYNC_UPLOAD_LOG", title="\u56fd\u8d44\u59d4\u6570\u636e\u4e0a\u4f20\u65e5\u5fd7\u8868")
public class ReportDataSyncUploadLogEO
extends DefaultTableEntity {
    public static final String TABLENAME = "GC_DATASYNC_UPLOAD_LOG";
    @DBColumn(title="\u7248\u672c\u53f7", dbType=DBColumn.DBType.Varchar, length=20)
    private String syncVersion;
    @DBColumn(title="\u6570\u636e\u4e0a\u4f20\u65b9\u6848ID", dbType=DBColumn.DBType.Varchar)
    private String uploadSchemeId;
    @DBColumn(title="\u65f6\u671f\u6807\u9898", dbType=DBColumn.DBType.Varchar)
    private String periodStr;
    @DBColumn(title="\u65f6\u671f", dbType=DBColumn.DBType.Varchar)
    private String periodValue;
    @DBColumn(title="\u8c03\u6574\u671f", dbType=DBColumn.DBType.Varchar)
    private String adjustCode;
    @DBColumn(title="\u62a5\u8868\u4efb\u52a1ID", dbType=DBColumn.DBType.Varchar)
    private String taskId;
    @DBColumn(title="\u62a5\u8868\u4efb\u52a1", dbType=DBColumn.DBType.Varchar)
    private String taskTitle;
    @DBColumn(title="\u62a5\u8868\u4efb\u52a1\u4ee3\u7801", dbType=DBColumn.DBType.Varchar)
    private String taskCode;
    @DBColumn(title="\u4e0a\u62a5\u6570\u636e\u5305\u9644\u4ef6ID", dbType=DBColumn.DBType.Varchar)
    private String syncDataAttachId;
    @DBColumn(title="\u6570\u636e\u4e0a\u4f20\u7528\u6237\u540d", dbType=DBColumn.DBType.Varchar, length=100)
    private String uploadUsername;
    @DBColumn(title="\u6570\u636e\u4e0a\u4f20\u7528\u6237ID", dbType=DBColumn.DBType.Varchar)
    private String uploadUserId;
    @DBColumn(title="\u6570\u636e\u4e0a\u4f20\u65f6\u95f4", dbType=DBColumn.DBType.Date)
    private Date uploadTime;
    @DBColumn(title="\u6570\u636e\u4e0a\u4f20\u72b6\u6001", dbType=DBColumn.DBType.Varchar)
    private String uploadStatus;
    @DBColumn(title="\u4e0a\u4f20\u8be6\u7ec6\u8bf4\u660e", dbType=DBColumn.DBType.Varchar, length=2000)
    private String uploadMsg;
    @DBColumn(title="\u6570\u636e\u88c5\u5165\u72b6\u6001", dbType=DBColumn.DBType.Varchar)
    private String loadStatus;
    @DBColumn(title="\u88c5\u5165\u8be6\u7ec6\u8bf4\u660e", dbType=DBColumn.DBType.Varchar, length=2000)
    private String loadMsg;
    @DBColumn(title="\u5355\u4f4d\u53e3\u5f84", dbType=DBColumn.DBType.Varchar, length=100)
    private String orgType;
    @DBColumn(title="\u5355\u4f4d\u53e3\u5f84\u6807\u9898", dbType=DBColumn.DBType.Varchar, length=100)
    private String orgTypeTitle;

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

    public Date getUploadTime() {
        return this.uploadTime;
    }

    public void setUploadTime(Date uploadTime) {
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
        if (uploadMsg != null && uploadMsg.length() > 2000) {
            uploadMsg = uploadMsg.substring(0, 2000);
        }
        this.uploadMsg = uploadMsg;
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

    public String getPeriodValue() {
        return this.periodValue;
    }

    public void setPeriodValue(String periodValue) {
        this.periodValue = periodValue;
    }

    public String getLoadStatus() {
        return this.loadStatus;
    }

    public void setLoadStatus(String loadStatus) {
        if (loadStatus != null && loadStatus.length() > 2000) {
            loadStatus = loadStatus.substring(0, 2000);
        }
        this.loadStatus = loadStatus;
    }

    public String getLoadMsg() {
        return this.loadMsg;
    }

    public void setLoadMsg(String loadMsg) {
        this.loadMsg = loadMsg;
    }

    public String getAdjustCode() {
        return this.adjustCode == null ? "0" : this.adjustCode;
    }

    public void setAdjustCode(String adjustCode) {
        this.adjustCode = adjustCode;
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
}

