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
package com.jiuqi.gcreport.archive.entity;

import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBIndex;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import java.util.Date;

@DBTable(name="GC_ARCHIVE_LOG", title="\u5f52\u6863\u65e5\u5fd7\u8868", inStorage=true)
@DBIndex(name="INDEX_ARCHIVELOG_T_S", columnsFields={"TASKID", "SCHEMEID"})
public class ArchiveLogEO
extends DefaultTableEntity {
    public static final String TABLENAME = "GC_ARCHIVE_LOG";
    @DBColumn(nameInDB="TASKID", title="\u4efb\u52a1ID", dbType=DBColumn.DBType.Varchar, length=36, isRequired=true)
    private String taskId;
    @DBColumn(nameInDB="SCHEMEID", title="\u65b9\u6848ID", dbType=DBColumn.DBType.Varchar, length=36, isRequired=true)
    private String schemeId;
    @DBColumn(nameInDB="ORGTYPE", title="\u53e3\u5f84", dbType=DBColumn.DBType.Varchar, length=36, isRequired=false)
    private String orgType;
    @DBColumn(nameInDB="START_PERIOD", title="\u5f00\u59cb\u65f6\u671f", dbType=DBColumn.DBType.NVarchar, length=20)
    private String startPeriod;
    @DBColumn(nameInDB="START_ADJUST", title="\u5f00\u59cb\u8c03\u6574\u671f", dbType=DBColumn.DBType.NVarchar, length=20)
    private String startAdjustCode;
    @DBColumn(nameInDB="END_PERIOD", title="\u7ed3\u675f\u65f6\u671f", dbType=DBColumn.DBType.NVarchar, length=20)
    private String endPeriod;
    @DBColumn(nameInDB="END_ADJUST", title="\u7ed3\u675f\u8c03\u6574\u671f", dbType=DBColumn.DBType.NVarchar, length=20)
    private String endAdjustCode;
    @DBColumn(nameInDB="ORGCODELIST", title="\u5355\u4f4d\u5217\u8868", dbType=DBColumn.DBType.Text)
    private String orgCodeList;
    @DBColumn(nameInDB="LOG_INFO", title="\u65e5\u5fd7\u4fe1\u606f", dbType=DBColumn.DBType.Text)
    private String logInfo;
    @DBColumn(nameInDB="STATUS", title="\u72b6\u6001", dbType=DBColumn.DBType.Int, length=2)
    private Integer status;
    @DBColumn(nameInDB="CREATE_DATE", dbType=DBColumn.DBType.DateTime)
    private Date createDate;
    @DBColumn(nameInDB="END_DATE", dbType=DBColumn.DBType.DateTime)
    private Date endDate;
    @DBColumn(nameInDB="CREATE_USER", dbType=DBColumn.DBType.NVarchar, length=100)
    private String createUser;
    @DBColumn(nameInDB="EXCEL_FORMINFO", title="\u5df2\u9009\u62a5\u8868\u4fe1\u606f", dbType=DBColumn.DBType.Text)
    private String excelFormInfo;

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

    public String getOrgType() {
        return this.orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public String getStartPeriod() {
        return this.startPeriod;
    }

    public void setStartPeriod(String startPeriod) {
        this.startPeriod = startPeriod;
    }

    public String getEndPeriod() {
        return this.endPeriod;
    }

    public void setEndPeriod(String endPeriod) {
        this.endPeriod = endPeriod;
    }

    public String getOrgCodeList() {
        return this.orgCodeList;
    }

    public void setOrgCodeList(String orgCodeList) {
        this.orgCodeList = orgCodeList;
    }

    public String getLogInfo() {
        return this.logInfo;
    }

    public void setLogInfo(String logInfo) {
        this.logInfo = logInfo;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateDate() {
        return this.createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getEndDate() {
        return this.endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getCreateUser() {
        return this.createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getExcelFormInfo() {
        return this.excelFormInfo;
    }

    public void setExcelFormInfo(String excelFormInfo) {
        this.excelFormInfo = excelFormInfo;
    }

    public String getStartAdjustCode() {
        return this.startAdjustCode;
    }

    public void setStartAdjustCode(String startAdjustCode) {
        this.startAdjustCode = startAdjustCode;
    }

    public String getEndAdjustCode() {
        return this.endAdjustCode;
    }

    public void setEndAdjustCode(String endAdjustCode) {
        this.endAdjustCode = endAdjustCode;
    }
}

