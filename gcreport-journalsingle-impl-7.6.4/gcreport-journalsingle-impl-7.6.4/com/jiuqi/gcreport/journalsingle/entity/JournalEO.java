/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn$DBType
 *  com.jiuqi.gcreport.definition.impl.anno.DBTable
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 *  javax.persistence.Temporal
 *  javax.persistence.TemporalType
 */
package com.jiuqi.gcreport.journalsingle.entity;

import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import java.util.Date;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@DBTable(name="GC_JOURNAL", inStorage=true, title="\u65e5\u8bb0\u8d26\u5206\u5f55")
public class JournalEO
extends DefaultTableEntity {
    private static final long serialVersionUID = 1L;
    public static final String TABLE_NAME = "GC_JOURNAL";
    @DBColumn(length=36, nameInDB="MRECID", dbType=DBColumn.DBType.Varchar)
    private String mRecid;
    @DBColumn(length=36, nameInDB="TASKID", dbType=DBColumn.DBType.Varchar)
    private String taskId;
    @DBColumn(length=36, nameInDB="SCHEMEID", dbType=DBColumn.DBType.Varchar)
    private String schemeId;
    @DBColumn(length=36, nameInDB="UNIONRULEID", dbType=DBColumn.DBType.Varchar)
    private String unionRuleId;
    @DBColumn(length=20, nameInDB="GCBUSINESSTYPECODE", dbType=DBColumn.DBType.NVarchar)
    private String gcBusinessTypeCode;
    @DBColumn(length=20, nameInDB="EFFECTTYPE", dbType=DBColumn.DBType.NVarchar)
    private String effectType;
    @DBColumn(length=20, nameInDB="ADJUSTTYPECODE", dbType=DBColumn.DBType.NVarchar)
    private String adjustTypeCode;
    @DBColumn(nameInDB="ACCTYEAR", dbType=DBColumn.DBType.Int)
    private Integer acctYear;
    @DBColumn(nameInDB="ACCTPERIOD", dbType=DBColumn.DBType.Int)
    private Integer acctPeriod;
    @DBColumn(nameInDB="ELMMODE", title="\u62b5\u9500\u6a21\u5f0f", dbType=DBColumn.DBType.Int, isRequired=true, description="\u679a\u4e3e\u7c7b\uff0c0-5\u6570\u5b57\uff0c\u5b9a\u4e49\u4e0d\u540c\u62b5\u9500\u65b9\u5f0f")
    private Integer elmMode;
    @DBColumn(length=20, nameInDB="DEFAULT_PERIOD", dbType=DBColumn.DBType.NVarchar)
    private String defaultPeriod;
    @DBColumn(length=36, nameInDB="INPUTUNITID", dbType=DBColumn.DBType.Varchar)
    private String inputUnitId;
    @DBColumn(length=36, nameInDB="UNITID", dbType=DBColumn.DBType.Varchar)
    private String unitId;
    @DBColumn(length=36, nameInDB="OPPUNITID", dbType=DBColumn.DBType.Varchar)
    private String oppUnitId;
    @DBColumn(nameInDB="MD_GCORGTYPE", title="\u5355\u4f4d\u7c7b\u578b", dbType=DBColumn.DBType.Varchar, length=36)
    private String orgType;
    @DBColumn(length=36, nameInDB="SYSTEMID", dbType=DBColumn.DBType.Varchar)
    private String systemId;
    @DBColumn(nameInDB="OFFSETSRCTYPE", title="\u62b5\u9500\u6765\u6e90\u7c7b\u578b", dbType=DBColumn.DBType.Int, isRequired=true, description="\u679a\u4e3e\u7c7b\uff0c0-99\u6570\u5b57\uff0c\u5b9a\u4e49\u4e0d\u540c\u6765\u6e90\u7c7b\u578b")
    private Integer offSetSrcType;
    @DBColumn(nameInDB="SORTORDER", dbType=DBColumn.DBType.Int)
    private Integer sortOrder;
    @DBColumn(length=20, nameInDB="SUBJECTCODE", dbType=DBColumn.DBType.NVarchar)
    private String subjectCode;
    @DBColumn(nameInDB="DC", dbType=DBColumn.DBType.Int)
    private Integer dc;
    @DBColumn(length=20, nameInDB="CURRENCYCODE", dbType=DBColumn.DBType.NVarchar)
    private String currencyCode;
    @DBColumn(length=16, nameInDB="DEBITCNY", dbType=DBColumn.DBType.Numeric)
    private Double debitCNY;
    @DBColumn(length=16, nameInDB="CREDITCNY", dbType=DBColumn.DBType.Numeric)
    private Double creditCNY;
    @DBColumn(length=16, nameInDB="DEBITUSD", dbType=DBColumn.DBType.Numeric)
    private Double debitUSD;
    @DBColumn(length=16, nameInDB="CREDITUSD", dbType=DBColumn.DBType.Numeric)
    private Double creditUSD;
    @DBColumn(length=16, nameInDB="DEBITHKD", dbType=DBColumn.DBType.Numeric)
    private Double debitHKD;
    @DBColumn(length=16, nameInDB="CREDITHKD", dbType=DBColumn.DBType.Numeric)
    private Double creditHKD;
    @DBColumn(length=300, nameInDB="MEMO", dbType=DBColumn.DBType.NVarchar)
    private String memo;
    @DBColumn(nameInDB="POSTFLAG", dbType=DBColumn.DBType.Numeric, precision=1, scale=0)
    private Integer postFlag;
    @DBColumn(nameInDB="ADJTYPE", dbType=DBColumn.DBType.NVarchar)
    private String adjType;
    @DBColumn(nameInDB="SRCTYPE", dbType=DBColumn.DBType.Int)
    private Integer srcType;
    @DBColumn(length=36, nameInDB="SRCID", dbType=DBColumn.DBType.Varchar)
    private String srcID;
    @DBColumn(length=50, nameInDB="CREATEUSER", dbType=DBColumn.DBType.NVarchar)
    private String createUser;
    @DBColumn(nameInDB="CREATEDATE", dbType=DBColumn.DBType.Date)
    @Temporal(value=TemporalType.DATE)
    private Date createDate;
    @DBColumn(nameInDB="CREATETIME", dbType=DBColumn.DBType.Date)
    @Temporal(value=TemporalType.TIMESTAMP)
    private Date createTime;
    @DBColumn(length=20, nameInDB="AREACODE", dbType=DBColumn.DBType.NVarchar)
    private String areaCode;
    @DBColumn(length=20, nameInDB="YWBKCODE", dbType=DBColumn.DBType.NVarchar)
    private String ywbkCode;
    @DBColumn(length=20, nameInDB="GCYWLXCODE", dbType=DBColumn.DBType.NVarchar)
    private String gcywlxCode;
    @DBColumn(length=20, nameInDB="TZYZMSCODE", dbType=DBColumn.DBType.NVarchar)
    private String tzyzmsCode;
    @DBColumn(length=20, nameInDB="PROJECTTITLE", dbType=DBColumn.DBType.NVarchar)
    private String projectTitle;

    public String getmRecid() {
        return this.mRecid;
    }

    public void setmRecid(String mRecid) {
        this.mRecid = mRecid;
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

    public String getUnionRuleId() {
        return this.unionRuleId;
    }

    public void setUnionRuleId(String unionRuleId) {
        this.unionRuleId = unionRuleId;
    }

    public String getGcBusinessTypeCode() {
        return this.gcBusinessTypeCode;
    }

    public void setGcBusinessTypeCode(String gcBusinessTypeCode) {
        this.gcBusinessTypeCode = gcBusinessTypeCode;
    }

    public String getEffectType() {
        return this.effectType;
    }

    public void setEffectType(String effectType) {
        this.effectType = effectType;
    }

    public String getAdjustTypeCode() {
        return this.adjustTypeCode;
    }

    public void setAdjustTypeCode(String adjustTypeCode) {
        this.adjustTypeCode = adjustTypeCode;
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

    public Integer getElmMode() {
        return this.elmMode;
    }

    public void setElmMode(Integer elmMode) {
        this.elmMode = elmMode;
    }

    public String getDefaultPeriod() {
        return this.defaultPeriod;
    }

    public void setDefaultPeriod(String defaultPeriod) {
        this.defaultPeriod = defaultPeriod;
    }

    public String getInputUnitId() {
        return this.inputUnitId;
    }

    public void setInputUnitId(String inputUnitId) {
        this.inputUnitId = inputUnitId;
    }

    public String getUnitId() {
        return this.unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getOppUnitId() {
        return this.oppUnitId;
    }

    public void setOppUnitId(String oppUnitId) {
        this.oppUnitId = oppUnitId;
    }

    public String getOrgType() {
        return this.orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public String getSystemId() {
        return this.systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public Integer getOffSetSrcType() {
        return this.offSetSrcType;
    }

    public void setOffSetSrcType(Integer offSetSrcType) {
        this.offSetSrcType = offSetSrcType;
    }

    public Integer getSortOrder() {
        return this.sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getSubjectCode() {
        return this.subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public Integer getDc() {
        return this.dc;
    }

    public void setDc(Integer dc) {
        this.dc = dc;
    }

    public String getCurrencyCode() {
        return this.currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public Double getDebitCNY() {
        return this.debitCNY;
    }

    public void setDebitCNY(Double debitCNY) {
        this.debitCNY = debitCNY;
    }

    public Double getCreditCNY() {
        return this.creditCNY;
    }

    public void setCreditCNY(Double creditCNY) {
        this.creditCNY = creditCNY;
    }

    public Double getDebitUSD() {
        return this.debitUSD;
    }

    public void setDebitUSD(Double debitUSD) {
        this.debitUSD = debitUSD;
    }

    public Double getCreditUSD() {
        return this.creditUSD;
    }

    public void setCreditUSD(Double creditUSD) {
        this.creditUSD = creditUSD;
    }

    public Double getDebitHKD() {
        return this.debitHKD;
    }

    public void setDebitHKD(Double debitHKD) {
        this.debitHKD = debitHKD;
    }

    public Double getCreditHKD() {
        return this.creditHKD;
    }

    public void setCreditHKD(Double creditHKD) {
        this.creditHKD = creditHKD;
    }

    public String getMemo() {
        return this.memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Integer getPostFlag() {
        return this.postFlag;
    }

    public void setPostFlag(Integer postFlag) {
        this.postFlag = postFlag;
    }

    public String getAdjType() {
        return this.adjType;
    }

    public void setAdjType(String adjType) {
        this.adjType = adjType;
    }

    public Integer getSrcType() {
        return this.srcType;
    }

    public void setSrcType(Integer srcType) {
        this.srcType = srcType;
    }

    public String getSrcID() {
        return this.srcID;
    }

    public void setSrcID(String srcID) {
        this.srcID = srcID;
    }

    public String getCreateUser() {
        return this.createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Date getCreateDate() {
        return this.createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getAreaCode() {
        return this.areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getYwbkCode() {
        return this.ywbkCode;
    }

    public void setYwbkCode(String ywbkCode) {
        this.ywbkCode = ywbkCode;
    }

    public String getGcywlxCode() {
        return this.gcywlxCode;
    }

    public void setGcywlxCode(String gcywlxCode) {
        this.gcywlxCode = gcywlxCode;
    }

    public String getTzyzmsCode() {
        return this.tzyzmsCode;
    }

    public void setTzyzmsCode(String tzyzmsCode) {
        this.tzyzmsCode = tzyzmsCode;
    }

    public String getProjectTitle() {
        return this.projectTitle;
    }

    public void setProjectTitle(String projectTitle) {
        this.projectTitle = projectTitle;
    }
}

