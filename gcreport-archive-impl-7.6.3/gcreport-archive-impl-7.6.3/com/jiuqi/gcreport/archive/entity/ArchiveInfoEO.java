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
package com.jiuqi.gcreport.archive.entity;

import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBIndex;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import java.util.Date;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@DBTable(name="GC_ARCHIVE_INFO", title="\u5f52\u6863\u4fe1\u606f\u8868", inStorage=true)
@DBIndex(name="INDEX_ARCHIVE_INFO_UP", columnsFields={"UNITID", "DEFAULT_PERIOD"})
public class ArchiveInfoEO
extends DefaultTableEntity {
    public static final String TABLENAME = "GC_ARCHIVE_INFO";
    @DBColumn(nameInDB="LOGID", title="\u5f52\u6863\u4e3b\u4efb\u52a1\u65e5\u5fd7ID", dbType=DBColumn.DBType.Varchar, length=36)
    private String logId;
    @DBColumn(nameInDB="TASKID", title="\u4efb\u52a1ID", dbType=DBColumn.DBType.Varchar, length=36, isRequired=true)
    private String taskId;
    @DBColumn(nameInDB="SCHEMEID", title="\u65b9\u6848ID", dbType=DBColumn.DBType.Varchar, length=36, isRequired=true)
    private String schemeId;
    @DBColumn(nameInDB="UNITID", title="\u5355\u4f4d\u4ee3\u7801", dbType=DBColumn.DBType.Varchar, length=36)
    private String unitId;
    @DBColumn(nameInDB="ORGTYPE", title="\u53e3\u5f84", dbType=DBColumn.DBType.Varchar, length=36, isRequired=false)
    private String orgType;
    @DBColumn(nameInDB="DEFAULT_PERIOD", title="\u65f6\u671f\u7c7b\u578b", dbType=DBColumn.DBType.NVarchar, length=20)
    private String defaultPeriod;
    @DBColumn(nameInDB="ADJUSTCODE", title="\u8c03\u6574\u671f", dbType=DBColumn.DBType.NVarchar, length=20)
    private String adjustCode;
    @DBColumn(nameInDB="FILE_NAME", title="\u6587\u4ef6\u540d", dbType=DBColumn.DBType.Text)
    private String fileName;
    @DBColumn(nameInDB="RES_APP", title="\u63a5\u5165\u7cfb\u7edf\u7f16\u7801,\u6863\u6848\u7cfb\u7edf\u4f7f\u7528\uff08\u6cd5\u4ebaYT0020\uff0c\u7ba1\u7406YT0030\uff09", dbType=DBColumn.DBType.Varchar, length=10)
    private String resApp;
    @DBColumn(nameInDB="FILE_PATH", title="\u5b58\u50a8\u5728ftp\u4e0a\u7684\u8def\u5f84", dbType=DBColumn.DBType.Text)
    private String filePath;
    @DBColumn(nameInDB="FASTDFS_FILE_PATH", title="\u5b58\u50a8\u5728fastDFS\u4e0a\u7684\u8def\u5f84", dbType=DBColumn.DBType.Text)
    private String fastdfsFilePath;
    @DBColumn(nameInDB="DATA_JSON", title="\u53d1\u9001\u5230\u6863\u6848\u7cfb\u7edf\u7684json\u5b57\u7b26\u4e32", dbType=DBColumn.DBType.NVarchar, length=1000)
    private String dataJson;
    @Temporal(value=TemporalType.DATE)
    @DBColumn(nameInDB="UPDATE_DATE", dbType=DBColumn.DBType.Date)
    private Date updateDate;
    @DBColumn(nameInDB="STATUS", title="\u5f52\u6863\u72b6\u6001", dbType=DBColumn.DBType.NVarchar, length=2)
    private Integer status;
    @DBColumn(nameInDB="RETRY_COUNT", title="\u91cd\u8bd5\u6b21\u6570", dbType=DBColumn.DBType.Int, length=2)
    private Integer retryCount;
    @Temporal(value=TemporalType.DATE)
    @DBColumn(nameInDB="CREATE_DATE", dbType=DBColumn.DBType.Date)
    private Date createDate;
    @DBColumn(nameInDB="CREATE_USER", dbType=DBColumn.DBType.NVarchar, length=100)
    private String createUser;
    @DBColumn(nameInDB="EXPORT_PARAM", title="\u91cd\u65b0\u4e0a\u4f20ftp\u6587\u4ef6\u65f6\uff0c\u751f\u6210\u4e0a\u4f20\u6587\u4ef6\u7684\u53c2\u6570", dbType=DBColumn.DBType.NVarchar, length=2000)
    private String exportParam;
    @DBColumn(nameInDB="ERROR_INFO", title="\u9519\u8bef\u65e5\u5fd7", dbType=DBColumn.DBType.Text)
    private String errorInfo;

    public String getExportParam() {
        return this.exportParam;
    }

    public void setExportParam(String exportParam) {
        this.exportParam = exportParam;
    }

    public String getOrgType() {
        return this.orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getRetryCount() {
        return this.retryCount;
    }

    public void setRetryCount(Integer retryCount) {
        this.retryCount = retryCount;
    }

    public String getResApp() {
        return this.resApp;
    }

    public void setResApp(String resApp) {
        this.resApp = resApp;
    }

    public String getDataJson() {
        return this.dataJson;
    }

    public void setDataJson(String dataJson) {
        this.dataJson = dataJson;
    }

    public Date getUpdateDate() {
        return this.updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
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

    public String getUnitId() {
        return this.unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getDefaultPeriod() {
        return this.defaultPeriod;
    }

    public void setDefaultPeriod(String defaultPeriod) {
        this.defaultPeriod = defaultPeriod;
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

    public String getFastdfsFilePath() {
        return this.fastdfsFilePath;
    }

    public void setFastdfsFilePath(String fastdfsFilePath) {
        this.fastdfsFilePath = fastdfsFilePath;
    }

    public String getErrorInfo() {
        return this.errorInfo;
    }

    public void setErrorInfo(String errorInfo) {
        this.errorInfo = errorInfo;
    }

    public String getLogId() {
        return this.logId;
    }

    public void setLogId(String logId) {
        this.logId = logId;
    }

    public String getAdjustCode() {
        return this.adjustCode;
    }

    public void setAdjustCode(String adjustCode) {
        this.adjustCode = adjustCode;
    }
}

