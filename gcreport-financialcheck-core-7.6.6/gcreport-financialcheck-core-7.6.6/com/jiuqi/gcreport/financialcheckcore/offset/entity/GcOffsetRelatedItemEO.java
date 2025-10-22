/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn$DBType
 *  com.jiuqi.gcreport.definition.impl.anno.DBIndex
 *  com.jiuqi.gcreport.definition.impl.anno.DBIndex$TableIndexType
 *  com.jiuqi.gcreport.definition.impl.anno.DBTable
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 */
package com.jiuqi.gcreport.financialcheckcore.offset.entity;

import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBIndex;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import java.util.Date;

@DBTable(name="GC_OFFSETRELATEDITEM", title="\u62b5\u9500\u5173\u8054\u4ea4\u6613\u5206\u5f55\u8868", indexs={@DBIndex(name="IDX_ORIE_OFFSETGROUPID", columnsFields={"OFFSETGROUPID"}), @DBIndex(name="IDX_ORIE_COM", columnsFields={"RELATEDITEMID", "CHECKSTATE"}), @DBIndex(name="IDX_ORIE_UNIQUE", columnsFields={"DATATIME", "RELATEDITEMID", "SYSTEMID"}, type=DBIndex.TableIndexType.TABLE_INDEX_UNIQUE)})
public class GcOffsetRelatedItemEO
extends DefaultTableEntity {
    public static final String TABLENAME = "GC_OFFSETRELATEDITEM";
    @DBColumn(title="\u62b5\u9500\u5206\u5f55\u5206\u7ec4ID", dbType=DBColumn.DBType.Varchar)
    private String offsetGroupId;
    @DBColumn(nameInDB="OFFSETTIME", title="\u62b5\u9500\u65e5\u671f", dbType=DBColumn.DBType.DateTime)
    private Date offsetTime;
    @DBColumn(nameInDB="OFFSETPERSON", title="\u62b5\u9500\u4eba", dbType=DBColumn.DBType.NVarchar, length=100)
    private String offsetPerson;
    @DBColumn(title="\u5173\u8054\u4ea4\u6613\u5206\u5f55\u4e3b\u952e", dbType=DBColumn.DBType.Varchar, isRequired=true)
    private String relatedItemId;
    @DBColumn(title="\u5bf9\u8d26\u72b6\u6001", dbType=DBColumn.DBType.Varchar, length=20, isRequired=true)
    private String checkState;
    @DBColumn(nameInDB="UNIONRULEID", title="\u5408\u5e76\u89c4\u5219ID", dbType=DBColumn.DBType.Varchar, length=36)
    private String unionRuleId;
    @DBColumn(length=36, nameInDB="SYSTEMID", title="\u5408\u5e76\u4f53\u7cfbID", dbType=DBColumn.DBType.Varchar, isRequired=true)
    private String systemId;
    @DBColumn(length=20, nameInDB="DATATIME", title="\u65f6\u671f", dbType=DBColumn.DBType.NVarchar, isRequired=true)
    private String dataTime;
    @DBColumn(nameInDB="RTOFFSETCANDEL", title="\u5b9e\u65f6\u62b5\u9500\u5141\u8bb8\u5220\u9664", dbType=DBColumn.DBType.Numeric, precision=1, scale=0)
    private Integer rtOffsetCanDel;
    @DBColumn(nameInDB="GCUNITID", title="\u5408\u5e76\u672c\u65b9\u5355\u4f4d", dbType=DBColumn.DBType.NVarchar)
    private String gcUnitId;
    @DBColumn(nameInDB="GCOPPUNITID", title="\u5408\u5e76\u5bf9\u65b9\u5355\u4f4d", dbType=DBColumn.DBType.NVarchar)
    private String gcOppUnitId;
    @DBColumn(length=200, nameInDB="GCSUBJECTCODE", title="\u5408\u5e76\u79d1\u76ee", dbType=DBColumn.DBType.NVarchar)
    private String gcSubjectCode;
    @DBColumn(length=100, nameInDB="MATCHINGINFORMATION", title="\u5339\u914d\u4fe1\u606f", dbType=DBColumn.DBType.NVarchar)
    private String matchingInformation;
    @DBColumn(nameInDB="SUBJECTCODE", title="\u79d1\u76ee", dbType=DBColumn.DBType.NVarchar, length=200)
    private String subjectCode;
    @DBColumn(nameInDB="UNITID", title="\u672c\u65b9\u5355\u4f4d", dbType=DBColumn.DBType.Varchar, length=36)
    private String unitId;
    @DBColumn(nameInDB="OPPUNITID", title="\u5bf9\u65b9\u5355\u4f4d", dbType=DBColumn.DBType.Varchar, length=36)
    private String oppUnitId;
    @DBColumn(nameInDB="ORIGINALCURR", title="\u539f\u5e01\u5e01\u79cd", dbType=DBColumn.DBType.NVarchar, length=50)
    private String originalCurr;
    @DBColumn(nameInDB="DEBITORIG", title="\u501f\u65b9\u91d1\u989d\uff08\u539f\u5e01\uff09", dbType=DBColumn.DBType.Numeric, precision=19, scale=2)
    private Double debitOrig;
    @DBColumn(nameInDB="CREDITORIG", title="\u8d37\u65b9\u91d1\u989d\uff08\u539f\u5e01\uff09", dbType=DBColumn.DBType.Numeric, precision=19, scale=2)
    private Double creditOrig;
    @DBColumn(nameInDB="DEBIT", title="\u501f\u65b9\u91d1\u989d\uff08\u672c\u4f4d\u5e01\uff09", dbType=DBColumn.DBType.Numeric, precision=19, scale=2)
    private Double debit;
    @DBColumn(nameInDB="CREDIT", title="\u8d37\u65b9\u91d1\u989d\uff08\u672c\u4f4d\u5e01\uff09", dbType=DBColumn.DBType.Numeric, precision=19, scale=2)
    private Double credit;
    @DBColumn(nameInDB="MEMO", title="\u5907\u6ce8", dbType=DBColumn.DBType.NVarchar, length=300)
    private String memo;
    @DBColumn(title="\u65f6\u95f4\u6233", dbType=DBColumn.DBType.Numeric, precision=19, scale=0)
    private Long recordTimestamp = System.currentTimeMillis();
    @DBColumn(title="\u6e90\u8868\u65f6\u95f4\u6233", dbType=DBColumn.DBType.Numeric, precision=19, scale=0)
    private Long srcTimestamp = System.currentTimeMillis();
    @DBColumn(nameInDB="CHECKID", title="\u5bf9\u8d26\u5206\u7ec4\u6807\u8bc6", dbType=DBColumn.DBType.Varchar, length=36)
    private String checkId;
    @DBColumn(nameInDB="CHECKMODE", title="\u6838\u5bf9\u65b9\u5f0f", dbType=DBColumn.DBType.Varchar, length=60)
    private String checkMode;
    @DBColumn(nameInDB="CHECKTYPE", title="\u5bf9\u8d26\u7c7b\u578b", dbType=DBColumn.DBType.NVarchar, length=50)
    private String checkType;
    @DBColumn(nameInDB="CHECKYEAR", title="\u5bf9\u8d26\u5e74\u5ea6", dbType=DBColumn.DBType.Int)
    private Integer checkYear;
    @DBColumn(nameInDB="CHECKPERIOD", title="\u5bf9\u8d26\u671f\u95f4", dbType=DBColumn.DBType.Int)
    private Integer checkPeriod;
    @DBColumn(nameInDB="CHECKRULEID", title="\u5bf9\u8d26\u89c4\u5219\u6807\u8bc6", dbType=DBColumn.DBType.Varchar, length=36)
    private String checkRuleId;
    @DBColumn(nameInDB="CHECKTIME", title="\u5bf9\u8d26\u65e5\u671f", dbType=DBColumn.DBType.DateTime)
    private Date checkTime;
    @DBColumn(nameInDB="CHECKER", title="\u5bf9\u8d26\u4eba", dbType=DBColumn.DBType.NVarchar, length=50)
    private String checker;
    @DBColumn(nameInDB="CHKCURR", title="\u5bf9\u8d26\u5e01\u79cd", dbType=DBColumn.DBType.NVarchar, length=50)
    private String chkCurr;
    @DBColumn(nameInDB="CHKAMTD", title="\u501f\u65b9\u5bf9\u8d26\u91d1\u989d", dbType=DBColumn.DBType.Numeric, precision=19, scale=2)
    private Double chkAmtD;
    @DBColumn(nameInDB="CHKAMTC", title="\u8d37\u65b9\u5bf9\u8d26\u91d1\u989d", dbType=DBColumn.DBType.Numeric, precision=19, scale=2)
    private Double chkAmtC;
    @DBColumn(nameInDB="DEBITCONVERSIONVALUE", title="\u501f\u65b9\u6298\u7b97\u91d1\u989d", dbType=DBColumn.DBType.Numeric, precision=19, scale=2)
    private Double debitConversionValue;
    @DBColumn(nameInDB="CREDITCONVERSIONVALUE", title="\u8d37\u65b9\u6298\u7b97\u91d1\u989d", dbType=DBColumn.DBType.Numeric, precision=19, scale=2)
    private Double creditConversionValue;
    @DBColumn(nameInDB="CONVERSIONCURR", title="\u6298\u7b97\u540e\u5e01\u79cd", dbType=DBColumn.DBType.NVarchar, length=50)
    private String conversionCurr;
    @DBColumn(nameInDB="AMT", title="\u91d1\u989d", dbType=DBColumn.DBType.Numeric, scale=2)
    private Double amt;
    @DBColumn(nameInDB="ADJUST", title="\u8c03\u6574\u671f", dbType=DBColumn.DBType.NVarchar, length=100)
    private String adjust;
    @DBColumn(nameInDB="CURRENCY", title="\u672c\u4f4d\u5e01\u79cd", dbType=DBColumn.DBType.NVarchar, length=50)
    private String currency;
    @DBColumn(nameInDB="CONVERSIONRATE", title="\u6298\u7b97\u6c47\u7387", dbType=DBColumn.DBType.Numeric, precision=19, scale=6)
    private Double conversionRate;

    public Double getAmt() {
        return this.amt;
    }

    public void setAmt(Double amt) {
        this.amt = amt;
    }

    public Double getDebitConversionValue() {
        return this.debitConversionValue;
    }

    public void setDebitConversionValue(Double debitConversionValue) {
        this.debitConversionValue = debitConversionValue;
    }

    public Double getCreditConversionValue() {
        return this.creditConversionValue;
    }

    public void setCreditConversionValue(Double creditConversionValue) {
        this.creditConversionValue = creditConversionValue;
    }

    public String getConversionCurr() {
        return this.conversionCurr;
    }

    public void setConversionCurr(String conversionCurr) {
        this.conversionCurr = conversionCurr;
    }

    public String getMatchingInformation() {
        return this.matchingInformation;
    }

    public void setMatchingInformation(String matchingInformation) {
        this.matchingInformation = matchingInformation;
    }

    public String getOffsetGroupId() {
        return this.offsetGroupId;
    }

    public void setOffsetGroupId(String offsetGroupId) {
        this.offsetGroupId = offsetGroupId;
    }

    public String getRelatedItemId() {
        return this.relatedItemId;
    }

    public void setRelatedItemId(String relatedItemId) {
        this.relatedItemId = relatedItemId;
    }

    public String getCheckState() {
        return this.checkState;
    }

    public void setCheckState(String checkState) {
        this.checkState = checkState;
    }

    public String getUnionRuleId() {
        return this.unionRuleId;
    }

    public void setUnionRuleId(String unionRuleId) {
        this.unionRuleId = unionRuleId;
    }

    public String getSystemId() {
        return this.systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public String getDataTime() {
        return this.dataTime;
    }

    public void setDataTime(String dataTime) {
        this.dataTime = dataTime;
    }

    public String getGcUnitId() {
        return this.gcUnitId;
    }

    public void setGcUnitId(String gcUnitId) {
        this.gcUnitId = gcUnitId;
    }

    public String getGcOppUnitId() {
        return this.gcOppUnitId;
    }

    public void setGcOppUnitId(String gcOppUnitId) {
        this.gcOppUnitId = gcOppUnitId;
    }

    public String getGcSubjectCode() {
        return this.gcSubjectCode;
    }

    public void setGcSubjectCode(String gcSubjectCode) {
        this.gcSubjectCode = gcSubjectCode;
    }

    public Integer getRtOffsetCanDel() {
        return this.rtOffsetCanDel;
    }

    public void setRtOffsetCanDel(Integer rtOffsetCanDel) {
        this.rtOffsetCanDel = rtOffsetCanDel;
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

    public Long getRecordTimestamp() {
        return this.recordTimestamp;
    }

    public void setRecordTimestamp(Long recordTimestamp) {
        this.recordTimestamp = recordTimestamp;
    }

    public Long getSrcTimestamp() {
        return this.srcTimestamp;
    }

    public void setSrcTimestamp(Long srcTimestamp) {
        this.srcTimestamp = srcTimestamp;
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

    public Double getConversionRate() {
        return this.conversionRate;
    }

    public void setConversionRate(Double conversionRate) {
        this.conversionRate = conversionRate;
    }

    public String toString() {
        return "RelatedItemOffsetRelEO{offsetGroupId='" + this.offsetGroupId + '\'' + ", itemBusinessId='" + this.relatedItemId + '\'' + ", checkState='" + this.checkState + '\'' + ", unionRuleId='" + this.unionRuleId + '\'' + ", systemId='" + this.systemId + '\'' + ", defaultPeriod='" + this.dataTime + '\'' + ", gcUnitId='" + this.gcUnitId + '\'' + ", gcOppUnitId='" + this.gcOppUnitId + '\'' + ", gcSubjectCode='" + this.gcSubjectCode + '\'' + ", matchingInformation='" + this.matchingInformation + '\'' + '}';
    }

    public String getAdjust() {
        return this.adjust;
    }

    public void setAdjust(String adjust) {
        this.adjust = adjust;
    }

    public void setOffsetPerson(String offsetPerson) {
        this.offsetPerson = offsetPerson;
    }

    public String getOffsetPerson() {
        return this.offsetPerson;
    }

    public void setOffsetTime(Date offsetTime) {
        this.offsetTime = offsetTime;
    }

    public Date getOffsetTime() {
        return this.offsetTime;
    }

    public String getCurrency() {
        return this.currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}

