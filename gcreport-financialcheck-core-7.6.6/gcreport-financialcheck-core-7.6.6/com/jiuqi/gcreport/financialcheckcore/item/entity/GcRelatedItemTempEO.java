/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn$DBType
 *  com.jiuqi.gcreport.definition.impl.anno.DBIndex
 *  com.jiuqi.gcreport.definition.impl.anno.DBTable
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 */
package com.jiuqi.gcreport.financialcheckcore.item.entity;

import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBIndex;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import java.util.Date;

@DBTable(name="GC_RELATEDITEM_TEMPORARY", title="\u5173\u8054\u4ea4\u6613\u5206\u5f55\u4e34\u65f6\u8868", indexs={@DBIndex(name="IDX_GC_RELATEDITEM_TEMPORARY_UNION_ID", columnsFields={"SRCITEMASSID", "UNITID", "RULECHANGEHANDLERFLAG"})}, dataSource="jiuqi.gcreport.mdd.datasource", sourceTable="GC_RELATED_ITEM")
public class GcRelatedItemTempEO
extends DefaultTableEntity {
    public static final String TABLENAME = "GC_RELATEDITEM_TEMPORARY";
    @DBColumn(title="\u6279\u6b21\u53f7", dbType=DBColumn.DBType.Varchar, isRequired=true)
    private String batchId;
    @DBColumn(nameInDB="RULECHANGEHANDLERFLAG", title="\u91cd\u5206\u7c7b\u89c4\u5219\u53d8\u66f4\u6807\u8bc6", dbType=DBColumn.DBType.Int)
    private Integer ruleChangeHandlerFlag;
    @DBColumn(nameInDB="SRCITEMASSID", title="\u539f\u59cb\u51ed\u8bc1\u5206\u5f55\u8f85\u52a9ID", dbType=DBColumn.DBType.NVarchar, length=60)
    private String srcIteMassId;
    @DBColumn(nameInDB="UNITID", title="\u672c\u65b9\u5355\u4f4d", dbType=DBColumn.DBType.Varchar, length=36, isRequired=true)
    private String unitId;
    @DBColumn(nameInDB="ACCTYEAR", title="\u5e74\u5ea6", dbType=DBColumn.DBType.Int)
    private Integer acctYear;
    @DBColumn(nameInDB="ACCTPERIOD", title="\u671f\u95f4", dbType=DBColumn.DBType.Int)
    private Integer acctPeriod;
    @DBColumn(nameInDB="SUBJECTCODE", title="\u79d1\u76ee", dbType=DBColumn.DBType.NVarchar, length=200)
    private String subjectCode;
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
    @DBColumn(nameInDB="GCNUMBER", title="\u534f\u540c\u7801", dbType=DBColumn.DBType.NVarchar, length=100)
    private String gcNumber;
    @DBColumn(nameInDB="REALGCNUMBER", title="\u5b9e\u9645\u534f\u540c\u7801", dbType=DBColumn.DBType.NVarchar, length=100)
    private String realGcNumber;
    @DBColumn(nameInDB="VCHRTYPE", title="\u51ed\u8bc1\u5b57", dbType=DBColumn.DBType.NVarchar, length=150)
    private String vchrType;
    @DBColumn(nameInDB="VCHRNUM", title="\u51ed\u8bc1\u53f7", dbType=DBColumn.DBType.NVarchar, length=50)
    private String vchrNum;
    @DBColumn(nameInDB="ITEMORDER", title="\u5206\u5f55\u5e8f\u53f7", dbType=DBColumn.DBType.NVarchar, length=100)
    private String itemOrder;
    @DBColumn(nameInDB="CREATEDATE", title="\u51ed\u8bc1\u65e5\u671f", dbType=DBColumn.DBType.Date)
    private Date createDate;
    @DBColumn(nameInDB="SRCITEMID", title="\u6765\u6e90\u5206\u5f55\u6807\u8bc6", dbType=DBColumn.DBType.Varchar)
    private String srcItemId;
    @DBColumn(nameInDB="DIGEST", title="\u6458\u8981", dbType=DBColumn.DBType.NVarchar, length=300)
    private String digest;
    @DBColumn(nameInDB="BILLCODE", title="\u5355\u636e\u7f16\u53f7", dbType=DBColumn.DBType.NVarchar, length=50)
    private String billCode;
    @DBColumn(nameInDB="VCHRSOURCETYPE", title="\u51ed\u8bc1\u7c7b\u578b", dbType=DBColumn.DBType.NVarchar, length=250)
    private String vchrSourceType;
    @DBColumn(nameInDB="CURRENCY", title="\u672c\u4f4d\u5e01\u79cd", dbType=DBColumn.DBType.NVarchar, length=50)
    private String currency;
    @DBColumn(nameInDB="SRCVCHRID", title="\u539f\u59cb\u51ed\u8bc1ID", dbType=DBColumn.DBType.NVarchar)
    private String srcVchrId;
    @DBColumn(nameInDB="CFITEMCODE", title="\u73b0\u6d41\u9879\u76ee\u7f16\u7801", dbType=DBColumn.DBType.NVarchar, length=100)
    private String cfItemCode;
    @DBColumn(nameInDB="VCHRID", title="\u4e00\u672c\u8d26\u51ed\u8bc1ID", dbType=DBColumn.DBType.NVarchar)
    private String vchrId;
    @DBColumn(nameInDB="RECLASSIFYSUBJCODE", title="\u91cd\u5206\u7c7b\u540e\u79d1\u76ee\u4ee3\u7801", dbType=DBColumn.DBType.NVarchar, length=100)
    private String reclassifySubjCode;
    @DBColumn(nameInDB="INPUTWAY", title="\u6765\u6e90\u7c7b\u578b", dbType=DBColumn.DBType.NVarchar, length=100)
    private String inputWay;
    @DBColumn(nameInDB="ACTIONTYPE", title="\u6570\u636e\u64cd\u4f5c\u7c7b\u578b", dbType=DBColumn.DBType.Int)
    private Integer actionType;

    public String getBatchId() {
        return this.batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public Integer getRuleChangeHandlerFlag() {
        return this.ruleChangeHandlerFlag;
    }

    public void setRuleChangeHandlerFlag(Integer ruleChangeHandlerFlag) {
        this.ruleChangeHandlerFlag = ruleChangeHandlerFlag;
    }

    public String getSrcIteMassId() {
        return this.srcIteMassId;
    }

    public void setSrcIteMassId(String srcIteMassId) {
        this.srcIteMassId = srcIteMassId;
    }

    public String getUnitId() {
        return this.unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public GcRelatedItemTempEO() {
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

    public String getRealGcNumber() {
        return this.realGcNumber;
    }

    public void setRealGcNumber(String realGcNumber) {
        this.realGcNumber = realGcNumber;
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

    public String getCurrency() {
        return this.currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getSrcVchrId() {
        return this.srcVchrId;
    }

    public void setSrcVchrId(String srcVchrId) {
        this.srcVchrId = srcVchrId;
    }

    public String getCfItemCode() {
        return this.cfItemCode;
    }

    public void setCfItemCode(String cfItemCode) {
        this.cfItemCode = cfItemCode;
    }

    public String getVchrId() {
        return this.vchrId;
    }

    public void setVchrId(String vchrId) {
        this.vchrId = vchrId;
    }

    public String getReclassifySubjCode() {
        return this.reclassifySubjCode;
    }

    public void setReclassifySubjCode(String reclassifySubjCode) {
        this.reclassifySubjCode = reclassifySubjCode;
    }

    public Integer getActionType() {
        return this.actionType;
    }

    public void setActionType(Integer actionType) {
        this.actionType = actionType;
    }

    public String getInputWay() {
        return this.inputWay;
    }

    public void setInputWay(String inputWay) {
        this.inputWay = inputWay;
    }

    public GcRelatedItemTempEO(String batchId, Integer ruleChangeHandlerFlag, String unitId, String srcIteMassId) {
        this.batchId = batchId;
        this.ruleChangeHandlerFlag = ruleChangeHandlerFlag;
        this.unitId = unitId;
        this.srcIteMassId = srcIteMassId;
        this.setId(UUIDUtils.newUUIDStr());
    }
}

