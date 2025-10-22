/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.annotation.JsonSerialize
 */
package com.jiuqi.gcreport.financialcheckapi.check.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jiuqi.gcreport.financialcheckapi.utils.DoubleSerializer;
import java.util.Date;
import java.util.Map;

public class FinancialCheckQueryVO {
    private String id;
    private Integer acctYear;
    private Integer acctPeriod;
    private String subjectCode;
    private String unitId;
    private String unitKey;
    private String srcVchrId;
    private String oppUnitId;
    private String originalCurr;
    @JsonSerialize(using=DoubleSerializer.class)
    private Double debitOrig;
    @JsonSerialize(using=DoubleSerializer.class)
    private Double creditOrig;
    private Double debit;
    private Double credit;
    private String memo;
    private String gcNumber;
    private String vchrType;
    private String vchrNum;
    private String itemOrder;
    private Date createDate;
    private String srcItemId;
    private String digest;
    private String billCode;
    private String vchrSourceType;
    private String inputWay;
    private Date createTime;
    private String createUser;
    private String updateTime;
    private Long recordTimestamp;
    private String checkId;
    private String checkMode;
    private String checkType;
    private Integer checkYear;
    private Integer checkPeriod;
    private String checkRuleId;
    private Date checkTime;
    private String checker;
    private String chkCurr;
    private String chkState;
    private Double chkAmtD;
    private Double chkAmtC;
    private String checkProject;
    private Integer checkProjectDirection;
    private Integer businessRole;
    private String unitCombine;
    private String cfItemCode;
    private Double diffAmount;
    private String currency;
    private String currencyCode;
    private Double chkAmt;
    private String unCheckType;
    private String unCheckTypeCode;
    private String unCheckDesc;
    private String unitState;
    private Map<String, Object> dimensions;
    private Integer rowspan;
    private Integer index;

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

