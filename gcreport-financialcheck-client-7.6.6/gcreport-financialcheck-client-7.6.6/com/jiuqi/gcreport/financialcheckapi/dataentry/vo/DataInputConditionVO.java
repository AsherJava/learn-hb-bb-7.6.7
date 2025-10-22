/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 */
package com.jiuqi.gcreport.financialcheckapi.dataentry.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties
public class DataInputConditionVO {
    private String orgType;
    private String queryType;
    private String unitId;
    private List<String> unitIds;
    private String oppUnitId;
    private List<String> oppUnitIds;
    private List<String> subjectCodes;
    private String gcNumber;
    private String vchrType;
    private String vchrNum;
    private String currencyCode;
    private Double debitCNYFrom;
    private Double debitCNYTo;
    private Double creditCNYFrom;
    private Double creditCNYTo;
    private String digest;
    private String createUser;
    private Integer acctYear;
    private Integer acctPeriod;
    private Integer allVchrPageNum = 1;
    private Integer allVchrPageSize = Integer.MAX_VALUE;
    private Integer uncheckedPageNum = 1;
    private Integer uncheckedPageSize = Integer.MAX_VALUE;
    private Integer checkedPageNum = 1;
    private Integer checkedPageSize = Integer.MAX_VALUE;
    private Integer oppUncheckedPageNum = 1;
    private Integer oppUncheckedPageSize = Integer.MAX_VALUE;

    public String getQueryType() {
        return this.queryType;
    }

    public void setQueryType(String queryType) {
        this.queryType = queryType;
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

    public List<String> getSubjectCodes() {
        return this.subjectCodes;
    }

    public void setSubjectCodes(List<String> subjectCodes) {
        this.subjectCodes = subjectCodes;
    }

    public String getGcNumber() {
        return this.gcNumber;
    }

    public void setGcNumber(String gcNumber) {
        this.gcNumber = gcNumber;
    }

    public String getVchrType() {
        return this.vchrType;
    }

    public void setVchrType(String vchrType) {
        this.vchrType = vchrType;
    }

    public String getVchrNum() {
        return this.vchrNum;
    }

    public void setVchrNum(String vchrNum) {
        this.vchrNum = vchrNum;
    }

    public Double getDebitCNYFrom() {
        return this.debitCNYFrom;
    }

    public void setDebitCNYFrom(Double debitCNYFrom) {
        this.debitCNYFrom = debitCNYFrom;
    }

    public Double getDebitCNYTo() {
        return this.debitCNYTo;
    }

    public void setDebitCNYTo(Double debitCNYTo) {
        this.debitCNYTo = debitCNYTo;
    }

    public Double getCreditCNYFrom() {
        return this.creditCNYFrom;
    }

    public void setCreditCNYFrom(Double creditCNYFrom) {
        this.creditCNYFrom = creditCNYFrom;
    }

    public Double getCreditCNYTo() {
        return this.creditCNYTo;
    }

    public void setCreditCNYTo(Double creditCNYTo) {
        this.creditCNYTo = creditCNYTo;
    }

    public String getDigest() {
        return this.digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    public String getCreateUser() {
        return this.createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
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

    public Integer getAllVchrPageNum() {
        return this.allVchrPageNum;
    }

    public void setAllVchrPageNum(Integer allVchrPageNum) {
        this.allVchrPageNum = allVchrPageNum == null ? Integer.valueOf(1) : allVchrPageNum;
    }

    public Integer getAllVchrPageSize() {
        return this.allVchrPageSize;
    }

    public void setAllVchrPageSize(Integer allVchrPageSize) {
        this.allVchrPageSize = allVchrPageSize == null ? Integer.MAX_VALUE : allVchrPageSize;
    }

    public Integer getUncheckedPageNum() {
        return this.uncheckedPageNum;
    }

    public void setUncheckedPageNum(Integer uncheckedPageNum) {
        this.uncheckedPageNum = uncheckedPageNum == null ? Integer.valueOf(1) : uncheckedPageNum;
    }

    public Integer getUncheckedPageSize() {
        return this.uncheckedPageSize;
    }

    public void setUncheckedPageSize(Integer uncheckedPageSize) {
        this.uncheckedPageSize = uncheckedPageSize == null ? Integer.MAX_VALUE : uncheckedPageSize;
    }

    public Integer getCheckedPageNum() {
        return this.checkedPageNum;
    }

    public void setCheckedPageNum(Integer checkedPageNum) {
        this.checkedPageNum = checkedPageNum == null ? Integer.valueOf(1) : checkedPageNum;
    }

    public Integer getCheckedPageSize() {
        return this.checkedPageSize;
    }

    public void setCheckedPageSize(Integer checkedPageSize) {
        this.checkedPageSize = checkedPageSize == null ? Integer.MAX_VALUE : checkedPageSize;
    }

    public Integer getOppUncheckedPageNum() {
        return this.oppUncheckedPageNum;
    }

    public void setOppUncheckedPageNum(Integer oppUncheckedPageNum) {
        this.oppUncheckedPageNum = oppUncheckedPageNum == null ? Integer.valueOf(0) : oppUncheckedPageNum;
    }

    public Integer getOppUncheckedPageSize() {
        return this.oppUncheckedPageSize;
    }

    public void setOppUncheckedPageSize(Integer oppUncheckedPageSize) {
        this.oppUncheckedPageSize = oppUncheckedPageSize == null ? Integer.MAX_VALUE : oppUncheckedPageSize;
    }

    public Integer getAllVchrStartPosition() {
        return (this.allVchrPageNum - 1) * this.allVchrPageSize;
    }

    public Integer getUncheckedStartPosition() {
        return (this.uncheckedPageNum - 1) * this.uncheckedPageSize;
    }

    public Integer getcheckedStartPosition() {
        return (this.checkedPageNum - 1) * this.checkedPageSize;
    }

    public Integer getOppUncheckedStartPosition() {
        return (this.oppUncheckedPageNum - 1) * this.oppUncheckedPageSize;
    }

    public List<String> getUnitIds() {
        return this.unitIds;
    }

    public void setUnitIds(List<String> unitIds) {
        this.unitIds = unitIds;
    }

    public List<String> getOppUnitIds() {
        return this.oppUnitIds;
    }

    public void setOppUnitIds(List<String> oppUnitIds) {
        this.oppUnitIds = oppUnitIds;
    }

    public String getCurrencyCode() {
        return this.currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getOrgVer() {
        String dateStr = String.format("%04d", this.acctYear) + String.format("%02d", this.acctPeriod) + "01";
        return dateStr;
    }

    public String getOrgType() {
        return this.orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }
}

