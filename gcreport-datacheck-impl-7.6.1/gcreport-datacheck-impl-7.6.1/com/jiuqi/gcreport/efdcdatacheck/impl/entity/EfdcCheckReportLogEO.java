/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn$DBType
 *  com.jiuqi.gcreport.definition.impl.anno.DBIndex
 *  com.jiuqi.gcreport.definition.impl.anno.DBTable
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 *  javax.persistence.Temporal
 *  javax.persistence.TemporalType
 */
package com.jiuqi.gcreport.efdcdatacheck.impl.entity;

import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBIndex;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import java.util.Date;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@DBTable(name="GC_EFDCCHECKREPORTLOG", title="\u6570\u636e\u7a3d\u6838\u62a5\u544a\u65e5\u5fd7\u8868", inStorage=true, indexs={@DBIndex(name="IDX_GC_EFDCCHECKREPORTLOG", columnsFields={"UNITID", "ACCT_YEAR"})})
public class EfdcCheckReportLogEO
extends DefaultTableEntity {
    public static final String TABLENAME = "GC_EFDCCHECKREPORTLOG";
    @DBColumn(nameInDB="TASKID", title="\u4efb\u52a1ID", dbType=DBColumn.DBType.Varchar, length=36, isRequired=true)
    private String taskId;
    @DBColumn(nameInDB="SCHEMEID", title="\u65b9\u6848ID", dbType=DBColumn.DBType.Varchar, length=36, isRequired=true)
    private String schemeId;
    @DBColumn(nameInDB="UNITID", dbType=DBColumn.DBType.Varchar, length=36)
    private String unitId;
    @DBColumn(nameInDB="GROUP_ID", dbType=DBColumn.DBType.Varchar, length=36)
    private String groupId;
    @DBColumn(nameInDB="ACCT_YEAR", dbType=DBColumn.DBType.Int)
    private Integer acctYear;
    @DBColumn(nameInDB="ACCT_PERIOD", dbType=DBColumn.DBType.Int)
    private Integer acctPeriod;
    @DBColumn(nameInDB="DEFAULT_PERIOD", title="\u65f6\u671f\u7c7b\u578b", dbType=DBColumn.DBType.NVarchar, length=20)
    private String defaultPeriod;
    @DBColumn(nameInDB="FILE_NAME", dbType=DBColumn.DBType.NVarchar, length=100)
    private String fileName;
    @DBColumn(nameInDB="FILE_PATH", dbType=DBColumn.DBType.NVarchar, length=200)
    private String filePath;
    @Temporal(value=TemporalType.DATE)
    @DBColumn(nameInDB="CREATE_DATE", dbType=DBColumn.DBType.Date)
    private Date createDate;
    @DBColumn(nameInDB="CREATE_USER", dbType=DBColumn.DBType.NVarchar, length=100)
    private String createUser;
    @DBColumn(nameInDB="ADJUST", title="\u8c03\u6574\u671f", dbType=DBColumn.DBType.Varchar, length=100)
    protected String adjust;
    private static final String ALLFIELDSELECTSQL = "%1$s.id as id,%1$s.unitId as unitId, \n%2$s%1$s.acct_year as acct_year,%1$s.acct_period as acct_period,%1$s.file_name as file_name,%1$s.file_path as file_path,  \n%2$s%1$s.create_date as create_date,%1$s.create_user as create_user,%1$s.taskId as taskId,%1$s.schemeId as schemeId,%1$s.default_period as default_period  \n";

    public String getAdjust() {
        return this.adjust;
    }

    public void setAdjust(String adjust) {
        this.adjust = adjust;
    }

    public static String getAllFieldSelectSql(String tableAlias, String space) {
        return String.format(ALLFIELDSELECTSQL, tableAlias, space == null ? "" : space);
    }

    public String getUnitId() {
        return this.unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
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

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return this.filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Date getCreateDate() {
        return this.createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCreateUser() {
        return this.createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getGroupId() {
        return this.groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
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

    public String getDefaultPeriod() {
        return this.defaultPeriod;
    }

    public void setDefaultPeriod(String defaultPeriod) {
        this.defaultPeriod = defaultPeriod;
    }
}