    public String getSubjectCode() {
        return this.subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
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

    public String getOriginalCurr() {
        return this.originalCurr;
    }

    public void setOriginalCurr(String originalCurr) {
        this.originalCurr = originalCurr;
    }

    public Double getDebitOrig() {
        return this.debitOrig;
    }

    public void setDebitOrig(Double debitOrig) {
        this.debitOrig = debitOrig;
    }

    public Double getCreditOrig() {
        return this.creditOrig;
    }

    public void setCreditOrig(Double creditOrig) {
        this.creditOrig = creditOrig;
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

    public String getMemo() {
        return this.memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
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

    public String getItemOrder() {
        return this.itemOrder;
    }

    public void setItemOrder(String itemOrder) {
        this.itemOrder = itemOrder;
    }

    public Date getCreateDate() {
        return this.createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getSrcItemId() {
        return this.srcItemId;
    }

    public void setSrcItemId(String srcItemId) {
        this.srcItemId = srcItemId;
    }

    public String getDigest() {
        return this.digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    public String getBillCode() {
        return this.billCode;
    }

    public void setBillCode(String billCode) {
        this.billCode = billCode;
    }

    public String getVchrSourceType() {
        return this.vchrSourceType;
    }

    public void setVchrSourceType(String vchrSourceType) {
        this.vchrSourceType = vchrSourceType;
    }

    public String getInputWay() {
        return this.inputWay;
    }

    public void setInputWay(String inputWay) {
        this.inputWay = inputWay;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateUser() {
        return this.createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public Long getRecordTimestamp() {
        return this.recordTimestamp;
    }

    public void setRecordTimestamp(Long recordTimestamp) {
        this.recordTimestamp = recordTimestamp;
    }

    public String getCheckId() {
        return this.checkId;
    }

    public void setCheckId(String checkId) {
        this.checkId = checkId;
    }

    public String getCheckMode() {
        return this.checkMode;
    }

    public void setCheckMode(String checkMode) {
        this.checkMode = checkMode;
    }

    public String getCheckType() {
        return this.checkType;
    }

    public void setCheckType(String checkType) {
        this.checkType = checkType;
    }

    public Integer getCheckYear() {
        return this.checkYear;
    }

    public void setCheckYear(Integer checkYear) {
        this.checkYear = checkYear;
    }

    public Integer getCheckPeriod() {
        return this.checkPeriod;
    }

    public void setCheckPeriod(Integer checkPeriod) {
        this.checkPeriod = checkPeriod;
    }

    public String getCheckRuleId() {
        return this.checkRuleId;
    }

    public void setCheckRuleId(String checkRuleId) {
        this.checkRuleId = checkRuleId;
    }

    public Date getCheckTime() {
        return this.checkTime;
    }

    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
    }

    public String getChecker() {
        return this.checker;
    }

    public void setChecker(String checker) {
        this.checker = checker;
    }

    public String getChkCurr() {
        return this.chkCurr;
    }

    public void setChkCurr(String chkCurr) {
        this.chkCurr = chkCurr;
    }

    public String getChkState() {
        return this.chkState;
    }

    public void setChkState(String chkState) {
        this.chkState = chkState;
    }

    public Double getChkAmtD() {
        return this.chkAmtD;
    }

    public void setChkAmtD(Double chkAmtD) {
        this.chkAmtD = chkAmtD;
    }

    public Double getChkAmtC() {
        return this.chkAmtC;
    }

    public void setChkAmtC(Double chkAmtC) {
        this.chkAmtC = chkAmtC;
    }

    public String getCheckProject() {
        return this.checkProject;
    }

    public void setCheckProject(String checkProject) {
        this.checkProject = checkProject;
    }

    public Integer getCheckProjectDirection() {
        return this.checkProjectDirection;
    }

    public void setCheckProjectDirection(Integer checkProjectDirection) {
        this.checkProjectDirection = checkProjectDirection;
    }

    public Integer getBusinessRole() {
        return this.businessRole;
    }

    public void setBusinessRole(Integer businessRole) {
        this.businessRole = businessRole;
    }

    public String getUnitCombine() {
        return this.unitCombine;
    }

    public void setUnitCombine(String unitCombine) {
        this.unitCombine = unitCombine;
    }

    public Double getDiffAmount() {
        return this.diffAmount;
    }

    public void setDiffAmount(Double diffAmount) {
        this.diffAmount = diffAmount;
    }

    public String getCurrency() {
        return this.currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Double getChkAmt() {
        return this.chkAmt;
    }

    public void setChkAmt(Double chkAmt) {
        this.chkAmt = chkAmt;
    }

    public String getUnCheckType() {
        return this.unCheckType;
    }

    public void setUnCheckType(String unCheckType) {
        this.unCheckType = unCheckType;
    }

    public String getUnCheckDesc() {
        return this.unCheckDesc;
    }

    public void setUnCheckDesc(String unCheckDesc) {
        this.unCheckDesc = unCheckDesc;
    }

    public Map<String, Object> getDimensions() {
        return this.dimensions;
    }

    public void setDimensions(Map<String, Object> dimensions) {
        this.dimensions = dimensions;
    }

    public Integer getRowspan() {
        return this.rowspan;
    }

    public void setRowspan(Integer rowspan) {
        this.rowspan = rowspan;
    }

    public Integer getIndex() {
        return this.index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getUnCheckTypeCode() {
        return this.unCheckTypeCode;
    }

    public void setUnCheckTypeCode(String unCheckTypeCode) {
        this.unCheckTypeCode = unCheckTypeCode;
    }

    public String getUnitKey() {
        return this.unitKey;
    }

    public void setUnitKey(String unitKey) {
        this.unitKey = unitKey;
    }

    public String getSrcVchrId() {
        return this.srcVchrId;
    }

    public void setSrcVchrId(String srcVchrId) {
        this.srcVchrId = srcVchrId;
    }

    public String getCurrencyCode() {
        return this.currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getUnitState() {
        return this.unitState;
    }

    public void setUnitState(String unitState) {
        this.unitState = unitState;
    }

    public String getCfItemCode() {
        return this.cfItemCode;
    }

    public void setCfItemCode(String cfItemCode) {
        this.cfItemCode = cfItemCode;
    }
}

