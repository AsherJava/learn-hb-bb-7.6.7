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

@DBTable(name="GC_PARAMSYNC_XF_LOG", title="\u56fd\u8d44\u59d4\u53c2\u6570\u540c\u6b65\u65e5\u5fd7\uff08\u4e0b\u53d1\u7aef\uff09")
public class ReportDataSyncIssuedLogEO
extends DefaultTableEntity {
    public static final String TABLENAME = "GC_PARAMSYNC_XF_LOG";
    @DBColumn(title="\u7248\u672c\u53f7", dbType=DBColumn.DBType.Varchar, length=20)
    private String syncVersion;
    @DBColumn(title="\u4efb\u52a1\u5305\u540d\u79f0", dbType=DBColumn.DBType.Varchar, length=100)
    private String taskTitle;
    @DBColumn(title="\u4efb\u52a1ID", dbType=DBColumn.DBType.Varchar)
    private String taskId;
    @DBColumn(title="\u4efb\u52a1\u4ee3\u7801", dbType=DBColumn.DBType.Varchar)
    private String taskCode;
    @DBColumn(title="\u53c2\u6570\u5305\u9644\u4ef6ID", dbType=DBColumn.DBType.Varchar)
    private String syncParamAttachId;
    @DBColumn(title="\u53c2\u6570\u66f4\u65b0\u8bf4\u660e\u6587\u6863\u9644\u4ef6ID", dbType=DBColumn.DBType.Varchar)
    private String syncDesAttachId;
    @DBColumn(title="\u53c2\u6570\u66f4\u65b0\u8bf4\u660e\u6587\u6863\u9644\u4ef6\u540d\u79f0", dbType=DBColumn.DBType.Varchar, length=200)
    private String syncDesAttachTitle;
    @DBColumn(title="\u5f02\u6784\u7cfb\u7edf\u53c2\u6570\u5305ID", dbType=DBColumn.DBType.Varchar)
    private String ygParamAttachId;
    @DBColumn(title="\u53c2\u6570\u5305\u72b6\u6001", dbType=DBColumn.DBType.Varchar, length=20)
    private String paramStatus;
    @DBColumn(title="\u540c\u6b65\u6267\u884c\u7ed3\u679c(\u6210\u529f/\u5931\u8d25/\u90e8\u5206\u6210\u529f)", dbType=DBColumn.DBType.Numeric)
    private Integer syncStatus;
    @DBColumn(title="\u540c\u6b65\u6267\u884c\u65f6\u95f4", dbType=DBColumn.DBType.Date)
    private Date syncTime;
    @DBColumn(title="\u540c\u6b65\u6267\u884c\u7528\u6237ID", dbType=DBColumn.DBType.Varchar)
    private String syncUserId;
    @DBColumn(title="\u540c\u6b65\u6267\u884c\u7528\u6237\u540d", dbType=DBColumn.DBType.Varchar, length=100)
    private String syncUserName;
    @DBColumn(title="\u53c2\u6570\u540c\u6b65\u65b9\u6848id", dbType=DBColumn.DBType.NVarchar)
    private String syncSchemeId;
    @DBColumn(nameInDB="CONTENT", title="\u540c\u6b65\u5185\u5bb9", dbType=DBColumn.DBType.NText)
    private String content;

    public String getParamStatus() {
        return this.paramStatus;
    }

    public void setParamStatus(String paramStatus) {
        this.paramStatus = paramStatus;
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

    public Date getSyncTime() {
        return this.syncTime;
    }

    public void setSyncTime(Date syncTime) {
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

    public String getSyncSchemeId() {
        return this.syncSchemeId;
    }

    public void setSyncSchemeId(String syncSchemeId) {
        this.syncSchemeId = syncSchemeId;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getYgParamAttachId() {
        return this.ygParamAttachId;
    }

    public void setYgParamAttachId(String ygSyncParamAttachId) {
        this.ygParamAttachId = ygSyncParamAttachId;
    }
}

