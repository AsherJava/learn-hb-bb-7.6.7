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

@DBTable(name="GC_ONEKEYMERGEPROCESS", title="\u4e00\u952e\u5408\u5e76\u7ed3\u679c\u5206\u7ec4", inStorage=true)
public class GcOnekeyMergeResultEO
extends DefaultTableEntity {
    public static final String TABLENAME = "GC_ONEKEYMERGEPROCESS";
    @DBColumn(nameInDB="taskCodes", title="\u6267\u884c\u7684\u4efb\u52a1", dbType=DBColumn.DBType.Varchar, length=300)
    private String taskCodes;
    @DBColumn(nameInDB="orgId", title="\u5408\u5e76\u5355\u4f4dID", dbType=DBColumn.DBType.Varchar, length=36)
    private String orgId;
    @DBColumn(nameInDB="orgType", title="\u5355\u4f4d\u7c7b\u578b", dbType=DBColumn.DBType.Varchar)
    private String orgType;
    @DBColumn(nameInDB="taskId", title="\u62a5\u8868\u4efb\u52a1ID", dbType=DBColumn.DBType.Varchar, length=36)
    private String taskId;
    @DBColumn(nameInDB="schemeId", title="\u62a5\u8868\u65b9\u6848ID", dbType=DBColumn.DBType.Varchar, length=36)
    private String schemeId;
    @DBColumn(nameInDB="currency", title="\u5e01\u79cd", dbType=DBColumn.DBType.Varchar)
    private String currency;
    @DBColumn(nameInDB="acctYear", title="\u65f6\u671f--\u5e74", dbType=DBColumn.DBType.Int)
    private Integer acctYear;
    @DBColumn(nameInDB="acctPeriod", title="\u65f6\u671f--\u671f\u95f4", dbType=DBColumn.DBType.Int)
    private Integer acctPeriod;
    @DBColumn(nameInDB="periodType", title="\u65f6\u671f\u7c7b\u578b", dbType=DBColumn.DBType.Int)
    private Integer periodType;
    @DBColumn(nameInDB="lastTask", title="\u4e2d\u65ad\u7684\u8282\u70b9", dbType=DBColumn.DBType.Varchar)
    private String lastTask;
    @DBColumn(nameInDB="taskState", title="\u4efb\u52a1\u72b6\u6001", dbType=DBColumn.DBType.Numeric, precision=1, scale=0)
    private Integer taskState;
    @DBColumn(nameInDB="taskTime", title="\u6267\u884c\u65f6\u95f4", dbType=DBColumn.DBType.Date)
    private Date taskTime;
    @DBColumn(nameInDB="userName", title="\u6267\u884c\u7528\u6237", dbType=DBColumn.DBType.NVarchar)
    private String userName;

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

    public String getOrgType() {
        return this.orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getSchemeId() {
        return this.schemeId;
    }

    public void setSchemeId(String schemeId) {
        this.schemeId = schemeId;
    }

    public String getCurrency() {
        return this.currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Integer getAcctYear() {
        return this.acctYear;
    }

    public void setAcctYear(Integer acctYear) {
        this.acctYear = acctYear;
    }

    public Integer getAcctPeriod() {
        return this.acctPeriod;
    }

    public void setAcctPeriod(Integer acctPeriod) {
        this.acctPeriod = acctPeriod;
    }

    public Integer getPeriodType() {
        return this.periodType;
    }

    public void setPeriodType(Integer periodType) {
        this.periodType = periodType;
    }

    public String getLastTask() {
        return this.lastTask;
    }

    public void setLastTask(String lastTask) {
        this.lastTask = lastTask;
    }

    public Integer getTaskState() {
        return this.taskState;
    }

    public void setTaskState(Integer taskState) {
        this.taskState = taskState;
    }

    public Date getTaskTime() {
        return this.taskTime;
    }

    public void setTaskTime(Date taskTime) {
        this.taskTime = taskTime;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}

