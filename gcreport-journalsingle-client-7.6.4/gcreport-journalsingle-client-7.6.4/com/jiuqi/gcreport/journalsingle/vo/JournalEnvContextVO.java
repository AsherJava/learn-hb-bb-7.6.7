/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 */
package com.jiuqi.gcreport.journalsingle.vo;

import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import java.util.Date;

public class JournalEnvContextVO
extends DefaultTableEntity {
    private static final long serialVersionUID = -8108075418607082864L;
    public static final String GC_JOURNAL = "GC_JOURNAL";
    private String mRecid;
    private String taskId;
    private String schemeId;
    private String unionRuleId;
    private String gcBusinessTypeCode;
    private String effectType;
    private String adjustTypeCode;
    private Integer acctYear;
    private Integer acctPeriod;
    private Integer elmMode;
    private String defaultPeriod;
    private String inputUnitId;
    private String unitId;
    private String oppUnitId;
    private String orgType;
    private String systemId;
    private Integer offSetSrcType;
    private Integer sortOrder;
    private String subjectCode;
    private Integer dc;
    private String currencyCode;
    private Double debitCNY;
    private Double creditCNY;
    private Double debitUSD;
    private Double creditUSD;
    private Double debitHKD;
    private Double creditHKD;
    private String memo;
    private boolean postFlag;
    private String adjType;
    private Integer srcType;
    private String srcID;
    private String createUser;
    private Date createDate;
    private Date createTime;
    private String areaCode;
    private String ywbkCode;
    private String gcywlxCode;
    private String tzyzmsCode;
    private String projectTitle;

    public String getTableName() {
        return GC_JOURNAL;
    }

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

    public boolean isPostFlag() {
        return this.postFlag;
    }

    public void setPostFlag(boolean postFlag) {
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

