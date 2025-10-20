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

@DBTable(name="GC_PARAMSYNC_RECEIVE", title="\u56fd\u8d44\u59d4\u53c2\u6570\u540c\u6b65\u63a5\u6536\u4efb\u52a1\u8868")
public class ReportDataSyncReceiveTaskEO
extends DefaultTableEntity {
    public static final String TABLENAME = "GC_PARAMSYNC_RECEIVE";
    @DBColumn(title="\u4efb\u52a1\u5305\u540d\u79f0", dbType=DBColumn.DBType.Varchar, length=100)
    private String taskTitle;
    @DBColumn(title="\u4efb\u52a1ID", dbType=DBColumn.DBType.Varchar)
    private String taskId;
    @DBColumn(title="\u7248\u672c\u53f7", dbType=DBColumn.DBType.Varchar)
    private String syncVersion;
    @DBColumn(title="\u53c2\u6570\u66f4\u65b0\u8bf4\u660e\u6587\u6863\u9644\u4ef6ID", dbType=DBColumn.DBType.Varchar)
    private String syncDesAttachId;
    @DBColumn(title="\u53c2\u6570\u66f4\u65b0\u8bf4\u660e\u6587\u6863\u9644\u4ef6\u540d\u79f0", dbType=DBColumn.DBType.Varchar, length=200)
    private String syncDesAttachTitle;
    @DBColumn(title="\u53c2\u6570\u5305\u9644\u4ef6ID", dbType=DBColumn.DBType.Varchar)
    private String syncParamAttachId;
    @DBColumn(title="\u5bf9\u6bd4\u6587\u4ef6ID", dbType=DBColumn.DBType.Varchar)
    private String compareFileId;
    @DBColumn(title="\u4e0b\u53d1\u65f6\u95f4", dbType=DBColumn.DBType.Date)
    private Date xfTime;
    @DBColumn(title="\u540c\u6b65\u66f4\u65b0\u65f6\u95f4", dbType=DBColumn.DBType.Date)
    private Date syncTime;
    @DBColumn(title="\u540c\u6b65\u66f4\u65b0\u7ed3\u679c", dbType=DBColumn.DBType.Numeric, precision=1, scale=0)
    private Integer syncStatus;
    @DBColumn(title="\u6765\u6e90\u7c7b\u578b", dbType=DBColumn.DBType.Varchar)
    private String receiveType;
    @DBColumn(title="\u53c2\u6570\u72b6\u6001(\u751f\u6548\u4e2d:1;\u5df2\u5931\u6548:0)", dbType=DBColumn.DBType.Numeric, precision=1, scale=0)
    private Integer available;

    public Integer getSyncStatus() {
        return this.syncStatus;
    }

    public void setSyncStatus(Integer syncStatus) {
        this.syncStatus = syncStatus;
    }

    public String getReceiveType() {
        return this.receiveType;
    }

    public void setReceiveType(String receiveType) {
        this.receiveType = receiveType;
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

    public String getCompareFileId() {
        return this.compareFileId;
    }

    public void setCompareFileId(String compareFileId) {
        this.compareFileId = compareFileId;
    }

    public String getSyncDesAttachId() {
        return this.syncDesAttachId;
    }

    public void setSyncDesAttachId(String syncDesAttachId) {
        this.syncDesAttachId = syncDesAttachId;
    }

    public Date getXfTime() {
        return this.xfTime;
    }

    public void setXfTime(Date xfTime) {
        this.xfTime = xfTime;
    }

    public Date getSyncTime() {
        return this.syncTime;
    }

    public void setSyncTime(Date syncTime) {
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

    public Integer getAvailable() {
        return this.available;
    }

    public void setAvailable(Integer available) {
        this.available = available;
    }
}

