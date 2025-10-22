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

@DBTable(name="GC_JOURNAL_SINGLE", title="\u65e5\u8bb0\u8d26\u5355\u6237\u5206\u5f55")
public class JournalSingleEO
extends DefaultTableEntity {
    private static final long serialVersionUID = 1L;
    public static final String TABLE_NAME = "GC_JOURNAL_SINGLE";
    @DBColumn(length=36, nameInDB="MRECID", dbType=DBColumn.DBType.Varchar)
    private String mRecid;
    @DBColumn(length=36, nameInDB="TASKID", dbType=DBColumn.DBType.Varchar)
    private String taskId;
    @DBColumn(length=36, nameInDB="SCHEMEID", dbType=DBColumn.DBType.Varchar)
    private String schemeId;
    @DBColumn(length=36, nameInDB="FORMID", dbType=DBColumn.DBType.Varchar)
    private String formId;
    @DBColumn(length=100, nameInDB="ADJUST", title="\u8c03\u6574\u671f", dbType=DBColumn.DBType.NVarchar)
    private String adjust;
    @DBColumn(length=20, nameInDB="EFFECTTYPE", dbType=DBColumn.DBType.NVarchar)
    private String effectType;
    @DBColumn(length=20, nameInDB="ADJUSTTYPECODE", dbType=DBColumn.DBType.NVarchar)
    private String adjustTypeCode;
    @DBColumn(nameInDB="ACCTYEAR", dbType=DBColumn.DBType.Int)
    private Integer acctYear;
    @DBColumn(nameInDB="ACCTPERIOD", dbType=DBColumn.DBType.Int)
    private Integer acctPeriod;
    @DBColumn(nameInDB="ENTERPERIOD", title="\u5f55\u5165\u671f\u95f4", dbType=DBColumn.DBType.Int)
    private Integer enterPeriod;
    @DBColumn(length=20, nameInDB="DEFAULT_PERIOD", dbType=DBColumn.DBType.NVarchar)
    private String defaultPeriod;
    @DBColumn(length=36, nameInDB="INPUTUNITID", dbType=DBColumn.DBType.Varchar)
    private String inputUnitId;
    @DBColumn(nameInDB="MD_GCORGTYPE", title="\u5355\u4f4d\u7c7b\u578b", dbType=DBColumn.DBType.Varchar, length=36, isRequired=true)
    private String orgType;
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
    @DBColumn(nameInDB="CREATETIME", dbType=DBColumn.DBType.Date)
    @Temporal(value=TemporalType.TIMESTAMP)
    private Date createTime;

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

    public String getOrgType() {
        return this.orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
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

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getFormId() {
        return this.formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public Integer getEnterPeriod() {
        return this.enterPeriod;
    }

    public void setEnterPeriod(Integer enterPeriod) {
        this.enterPeriod = enterPeriod;
    }

    public String getAdjust() {
        return this.adjust;
    }

    public void setAdjust(String adjust) {
        this.adjust = adjust;
    }
}

