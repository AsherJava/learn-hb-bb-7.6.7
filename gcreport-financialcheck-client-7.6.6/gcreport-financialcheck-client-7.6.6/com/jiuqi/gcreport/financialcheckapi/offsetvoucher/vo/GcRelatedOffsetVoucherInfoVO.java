/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.annotation.JsonSerialize
 */
package com.jiuqi.gcreport.financialcheckapi.offsetvoucher.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jiuqi.gcreport.financialcheckapi.utils.DoubleSerializer;
import java.util.Map;

public class GcRelatedOffsetVoucherInfoVO {
    private String id;
    private Map<String, Object> unitId;
    private Map<String, Object> offsetSubject;
    private Map<String, Object> dims;
    private String offsetMethod;
    @JsonSerialize(using=DoubleSerializer.class)
    private Double debitOffset;
    @JsonSerialize(using=DoubleSerializer.class)
    private Double creditOffset;
    @JsonSerialize(using=DoubleSerializer.class)
    private Double debitSrc;
    @JsonSerialize(using=DoubleSerializer.class)
    private Double creditSrc;
    private String remark;
    private Integer acctYear;
    private Integer acctPeriod;
    private String gcNumber;
    private String checkId;
    private Integer offsetPeriod;
    private String offsetPeriodStr;
    private String srcItemId;
    private Long recordTimestamp;
    private String oppUnitId;
    private String offsetCurrency;
    private String org;
    private String period;
    private String vchrNum;
    private String subjectCode;
    private String cfItemCode;
    @JsonSerialize(using=DoubleSerializer.class)
    private Double debit;
    @JsonSerialize(using=DoubleSerializer.class)
    private Double credit;
    private String digest;

    public String getOrg() {
        return this.org;
    }

    public void setOrg(String org) {
        this.org = org;
    }

    public String getPeriod() {
        return this.period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getVchrNum() {
        return this.vchrNum;
    }

    public void setVchrNum(String vchrNum) {
        this.vchrNum = vchrNum;
    }

    public String getSubjectCode() {
        return this.subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public Double getDebit() {
        return this.debit;
    }

    public void setDebit(Double debit) {
        this.debit = debit;
    }

    public Double getCredit() {
        return this.credit;
    }

    public void setCredit(Double credit) {
        this.credit = credit;
    }

    public String getDigest() {
        return this.digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    public Map<String, Object> getUnitId() {
        return this.unitId;
    }

    public void setUnitId(Map<String, Object> unitId) {
        this.unitId = unitId;
    }

    public Map<String, Object> getOffsetSubject() {
        return this.offsetSubject;
    }

    public void setOffsetSubject(Map<String, Object> offsetSubject) {
        this.offsetSubject = offsetSubject;
    }

    public String getOffsetMethod() {
        return this.offsetMethod;
    }

    public void setOffsetMethod(String offsetMethod) {
        this.offsetMethod = offsetMethod;
    }

    public Double getDebitOffset() {
        return this.debitOffset;
    }

    public void setDebitOffset(Double debitOffset) {
        this.debitOffset = debitOffset;
    }

    public Double getCreditOffset() {
        return this.creditOffset;
    }

    public void setCreditOffset(Double creditOffset) {
        this.creditOffset = creditOffset;
    }

    public Double getDebitSrc() {
        return this.debitSrc;
    }

    public void setDebitSrc(Double debitSrc) {
        this.debitSrc = debitSrc;
    }

    public Double getCreditSrc() {
        return this.creditSrc;
    }

    public void setCreditSrc(Double creditSrc) {
        this.creditSrc = creditSrc;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Map<String, Object> getDims() {
        return this.dims;
    }

    public void setDims(Map<String, Object> dims) {
        this.dims = dims;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getGcNumber() {
        return this.gcNumber;
    }

    public void setGcNumber(String gcNumber) {
        this.gcNumber = gcNumber;
    }

    public String getCheckId() {
        return this.checkId;
    }

    public void setCheckId(String checkId) {
        this.checkId = checkId;
    }

    public Integer getOffsetPeriod() {
        return this.offsetPeriod;
    }

    public void setOffsetPeriod(Integer offsetPeriod) {
        this.offsetPeriod = offsetPeriod;
    }

    public String getSrcItemId() {
        return this.srcItemId;
    }

    public void setSrcItemId(String srcItemId) {
        this.srcItemId = srcItemId;
    }

    public Long getRecordTimestamp() {
        return this.recordTimestamp;
    }

    public void setRecordTimestamp(Long recordTimestamp) {
        this.recordTimestamp = recordTimestamp;
    }

    public String getOppUnitId() {
        return this.oppUnitId;
    }

    public void setOppUnitId(String oppUnitId) {
        this.oppUnitId = oppUnitId;
    }

    public String getOffsetCurrency() {
        return this.offsetCurrency;
    }

    public void setOffsetCurrency(String offsetCurrency) {
        this.offsetCurrency = offsetCurrency;
    }

    public String getOffsetPeriodStr() {
        return this.offsetPeriodStr;
    }

    public void setOffsetPeriodStr(String offsetPeriodStr) {
        this.offsetPeriodStr = offsetPeriodStr;
    }

    public String getCfItemCode() {
        return this.cfItemCode;
    }

    public void setCfItemCode(String cfItemCode) {
        this.cfItemCode = cfItemCode;
    }

    public String toString() {
        return "GcRelatedOffsetVoucherInfoVO{id='" + this.id + '\'' + ", org=" + this.org + ", acctPeriod=" + this.acctPeriod + ", vchrNum='" + this.vchrNum + '\'' + ", subjectCode='" + this.subjectCode + '\'' + ", debit=" + this.debit + ", credit=" + this.credit + ", digest='" + this.digest + '\'' + ", unitId=" + this.unitId + ", offsetSubject=" + this.offsetSubject + ", dims=" + this.dims + ", offsetMethod='" + this.offsetMethod + '\'' + ", debitOffset=" + this.debitOffset + ", creditOffset=" + this.creditOffset + ", debitSrc=" + this.debitSrc + ", creditSrc=" + this.creditSrc + ", remark='" + this.remark + '\'' + '}';
    }
}

