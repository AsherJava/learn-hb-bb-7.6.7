/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.gcreport.common.OrientEnum
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn$DBType
 *  com.jiuqi.gcreport.definition.impl.anno.DBIndex
 *  com.jiuqi.gcreport.definition.impl.anno.DBTable
 *  com.jiuqi.gcreport.definition.impl.basic.base.define.DefinitionFieldV
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 *  com.jiuqi.gcreport.definition.impl.basic.intf.ITableExtend
 *  com.jiuqi.gcreport.dimension.internal.utils.DimensionManagerUtil
 */
package com.jiuqi.gcreport.financialcheckcore.item.entity;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.gcreport.common.OrientEnum;
import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBIndex;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.basic.base.define.DefinitionFieldV;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import com.jiuqi.gcreport.definition.impl.basic.intf.ITableExtend;
import com.jiuqi.gcreport.dimension.internal.utils.DimensionManagerUtil;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@DBTable(name="GC_RELATED_ITEM", title="\u5173\u8054\u4ea4\u6613\u5206\u5f55\u8868", indexs={@DBIndex(name="IDX_GC_RELATED_ITEM_CHECKID", columnsFields={"CHECKID"}), @DBIndex(name="IDX_GC_RELATED_ITEM_ORGCOM", columnsFields={"UNITCOMBINE"}), @DBIndex(name="IDX_GC_RELATED_ITEM_Y_M_STATE", columnsFields={"ACCTYEAR", "ACCTPERIOD", "CHKSTATE"}), @DBIndex(name="IDX_GC_RELATED_ITEM_UNIT_GCNUMBER", columnsFields={"UNITID", "OPPUNITID", "GCNUMBER"}), @DBIndex(name="IDX_GC_RELATED_ITEM_UNION_ID", columnsFields={"SRCITEMASSID", "UNITID", "RULECHANGEHANDLERFLAG"})}, dataSource="jiuqi.gcreport.mdd.datasource", convertToBudModel=true)
public class GcRelatedItemEO
extends DefaultTableEntity
implements ITableExtend {
    private static final long serialVersionUID = 3655821557320471599L;
    public static final String TABLENAME = "GC_RELATED_ITEM";
    @DBColumn(nameInDB="ACCTYEAR", title="\u5e74\u5ea6", dbType=DBColumn.DBType.Int, isRequired=true)
    private Integer acctYear;
    @DBColumn(nameInDB="ACCTPERIOD", title="\u671f\u95f4", dbType=DBColumn.DBType.Int, isRequired=true)
    private Integer acctPeriod;
    @DBColumn(nameInDB="SUBJECTCODE", title="\u79d1\u76ee", dbType=DBColumn.DBType.NVarchar, length=200, isRequired=true)
    private String subjectCode;
    @DBColumn(nameInDB="UNITID", title="\u672c\u65b9\u5355\u4f4d", dbType=DBColumn.DBType.Varchar, length=36, isRequired=true)
    private String unitId;
    @DBColumn(nameInDB="OPPUNITID", title="\u5bf9\u65b9\u5355\u4f4d", dbType=DBColumn.DBType.Varchar, length=36, isRequired=true)
    private String oppUnitId;
    @DBColumn(nameInDB="ORIGINALCURR", title="\u539f\u5e01\u5e01\u79cd", dbType=DBColumn.DBType.NVarchar, length=50, isRequired=true)
    private String originalCurr;
    @DBColumn(nameInDB="DEBITORIG", title="\u501f\u65b9\u91d1\u989d\uff08\u539f\u5e01\uff09", dbType=DBColumn.DBType.Numeric, precision=19, scale=2, isRequired=true)
    private Double debitOrig;
    @DBColumn(nameInDB="CREDITORIG", title="\u8d37\u65b9\u91d1\u989d\uff08\u539f\u5e01\uff09", dbType=DBColumn.DBType.Numeric, precision=19, scale=2, isRequired=true)
    private Double creditOrig;
    @DBColumn(nameInDB="DEBIT", title="\u501f\u65b9\u91d1\u989d\uff08\u672c\u4f4d\u5e01\uff09", dbType=DBColumn.DBType.Numeric, precision=19, scale=2, isRequired=true)
    private Double debit;
    @DBColumn(nameInDB="CREDIT", title="\u8d37\u65b9\u91d1\u989d\uff08\u672c\u4f4d\u5e01\uff09", dbType=DBColumn.DBType.Numeric, precision=19, scale=2, isRequired=true)
    private Double credit;
    @DBColumn(nameInDB="MEMO", title="\u5907\u6ce8", dbType=DBColumn.DBType.NVarchar, length=300)
    private String memo;
    @DBColumn(nameInDB="GCNUMBER", title="\u534f\u540c\u7801", dbType=DBColumn.DBType.NVarchar, length=100)
    private String gcNumber;
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
    @DBColumn(nameInDB="INPUTWAY", title="\u6765\u6e90\u7c7b\u578b", dbType=DBColumn.DBType.NVarchar, length=100, isRequired=true)
    private String inputWay;
    @DBColumn(nameInDB="CREATETIME", title="\u521b\u5efa\u65f6\u95f4", dbType=DBColumn.DBType.DateTime)
    private Date createTime;
    @DBColumn(nameInDB="CREATEUSER", title="\u521b\u5efa\u4eba", dbType=DBColumn.DBType.NVarchar, length=100)
    private String createUser;
    @DBColumn(nameInDB="UPDATETIME", title="\u66f4\u65b0\u65f6\u95f4", dbType=DBColumn.DBType.NVarchar, length=60)
    private String updateTime;
    @DBColumn(title="\u65f6\u95f4\u6233", dbType=DBColumn.DBType.Numeric, precision=19, scale=0, isRequired=true)
    private Long recordTimestamp = System.currentTimeMillis();
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
    @DBColumn(nameInDB="CHKSTATE", title="\u5bf9\u8d26\u72b6\u6001", dbType=DBColumn.DBType.NVarchar, length=50, isRequired=true)
    private String chkState;
    @DBColumn(nameInDB="CHKAMTD", title="\u501f\u65b9\u5bf9\u8d26\u91d1\u989d", dbType=DBColumn.DBType.Numeric, precision=19, scale=2, isRequired=true)
    private Double chkAmtD;
    @DBColumn(nameInDB="CHKAMTC", title="\u8d37\u65b9\u5bf9\u8d26\u91d1\u989d", dbType=DBColumn.DBType.Numeric, precision=19, scale=2, isRequired=true)
    private Double chkAmtC;
    @DBColumn(nameInDB="CHECKPROJECT", title="\u5bf9\u8d26\u9879\u76ee", dbType=DBColumn.DBType.NVarchar, length=36)
    private String checkProject;
    @DBColumn(nameInDB="CHECkPROJECTDIRECTION", title="\u5bf9\u8d26\u9879\u76ee\u65b9\u5411", dbType=DBColumn.DBType.Int)
    private Integer checkProjectDirection;
    @DBColumn(nameInDB="BUSINESSROLE", title="\u4e1a\u52a1\u89d2\u8272", dbType=DBColumn.DBType.Int)
    private Integer businessRole;
    @DBColumn(nameInDB="UNITCOMBINE", title="\u5355\u4f4d\u7ec4\u5408", dbType=DBColumn.DBType.Varchar, length=100)
    private String unitCombine;
    @DBColumn(nameInDB="CHECKATTRIBUTE", title="\u5bf9\u8d26\u4e1a\u52a1", dbType=DBColumn.DBType.Varchar, length=50)
    private String checkAttribute;
    @DBColumn(nameInDB="AMT", title="\u91d1\u989d", dbType=DBColumn.DBType.Numeric, scale=2)
    private Double amt;
    @DBColumn(nameInDB="DC", title="\u501f\u8d37\u65b9\u5411", dbType=DBColumn.DBType.Int)
    private Integer dc;
    @DBColumn(nameInDB="CURRENCY", title="\u672c\u4f4d\u5e01\u79cd", dbType=DBColumn.DBType.NVarchar, length=50)
    private String currency;
    @DBColumn(nameInDB="REALGCNUMBER", title="\u5b9e\u9645\u534f\u540c\u7801", dbType=DBColumn.DBType.NVarchar, length=100)
    private String realGcNumber;
    @DBColumn(nameInDB="SRCVCHRID", title="\u539f\u59cb\u51ed\u8bc1ID", dbType=DBColumn.DBType.NVarchar)
    private String srcVchrId;
    @DBColumn(nameInDB="CFITEMCODE", title="\u73b0\u6d41\u9879\u76ee", dbType=DBColumn.DBType.NVarchar, length=100)
    private String cfItemCode;
    @DBColumn(nameInDB="VCHRID", title="\u4e00\u672c\u8d26\u51ed\u8bc1ID", dbType=DBColumn.DBType.NVarchar)
    private String vchrId;
    @DBColumn(nameInDB="RULECHANGEHANDLERFLAG", title="\u91cd\u5206\u7c7b\u89c4\u5219\u53d8\u66f4\u6807\u8bc6", dbType=DBColumn.DBType.Int)
    private Integer ruleChangeHandlerFlag;
    @DBColumn(nameInDB="RECLASSIFYSUBJCODE", title="\u91cd\u5206\u7c7b\u540e\u79d1\u76ee\u4ee3\u7801", dbType=DBColumn.DBType.NVarchar, length=100)
    private String reclassifySubjCode;
    @DBColumn(nameInDB="SRCITEMASSID", title="\u539f\u59cb\u51ed\u8bc1\u5206\u5f55\u8f85\u52a9ID", dbType=DBColumn.DBType.NVarchar, length=60)
    private String srcIteMassId;
    private Integer amtOrient;
    private BigDecimal amountAsDebit;

    public Long getRecordTimestamp() {
        return this.recordTimestamp;
    }

    public void setRecordTimestamp(Long recordTimestamp) {
        this.addFieldValue("RECORDTIMESTAMP", recordTimestamp);
        this.recordTimestamp = recordTimestamp;
    }

    public Integer getAcctYear() {
        return this.acctYear;
    }

    public void setAcctYear(Integer acctYear) {
        this.addFieldValue("ACCTYEAR", acctYear);
        this.acctYear = acctYear;
    }

    public Integer getAcctPeriod() {
        return this.acctPeriod;
    }

    public void setAcctPeriod(Integer acctPeriod) {
        this.addFieldValue("ACCTPERIOD", acctPeriod);
        this.acctPeriod = acctPeriod;
    }

    public String getSubjectCode() {
        return this.subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.addFieldValue("SUBJECTCODE", subjectCode);
        this.subjectCode = subjectCode;
    }

    public String getUnitId() {
        return this.unitId;
    }

    public void setUnitId(String unitId) {
        this.addFieldValue("UNITID", unitId);
        this.unitId = unitId;
    }

    public String getOppUnitId() {
        return this.oppUnitId;
    }

    public void setOppUnitId(String oppUnitId) {
        this.addFieldValue("OPPUNITID", oppUnitId);
        this.oppUnitId = oppUnitId;
    }

    public String getOriginalCurr() {
        return this.originalCurr;
    }

    public void setOriginalCurr(String originalCurr) {
        this.addFieldValue("ORIGINALCURR", originalCurr);
        this.originalCurr = originalCurr;
    }

    public Double getDebitOrig() {
        return this.debitOrig;
    }

    public void setDebitOrig(Double debitOrig) {
        this.addFieldValue("DEBITORIG", debitOrig);
        this.debitOrig = debitOrig;
    }

    public Double getCreditOrig() {
        return this.creditOrig;
    }

    public void setCreditOrig(Double creditOrig) {
        this.addFieldValue("CREDITORIG", creditOrig);
        this.creditOrig = creditOrig;
    }

    public Double getDebit() {
        return this.debit;
    }

    public void setDebit(Double debit) {
        this.addFieldValue("DEBIT", debit);
        this.debit = debit;
    }

    public Double getCredit() {
        return this.credit;
    }

    public void setCredit(Double credit) {
        this.addFieldValue("CREDIT", credit);
        this.credit = credit;
    }

    public String getMemo() {
        return this.memo;
    }

    public void setMemo(String memo) {
        this.addFieldValue("MEMO", memo);
        this.memo = memo;
    }

    public String getGcNumber() {
        return this.gcNumber;
    }

    public void setGcNumber(String gcNumber) {
        this.addFieldValue("GCNUMBER", gcNumber);
        this.gcNumber = gcNumber;
    }

    public String getVchrType() {
        return this.vchrType;
    }

    public void setVchrType(String vchrType) {
        this.addFieldValue("VCHRTYPE", vchrType);
        this.vchrType = vchrType;
    }

    public String getVchrNum() {
        return this.vchrNum;
    }

    public void setVchrNum(String vchrNum) {
        this.addFieldValue("VCHRNUM", vchrNum);
        this.vchrNum = vchrNum;
    }

    public String getItemOrder() {
        return this.itemOrder;
    }

    public void setItemOrder(String itemOrder) {
        this.addFieldValue("ITEMORDER", itemOrder);
        this.itemOrder = itemOrder;
    }

    public Date getCreateDate() {
        return this.createDate;
    }

    public void setCreateDate(Date createDate) {
        this.addFieldValue("CREATEDATE", createDate);
        this.createDate = createDate;
    }

    public String getSrcItemId() {
        return this.srcItemId;
    }

    public void setSrcItemId(String srcItemId) {
        this.addFieldValue("SRCITEMID", srcItemId);
        this.srcItemId = srcItemId;
    }

    public String getDigest() {
        return this.digest;
    }

    public void setDigest(String digest) {
        this.addFieldValue("DIGEST", digest);
        this.digest = digest;
    }

    public String getBillCode() {
        return this.billCode;
    }

    public void setBillCode(String billCode) {
        this.addFieldValue("BILLCODE", billCode);
        this.billCode = billCode;
    }

    public String getVchrSourceType() {
        return this.vchrSourceType;
    }

    public void setVchrSourceType(String vchrSourceType) {
        this.addFieldValue("VCHRSOURCETYPE", vchrSourceType);
        this.vchrSourceType = vchrSourceType;
    }

    public String getInputWay() {
        return this.inputWay;
    }

    public void setInputWay(String inputWay) {
        this.addFieldValue("INPUTWAY", inputWay);
        this.inputWay = inputWay;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.addFieldValue("CREATETIME", createTime);
        this.createTime = createTime;
    }

    public String getCreateUser() {
        return this.createUser;
    }

    public void setCreateUser(String createUser) {
        this.addFieldValue("CREATEUSER", createUser);
        this.createUser = createUser;
    }

    public String getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.addFieldValue("UPDATETIME", updateTime);
        this.updateTime = updateTime;
    }

    public String getCheckId() {
        return this.checkId;
    }

    public void setCheckId(String checkId) {
        this.addFieldValue("CHECKID", checkId);
        this.checkId = checkId;
    }

    public String getCheckMode() {
        return this.checkMode;
    }

    public void setCheckMode(String checkMode) {
        this.addFieldValue("CHECKMODE", checkMode);
        this.checkMode = checkMode;
    }

    public String getCheckType() {
        return this.checkType;
    }

    public void setCheckType(String checkType) {
        this.addFieldValue("CHECKTYPE", checkType);
        this.checkType = checkType;
    }

    public Integer getCheckYear() {
        return this.checkYear;
    }

    public void setCheckYear(Integer checkYear) {
        this.addFieldValue("CHECKYEAR", checkYear);
        this.checkYear = checkYear;
    }

    public Integer getCheckPeriod() {
        return this.checkPeriod;
    }

    public void setCheckPeriod(Integer checkPeriod) {
        this.addFieldValue("CHECKPERIOD", checkPeriod);
        this.checkPeriod = checkPeriod;
    }

    public String getCheckRuleId() {
        return this.checkRuleId;
    }

    public void setCheckRuleId(String checkRuleId) {
        this.addFieldValue("CHECKRULEID", checkRuleId);
        this.checkRuleId = checkRuleId;
    }

    public Date getCheckTime() {
        return this.checkTime;
    }

    public void setCheckTime(Date checkTime) {
        this.addFieldValue("CHECKTIME", checkTime);
        this.checkTime = checkTime;
    }

    public String getChecker() {
        return this.checker;
    }

    public void setChecker(String checker) {
        this.addFieldValue("CHECKER", checker);
        this.checker = checker;
    }

    public String getChkCurr() {
        return this.chkCurr;
    }

    public void setChkCurr(String chkCurr) {
        this.addFieldValue("CHKCURR", chkCurr);
        this.chkCurr = chkCurr;
    }

    public String getChkState() {
        return this.chkState;
    }

    public void setChkState(String chkState) {
        this.addFieldValue("CHKSTATE", chkState);
        this.chkState = chkState;
    }

    public Double getChkAmtD() {
        return this.chkAmtD;
    }

    public void setChkAmtD(Double chkAmtD) {
        this.addFieldValue("CHKAMTD", chkAmtD);
        this.chkAmtD = chkAmtD;
    }

    public Double getChkAmtC() {
        return this.chkAmtC;
    }

    public void setChkAmtC(Double chkAmtC) {
        this.addFieldValue("CHKAMTC", chkAmtC);
        this.chkAmtC = chkAmtC;
    }

    public void setAmountAsDebit(BigDecimal amountAsDebit) {
        this.amountAsDebit = amountAsDebit;
    }

    public void initAmtInfo() {
        Double debit = this.getDebitOrig();
        Double credit = this.getCreditOrig();
        if (debit != 0.0) {
            this.amountAsDebit = BigDecimal.valueOf(debit);
            this.amtOrient = OrientEnum.D.getValue();
        } else {
            this.amountAsDebit = BigDecimal.valueOf(credit).negate();
            this.amtOrient = OrientEnum.C.getValue();
        }
    }

    public Integer getAmtOrient() {
        return this.amtOrient;
    }

    public void setAmtOrient(Integer amtOrient) {
        this.amtOrient = amtOrient;
    }

    public BigDecimal getAmountAsDebit() {
        return this.amountAsDebit;
    }

    public String getCheckProject() {
        return this.checkProject;
    }

    public void setCheckProject(String checkProject) {
        this.addFieldValue("CHECKPROJECT", checkProject);
        this.checkProject = checkProject;
    }

    public Integer getCheckProjectDirection() {
        return this.checkProjectDirection;
    }

    public void setCheckProjectDirection(Integer checkProjectDirection) {
        this.addFieldValue("CHECKPROJECTDIRECTION", checkProjectDirection);
        this.checkProjectDirection = checkProjectDirection;
    }

    public Integer getBusinessRole() {
        return this.businessRole;
    }

    public void setBusinessRole(Integer businessRole) {
        this.addFieldValue("BUSINESSROLE", businessRole);
        this.businessRole = businessRole;
    }

    public String getCheckAttribute() {
        return this.checkAttribute;
    }

    public void setCheckAttribute(String checkAttribute) {
        this.addFieldValue("CHECKATTRIBUTE", checkAttribute);
        this.checkAttribute = checkAttribute;
    }

    public String getUnitCombine() {
        return this.unitCombine;
    }

    public void setUnitCombine(String unitCombine) {
        this.addFieldValue("UNITCOMBINE", unitCombine);
        this.unitCombine = unitCombine;
    }

    public Double getAmt() {
        return this.amt;
    }

    public void setAmt(Double amt) {
    }

    public void setDc(Integer dc) {
    }

    public Integer getDc() {
        return null;
    }

    public Double getChkAmt() {
        return this.getChkAmtD() == null || this.getChkAmtD() == 0.0 ? this.getChkAmtC() : this.getChkAmtD();
    }

    public String getCurrency() {
        return this.currency;
    }

    public void setCurrency(String currency) {
        this.addFieldValue("CURRENCY", currency);
        this.currency = currency;
    }

    public String getRealGcNumber() {
        return this.realGcNumber;
    }

    public void setRealGcNumber(String realGcNumber) {
        this.addFieldValue("REALGCNUMBER", realGcNumber);
        this.realGcNumber = realGcNumber;
    }

    public String getSrcVchrId() {
        return this.srcVchrId;
    }

    public void setSrcVchrId(String srcVchrId) {
        this.addFieldValue("SRCVCHRID", srcVchrId);
        this.srcVchrId = srcVchrId;
    }

    public String getCfItemCode() {
        return this.cfItemCode;
    }

    public void setCfItemCode(String cfItemCode) {
        this.addFieldValue("CFITEMCODE", cfItemCode);
        this.cfItemCode = cfItemCode;
    }

    public String getVchrId() {
        return this.vchrId;
    }

    public void setVchrId(String vchrId) {
        this.addFieldValue("VCHRID", vchrId);
        this.vchrId = vchrId;
    }

    public Integer getRuleChangeHandlerFlag() {
        return this.ruleChangeHandlerFlag;
    }

    public void setRuleChangeHandlerFlag(Integer ruleChangeHandlerFlag) {
        this.addFieldValue("RULECHANGEHANDLERFLAG", ruleChangeHandlerFlag);
        this.ruleChangeHandlerFlag = ruleChangeHandlerFlag;
    }

    public String getReclassifySubjCode() {
        return this.reclassifySubjCode;
    }

    public void setReclassifySubjCode(String reclassifySubjCode) {
        this.addFieldValue("RECLASSIFYSUBJCODE", reclassifySubjCode);
        this.reclassifySubjCode = reclassifySubjCode;
    }

    public String getSrcIteMassId() {
        return this.srcIteMassId;
    }

    public void setSrcIteMassId(String srcIteMassId) {
        this.addFieldValue("SRCITEMASSID", srcIteMassId);
        this.srcIteMassId = srcIteMassId;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || ((Object)((Object)this)).getClass() != o.getClass()) {
            return false;
        }
        GcRelatedItemEO that = (GcRelatedItemEO)((Object)o);
        return this.getId().equals(that.getId());
    }

    public int hashCode() {
        return Objects.hash(this.getId());
    }

    public String toString() {
        return "GcRelatedItemEO{acctYear=" + this.acctYear + ", acctPeriod=" + this.acctPeriod + ", subjectCode='" + this.subjectCode + '\'' + ", unitId='" + this.unitId + '\'' + ", oppUnitId='" + this.oppUnitId + '\'' + ", originalCurr='" + this.originalCurr + '\'' + ", debitOrig=" + this.debitOrig + ", creditOrig=" + this.creditOrig + ", debit=" + this.debit + ", credit=" + this.credit + ", memo='" + this.memo + '\'' + ", gcNumber='" + this.gcNumber + '\'' + ", vchrType='" + this.vchrType + '\'' + ", vchrNum='" + this.vchrNum + '\'' + ", itemOrder='" + this.itemOrder + '\'' + ", createDate=" + this.createDate + ", srcItemId='" + this.srcItemId + '\'' + ", digest='" + this.digest + '\'' + ", billCode='" + this.billCode + '\'' + ", vchrSourceType='" + this.vchrSourceType + '\'' + ", inputWay='" + this.inputWay + '\'' + ", createTime=" + this.createTime + ", createUser='" + this.createUser + '\'' + ", updateTime='" + this.updateTime + '\'' + ", recordTimestamp=" + this.recordTimestamp + ", checkId='" + this.checkId + '\'' + ", checkMode='" + this.checkMode + '\'' + ", checkType='" + this.checkType + '\'' + ", checkYear=" + this.checkYear + ", checkPeriod=" + this.checkPeriod + ", checkRuleId='" + this.checkRuleId + '\'' + ", checkTime=" + this.checkTime + ", checker='" + this.checker + '\'' + ", chkCurr='" + this.chkCurr + '\'' + ", chkState='" + this.chkState + '\'' + ", chkAmtD=" + this.chkAmtD + ", chkAmtC=" + this.chkAmtC + ", checkProject='" + this.checkProject + '\'' + ", checkProjectDirection=" + this.checkProjectDirection + ", businessRole=" + this.businessRole + ", unitCombine='" + this.unitCombine + '\'' + ", amt=" + this.amt + ", dc=" + this.dc + ", currency='" + this.currency + '\'' + ", realGcNumber='" + this.realGcNumber + '\'' + ", srcVchrId='" + this.srcVchrId + '\'' + ", cfItemCode='" + this.cfItemCode + '\'' + ", vchrId='" + this.vchrId + '\'' + ", ruleChangeHandlerFlag=" + this.ruleChangeHandlerFlag + ", reclassifySubjCode='" + this.reclassifySubjCode + '\'' + ", srcIteMassId='" + this.srcIteMassId + '\'' + ", amtOrient=" + this.amtOrient + ", amountAsDebit=" + this.amountAsDebit + "} " + super.toString();
    }

    public List<DefinitionFieldV> getExtendFieldList(String param) {
        ArrayList fields = CollectionUtils.newArrayList();
        List extendColumn = DimensionManagerUtil.getExtendColumn((String)TABLENAME);
        fields.addAll(extendColumn);
        return fields;
    }
}

