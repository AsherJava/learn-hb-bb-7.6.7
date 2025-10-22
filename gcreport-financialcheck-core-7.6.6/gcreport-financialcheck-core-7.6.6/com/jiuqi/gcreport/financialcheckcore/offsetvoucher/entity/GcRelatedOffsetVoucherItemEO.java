/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn$DBType
 *  com.jiuqi.gcreport.definition.impl.anno.DBIndex
 *  com.jiuqi.gcreport.definition.impl.anno.DBTable
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 */
package com.jiuqi.gcreport.financialcheckcore.offsetvoucher.entity;

import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBIndex;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;

@DBTable(name="GC_RELATEDOFFSETVCHRITEM", title="\u5173\u8054\u4ea4\u6613\u62b5\u9500\u51ed\u8bc1\u5206\u5f55\u8868", indexs={@DBIndex(name="IDX_GC_RELATEDOFFSETVCHRITEM_CHECKID", columnsFields={"CHECKID"}), @DBIndex(name="IDX_RELTX_OVI_UNIT", columnsFields={"UNITID", "OPPUNITID"})}, dataSource="jiuqi.gcreport.mdd.datasource", sourceTable="GC_RELATED_ITEM")
public class GcRelatedOffsetVoucherItemEO
extends DefaultTableEntity {
    public static final String TABLENAME = "GC_RELATEDOFFSETVCHRITEM";
    @DBColumn(nameInDB="ACCTYEAR", title="\u5e74\u5ea6", dbType=DBColumn.DBType.Int, isRequired=true)
    private Integer acctYear;
    @DBColumn(nameInDB="ACCTPERIOD", title="\u671f\u95f4", dbType=DBColumn.DBType.Int, isRequired=true)
    private Integer acctPeriod;
    @DBColumn(nameInDB="UNITID", title="\u672c\u65b9\u5355\u4f4d", dbType=DBColumn.DBType.Varchar, length=36, isRequired=true)
    private String unitId;
    @DBColumn(nameInDB="OPPUNITID", title="\u5bf9\u65b9\u5355\u4f4d", dbType=DBColumn.DBType.Varchar, length=36, isRequired=true)
    private String oppUnitId;
    @DBColumn(nameInDB="OFFSETSUBJECT", title="\u62b5\u9500\u79d1\u76ee", dbType=DBColumn.DBType.Varchar, length=36, isRequired=true)
    private String offsetSubject;
    @DBColumn(nameInDB="OFFSETCURRENCY", title="\u62b5\u9500\u5e01\u79cd", dbType=DBColumn.DBType.Varchar, length=36, isRequired=true)
    private String offsetCurrency;
    @DBColumn(nameInDB="DEBITSRC", title="\u539f\u59cb\u501f\u65b9\u91d1\u989d", dbType=DBColumn.DBType.Numeric, precision=19, scale=2, isRequired=true)
    private Double debitSrc;
    @DBColumn(nameInDB="CREDITSRC", title="\u539f\u59cb\u8d37\u65b9\u91d1\u989d", dbType=DBColumn.DBType.Numeric, precision=19, scale=2, isRequired=true)
    private Double creditSrc;
    @DBColumn(nameInDB="DEBITOFFSET", title="\u62b5\u9500\u501f\u65b9\u91d1\u989d", dbType=DBColumn.DBType.Numeric, precision=19, scale=2, isRequired=true)
    private Double debitOffset;
    @DBColumn(nameInDB="CREDITOFFSET", title="\u62b5\u9500\u8d37\u65b9\u91d1\u989d", dbType=DBColumn.DBType.Numeric, precision=19, scale=2, isRequired=true)
    private Double creditOffset;
    @DBColumn(nameInDB="GCNUMBER", title="\u534f\u540c\u7801", dbType=DBColumn.DBType.NVarchar, length=100)
    private String gcNumber;
    @DBColumn(nameInDB="CHECKID", title="\u5bf9\u8d26\u5206\u7ec4\u6807\u8bc6", dbType=DBColumn.DBType.Varchar, length=36, isRequired=true)
    private String checkId;
    @DBColumn(nameInDB="OFFSETPERIOD", title="\u62b5\u9500\u8d26\u671f", dbType=DBColumn.DBType.Int)
    private Integer offsetPeriod;
    @DBColumn(nameInDB="OFFSETMETHOD", title="\u62b5\u9500\u5904\u7406\u65b9\u6cd5", dbType=DBColumn.DBType.Varchar, length=36)
    private String offsetMethod;
    @DBColumn(nameInDB="REMARK", title="\u5907\u6ce8", dbType=DBColumn.DBType.NVarchar, length=300)
    private String remark;
    @DBColumn(nameInDB="SRCITEMID", title="\u539f\u59cb\u5206\u5f55ID", dbType=DBColumn.DBType.Varchar, length=36)
    private String srcItemId;

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

    public String getOffsetSubject() {
        return this.offsetSubject;
    }

    public void setOffsetSubject(String offsetSubject) {
        this.offsetSubject = offsetSubject;
    }

    public String getOffsetCurrency() {
        return this.offsetCurrency;
    }

    public void setOffsetCurrency(String offsetCurrency) {
        this.offsetCurrency = offsetCurrency;
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

    public String getOffsetMethod() {
        return this.offsetMethod;
    }

    public void setOffsetMethod(String offsetMethod) {
        this.offsetMethod = offsetMethod;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSrcItemId() {
        return this.srcItemId;
    }

    public void setSrcItemId(String srcItemId) {
        this.srcItemId = srcItemId;
    }

    public String toString() {
        return "GcRelatedOffsetVoucherItemEO{acctYear=" + this.acctYear + ", acctPeriod=" + this.acctPeriod + ", unitId='" + this.unitId + '\'' + ", oppUnitId='" + this.oppUnitId + '\'' + ", offsetSubject='" + this.offsetSubject + '\'' + ", offsetCurrency='" + this.offsetCurrency + '\'' + ", debitSrc=" + this.debitSrc + ", creditSrc=" + this.creditSrc + ", debitOffset=" + this.debitOffset + ", creditOffset=" + this.creditOffset + ", gcNumber='" + this.gcNumber + '\'' + ", checkId='" + this.checkId + '\'' + ", offsetPeriod=" + this.offsetPeriod + ", offsetMethod='" + this.offsetMethod + '\'' + ", remark='" + this.remark + '\'' + ", srcItemId='" + this.srcItemId + '\'' + "} " + super.toString();
    }
}

