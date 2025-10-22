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

@DBTable(name="GC_DATASYNC_RECEIVE", title="\u56fd\u8d44\u59d4\u6570\u636e\u4e0a\u4f20\u63a5\u6536\u4efb\u52a1\u7ba1\u7406\u8868")
public class ReportDataSyncUploadDataTaskEO
extends DefaultTableEntity {
    public static final String TABLENAME = "GC_DATASYNC_RECEIVE";
    @DBColumn(title="\u4efb\u52a1\u540d\u79f0", dbType=DBColumn.DBType.Varchar, length=100)
    private String taskTitle;
    @DBColumn(title="\u4efb\u52a1\u4ee3\u7801", dbType=DBColumn.DBType.Varchar, length=100)
    private String taskCode;
    @DBColumn(title="\u4efb\u52a1ID", dbType=DBColumn.DBType.Varchar, length=100)
    private String taskId;
    @DBColumn(title="\u4e0a\u4f20\u65f6\u671f\u6807\u9898", dbType=DBColumn.DBType.Varchar)
    private String periodStr;
    @DBColumn(title="\u4e0a\u4f20\u65f6\u671f", dbType=DBColumn.DBType.Varchar)
    private String periodValue;
    @DBColumn(title="\u8c03\u6574\u671f", dbType=DBColumn.DBType.Varchar)
    private String selectAdjust;
    @DBColumn(title="\u4e0a\u4f20\u5355\u4f4d\u4ee3\u7801(\u76ee\u6807\u670d\u52a1\u5668\u8bbe\u7f6e\u5355\u4f4d)", dbType=DBColumn.DBType.Varchar, length=100)
    private String orgCode;
    @DBColumn(title="\u4e0a\u4f20\u5355\u4f4d\u4ee3\u7801\uff08\u62a5\u8868\u6570\u636e\u6240\u9009\u5355\u4f4d\uff09", dbType=DBColumn.DBType.Varchar, length=100)
    private String unitCodes;
    @DBColumn(title="\u4e0a\u4f20\u5355\u4f4d", dbType=DBColumn.DBType.Varchar, length=100)
    private String orgTitle;
    @DBColumn(title="\u4e0a\u4f20\u65f6\u95f4", dbType=DBColumn.DBType.Date)
    private Date uploadTime;
    @DBColumn(title="\u72b6\u6001", dbType=DBColumn.DBType.Varchar)
    private String status;
    @DBColumn(title="\u88c5\u5165\u65f6\u95f4", dbType=DBColumn.DBType.Date)
    private Date syncTime;
    @DBColumn(title="\u65e5\u5fd7\u8be6\u60c5", dbType=DBColumn.DBType.Varchar, length=2000)
    private String syncLogInfo;
    @DBColumn(title="\u9000\u56de\u8bf4\u660e", dbType=DBColumn.DBType.Varchar, length=200)
    private String rejectMsg;
    @DBColumn(title="\u6570\u636e\u4e0a\u62a5\u6570\u636e\u5305\u9644\u4ef6ID", dbType=DBColumn.DBType.Varchar)
    private String syncDataAttachId;
    @DBColumn(title="\u6620\u5c04\u65b9\u6848\u540d\u79f0", dbType=DBColumn.DBType.Varchar, length=100)
    private String mappingSchemeTitle;
    @DBColumn(title="\u6620\u5c04\u65b9\u6848Id", dbType=DBColumn.DBType.Varchar)
    private String mappingSchemeId;

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

    public String getOrgCode() {
        return this.orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public Date getUploadTime() {
        return this.uploadTime;
    }

    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getSyncTime() {
        return this.syncTime;
    }

    public void setSyncTime(Date syncTime) {
        this.syncTime = syncTime;
    }

    public String getSyncLogInfo() {
        return this.syncLogInfo;
    }

    public void setSyncLogInfo(String syncLogInfo) {
        if (syncLogInfo != null && syncLogInfo.length() > 2000) {
            syncLogInfo = syncLogInfo.substring(0, 2000);
        }
        this.syncLogInfo = syncLogInfo;
    }

    public String getUnitCodes() {
        return this.unitCodes;
    }

    public void setUnitCodes(String unitCodes) {
        this.unitCodes = unitCodes;
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

    public String getTaskCode() {
        return this.taskCode;
    }

    public void setTaskCode(String taskCode) {
        this.taskCode = taskCode;
    }

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getPeriodValue() {
        return this.periodValue;
    }

    public void setPeriodValue(String periodValue) {
        this.periodValue = periodValue;
    }

    public String getMappingSchemeTitle() {
        return this.mappingSchemeTitle;
    }

    public void setMappingSchemeTitle(String mappingSchemeTitle) {
        this.mappingSchemeTitle = mappingSchemeTitle;
    }

    public String getMappingSchemeId() {
        return this.mappingSchemeId;
    }

    public void setMappingSchemeId(String mappingSchemeId) {
        this.mappingSchemeId = mappingSchemeId;
    }

    public String getSelectAdjust() {
        return this.selectAdjust;
    }

    public void setSelectAdjust(String selectAdjust) {
        this.selectAdjust = selectAdjust;
    }
}

