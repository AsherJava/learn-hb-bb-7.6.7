/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFormat
 *  com.fasterxml.jackson.databind.annotation.JsonSerialize
 *  com.jiuqi.gcreport.basedata.api.itree.GcBaseDataVO
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 */
package com.jiuqi.gcreport.financialcheckapi.dataentry.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jiuqi.gcreport.basedata.api.itree.GcBaseDataVO;
import com.jiuqi.gcreport.financialcheckapi.common.vo.DimBaseDataVO;
import com.jiuqi.gcreport.financialcheckapi.utils.DoubleSerializer;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import java.util.Date;
import java.util.Map;

public class GcRelatedItemVO {
    private String id;
    private Long recver;
    private String subjectCode;
    private String subjectName;
    private GcBaseDataVO subjectVo;
    private String unitId;
    private String unitName;
    private GcOrgCacheVO unitVo;
    private String oppUnitId;
    private String oppUnitName;
    private GcOrgCacheVO oppUnitVo;
    private Integer acctYear;
    private Integer acctPeriod;
    @JsonSerialize(using=DoubleSerializer.class)
    private Double debit;
    @JsonSerialize(using=DoubleSerializer.class)
    private Double credit;
    private String currency;
    private String currencyName;
    @JsonSerialize(using=DoubleSerializer.class)
    private Double debitOrig;
    @JsonSerialize(using=DoubleSerializer.class)
    private Double creditOrig;
    private String originalCurr;
    private String originalCurrName;
    private String chkCurr;
    private String chkCurrName;
    private String chkState;
    private Double chkAmtD;
    private Double chkAmtC;
    private Double chkDiffD;
    private Double chkDiffC;
    private String checkId;
    private Date checkTime;
    private Integer checkYear;
    private Integer checkPeriod;
    private String checker;
    private String checkType;
    private String checkMode;
    private String checkRuleId;
    private String checkRuleName;
    private String vchrSourceType;
    private String vchrSourceTypeCode;
    private String gcNumber;
    private String srcItemId;
    @JsonFormat(pattern="yyyy-MM-dd", timezone="GMT+8")
    private Date createDate;
    private String inputWay;
    private Date createTime;
    private String createUser;
    private String memo;
    private String itemOrder;
    private String digest;
    private String vchrType;
    private String vchrNum;
    private Integer orient;
    private String createDateStr;
    private Double chkAmt;
    private Double chkDiff;
    private String unCheckReasonType;
    private String unCheckReasonTypeCode;
    private String unCheckReason;
    private String billCode;
    private Long recordTimestamp = System.currentTimeMillis();
    private Map<String, DimBaseDataVO> dimBaseData;
    private Map<String, Object> dimensionCode;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getRecver() {
        return this.recver;
    }

    public void setRecver(Long recver) {
        this.recver = recver;
    }

