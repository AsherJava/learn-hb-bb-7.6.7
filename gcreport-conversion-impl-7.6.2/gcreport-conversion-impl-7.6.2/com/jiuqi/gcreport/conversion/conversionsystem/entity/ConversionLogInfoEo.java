/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn$DBType
 *  com.jiuqi.gcreport.definition.impl.anno.DBTable
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 */
package com.jiuqi.gcreport.conversion.conversionsystem.entity;

import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;

@DBTable(name="GC_CONV_LOG", title="\u6298\u7b97\u65e5\u5fd7", inStorage=true)
public class ConversionLogInfoEo
extends DefaultTableEntity {
    private static final long serialVersionUID = 1L;
    public static final String TABLENAME = "GC_CONV_LOG";
    @DBColumn(nameInDB="UNITID", title="\u6298\u7b97\u5355\u4f4dID", dbType=DBColumn.DBType.Varchar, length=36)
    private String unitId;
    @DBColumn(nameInDB="UNITNAME", title="\u6298\u7b97\u5355\u4f4d\u6807\u9898", dbType=DBColumn.DBType.NVarchar, length=200)
    private String unitName;
    @DBColumn(nameInDB="TASKID", title="\u62a5\u8868\u4efb\u52a1ID", dbType=DBColumn.DBType.Varchar, length=36)
    private String taskId;
    @DBColumn(nameInDB="TASKNAME", title="\u62a5\u8868\u4efb\u52a1\u6807\u9898", dbType=DBColumn.DBType.NVarchar, length=200)
    private String taskName;
    @DBColumn(nameInDB="SCHEMEID", title="\u62a5\u8868\u65b9\u6848ID", dbType=DBColumn.DBType.Varchar, length=36)
    private String schemeId;
    @DBColumn(nameInDB="SCHEMENAME", title="\u62a5\u8868\u65b9\u6848\u6807\u9898", dbType=DBColumn.DBType.NVarchar, length=200)
    private String schemeName;
    @DBColumn(nameInDB="PERIODSTR", title="\u65f6\u671f", dbType=DBColumn.DBType.NVarchar)
    private String periodStr;
    @DBColumn(nameInDB="ADJUST", title="\u8c03\u6574\u671f", dbType=DBColumn.DBType.NVarchar)
    private String adjustCode;
    @DBColumn(nameInDB="SRCCURRENCY", title="\u6e90\u5e01\u79cd", dbType=DBColumn.DBType.NVarchar)
    private String srcCurrency;
    @DBColumn(nameInDB="DSTCURRENCY", title="\u76ee\u6807\u5e01\u79cd", dbType=DBColumn.DBType.NVarchar)
    private String dstCurrency;
    @DBColumn(nameInDB="LOGTIMETEXT", title="\u6298\u7b97\u65f6\u95f4", dbType=DBColumn.DBType.NVarchar)
    private String logtimeText;
    @DBColumn(nameInDB="USERTITLE", title="\u64cd\u4f5c\u4eba", dbType=DBColumn.DBType.NVarchar)
    private String userTitle;
    @DBColumn(nameInDB="LOGINFO", title="\u6298\u7b97\u65e5\u5fd7", dbType=DBColumn.DBType.Text)
    private String loginfo;
    @DBColumn(nameInDB="SUCCESSFLAG", title="\u6298\u7b97\u6210\u529f\u6807\u8bc6", dbType=DBColumn.DBType.Numeric, precision=1, scale=0)
    private Integer successFlag;

    public String getUnitId() {
        return this.unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getUnitName() {
        return this.unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getSchemeId() {
        return this.schemeId;
    }

    public void setSchemeId(String schemeId) {
        this.schemeId = schemeId;
    }

    public String getSchemeName() {
        return this.schemeName;
    }

    public void setSchemeName(String schemeName) {
        this.schemeName = schemeName;
    }

    public String getSrcCurrency() {
        return this.srcCurrency;
    }

    public void setSrcCurrency(String srcCurrency) {
        this.srcCurrency = srcCurrency;
    }

    public String getDstCurrency() {
        return this.dstCurrency;
    }

    public void setDstCurrency(String dstCurrency) {
        this.dstCurrency = dstCurrency;
    }

    public String getLoginfo() {
        return this.loginfo;
    }

    public void setLoginfo(String loginfo) {
        this.loginfo = loginfo;
    }

    public Integer getSuccessFlag() {
        return this.successFlag;
    }

    public void setSuccessFlag(Integer successFlag) {
        this.successFlag = successFlag;
    }

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return this.taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getLogtimeText() {
        return this.logtimeText;
    }

    public void setLogtimeText(String logtimeText) {
        this.logtimeText = logtimeText;
    }

    public String getUserTitle() {
        return this.userTitle;
    }

    public void setUserTitle(String userTitle) {
        this.userTitle = userTitle;
    }

    public String getPeriodStr() {
        return this.periodStr;
    }

    public void setPeriodStr(String periodStr) {
        this.periodStr = periodStr;
    }

    public String getAdjustCode() {
        return this.adjustCode;
    }

    public void setAdjustCode(String adjustCode) {
        this.adjustCode = adjustCode;
    }
}

