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
package com.jiuqi.gcreport.calculate.entity;

import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBIndex;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;

@DBTable(name="GC_FLOATBALANCE_DIFF", title="\u6d6e\u52a8\u4f59\u989d\u5dee\u989d\u8868", stopSuper=DefaultTableEntity.class, indexs={@DBIndex(name="IDX_GC_FLOATBALANCEDIFF_BIZKEYORDER", columnsFields={"BIZKEYORDER"}, type=DBIndex.TableIndexType.TABLE_INDEX_UNIQUE)})
@Deprecated
public class FloatBalanceDiffEO
extends DefaultTableEntity {
    private static final long serialVersionUID = 1L;
    public static final String TABLENAME = "GC_FLOATBALANCE_DIFF";
    @DBColumn(nameInDB="MDCODE", title="\u7ec4\u7ec7\u5b9e\u4f53", dbType=DBColumn.DBType.NVarchar, length=50, refTabField="MD_ORG_CORPORATE.CODE")
    private String mdOrg;
    @DBColumn(nameInDB="OPPUNITTITLE", title="\u5bf9\u65b9\u5355\u4f4d", dbType=DBColumn.DBType.NVarchar, length=50)
    private String oppUnitTitle;
    @DBColumn(nameInDB="ACCTORGTITLE", title="\u8bb0\u8d26\u5355\u4f4d", dbType=DBColumn.DBType.NVarchar, length=50)
    private String acctOrgTitle;
    @DBColumn(nameInDB="DEFAULT_PERIOD", title="\u9ed8\u8ba4\u65f6\u671f\u4e3b\u4f53", dbType=DBColumn.DBType.NVarchar, length=9, isRequired=true, refTabField="DEFAULT_PERIOD.SQ_CODE")
    private String defaultPeriod;
    @DBColumn(nameInDB="MD_CURRENCY", title="\u5e01\u79cd", dbType=DBColumn.DBType.NVarchar, length=100, isRequired=true, refTabField="MD_CURRENCY.CODE")
    private String currency;
    @DBColumn(nameInDB="MD_GCORGTYPE", title="\u5408\u5e76\u5355\u4f4d\u7c7b\u578b", dbType=DBColumn.DBType.NVarchar, length=50, isRequired=true, refTabField="MD_GCORGTYPE.CODE")
    private String orgType;
    @DBColumn(nameInDB="FLOATORDER", title="FLOATORDER", dbType=DBColumn.DBType.Double, fieldValueType=10, isRequired=true)
    private Double floatOrder;
    @DBColumn(nameInDB="SUBJECTCODE", title="\u79d1\u76ee", dbType=DBColumn.DBType.NVarchar, length=200, isRequired=true, refTabField="MD_GCSUBJECT.CODE")
    private String subjectCode;
    @DBColumn(nameInDB="AMT", title="\u91d1\u989d", dbType=DBColumn.DBType.Numeric, scale=2)
    private Double amt;
    @DBColumn(nameInDB="ACCOUNTAGE1", title="\u8d26\u9f841", dbType=DBColumn.DBType.Numeric, scale=2)
    private Double accountAge1;
    @DBColumn(nameInDB="ACCOUNTAGE2", title="\u8d26\u9f842", dbType=DBColumn.DBType.Numeric, scale=2)
    private Double accountAge2;
    @DBColumn(nameInDB="ACCOUNTAGE3", title="\u8d26\u9f843", dbType=DBColumn.DBType.Numeric, scale=2)
    private Double accountAge3;
    @DBColumn(nameInDB="ACCOUNTAGE4", title="\u8d26\u9f844", dbType=DBColumn.DBType.Numeric, scale=2)
    private Double accountAge4;
    @DBColumn(nameInDB="ACCOUNTAGE5", title="\u8d26\u9f845", dbType=DBColumn.DBType.Numeric, scale=2)
    private Double accountAge5;
    @DBColumn(nameInDB="ACCOUNTAGE6", title="\u8d26\u9f846", dbType=DBColumn.DBType.Numeric, scale=2)
    private Double accountAge6;
    @DBColumn(nameInDB="ACCOUNTAGE7", title="\u8d26\u9f847", dbType=DBColumn.DBType.Numeric, scale=2)
    private Double accountAge7;
    @DBColumn(nameInDB="ACCOUNTAGE8", title="\u8d26\u9f848", dbType=DBColumn.DBType.Numeric, scale=2)
    private Double accountAge8;
    @DBColumn(nameInDB="ACCOUNTAGE9", title="\u8d26\u9f849", dbType=DBColumn.DBType.Numeric, scale=2)
    private Double accountAge9;
    @DBColumn(nameInDB="ACCOUNTAGE10", title="\u8d26\u9f8410", dbType=DBColumn.DBType.Numeric, scale=2)
    private Double accountAge10;
    @DBColumn(nameInDB="BIZKEYORDER", title="BIZKEYORDER", dbType=DBColumn.DBType.NVarchar, length=50, fieldValueType=13, isRequired=true, isRecid=true)
    private String bizKeyOrder;
    @DBColumn(nameInDB="ID", title="\u884c\u6807\u8bc6", dbType=DBColumn.DBType.Varchar, isRequired=false)
    private String id;

    public String getMdOrg() {
        return this.mdOrg;
    }

    public void setMdOrg(String mdOrg) {
        this.mdOrg = mdOrg;
    }

    public String getDefaultPeriod() {
        return this.defaultPeriod;
    }

    public void setDefaultPeriod(String defaultPeriod) {
        this.defaultPeriod = defaultPeriod;
    }

    public String getCurrency() {
        return this.currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getOrgType() {
        return this.orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public Double getFloatOrder() {
        return this.floatOrder;
    }

    public void setFloatOrder(Double floatOrder) {
        this.floatOrder = floatOrder;
    }

    public String getSubjectCode() {
        return this.subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public Double getAmt() {
        return this.amt;
    }

    public void setAmt(Double amt) {
        this.amt = amt;
    }

    public String getBizKeyOrder() {
        return this.bizKeyOrder;
    }

    public void setBizKeyOrder(String bizKeyOrder) {
        this.bizKeyOrder = bizKeyOrder;
    }

    @Deprecated
    public String getId() {
        return this.id;
    }

    @Deprecated
    public void setId(String id) {
        this.id = id;
    }

    public String getOppUnitTitle() {
        return this.oppUnitTitle;
    }

    public void setOppUnitTitle(String oppUnitTitle) {
        this.oppUnitTitle = oppUnitTitle;
    }

    public String getAcctOrgTitle() {
        return this.acctOrgTitle;
    }

    public void setAcctOrgTitle(String acctOrgTitle) {
        this.acctOrgTitle = acctOrgTitle;
    }

    public Double getAccountAge1() {
        return this.accountAge1;
    }

    public void setAccountAge1(Double accountAge1) {
        this.accountAge1 = accountAge1;
    }

    public Double getAccountAge2() {
        return this.accountAge2;
    }

    public void setAccountAge2(Double accountAge2) {
        this.accountAge2 = accountAge2;
    }

    public Double getAccountAge3() {
        return this.accountAge3;
    }

    public void setAccountAge3(Double accountAge3) {
        this.accountAge3 = accountAge3;
    }

    public Double getAccountAge4() {
        return this.accountAge4;
    }

    public void setAccountAge4(Double accountAge4) {
        this.accountAge4 = accountAge4;
    }

    public Double getAccountAge5() {
        return this.accountAge5;
    }

    public void setAccountAge5(Double accountAge5) {
        this.accountAge5 = accountAge5;
    }

    public Double getAccountAge6() {
        return this.accountAge6;
    }

    public void setAccountAge6(Double accountAge6) {
        this.accountAge6 = accountAge6;
    }

    public Double getAccountAge7() {
        return this.accountAge7;
    }

    public void setAccountAge7(Double accountAge7) {
        this.accountAge7 = accountAge7;
    }

    public Double getAccountAge8() {
        return this.accountAge8;
    }

    public void setAccountAge8(Double accountAge8) {
        this.accountAge8 = accountAge8;
    }

    public Double getAccountAge9() {
        return this.accountAge9;
    }

    public void setAccountAge9(Double accountAge9) {
        this.accountAge9 = accountAge9;
    }

    public Double getAccountAge10() {
        return this.accountAge10;
    }

    public void setAccountAge10(Double accountAge10) {
        this.accountAge10 = accountAge10;
    }
}

