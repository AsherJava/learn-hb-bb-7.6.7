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

@DBTable(name="GC_ONEKEYMERGETASK_4", title="\u4e00\u952e\u5408\u5e76\u7ed3\u679c\u8868", inStorage=true)
public class GcTaskResultEO
extends DefaultTableEntity {
    private static final long serialVersionUID = 1L;
    public static final String TABLENAME = "GC_ONEKEYMERGETASK_4";
    @DBColumn(nameInDB="GROUP_ID", title="\u4efb\u52a1\u6240\u5c5e\u5206\u7ec4", dbType=DBColumn.DBType.NVarchar, length=36)
    private String groupId;
    @DBColumn(nameInDB="taskCode", title="\u4efb\u52a1\u6807\u8bc6", dbType=DBColumn.DBType.Varchar)
    private String taskCode;
    @DBColumn(nameInDB="orgId", title="\u5408\u5e76\u5355\u4f4dID", dbType=DBColumn.DBType.Varchar, length=36)
    private String orgId;
    @DBColumn(nameInDB="orgType", title="\u5355\u4f4d\u7c7b\u578b", dbType=DBColumn.DBType.Varchar)
    private String orgType;
    @DBColumn(nameInDB="taskId", title="\u4efb\u52a1ID", dbType=DBColumn.DBType.Varchar, length=36)
    private String taskId;
    @DBColumn(nameInDB="schemeId", title="\u65b9\u6848ID", dbType=DBColumn.DBType.Varchar, length=36)
    private String schemeId;
    @DBColumn(nameInDB="currency", title="\u5e01\u79cd", dbType=DBColumn.DBType.Varchar)
    private String currency;
    @DBColumn(nameInDB="acctYear", title="\u65f6\u671f--\u5e74", dbType=DBColumn.DBType.Int)
    private Integer acctYear;
    @DBColumn(nameInDB="acctPeriod", title="\u65f6\u671f--\u671f\u95f4", dbType=DBColumn.DBType.Int)
    private Integer acctPeriod;
    @DBColumn(nameInDB="periodType", title="\u65f6\u671f\u7c7b\u578b", dbType=DBColumn.DBType.Int)
    private Integer periodType;
    @DBColumn(nameInDB="taskState", title="\u4efb\u52a1\u72b6\u6001", dbType=DBColumn.DBType.NVarchar)
    private String taskState;
    @DBColumn(nameInDB="taskTime", title="\u6267\u884c\u65f6\u95f4", dbType=DBColumn.DBType.Date)
    private Date taskTime;
    @DBColumn(nameInDB="userName", title="\u6267\u884c\u7528\u6237", dbType=DBColumn.DBType.NVarchar)
    private String userName;
    @DBColumn(nameInDB="taskData", title="\u5355\u4e2a\u4efb\u52a1\u7684\u7ed3\u679c\u6570\u636e", dbType=DBColumn.DBType.Text)
    private String taskData;
    @DBColumn(nameInDB="completeLogs", title="\u5b8c\u6574\u65e5\u5fd7\u4fe1\u606f", dbType=DBColumn.DBType.Text)
    private String completeLogs;

    public String getTaskCode() {
        return this.taskCode;
    }

    public void setTaskCode(String taskCode) {
        this.taskCode = taskCode;
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

    public String getTaskState() {
        return this.taskState;
    }

    public void setTaskState(String taskState) {
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

    public String getTaskData() {
        return this.taskData;
    }

    public void setTaskData(String taskData) {
        this.taskData = taskData;
    }

    public String getGroupId() {
        return this.groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getCurrency() {
        return this.currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCompleteLogs() {
        return this.completeLogs;
    }

    public void setCompleteLogs(String completeLogs) {
        this.completeLogs = completeLogs;
    }
}

