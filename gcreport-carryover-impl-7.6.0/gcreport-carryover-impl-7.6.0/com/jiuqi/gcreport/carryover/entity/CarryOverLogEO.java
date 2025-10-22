/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn$DBType
 *  com.jiuqi.gcreport.definition.impl.anno.DBIndex
 *  com.jiuqi.gcreport.definition.impl.anno.DBTable
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 */
package com.jiuqi.gcreport.carryover.entity;

import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBIndex;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import java.util.Date;

@DBTable(name="GC_CARRYOVER_LOG", title="\u5e74\u7ed3\u65e5\u5fd7\u8868", indexs={@DBIndex(name="IDX_GC_CARRYOVER_LOG_CARRYOVERSCHEMEID", columnsFields={"carryOverSchemeId"})})
public class CarryOverLogEO
extends DefaultTableEntity {
    public static final String TABLENAME = "GC_CARRYOVER_LOG";
    @DBColumn(nameInDB="GROUP_ID", title="\u4efb\u52a1\u6240\u5c5e\u5206\u7ec4", dbType=DBColumn.DBType.Varchar, length=36)
    private String groupId;
    @DBColumn(nameInDB="taskState", title="\u4efb\u52a1\u72b6\u6001", dbType=DBColumn.DBType.Varchar)
    private String taskState;
    @DBColumn(nameInDB="TASKID", title="\u4efb\u52a1id", dbType=DBColumn.DBType.Varchar, length=36)
    private String taskId;
    @DBColumn(nameInDB="SCHEMEID", title="\u62a5\u8868\u65b9\u6848", dbType=DBColumn.DBType.Varchar, length=36)
    private String schemeId;
    @DBColumn(nameInDB="CARRYOVERSCHEMEID", title="\u5e74\u7ed3\u65b9\u6848ID", dbType=DBColumn.DBType.Varchar, isRequired=true)
    private String carryOverSchemeId;
    @DBColumn(nameInDB="TYPECODE", title="\u5e74\u7ed3\u65b9\u6848\u7c7b\u578bcode", dbType=DBColumn.DBType.Varchar, length=50, isRequired=true)
    private String typeCode;
    @DBColumn(nameInDB="ACCTYEAR", title="\u5e74\u5ea6", dbType=DBColumn.DBType.Int, isRequired=true)
    private Integer acctYear;
    @DBColumn(nameInDB="adjust", title="\u8c03\u6574\u671f\u7b26\u53f7", dbType=DBColumn.DBType.NVarchar)
    private String selectAdjustCode;
    @DBColumn(nameInDB="UNITINFO", title="\u5355\u4f4d\u4fe1\u606f", dbType=DBColumn.DBType.NText)
    private String unitInfo;
    @DBColumn(nameInDB="createTime", title="\u521b\u5efa\u65f6\u95f4", dbType=DBColumn.DBType.Date)
    private Date createTime;
    @DBColumn(nameInDB="STARTTIME", title="\u5f00\u59cb\u65f6\u95f4", dbType=DBColumn.DBType.Date)
    private Date startTime;
    @DBColumn(nameInDB="ENDTIME", title="\u7ed3\u675f\u65f6\u95f4", dbType=DBColumn.DBType.Date)
    private Date endTime;
    @DBColumn(nameInDB="CREATOR", title="\u521b\u5efa\u8005", dbType=DBColumn.DBType.Varchar, length=80)
    private String creator;
    @DBColumn(nameInDB="TARGETSYSTEMID", title="\u76ee\u6807\u4f53\u7cfbID", dbType=DBColumn.DBType.Varchar)
    private String targetSystemId;
    @DBColumn(title="\u8fdb\u5ea6", dbType=DBColumn.DBType.Numeric, precision=19, scale=2)
    private Double process;
    @DBColumn(nameInDB="INFO", title="\u65e5\u5fd7\u4fe1\u606f", dbType=DBColumn.DBType.NText)
    private String info;

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getCarryOverSchemeId() {
        return this.carryOverSchemeId;
    }

    public void setCarryOverSchemeId(String carryOverSchemeId) {
        this.carryOverSchemeId = carryOverSchemeId;
    }

    public String getTypeCode() {
        return this.typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public String getSchemeId() {
        return this.schemeId;
    }

    public void setSchemeId(String schemeId) {
        this.schemeId = schemeId;
    }

    public Integer getAcctYear() {
        return this.acctYear;
    }

    public void setAcctYear(Integer acctYear) {
        this.acctYear = acctYear;
    }

    public String getUnitInfo() {
        return this.unitInfo;
    }

    public void setUnitInfo(String unitInfo) {
        this.unitInfo = unitInfo;
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

    public String getCreator() {
        return this.creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getTargetSystemId() {
        return this.targetSystemId;
    }

    public void setTargetSystemId(String targetSystemId) {
        this.targetSystemId = targetSystemId;
    }

    public String getInfo() {
        return this.info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getSelectAdjustCode() {
        return this.selectAdjustCode;
    }

    public void setSelectAdjustCode(String selectAdjustCode) {
        this.selectAdjustCode = selectAdjustCode;
    }

    public String getGroupId() {
        return this.groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
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

    public Double getProcess() {
        return this.process;
    }

    public void setProcess(Double process) {
        this.process = process;
    }
}