    public String getSubjectCode() {
        return this.subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getSubjectName() {
        return this.subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public GcBaseDataVO getSubjectVo() {
        return this.subjectVo;
    }

    public void setSubjectVo(GcBaseDataVO subjectVo) {
        this.subjectVo = subjectVo;
    }

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

    public GcOrgCacheVO getUnitVo() {
        return this.unitVo;
    }

    public void setUnitVo(GcOrgCacheVO unitVo) {
        this.unitVo = unitVo;
    }

    public String getOppUnitId() {
        return this.oppUnitId;
    }

    public void setOppUnitId(String oppUnitId) {
        this.oppUnitId = oppUnitId;
    }

    public String getOppUnitName() {
        return this.oppUnitName;
    }

    public void setOppUnitName(String oppUnitName) {
        this.oppUnitName = oppUnitName;
    }

    public GcOrgCacheVO getOppUnitVo() {
        return this.oppUnitVo;
    }

    public void setOppUnitVo(GcOrgCacheVO oppUnitVo) {
        this.oppUnitVo = oppUnitVo;
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

    public String getCurrency() {
        return this.currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCurrencyName() {
        return this.currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
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

    public String getOriginalCurr() {
        return this.originalCurr;
    }

    public void setOriginalCurr(String originalCurr) {
        this.originalCurr = originalCurr;
    }

    public String getOriginalCurrName() {
        return this.originalCurrName;
    }

    public void setOriginalCurrName(String originalCurrName) {
        this.originalCurrName = originalCurrName;
    }

    public String getChkCurr() {
        return this.chkCurr;
    }

    public void setChkCurr(String chkCurr) {
        this.chkCurr = chkCurr;
    }

    public String getChkCurrName() {
        return this.chkCurrName;
    }

    public void setChkCurrName(String chkCurrName) {
        this.chkCurrName = chkCurrName;
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

    public Double getChkDiffD() {
        return this.chkDiffD;
    }

    public void setChkDiffD(Double chkDiffD) {
        this.chkDiffD = chkDiffD;
    }

    public Double getChkDiffC() {
        return this.chkDiffC;
    }

    public void setChkDiffC(Double chkDiffC) {
        this.chkDiffC = chkDiffC;
    }

    public String getCheckId() {
        return this.checkId;
    }

    public void setCheckId(String checkId) {
        this.checkId = checkId;
    }

    public Date getCheckTime() {
        return this.checkTime;
    }

    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
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

    public String getChecker() {
        return this.checker;
    }

    public void setChecker(String checker) {
        this.checker = checker;
    }

    public String getCheckType() {
        return this.checkType;
    }

    public void setCheckType(String checkType) {
        this.checkType = checkType;
    }

    public String getCheckMode() {
        return this.checkMode;
    }

    public void setCheckMode(String checkMode) {
        this.checkMode = checkMode;
    }

    public String getCheckRuleId() {
        return this.checkRuleId;
    }

    public void setCheckRuleId(String checkRuleId) {
        this.checkRuleId = checkRuleId;
    }

    public String getGcNumber() {
        return this.gcNumber;
    }

    public void setGcNumber(String gcNumber) {
        this.gcNumber = gcNumber;
    }

    public String getSrcItemId() {
        return this.srcItemId;
    }

    public void setSrcItemId(String srcItemId) {
        this.srcItemId = srcItemId;
    }

    public Date getCreateDate() {
        return this.createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
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

    public String getMemo() {
        return this.memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getItemOrder() {
        return this.itemOrder;
    }

    public void setItemOrder(String itemOrder) {
        this.itemOrder = itemOrder;
    }

    public String getDigest() {
        return this.digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
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

    public Integer getOrient() {
        return this.orient;
    }

    public void setOrient(Integer orient) {
        this.orient = orient;
    }

    public String getCreateDateStr() {
        return this.createDateStr;
    }

    public void setCreateDateStr(String createDateStr) {
        this.createDateStr = createDateStr;
    }

    public Double getChkAmt() {
        return this.chkAmt;
    }

    public void setChkAmt(Double chkAmt) {
        this.chkAmt = chkAmt;
    }

    public Double getChkDiff() {
        return this.chkDiff;
    }

    public void setChkDiff(Double chkDiff) {
        this.chkDiff = chkDiff;
    }

    public Map<String, Object> getDimensionCode() {
        return this.dimensionCode;
    }

    public void setDimensionCode(Map<String, Object> dimensionCode) {
        this.dimensionCode = dimensionCode;
    }

    public Map<String, DimBaseDataVO> getDimBaseData() {
        return this.dimBaseData;
    }

    public void setDimBaseData(Map<String, DimBaseDataVO> dimBaseData) {
        this.dimBaseData = dimBaseData;
    }

    public String getCheckRuleName() {
        return this.checkRuleName;
    }

    public String getVchrSourceType() {
        return this.vchrSourceType;
    }

    public void setCheckRuleName(String checkRuleName) {
        this.checkRuleName = checkRuleName;
    }

    public void setVchrSourceType(String vchrSourceType) {
        this.vchrSourceType = vchrSourceType;
    }

    public String getUnCheckReasonType() {
        return this.unCheckReasonType;
    }

    public void setUnCheckReasonType(String unCheckReasonType) {
        this.unCheckReasonType = unCheckReasonType;
    }

    public String getUnCheckReasonTypeCode() {
        return this.unCheckReasonTypeCode;
    }

    public void setUnCheckReasonTypeCode(String unCheckReasonTypeCode) {
        this.unCheckReasonTypeCode = unCheckReasonTypeCode;
    }

    public String getUnCheckReason() {
        return this.unCheckReason;
    }

    public void setUnCheckReason(String unCheckReason) {
        this.unCheckReason = unCheckReason;
    }

    public String getVchrSourceTypeCode() {
        return this.vchrSourceTypeCode;
    }

    public void setVchrSourceTypeCode(String vchrSourceTypeCode) {
        this.vchrSourceTypeCode = vchrSourceTypeCode;
    }

    public String getBillCode() {
        return this.billCode;
    }

    public void setBillCode(String billCode) {
        this.billCode = billCode;
    }

    public Long getRecordTimestamp() {
        return this.recordTimestamp;
    }

    public void setRecordTimestamp(Long recordTimestamp) {
        this.recordTimestamp = recordTimestamp;
    }

    public String toString() {
        return "VoucherItemVO{id='" + this.id + '\'' + ", recver=" + this.recver + ", subjectCode='" + this.subjectCode + '\'' + ", subjectName='" + this.subjectName + '\'' + ", subjectVo=" + this.subjectVo + ", unitId='" + this.unitId + '\'' + ", unitName='" + this.unitName + '\'' + ", unitVo=" + this.unitVo + ", oppUnitId='" + this.oppUnitId + '\'' + ", oppUnitName='" + this.oppUnitName + '\'' + ", oppUnitVo=" + this.oppUnitVo + ", acctYear=" + this.acctYear + ", acctPeriod=" + this.acctPeriod + ", debit=" + this.debit + ", credit=" + this.credit + ", currency='" + this.currency + '\'' + ", currencyName='" + this.currencyName + '\'' + ", debitOrig=" + this.debitOrig + ", creditOrig=" + this.creditOrig + ", originalCurr='" + this.originalCurr + '\'' + ", originalCurrName='" + this.originalCurrName + '\'' + ", chkCurr='" + this.chkCurr + '\'' + ", chkCurrName='" + this.chkCurrName + '\'' + ", chkState=" + this.chkState + ", chkAmtD=" + this.chkAmtD + ", chkAmtC=" + this.chkAmtC + ", chkDiffD=" + this.chkDiffD + ", chkDiffC=" + this.chkDiffC + ", checkId='" + this.checkId + '\'' + ", checkTime=" + this.checkTime + ", checkYear=" + this.checkYear + ", checkPeriod=" + this.checkPeriod + ", checker='" + this.checker + '\'' + ", checkType='" + this.checkType + '\'' + ", checkMode='" + this.checkMode + '\'' + ", checkRuleId='" + this.checkRuleId + '\'' + ", gcNumber='" + this.gcNumber + '\'' + ", srcItemId='" + this.srcItemId + '\'' + ", createDate=" + this.createDate + ", inputWay='" + this.inputWay + '\'' + ", createTime=" + this.createTime + ", createUser='" + this.createUser + '\'' + ", memo='" + this.memo + '\'' + ", itemOrder=" + this.itemOrder + ", digest='" + this.digest + '\'' + ", vchrType='" + this.vchrType + '\'' + ", vchrNum='" + this.vchrNum + '\'' + ", orient=" + this.orient + ", createDateStr='" + this.createDateStr + '\'' + ", chkAmt=" + this.chkAmt + ", chkDiff=" + this.chkDiff + ", dimBaseData=" + this.dimBaseData + ", dimensionCode=" + this.dimensionCode + '}';
    }
}

