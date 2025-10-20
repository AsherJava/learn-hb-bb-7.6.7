/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.dc.base.common.definition.DcDefaultTableEntity
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn$DBType
 *  com.jiuqi.gcreport.definition.impl.anno.DBTable
 *  com.jiuqi.gcreport.definition.impl.anno.DBTableGroup
 *  com.jiuqi.nvwa.definition.common.TableModelKind
 */
package com.jiuqi.dc.integration.execute.impl.entity;

import com.jiuqi.dc.base.common.definition.DcDefaultTableEntity;
import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.anno.DBTableGroup;
import com.jiuqi.nvwa.definition.common.TableModelKind;

@DBTable(name="DC_TEMP_CONVERTVCHRITEM", title="\u51ed\u8bc1\u5206\u5f55\u8f6c\u6362\u6570\u636e\u4e34\u65f6\u8868", kind=TableModelKind.SYSTEM_EXTEND, ownerGroupID=@DBTableGroup(id="11000000-0000-0000-0000-000000000003", code="table_group_datacenter", title="\u4e00\u672c\u8d26"), sourceTable="DC_VOUCHERITEMASS")
public class VchrItemConvertTempEO
extends DcDefaultTableEntity {
    private static final long serialVersionUID = -9004647358517658180L;
    @DBColumn(nameInDB="UNITCODE", title="\u7ec4\u7ec7\u673a\u6784", dbType=DBColumn.DBType.NVarchar, length=50, order=1)
    private String unitCode;
    @DBColumn(nameInDB="VCHRID", title="\u51ed\u8bc1ID", dbType=DBColumn.DBType.NVarchar, length=36, order=2)
    private String vchrId;
    @DBColumn(nameInDB="ACCTYEAR", title="\u5e74\u5ea6", dbType=DBColumn.DBType.Int, length=4, order=3)
    private String acctYear;
    @DBColumn(nameInDB="ACCTPERIOD", title="\u4f1a\u8ba1\u671f\u95f4", dbType=DBColumn.DBType.NVarchar, length=5, order=4)
    private String acctPeriod;
    @DBColumn(nameInDB="VCHRTYPECODE", title="\u51ed\u8bc1\u7c7b\u522b", dbType=DBColumn.DBType.NVarchar, length=60, order=5)
    private String vchrTypeCode;
    @DBColumn(nameInDB="VCHRNUM", title="\u51ed\u8bc1\u53f7", dbType=DBColumn.DBType.NVarchar, length=10, order=6)
    private String vchrNum;
    @DBColumn(nameInDB="ITEMORDER", title="\u51ed\u8bc1\u5206\u5f55\u53f7", dbType=DBColumn.DBType.NVarchar, length=10, order=7)
    private String itemOrder;
    @DBColumn(nameInDB="CREATEDATE", title="\u51ed\u8bc1\u65e5\u671f", dbType=DBColumn.DBType.Date, order=8)
    private String createDate;
    @DBColumn(nameInDB="SUBJECTCODE", title="\u79d1\u76ee\u4ee3\u7801", dbType=DBColumn.DBType.NVarchar, length=60, order=9)
    private String subjectCode;
    @DBColumn(nameInDB="SUBJECTNAME", title="\u79d1\u76ee\u540d\u79f0", dbType=DBColumn.DBType.NVarchar, length=100, order=10)
    private String subjectName;
    @DBColumn(nameInDB="EXTSUBJECT", title="\u5916\u90e8\u7cfb\u7edf\u79d1\u76ee", dbType=DBColumn.DBType.NVarchar, length=200, order=11)
    private String extSubject;
    @DBColumn(nameInDB="DIGEST", title="\u6458\u8981", dbType=DBColumn.DBType.NVarchar, length=1000, order=12)
    private String digest;
    @DBColumn(nameInDB="CURRENCYCODE", title="\u5e01\u522b\u4ee3\u7801", dbType=DBColumn.DBType.NVarchar, length=60, order=13)
    private String currencyCode;
    @DBColumn(nameInDB="FINCURR", title="\u7b2c\u4e00\u672c\u4f4d\u5e01\u5e01\u522b\u4ee3\u7801", dbType=DBColumn.DBType.NVarchar, length=60, order=14)
    private String fincurr;
    @DBColumn(nameInDB="EXCHRATE", title="\u6c47\u7387", dbType=DBColumn.DBType.Numeric, precision=19, scale=10, order=15)
    private String exchrate;
    @DBColumn(nameInDB="DEBIT", title="\u501f\u65b9\u91d1\u989d", dbType=DBColumn.DBType.Numeric, precision=19, scale=2, order=16)
    private String debit;
    @DBColumn(nameInDB="CREDIT", title="\u8d37\u65b9\u91d1\u989d", dbType=DBColumn.DBType.Numeric, precision=19, scale=2, order=17)
    private String credit;
    @DBColumn(nameInDB="ORGND", title="\u501f\u65b9\u539f\u5e01", dbType=DBColumn.DBType.Numeric, precision=19, scale=2, order=18)
    private String orgnD;
    @DBColumn(nameInDB="ORGNC", title="\u8d37\u65b9\u539f\u5e01", dbType=DBColumn.DBType.Numeric, precision=19, scale=2, order=19)
    private String orgnC;
    @DBColumn(nameInDB="SRCVCHRID", title="\u6765\u6e90\u51ed\u8bc1ID", dbType=DBColumn.DBType.NVarchar, length=60, order=20)
    private String srcVchrId;
    @DBColumn(nameInDB="SRCITEMID", title="\u6765\u6e90\u51ed\u8bc1\u5206\u5f55ID", dbType=DBColumn.DBType.NVarchar, length=60, order=21)
    private String srcItemId;
    @DBColumn(nameInDB="SRCITEMASSID", title="\u6765\u6e90\u8f85\u52a9\u5206\u5f55ID", dbType=DBColumn.DBType.NVarchar, length=60, order=22)
    private String srcItemAssId;
    @DBColumn(nameInDB="EXPIREDATE", title="\u5230\u671f\u65e5", dbType=DBColumn.DBType.NVarchar, length=8, defaultValue="'#'", isRequired=true, order=23)
    private String expireDate;
    @DBColumn(nameInDB="RECLASSIFYSUBJCODE", title="\u91cd\u5206\u7c7b\u540e\u79d1\u76ee\u4ee3\u7801", dbType=DBColumn.DBType.NVarchar, length=8, defaultValue="'#'", isRequired=true, order=24)
    private String reclassifySubjCode;
    @DBColumn(nameInDB="BIZDATE", title="\u4e1a\u52a1\u65e5\u671f", dbType=DBColumn.DBType.DateTime, order=25)
    private String bizDate;
    @DBColumn(nameInDB="CFITEMCODE", title="\u73b0\u91d1\u6d41\u91cf\u9879\u76ee", dbType=DBColumn.DBType.NVarchar, length=60, order=26)
    private String cfItemCode;
    @DBColumn(nameInDB="QTY", title="\u6570\u91cf", dbType=DBColumn.DBType.Numeric, precision=19, scale=6, order=27)
    private String qty;
    @DBColumn(nameInDB="PRICE", title="\u5355\u4ef7", dbType=DBColumn.DBType.Numeric, precision=19, scale=6, order=28)
    private String price;
    @DBColumn(nameInDB="SN", title="\u6279\u6b21\u53f7", dbType=DBColumn.DBType.Int, length=10, order=29)
    private String sn;
    @DBColumn(nameInDB="VCHRSRCTYPE", title="\u51ed\u8bc1\u6765\u6e90\u7c7b\u522b", dbType=DBColumn.DBType.NVarchar, length=20, order=30)
    private String vchrSrcType;
    @DBColumn(nameInDB="AMOUNT", title="\u4ea4\u6613\u5e01\u91d1\u989d", dbType=DBColumn.DBType.Numeric, precision=19, scale=2, order=31)
    private String amount;
    @DBColumn(nameInDB="ORGNAMOUNT", title="\u539f\u5e01\u91d1\u989d", dbType=DBColumn.DBType.Numeric, precision=19, scale=2, order=32)
    private String orgAmount;
    @DBColumn(nameInDB="QTYD", title="\u501f\u65b9\u6570\u91cf", dbType=DBColumn.DBType.Numeric, precision=19, scale=6, order=33)
    private String qtyD;
    @DBColumn(nameInDB="QTYC", title="\u8d37\u65b9\u6570\u91cf", dbType=DBColumn.DBType.Numeric, precision=19, scale=6, order=34)
    private String qtyC;

    public String getUnitCode() {
        return this.unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public String getVchrId() {
        return this.vchrId;
    }

    public void setVchrId(String vchrId) {
        this.vchrId = vchrId;
    }

    public String getAcctYear() {
        return this.acctYear;
    }

    public void setAcctYear(String acctYear) {
        this.acctYear = acctYear;
    }

    public String getAcctPeriod() {
        return this.acctPeriod;
    }

    public void setAcctPeriod(String acctPeriod) {
        this.acctPeriod = acctPeriod;
    }

    public String getVchrTypeCode() {
        return this.vchrTypeCode;
    }

    public void setVchrTypeCode(String vchrTypeCode) {
        this.vchrTypeCode = vchrTypeCode;
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

    public String getCreateDate() {
        return this.createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
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

    public String getExtSubject() {
        return this.extSubject;
    }

    public void setExtSubject(String extSubject) {
        this.extSubject = extSubject;
    }

    public String getDigest() {
        return this.digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    public String getCurrencyCode() {
        return this.currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getFincurr() {
        return this.fincurr;
    }

    public void setFincurr(String fincurr) {
        this.fincurr = fincurr;
    }

    public String getExchrate() {
        return this.exchrate;
    }

    public void setExchrate(String exchrate) {
        this.exchrate = exchrate;
    }

    public String getDebit() {
        return this.debit;
    }

    public void setDebit(String debit) {
        this.debit = debit;
    }

    public String getCredit() {
        return this.credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public String getOrgnD() {
        return this.orgnD;
    }

    public void setOrgnD(String orgnD) {
        this.orgnD = orgnD;
    }

    public String getOrgnC() {
        return this.orgnC;
    }

    public void setOrgnC(String orgnC) {
        this.orgnC = orgnC;
    }

    public String getSrcVchrId() {
        return this.srcVchrId;
    }

    public void setSrcVchrId(String srcVchrId) {
        this.srcVchrId = srcVchrId;
    }

    public String getSrcItemId() {
        return this.srcItemId;
    }

    public void setSrcItemId(String srcItemId) {
        this.srcItemId = srcItemId;
    }

    public String getSrcItemAssId() {
        return this.srcItemAssId;
    }

    public void setSrcItemAssId(String srcItemAssId) {
        this.srcItemAssId = srcItemAssId;
    }

    public String getExpireDate() {
        return this.expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    public String getReclassifySubjCode() {
        return this.reclassifySubjCode;
    }

    public void setReclassifySubjCode(String reclassifySubjCode) {
        this.reclassifySubjCode = reclassifySubjCode;
    }

    public String getBizDate() {
        return this.bizDate;
    }

    public void setBizDate(String bizDate) {
        this.bizDate = bizDate;
    }

    public String getCfItemCode() {
        return this.cfItemCode;
    }

    public void setCfItemCode(String cfItemCode) {
        this.cfItemCode = cfItemCode;
    }

    public String getQty() {
        return this.qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getPrice() {
        return this.price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSn() {
        return this.sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getVchrSrcType() {
        return this.vchrSrcType;
    }

    public void setVchrSrcType(String vchrSrcType) {
        this.vchrSrcType = vchrSrcType;
    }

    public String getAmount() {
        return this.amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getOrgAmount() {
        return this.orgAmount;
    }

    public void setOrgAmount(String orgAmount) {
        this.orgAmount = orgAmount;
    }

    public String getQtyD() {
        return this.qtyD;
    }

    public void setQtyD(String qtyD) {
        this.qtyD = qtyD;
    }

    public String getQtyC() {
        return this.qtyC;
    }

    public void setQtyC(String qtyC) {
        this.qtyC = qtyC;
    }
}

