/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn$DBType
 *  com.jiuqi.gcreport.definition.impl.anno.DBIndex
 *  com.jiuqi.gcreport.definition.impl.anno.DBIndexs
 *  com.jiuqi.gcreport.definition.impl.anno.DBTable
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 */
package com.jiuqi.gcreport.workingpaper.entity;

import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBIndex;
import com.jiuqi.gcreport.definition.impl.anno.DBIndexs;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import java.util.Date;

@DBTable(name="GC_RY_OFFSETVCHRITEM", title="\u4efb\u610f\u8f93\u5165\u62b5\u9500\u5206\u5f55\u8868", inStorage=true)
@DBIndexs(value={@DBIndex(name="INDEX_RY_OFFSET_MRECID", columnsFields={"MRECID"}), @DBIndex(name="INDEX_RY_OFFSET_SRCID", columnsFields={"SRCID"}), @DBIndex(name="INDEX_RY_OFFSETVCHRITEM_3", columnsFields={"OPPUNITID", "UNITID"})})
public class ArbitrarilyMergeOffSetVchrItemAdjustEO
extends DefaultTableEntity {
    public static final String TABLENAME = "GC_RY_OFFSETVCHRITEM";
    public static final String SRCOFFSETGROUPID = "SRCOFFSETGROUPID";
    @DBColumn(nameInDB="MRECID", title="\u5206\u7ec4ID", dbType=DBColumn.DBType.Varchar, length=36)
    protected String mRecid;
    @DBColumn(nameInDB="TASKID", title="\u4efb\u52a1id", dbType=DBColumn.DBType.Varchar, length=36, isRequired=true)
    protected String taskId;
    @DBColumn(nameInDB="SCHEMEID", title="\u62a5\u8868\u65b9\u6848", dbType=DBColumn.DBType.Varchar, length=36)
    protected String schemeId;
    @DBColumn(length=20, nameInDB="DATATIME", title="\u65f6\u671f\u7c7b\u578b", dbType=DBColumn.DBType.NVarchar)
    protected String defaultPeriod;
    @DBColumn(nameInDB="GCBUSINESSTYPECODE", title="\u5408\u5e76\u4e1a\u52a1\u7c7b\u578b", dbType=DBColumn.DBType.NVarchar)
    protected String gcBusinessTypeCode;
    @DBColumn(length=20, nameInDB="UNITVERSION", title="\u5355\u4f4d\u7c7b\u578b", dbType=DBColumn.DBType.NVarchar)
    private String unitVersion;
    @DBColumn(nameInDB="ACCTYEAR", title="\u5e74\u5ea6", dbType=DBColumn.DBType.Int, isRequired=true)
    protected Integer acctYear;
    @DBColumn(nameInDB="ACCTPERIOD", title="\u671f\u95f4", dbType=DBColumn.DBType.Int, isRequired=true)
    protected Integer acctPeriod;
    @DBColumn(nameInDB="UNITID", title="\u672c\u65b9\u5355\u4f4d", dbType=DBColumn.DBType.Varchar, length=36, isRequired=true)
    protected String unitId;
    @DBColumn(nameInDB="OPPUNITID", title="\u5bf9\u65b9\u5355\u4f4d", dbType=DBColumn.DBType.Varchar, length=36, isRequired=true)
    protected String oppUnitId;
    @DBColumn(nameInDB="SRCOFFSETGROUPID", title="\u6765\u6e90\u62b5\u9500\u5206\u7ec4id", dbType=DBColumn.DBType.Varchar, length=36)
    protected String srcOffsetGroupId;
    @DBColumn(nameInDB="ADJUST", title="\u8c03\u6574\u671f", dbType=DBColumn.DBType.Varchar, length=100)
    protected String adjust;
    @DBColumn(nameInDB="INPUTUNITID", title="\u5f55\u5165/\u5408\u5e76\u7ec4\u7ec7", dbType=DBColumn.DBType.Varchar, length=36, description="\u9ed8\u8ba4\u540cUNITID")
    protected String inputUnitId;
    @DBColumn(length=36, nameInDB="SYSTEMID", title="\u5408\u5e76\u4f53\u7cfbID", dbType=DBColumn.DBType.Varchar)
    protected String systemId;
    @DBColumn(nameInDB="SUBJECTCODE", title="\u79d1\u76ee", dbType=DBColumn.DBType.NVarchar, isRequired=true)
    protected String subjectCode;
    @DBColumn(nameInDB="SUBJECTORIENT", title="\u79d1\u76ee\u501f\u8d37\u65b9\u5411", dbType=DBColumn.DBType.Int)
    private Integer subjectOrient;
    @DBColumn(nameInDB="ORIENT", title="\u501f\u8d37\u65b9\u5411", dbType=DBColumn.DBType.Int)
    private Integer orient;
    @DBColumn(nameInDB="MEMO", title="\u63cf\u8ff0\u4fe1\u606f", dbType=DBColumn.DBType.NVarchar, length=100, description="\u5ba2\u6237\u539f\u59cb\u5f55\u5165\u7684\u63cf\u8ff0")
    protected String memo;
    @DBColumn(nameInDB="MODIFYTIME", title="\u4fee\u6539\u65f6\u95f4", dbType=DBColumn.DBType.DateTime)
    protected Date modifyTime;
    @DBColumn(nameInDB="SORTORDER", title="\u6392\u5e8f\u5b57\u6bb5", dbType=DBColumn.DBType.Numeric)
    protected Double sortOrder;
    @DBColumn(nameInDB="OFFSETCURR", title="\u62b5\u9500\u5e01\u79cd\u4ee3\u7801", dbType=DBColumn.DBType.NVarchar, length=50, description="\u53c2\u4e0e\u6838\u5bf9\u7684\u5e01\u79cd\u4ee3\u7801\uff0c\u5bf9\u5e94\u5e01\u79cd\u57fa\u7840\u6570\u636e\u8868")
    protected String offSetCurr;
    @DBColumn(nameInDB="DEBIT_CNY", title="\u501f\u65b9\u91d1\u989d_\u4eba\u6c11\u5e01", dbType=DBColumn.DBType.Numeric, precision=19, scale=4)
    protected Double debitCNY;
    @DBColumn(nameInDB="CREDIT_CNY", title="\u8d37\u65b9\u91d1\u989d_\u4eba\u6c11\u5e01", dbType=DBColumn.DBType.Numeric, precision=19, scale=4)
    protected Double creditCNY;
    @DBColumn(nameInDB="DEBIT_USD", title="\u501f\u65b9\u91d1\u989d_\u7f8e\u5143", dbType=DBColumn.DBType.Numeric, precision=19, scale=4)
    protected Double debitUSD;
    @DBColumn(nameInDB="CREDIT_USD", title="\u8d37\u65b9\u91d1\u989d_\u7f8e\u5143", dbType=DBColumn.DBType.Numeric, precision=19, scale=4)
    protected Double creditUSD;
    @DBColumn(nameInDB="DEBIT_HKD", title="\u501f\u65b9\u91d1\u989d_\u6e2f\u5e01", dbType=DBColumn.DBType.Numeric, precision=19, scale=4)
    protected Double debitHKD;
    @DBColumn(nameInDB="CREDIT_HKD", title="\u8d37\u65b9\u91d1\u989d_\u6e2f\u5e01", dbType=DBColumn.DBType.Numeric, precision=19, scale=4)
    protected Double creditHKD;
    @DBColumn(nameInDB="OFFSET_DEBIT_CNY", title="\u62b5\u9500\u501f\u65b9\u91d1\u989d_\u4eba\u6c11\u5e01", dbType=DBColumn.DBType.Numeric, precision=19, scale=4)
    protected Double offSetDebitCNY;
    @DBColumn(nameInDB="OFFSET_CREDIT_CNY", title="\u62b5\u9500\u8d37\u65b9\u91d1\u989d_\u4eba\u6c11\u5e01", dbType=DBColumn.DBType.Numeric, precision=19, scale=4)
    protected Double offSetCreditCNY;
    @DBColumn(nameInDB="OFFSET_DEBIT_USD", title="\u62b5\u9500\u501f\u65b9\u91d1\u989d_\u7f8e\u5143", dbType=DBColumn.DBType.Numeric, precision=19, scale=4)
    protected Double offSetDebitUSD;
    @DBColumn(nameInDB="OFFSET_CREDIT_USD", title="\u62b5\u9500\u8d37\u65b9\u91d1\u989d_\u7f8e\u5143", dbType=DBColumn.DBType.Numeric, precision=19, scale=4)
    protected Double offSetCreditUSD;
    @DBColumn(nameInDB="OFFSET_DEBIT_HKD", title="\u62b5\u9500\u501f\u65b9\u91d1\u989d_\u6e2f\u5e01", dbType=DBColumn.DBType.Numeric, precision=19, scale=4)
    protected Double offSetDebitHKD;
    @DBColumn(nameInDB="OFFSET_CREDIT_HKD", title="\u62b5\u9500\u8d37\u65b9\u91d1\u989d_\u6e2f\u5e01", dbType=DBColumn.DBType.Numeric, precision=19, scale=4)
    protected Double offSetCreditHKD;
    @DBColumn(nameInDB="DIFFD_CNY", title="\u4eba\u6c11\u5e01\u501f\u65b9\u5dee\u989d", dbType=DBColumn.DBType.Numeric, precision=19, scale=4)
    protected Double diffdCNY;
    @DBColumn(nameInDB="DIFFC_CNY", title="\u4eba\u6c11\u5e01\u8d37\u65b9\u5dee\u989d", dbType=DBColumn.DBType.Numeric, precision=19, scale=4)
    protected Double diffcCNY;
    @DBColumn(nameInDB="DIFFD_USD", title="\u7f8e\u5143\u501f\u65b9\u5dee\u989d", dbType=DBColumn.DBType.Numeric, precision=19, scale=4)
    protected Double diffdUSD;
    @DBColumn(nameInDB="DIFFC_USD", title="\u7f8e\u5143\u8d37\u65b9\u5dee\u989d", dbType=DBColumn.DBType.Numeric, precision=19, scale=4)
    protected Double diffcUSD;
    @DBColumn(nameInDB="DIFFD_HKD", title="\u6e2f\u5e01\u501f\u65b9\u5dee\u989d", dbType=DBColumn.DBType.Numeric, precision=19, scale=4)
    protected Double diffdHKD;
    @DBColumn(nameInDB="DIFFC_HKD", title="\u6e2f\u5e01\u8d37\u65b9\u5dee\u989d", dbType=DBColumn.DBType.Numeric, precision=19, scale=4)
    protected Double diffcHKD;
    @DBColumn(nameInDB="MD_GCORGTYPE", title="\u5355\u4f4d\u7c7b\u578b", dbType=DBColumn.DBType.Varchar, length=36, isRequired=true)
    private String orgType;
    @DBColumn(nameInDB="ELMMODE", title="\u62b5\u9500\u6a21\u5f0f", dbType=DBColumn.DBType.Int, isRequired=true, description="\u679a\u4e3e\u7c7b\uff0c0-5\u6570\u5b57\uff0c\u5b9a\u4e49\u4e0d\u540c\u62b5\u9500\u65b9\u5f0f")
    protected Integer elmMode;
    @DBColumn(nameInDB="RULEID", title="\u62b5\u9500\u89c4\u5219ID", dbType=DBColumn.DBType.Varchar, length=36)
    protected String ruleId;
    @DBColumn(nameInDB="CREATETIME", title="\u65f6\u95f4\u6233", dbType=DBColumn.DBType.DateTime)
    protected Date createTime;
    @DBColumn(nameInDB="OFFSETSRCTYPE", title="\u62b5\u9500\u6765\u6e90\u7c7b\u578b", dbType=DBColumn.DBType.Int, isRequired=true, description="\u679a\u4e3e\u7c7b\uff0c0-99\u6570\u5b57\uff0c\u5b9a\u4e49\u4e0d\u540c\u6765\u6e90\u7c7b\u578b")
    protected Integer offSetSrcType;
    @DBColumn(nameInDB="SRCID", title="\u62b5\u9500\u6765\u6e90ID", dbType=DBColumn.DBType.Varchar, length=36, description="\u5bf9\u5e94\u91c7\u96c6\u65b9\u7684id\u6216\u8005\u5206\u7ec4id")
    protected String srcId;
    @DBColumn(nameInDB="EFFECTTYPE", title="\u5f71\u54cd\u671f\u95f4", dbType=DBColumn.DBType.NVarchar, length=20)
    private String effectType;
    @DBColumn(nameInDB="DISABLEFLAG", title="\u662f\u5426\u7981\u7528", dbType=DBColumn.DBType.Numeric, precision=1, scale=0)
    private Integer disableFlag = 0;
    @DBColumn(nameInDB="CREATEUSER", title="\u64cd\u4f5c\u4eba", dbType=DBColumn.DBType.NVarchar, length=100)
    private String createUser;

    public String getAdjust() {
        return this.adjust;
    }

    public void setAdjust(String adjust) {
        this.adjust = adjust;
    }

    public String getmRecid() {
        return this.mRecid;
    }

    public void setmRecid(String mRecid) {
        this.mRecid = mRecid;
    }

    public String getSrcOffsetGroupId() {
        return this.srcOffsetGroupId;
    }

    public void setSrcOffsetGroupId(String srcOffsetGroupId) {
        this.srcOffsetGroupId = srcOffsetGroupId;
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

    public String getDefaultPeriod() {
        return this.defaultPeriod;
    }

    public void setDefaultPeriod(String defaultPeriod) {
        this.defaultPeriod = defaultPeriod;
    }

    public String getGcBusinessTypeCode() {
        return this.gcBusinessTypeCode;
    }

    public void setGcBusinessTypeCode(String gcBusinessTypeCode) {
        this.gcBusinessTypeCode = gcBusinessTypeCode;
    }

    public String getUnitVersion() {
        return this.unitVersion;
    }

    public void setUnitVersion(String unitVersion) {
        this.unitVersion = unitVersion;
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

    public String getInputUnitId() {
        return this.inputUnitId;
    }

    public void setInputUnitId(String inputUnitId) {
        this.inputUnitId = inputUnitId;
    }

    public String getSystemId() {
        return this.systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public String getSubjectCode() {
        return this.subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public Integer getSubjectOrient() {
        return this.subjectOrient;
    }

    public void setSubjectOrient(Integer subjectOrient) {
        this.subjectOrient = subjectOrient;
    }

    public Integer getOrient() {
        return this.orient;
    }

    public void setOrient(Integer orient) {
        this.orient = orient;
    }

    public String getMemo() {
        return this.memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Date getModifyTime() {
        return this.modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Double getSortOrder() {
        return this.sortOrder;
    }

    public void setSortOrder(Double sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getOffSetCurr() {
        return this.offSetCurr;
    }

    public void setOffSetCurr(String offSetCurr) {
        this.addFieldValue("OFFSETCURR", offSetCurr);
        this.offSetCurr = offSetCurr;
    }

    public Double getDebitCNY() {
        return this.debitCNY;
    }

    public void setDebitCNY(Double debitCNY) {
        this.addFieldValue("DEBITCNY", debitCNY);
        this.debitCNY = debitCNY;
    }

    public Double getCreditCNY() {
        return this.creditCNY;
    }

    public void setCreditCNY(Double creditCNY) {
        this.addFieldValue("CREDITCNY", creditCNY);
        this.creditCNY = creditCNY;
    }

    public Double getDebitUSD() {
        return this.debitUSD;
    }

    public void setDebitUSD(Double debitUSD) {
        this.addFieldValue("DEBITUSD", debitUSD);
        this.debitUSD = debitUSD;
    }

    public Double getCreditUSD() {
        return this.creditUSD;
    }

    public void setCreditUSD(Double creditUSD) {
        this.addFieldValue("CREDITUSD", creditUSD);
        this.creditUSD = creditUSD;
    }

    public Double getDebitHKD() {
        return this.debitHKD;
    }

    public void setDebitHKD(Double debitHKD) {
        this.addFieldValue("DEBITHKD", debitHKD);
        this.debitHKD = debitHKD;
    }

    public Double getCreditHKD() {
        return this.creditHKD;
    }

    public void setCreditHKD(Double creditHKD) {
        this.addFieldValue("CREDITHKD", creditHKD);
        this.creditHKD = creditHKD;
    }

    public Double getOffSetDebitCNY() {
        return this.offSetDebitCNY;
    }

    public void setOffSetDebitCNY(Double offSetDebitCNY) {
        this.addFieldValue("OFFSETDEBITCNY", offSetDebitCNY);
        this.offSetDebitCNY = offSetDebitCNY;
    }

    public Double getOffSetCreditCNY() {
        return this.offSetCreditCNY;
    }

    public void setOffSetCreditCNY(Double offSetCreditCNY) {
        this.addFieldValue("OFFSETCREDITCNY", offSetCreditCNY);
        this.offSetCreditCNY = offSetCreditCNY;
    }

    public Double getOffSetDebitUSD() {
        return this.offSetDebitUSD;
    }

    public void setOffSetDebitUSD(Double offSetDebitUSD) {
        this.addFieldValue("OFFSETDEBITUSD", offSetDebitUSD);
        this.offSetDebitUSD = offSetDebitUSD;
    }

    public Double getOffSetCreditUSD() {
        return this.offSetCreditUSD;
    }

    public void setOffSetCreditUSD(Double offSetCreditUSD) {
        this.addFieldValue("OFFSETCREDITUSD", offSetCreditUSD);
        this.offSetCreditUSD = offSetCreditUSD;
    }

    public Double getOffSetDebitHKD() {
        return this.offSetDebitHKD;
    }

    public void setOffSetDebitHKD(Double offSetDebitHKD) {
        this.addFieldValue("OFFSETDEBITHKD", offSetDebitHKD);
        this.offSetDebitHKD = offSetDebitHKD;
    }

    public Double getOffSetCreditHKD() {
        return this.offSetCreditHKD;
    }

    public void setOffSetCreditHKD(Double offSetCreditHKD) {
        this.addFieldValue("OFFSETCREDITHKD", offSetCreditHKD);
        this.offSetCreditHKD = offSetCreditHKD;
    }

    public Double getDiffdCNY() {
        return this.diffdCNY;
    }

    public void setDiffdCNY(Double diffdCNY) {
        this.addFieldValue("DIFFDCNY", diffdCNY);
        this.diffdCNY = diffdCNY;
    }

    public Double getDiffcCNY() {
        return this.diffcCNY;
    }

    public void setDiffcCNY(Double diffcCNY) {
        this.addFieldValue("DIFFCCNY", diffcCNY);
        this.diffcCNY = diffcCNY;
    }

    public Double getDiffdUSD() {
        return this.diffdUSD;
    }

    public void setDiffdUSD(Double diffdUSD) {
        this.addFieldValue("DIFFDUSD", diffdUSD);
        this.diffdUSD = diffdUSD;
    }

    public Double getDiffcUSD() {
        return this.diffcUSD;
    }

    public void setDiffcUSD(Double diffcUSD) {
        this.addFieldValue("DIFFCUSD", diffcUSD);
        this.diffcUSD = diffcUSD;
    }

    public Double getDiffdHKD() {
        return this.diffdHKD;
    }

    public void setDiffdHKD(Double diffdHKD) {
        this.addFieldValue("DIFFDHKD", diffdHKD);
        this.diffdHKD = diffdHKD;
    }

    public Double getDiffcHKD() {
        return this.diffcHKD;
    }

    public void setDiffcHKD(Double diffcHKD) {
        this.addFieldValue("DIFFCHKD", diffcHKD);
        this.diffcHKD = diffcHKD;
    }

    public String getOrgType() {
        return this.orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public Integer getElmMode() {
        return this.elmMode;
    }

    public void setElmMode(Integer elmMode) {
        this.elmMode = elmMode;
    }

    public String getRuleId() {
        return this.ruleId;
    }

    public void setRuleId(String ruleId) {
        this.ruleId = ruleId;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getOffSetSrcType() {
        return this.offSetSrcType;
    }

    public void setOffSetSrcType(Integer offSetSrcType) {
        this.offSetSrcType = offSetSrcType;
    }

    public String getSrcId() {
        return this.srcId;
    }

    public void setSrcId(String srcId) {
        this.srcId = srcId;
    }

    public String getEffectType() {
        return this.effectType;
    }

    public void setEffectType(String effectType) {
        this.effectType = effectType;
    }

    public Integer getDisableFlag() {
        return this.disableFlag;
    }

    public void setDisableFlag(Integer disableFlag) {
        this.disableFlag = disableFlag;
    }

    public String getCreateUser() {
        return this.createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }
}

