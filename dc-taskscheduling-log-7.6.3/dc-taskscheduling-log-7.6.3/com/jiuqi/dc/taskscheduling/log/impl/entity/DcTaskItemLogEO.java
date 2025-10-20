/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.dc.base.common.definition.DcDefaultTableEntity
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn$DBType
 *  com.jiuqi.gcreport.definition.impl.anno.DBIndex
 *  com.jiuqi.gcreport.definition.impl.anno.DBIndex$TableIndexType
 *  com.jiuqi.gcreport.definition.impl.anno.DBTable
 *  com.jiuqi.gcreport.definition.impl.anno.DBTableGroup
 *  com.jiuqi.nvwa.definition.common.TableModelKind
 */
package com.jiuqi.dc.taskscheduling.log.impl.entity;

import com.jiuqi.dc.base.common.definition.DcDefaultTableEntity;
import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBIndex;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.anno.DBTableGroup;
import com.jiuqi.nvwa.definition.common.TableModelKind;
import java.util.Date;

@DBTable(name="GC_LOG_TASKITEMINFO", title="\u4efb\u52a1\u4fe1\u606f\u5b50\u8868", indexs={@DBIndex(name="IDX_GC_LOG_TASKITEM_INSTID", type=DBIndex.TableIndexType.TABLE_INDEX_NORMAL, columnsFields={"INSTANCEID"}), @DBIndex(name="IDX_GC_LOG_TASKITEM_RID_STATE", type=DBIndex.TableIndexType.TABLE_INDEX_NORMAL, columnsFields={"RUNNERID", "EXECUTESTATE"})}, kind=TableModelKind.SYSTEM_EXTEND, ownerGroupID=@DBTableGroup(id="11000000-0000-0000-0000-000000000003", code="table_group_datacenter", title="\u4e00\u672c\u8d26"), dataSource="jiuqi.gcreport.mdd.datasource")
public class DcTaskItemLogEO
extends DcDefaultTableEntity {
    public static final String TABLENAME = "GC_LOG_TASKITEMINFO";
    private static final long serialVersionUID = -3048103190324846835L;
    @DBColumn(nameInDB="INSTANCEID", title="\u6d41\u7a0bID", dbType=DBColumn.DBType.Varchar, length=36, order=1)
    private String instanceId;
    @DBColumn(nameInDB="RUNNERID", title="\u4e3b\u4efb\u52a1ID", dbType=DBColumn.DBType.Varchar, length=36, order=2)
    private String runnerId;
    @DBColumn(nameInDB="PRENODEID", title="\u540e\u7f6e\u4efb\u52a1ID", dbType=DBColumn.DBType.Varchar, length=36, order=3)
    private String preNodeId;
    @DBColumn(nameInDB="TASKTYPE", title="\u4efb\u52a1\u7c7b\u578b", dbType=DBColumn.DBType.NVarchar, length=60, order=4)
    private String taskType;
    @DBColumn(nameInDB="DIMTYPE", title="\u7ef4\u5ea6\u7c7b\u578b", dbType=DBColumn.DBType.Varchar, length=60, order=5)
    private String dimType;
    @DBColumn(nameInDB="DIMCODE", title="\u7ef4\u5ea6\u4ee3\u7801", dbType=DBColumn.DBType.Varchar, length=300, order=6)
    private String dimCode;
    @DBColumn(nameInDB="MESSAGE", title="\u6d88\u606f\u4fe1\u606f", dbType=DBColumn.DBType.Text, order=7)
    private String message;
    @DBColumn(nameInDB="MESSAGEDIGEST", title="\u6d88\u606f\u6458\u8981", dbType=DBColumn.DBType.NVarchar, length=200, order=8)
    private String messageDigest;
    @DBColumn(nameInDB="EXECUTESTATE", title="\u6267\u884c\u72b6\u6001", dbType=DBColumn.DBType.Int, length=1, order=9)
    private Integer executeState;
    @DBColumn(nameInDB="STARTTIME", title="\u5f00\u59cb\u65f6\u95f4", dbType=DBColumn.DBType.DateTime, order=10)
    private Date startTime;
    @DBColumn(nameInDB="ENDTIME", title="\u622a\u6b62\u65f6\u95f4", dbType=DBColumn.DBType.DateTime, order=11)
    private Date endTime;
    @DBColumn(nameInDB="RESULTLOG", title="\u7ed3\u679c\u65e5\u5fd7", dbType=DBColumn.DBType.Text, order=12)
    private String resultLog;
    @DBColumn(nameInDB="PROGRESS", title="\u6267\u884c\u8fdb\u5ea6", dbType=DBColumn.DBType.Numeric, precision=10, scale=2, defaultValue="0.00", order=13)
    private Double progress;
    @DBColumn(nameInDB="QUEUENAME", title="\u961f\u5217\u540d\u79f0", dbType=DBColumn.DBType.Varchar, length=200, order=14)
    private String queueName;
    @DBColumn(nameInDB="EXECUTEAPPNAME", title="\u6267\u884c\u670d\u52a1\u540d\u79f0", dbType=DBColumn.DBType.Varchar, length=60, order=15)
    private String executeAppName;
    @DBColumn(nameInDB="SERVERIP", title="\u6267\u884c\u670d\u52a1IP", dbType=DBColumn.DBType.Varchar, length=20, order=16)
    private String serverIp;

    public String getInstanceId() {
        return this.instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public String getRunnerId() {
        return this.runnerId;
    }

    public void setRunnerId(String runnerId) {
        this.runnerId = runnerId;
    }

    public String getPreNodeId() {
        return this.preNodeId;
    }

    public void setPreNodeId(String preNodeId) {
        this.preNodeId = preNodeId;
    }

    public String getTaskType() {
        return this.taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public String getDimType() {
        return this.dimType;
    }

    public void setDimType(String dimType) {
        this.dimType = dimType;
    }

    public String getDimCode() {
        return this.dimCode;
    }

    public void setDimCode(String dimCode) {
        this.dimCode = dimCode;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessageDigest() {
        return this.messageDigest;
    }

    public void setMessageDigest(String messageDigest) {
        this.messageDigest = messageDigest;
    }

    public Integer getExecuteState() {
        return this.executeState;
    }

    public void setExecuteState(Integer executeState) {
        this.executeState = executeState;
    }

    public Date getStartTime() {
        return this.startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return this.endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getResultLog() {
        return this.resultLog;
    }

    public void setResultLog(String resultLog) {
        this.resultLog = resultLog;
    }

    public Double getProgress() {
        return this.progress;
    }

    public void setProgress(Double progress) {
        this.progress = progress;
    }

    public String getQueueName() {
        return this.queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    public String getExecuteAppName() {
        return this.executeAppName;
    }

    public void setExecuteAppName(String executeAppName) {
        this.executeAppName = executeAppName;
    }

    public String getServerIp() {
        return this.serverIp;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }
}

