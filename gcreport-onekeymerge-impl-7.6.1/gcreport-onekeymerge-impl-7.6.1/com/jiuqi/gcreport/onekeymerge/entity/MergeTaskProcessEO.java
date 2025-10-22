/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn$DBType
 *  com.jiuqi.gcreport.definition.impl.anno.DBTable
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 */
package com.jiuqi.gcreport.onekeymerge.entity;

import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import java.util.Date;

@DBTable(name="GC_MERGEPROCESS", title="\u4e00\u952e\u5408\u5e76\u4efb\u52a1")
public class MergeTaskProcessEO
extends DefaultTableEntity {
    public static final String TABLENAME = "GC_MERGEPROCESS";
    private static final long serialVersionUID = 3973932522950879356L;
    @DBColumn(nameInDB="taskState", title="\u4efb\u52a1\u72b6\u6001", dbType=DBColumn.DBType.Varchar)
    private String taskState;
    @DBColumn(nameInDB="dataTime", title="\u4efb\u52a1\u65f6\u671f", dbType=DBColumn.DBType.Varchar)
    private String dataTime;
    @DBColumn(nameInDB="taskCodes", title="\u4efb\u52a1\u6807\u8bc6", dbType=DBColumn.DBType.Varchar, length=100)
    private String taskCodes;
    @DBColumn(nameInDB="orgId", title="\u5408\u5e76\u5355\u4f4dID", dbType=DBColumn.DBType.Varchar, length=500)
    private String orgId;
    @DBColumn(nameInDB="createTime", title="\u521b\u5efa\u65f6\u95f4", dbType=DBColumn.DBType.Date)
    private Date createTime;
    @DBColumn(nameInDB="userName", title="\u7528\u6237\u540d", dbType=DBColumn.DBType.Varchar)
    private String userName;
    @DBColumn(nameInDB="finishTime", title="\u5b8c\u6210\u65f6\u95f4", dbType=DBColumn.DBType.Date)
    private Date finishTime;
    @DBColumn(title="\u8fdb\u5ea6", dbType=DBColumn.DBType.Numeric, precision=19, scale=2)
    private Double process;
    @DBColumn(nameInDB="dims", title="\u7ef4\u5ea6\u4fe1\u606f", dbType=DBColumn.DBType.Varchar, length=500)
    private String dims;
    @DBColumn(nameInDB="nrTaskId", title="\u62a5\u8868\u4efb\u52a1Id", dbType=DBColumn.DBType.Varchar)
    private String nrTaskId;
    @DBColumn(nameInDB="totalTaskCount", title="\u603b\u4efb\u52a1\u6570\u91cf", dbType=DBColumn.DBType.Long)
    private Long totalTaskCount;
    @DBColumn(nameInDB="finishedTaskCount", title="\u5df2\u5b8c\u6210\u4efb\u52a1\u6570\u91cf", dbType=DBColumn.DBType.Long)
    private Long finishedTaskCount;
    @DBColumn(nameInDB="configSchemeName", title="\u914d\u7f6e\u65b9\u6848\u540d\u79f0", dbType=DBColumn.DBType.Varchar)
    private String configSchemeName;
    @DBColumn(nameInDB="mergeType", title="\u5408\u5e76\u65b9\u5f0f", dbType=DBColumn.DBType.Varchar)
    private String mergeType;
    @DBColumn(nameInDB="logInfo", title="\u65e5\u5fd7\u4fe1\u606f", dbType=DBColumn.DBType.Text)
    private String logInfo;

    public Long getTotalTaskCount() {
        return this.totalTaskCount;
    }

    public void setTotalTaskCount(Long totalTaskCount) {
        this.totalTaskCount = totalTaskCount;
    }

    public Long getFinishedTaskCount() {
        return this.finishedTaskCount;
    }

    public void setFinishedTaskCount(Long finishedTaskCount) {
        this.finishedTaskCount = finishedTaskCount;
    }

    public String getDims() {
        return this.dims;
    }

    public void setDims(String dims) {
        this.dims = dims;
    }

    public String getNrTaskId() {
        return this.nrTaskId;
    }

    public void setNrTaskId(String nrTaskId) {
        this.nrTaskId = nrTaskId;
    }

    public String getTaskState() {
        return this.taskState;
    }

    public void setTaskState(String taskState) {
        this.taskState = taskState;
    }

    public String getDataTime() {
        return this.dataTime;
    }

    public void setDataTime(String dataTime) {
        this.dataTime = dataTime;
    }

    public String getTaskCodes() {
        return this.taskCodes;
    }

    public void setTaskCodes(String taskCodes) {
        this.taskCodes = taskCodes;
    }

    public String getOrgId() {
        return this.orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getFinishTime() {
        return this.finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public Double getProcess() {
        return this.process;
    }

    public void setProcess(Double process) {
        this.process = process;
    }

    public String getConfigSchemeName() {
        return this.configSchemeName;
    }

    public void setConfigSchemeName(String configSchemeName) {
        this.configSchemeName = configSchemeName;
    }

    public String getMergeType() {
        return this.mergeType;
    }

    public void setMergeType(String mergeType) {
        this.mergeType = mergeType;
    }

    public String getLogInfo() {
        return this.logInfo;
    }

    public void setLogInfo(String logInfo) {
        this.logInfo = logInfo;
    }
}

