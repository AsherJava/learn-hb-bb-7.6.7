/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn$DBType
 *  com.jiuqi.gcreport.definition.impl.anno.DBIndex
 *  com.jiuqi.gcreport.definition.impl.anno.DBIndex$TableIndexType
 *  com.jiuqi.gcreport.definition.impl.anno.DBTable
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 */
package com.jiuqi.gcreport.calculate.entity;

import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBIndex;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;

@DBTable(name="GC_CALC_LOG", title="\u5408\u5e76\u8ba1\u7b97\u65e5\u5fd7\u8868", inStorage=true, indexs={@DBIndex(name="IDX_GC_CALC_LOG_ID", columnsFields={"ID"}, type=DBIndex.TableIndexType.TABLE_INDEX_UNIQUE), @DBIndex(name="IDX_GC_CALC_LOG_LATESTFLAG", columnsFields={"LATESTFLAG"}), @DBIndex(name="IDX_GC_CALC_LOG_COM1", columnsFields={"TASKID", "CURRENCY", "PERIOD", "ORGTYPE", "ORGID", "OPERATE"})})
public class GcCalcLogEO
extends DefaultTableEntity {
    private static final long serialVersionUID = 1L;
    public static final String TABLENAME = "GC_CALC_LOG";
    @DBColumn(nameInDB="operate", title="\u64cd\u4f5c\u6807\u8bc6", dbType=DBColumn.DBType.NVarchar, isRequired=true)
    private String operate;
    @DBColumn(nameInDB="taskid", title="\u4efb\u52a1ID", dbType=DBColumn.DBType.Varchar, length=36, isRequired=true)
    private String taskId;
    @DBColumn(nameInDB="period", title="\u65f6\u671f\u7c7b\u578b9\u4f4d\u5b57\u7b26\u4e32 yyyyTmmmm", dbType=DBColumn.DBType.NVarchar, isRequired=true)
    private String period;
    @DBColumn(nameInDB="adjust", title="\u8c03\u6574\u671f\u7b26\u53f7", dbType=DBColumn.DBType.NVarchar)
    private String selectAdjustCode;
    @DBColumn(nameInDB="orgtype", title="\u5355\u4f4d\u7c7b\u578b", dbType=DBColumn.DBType.NVarchar, isRequired=true)
    private String orgType;
    @DBColumn(nameInDB="orgid", title="\u5408\u5e76\u5355\u4f4dID", dbType=DBColumn.DBType.Varchar, length=36, isRequired=true)
    private String orgId;
    @DBColumn(nameInDB="currency", title="\u5e01\u79cd", dbType=DBColumn.DBType.Varchar)
    private String currency;
    @DBColumn(nameInDB="begintime", title="\u6267\u884c\u5f00\u59cb\u65f6\u95f4\u6233", dbType=DBColumn.DBType.Long)
    private Long begintime;
    @DBColumn(nameInDB="endtime", title="\u6267\u884c\u7ed3\u675f\u65f6\u95f4\u6233", dbType=DBColumn.DBType.Long)
    private Long endtime;
    @DBColumn(nameInDB="taskstate", title="\u6267\u884c\u72b6\u6001", dbType=DBColumn.DBType.Varchar)
    private String taskState;
    @DBColumn(nameInDB="userid", title="\u6267\u884c\u4ebaID", dbType=DBColumn.DBType.Varchar, length=36)
    private String userId;
    @DBColumn(nameInDB="username", title="\u6267\u884c\u4eba", dbType=DBColumn.DBType.NVarchar)
    private String username;
    @DBColumn(nameInDB="lockflag", title="\u662f\u5426\u4e0a\u9501\uff0c\u6b63\u5728\u8fd0\u884c\u4e0a\u9501", dbType=DBColumn.DBType.Numeric, precision=1, scale=0)
    private Integer lockFlag;
    @DBColumn(nameInDB="latestflag", title="\u662f\u5426\u6700\u65b0\u8bb0\u5f55", dbType=DBColumn.DBType.Numeric, precision=1, scale=0)
    private Integer latestFlag;
    @DBColumn(nameInDB="info", title="\u6267\u884c\u65e5\u5fd7", dbType=DBColumn.DBType.Text)
    private String info;

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getPeriod() {
        return this.period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getOrgType() {
        return this.orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public String getOrgId() {
        return this.orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public Long getBegintime() {
        return this.begintime;
    }

    public void setBegintime(Long begintime) {
        this.begintime = begintime;
    }

    public Long getEndtime() {
        return this.endtime;
    }

    public void setEndtime(Long endtime) {
        this.endtime = endtime;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getOperate() {
        return this.operate;
    }

    public void setOperate(String operate) {
        this.operate = operate;
    }

    public String getInfo() {
        return this.info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Integer getLockFlag() {
        return this.lockFlag;
    }

    public void setLockFlag(Integer lockFlag) {
        this.lockFlag = lockFlag;
    }

    public String getTaskState() {
        return this.taskState;
    }

    public void setTaskState(String taskState) {
        this.taskState = taskState;
    }

    public Integer getLatestFlag() {
        return this.latestFlag;
    }

    public void setLatestFlag(Integer latestFlag) {
        this.latestFlag = latestFlag;
    }

    public String getCurrency() {
        return this.currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getSelectAdjustCode() {
        return this.selectAdjustCode;
    }

    public void setSelectAdjustCode(String selectAdjustCode) {
        this.selectAdjustCode = selectAdjustCode;
    }
}

