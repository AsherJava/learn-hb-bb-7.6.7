/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn$DBType
 *  com.jiuqi.gcreport.definition.impl.anno.DBTable
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 */
package com.jiuqi.gcreport.carryover.entity;

import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import java.util.Date;

@DBTable(name="GC_CARRYOVER_PROCESS", title="\u5e74\u7ed3\u4efb\u52a1\u8fdb\u7a0b\u8bb0\u5f55\u8868")
public class CarryOverTaskProcessEO
extends DefaultTableEntity {
    public static final String TABLENAME = "GC_CARRYOVER_PROCESS";
    @DBColumn(nameInDB="nrTaskId", title="\u62a5\u8868\u4efb\u52a1Id", dbType=DBColumn.DBType.Varchar)
    private String nrTaskId;
    @DBColumn(nameInDB="acctYear", title="\u6267\u884c\u5e74\u5ea6", dbType=DBColumn.DBType.Varchar)
    private String acctYear;
    @DBColumn(nameInDB="CARRYOVERSCHEMEID", title="\u5e74\u7ed3\u65b9\u6848ID", dbType=DBColumn.DBType.Varchar)
    private String carryOverSchemeId;
    @DBColumn(nameInDB="TARGETSYSTEMID", title="\u76ee\u6807\u4f53\u7cfbID", dbType=DBColumn.DBType.Varchar)
    private String targetSystemId;
    @DBColumn(nameInDB="orgId", title="\u5355\u4f4dID", dbType=DBColumn.DBType.NText)
    private String orgId;
    @DBColumn(nameInDB="userName", title="\u7528\u6237\u540d", dbType=DBColumn.DBType.Varchar)
    private String userName;
    @DBColumn(nameInDB="taskState", title="\u4efb\u52a1\u72b6\u6001", dbType=DBColumn.DBType.Varchar)
    private String taskState;
    @DBColumn(nameInDB="createTime", title="\u521b\u5efa\u65f6\u95f4", dbType=DBColumn.DBType.Date)
    private Date createTime;
    @DBColumn(nameInDB="finishTime", title="\u5b8c\u6210\u65f6\u95f4", dbType=DBColumn.DBType.Date)
    private Date finishTime;
    @DBColumn(title="\u8fdb\u5ea6", dbType=DBColumn.DBType.Numeric, precision=19, scale=2)
    private Double process;
    @DBColumn(nameInDB="totalTaskCount", title="\u603b\u4efb\u52a1\u6570\u91cf", dbType=DBColumn.DBType.Long)
    private Long totalTaskCount;
    @DBColumn(nameInDB="finishedTaskCount", title="\u5df2\u5b8c\u6210\u4efb\u52a1\u6570\u91cf", dbType=DBColumn.DBType.Long)
    private Long finishedTaskCount;

    public String getNrTaskId() {
        return this.nrTaskId;
    }

    public void setNrTaskId(String nrTaskId) {
        this.nrTaskId = nrTaskId;
    }

    public String getAcctYear() {
        return this.acctYear;
    }

    public void setAcctYear(String acctYear) {
        this.acctYear = acctYear;
    }

    public String getCarryOverSchemeId() {
        return this.carryOverSchemeId;
    }

    public void setCarryOverSchemeId(String carryOverSchemeId) {
        this.carryOverSchemeId = carryOverSchemeId;
    }

    public String getTargetSystemId() {
        return this.targetSystemId;
    }

    public void setTargetSystemId(String targetSystemId) {
        this.targetSystemId = targetSystemId;
    }

    public String getOrgId() {
        return this.orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTaskState() {
        return this.taskState;
    }

    public void setTaskState(String taskState) {
        this.taskState = taskState;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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
}

