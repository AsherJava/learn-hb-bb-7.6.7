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

@DBTable(name="GC_PARAMSYNC_RECEIVE_LOG", title="\u56fd\u8d44\u59d4\u53c2\u6570\u540c\u6b65\u63a5\u6536\u4efb\u52a1\u6267\u884c\u65e5\u5fd7\u8868")
public class ReportDataSyncReceiveLogEO
extends DefaultTableEntity {
    public static final String TABLENAME = "GC_PARAMSYNC_RECEIVE_LOG";
    @DBColumn(title="\u4efb\u52a1\u5305\u540d\u79f0", dbType=DBColumn.DBType.Varchar, length=100)
    private String taskTitle;
    @DBColumn(title="\u4efb\u52a1ID", dbType=DBColumn.DBType.Varchar)
    private String taskId;
    @DBColumn(title="\u4efb\u52a1\u4ee3\u7801", dbType=DBColumn.DBType.Varchar)
    private String taskCode;
    @DBColumn(title="\u7248\u672c\u53f7", dbType=DBColumn.DBType.Varchar)
    private String syncVersion;
    @DBColumn(title="\u4e0b\u53d1\u65f6\u95f4", dbType=DBColumn.DBType.Date)
    private Date xfTime;
    @DBColumn(title="\u540c\u6b65\u66f4\u65b0\u65f6\u95f4", dbType=DBColumn.DBType.Date)
    private Date syncTime;
    @DBColumn(title="\u540c\u6b65\u66f4\u65b0\u7ed3\u679c", dbType=DBColumn.DBType.Numeric, precision=1, scale=0)
    private Integer syncStatus;
    @DBColumn(title="\u540c\u6b65\u6267\u884c\u7528\u6237ID", dbType=DBColumn.DBType.Varchar)
    private String syncUserId;
    @DBColumn(title="\u5bf9\u6bd4\u6587\u4ef6ID", dbType=DBColumn.DBType.Varchar)
    private String compareFileId;
    @DBColumn(title="\u540c\u6b65\u6267\u884c\u7528\u6237\u540d", dbType=DBColumn.DBType.Varchar, length=100)
    private String syncUserName;
    @DBColumn(title="\u6765\u6e90\u7c7b\u578b", dbType=DBColumn.DBType.Varchar)
    private String receiveType;
    @DBColumn(title="\u53c2\u6570\u540c\u6b65\u63a5\u6536\u8bb0\u5f55ID", dbType=DBColumn.DBType.Varchar)
    private String receiveTaskId;

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

    public Integer getSyncStatus() {
        return this.syncStatus;
    }

    public void setSyncStatus(Integer syncStatus) {
        this.syncStatus = syncStatus;
    }

    public String getSyncUserId() {
        return this.syncUserId;
    }

    public String getCompareFileId() {
        return this.compareFileId;
    }

    public void setCompareFileId(String compareFileId) {
        this.compareFileId = compareFileId;
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

    public String getTaskCode() {
        return this.taskCode;
    }

    public void setTaskCode(String taskCode) {
        this.taskCode = taskCode;
    }

    public void setReceiveTaskId(String receiveTaskId) {
        this.receiveTaskId = receiveTaskId;
    }

    public String getReceiveTaskId() {
        return this.receiveTaskId;
    }
}

