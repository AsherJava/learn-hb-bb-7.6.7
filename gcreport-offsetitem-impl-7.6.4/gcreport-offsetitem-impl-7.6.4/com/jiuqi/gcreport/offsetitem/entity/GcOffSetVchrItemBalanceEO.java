/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn$DBType
 *  com.jiuqi.gcreport.definition.impl.anno.DBTable
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 *  javax.persistence.Table
 *  javax.persistence.UniqueConstraint
 */
package com.jiuqi.gcreport.offsetitem.entity;

import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import java.util.Arrays;
import java.util.List;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Table(name="GC_OFFSETVCHRITEMBLC", uniqueConstraints={@UniqueConstraint(columnNames={"unitid", "oppunitid", "inputUnitId", "acctyear", "acctperiod", "subjectcode", "ruleid", "schemeid", "gcbusinesstypecode"})})
@DBTable(name="GC_OFFSETVCHRITEMBLC", title="\u62b5\u9500\u5206\u5f55\u4f59\u989d\u8868", inStorage=true)
public class GcOffSetVchrItemBalanceEO
extends DefaultTableEntity {
    public static final String TABLENAME = "GC_OFFSETVCHRITEMBLC";
    public static final List<String> BALANCE_DIMENSION_FIELDS = Arrays.asList("unitId", "oppUnitId", "inputUnitId", "acctYear", "acctPeriod", "subjectCode", "ruleId", "schemeid", "gcBusinessTypeCode");
    @DBColumn(nameInDB="acctyear", title="\u5e74\u5ea6", dbType=DBColumn.DBType.Int, isRequired=true)
    private Integer acctYear;
    @DBColumn(nameInDB="acctperiod", title="\u671f\u95f4", dbType=DBColumn.DBType.Int, isRequired=true)
    private Integer acctPeriod;
    @DBColumn(nameInDB="unitid", title="\u672c\u65b9\u5355\u4f4d", dbType=DBColumn.DBType.Varchar, length=36, isRequired=true)
    private String unitId;
    @DBColumn(nameInDB="oppunitid", title="\u5bf9\u65b9\u5355\u4f4d", dbType=DBColumn.DBType.Varchar, length=36, isRequired=true)
    private String oppUnitId;
    @DBColumn(nameInDB="inputunitid", title="\u5408\u5e76\u5355\u4f4d(\u5f55\u5165\u5355\u4f4d)", dbType=DBColumn.DBType.Varchar, length=36, isRequired=true)
    private String inputUnitId;
    @DBColumn(nameInDB="subjectcode", title="\u79d1\u76ee", dbType=DBColumn.DBType.NVarchar, isRequired=true)
    private String subjectCode;
    @DBColumn(nameInDB="ruleid", title="\u62b5\u9500\u89c4\u5219ID", dbType=DBColumn.DBType.Varchar)
    private String ruleId;
    @DBColumn(nameInDB="schemeid", title="\u62a5\u8868\u65b9\u6848", dbType=DBColumn.DBType.Varchar)
    private String schemeId;
    @DBColumn(nameInDB="gcbusinesstypecode", title="\u5408\u5e76\u4e1a\u52a1\u7c7b\u578b", dbType=DBColumn.DBType.NVarchar)
    private String gcBusinessTypeCode;
    @DBColumn(nameInDB="affectstrategy", title="\u5f71\u54cd\u4f59\u989d\u8868\u7b56\u7565", dbType=DBColumn.DBType.Int)
    private int affectStrategy;
    @DBColumn(nameInDB="debit_cny", title="\u672c\u671f\u501f\u65b9\u53d1\u751f\u91d1\u989d_\u4eba\u6c11\u5e01", dbType=DBColumn.DBType.Numeric, precision=19, scale=4)
    private Double debitCNY;
    @DBColumn(nameInDB="credit_cny", title="\u672c\u671f\u8d37\u65b9\u53d1\u751f\u91d1\u989d_\u4eba\u6c11\u5e01", dbType=DBColumn.DBType.Numeric, precision=19, scale=4)
    private Double creditCNY;
    @DBColumn(nameInDB="cf_cny", title="\u672c\u671f\u671f\u672b\u4f59\u989d_\u4eba\u6c11\u5e01", dbType=DBColumn.DBType.Numeric, precision=19, scale=4)
    private Double cfCNY;
    @DBColumn(nameInDB="debit_usd", title="\u672c\u671f\u501f\u65b9\u53d1\u751f\u91d1\u989d_\u7f8e\u5143", dbType=DBColumn.DBType.Numeric, precision=19, scale=4)
    private Double debitUSD;
    @DBColumn(nameInDB="credit_usd", title="\u672c\u671f\u8d37\u65b9\u53d1\u751f\u91d1\u989d_\u7f8e\u5143", dbType=DBColumn.DBType.Numeric, precision=19, scale=4)
    private Double creditUSD;
    @DBColumn(nameInDB="cf_usd", title="\u672c\u671f\u671f\u672b\u4f59\u989d_\u7f8e\u5143", dbType=DBColumn.DBType.Numeric, precision=19, scale=4)
    private Double cfUSD;
    @DBColumn(nameInDB="debit_hkd", title="\u672c\u671f\u501f\u65b9\u53d1\u751f\u91d1\u989d_\u6e2f\u5143", dbType=DBColumn.DBType.Numeric, precision=19, scale=4)
    private Double debitHKD;
    @DBColumn(nameInDB="credit_hkd", title="\u672c\u671f\u8d37\u65b9\u53d1\u751f\u91d1\u989d_\u6e2f\u5143", dbType=DBColumn.DBType.Numeric, precision=19, scale=4)
    private Double creditHKD;
    @DBColumn(nameInDB="cf_hkd", title="\u672c\u671f\u671f\u672b\u4f59\u989d_\u6e2f\u5143", dbType=DBColumn.DBType.Numeric, precision=19, scale=4)
    private Double cfHKD;

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

    public String getRuleId() {
        return this.ruleId;
    }

    public void setRuleId(String ruleId) {
        this.ruleId = ruleId;
    }

    public String getSchemeId() {
        return this.schemeId;
    }

    public void setSchemeId(String schemeId) {
        this.schemeId = schemeId;
    }

    public String getSubjectCode() {
        return this.subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getGcBusinessTypeCode() {
        return this.gcBusinessTypeCode;
    }

    public void setGcBusinessTypeCode(String gcBusinessTypeCode) {
        this.gcBusinessTypeCode = gcBusinessTypeCode;
    }

    public Double getDebitCNY() {
        return this.debitCNY;
    }

    public void setDebitCNY(Double debitCNY) {
        this.debitCNY = debitCNY;
    }

    public Double getCreditCNY() {
        return this.creditCNY;
    }

    public void setCreditCNY(Double creditCNY) {
        this.creditCNY = creditCNY;
    }

    public Double getCfCNY() {
        return this.cfCNY;
    }

    public void setCfCNY(Double cfCNY) {
        this.cfCNY = cfCNY;
    }

    public Double getDebitUSD() {
        return this.debitUSD;
    }

    public void setDebitUSD(Double debitUSD) {
        this.debitUSD = debitUSD;
    }

    public Double getCreditUSD() {
        return this.creditUSD;
    }

    public void setCreditUSD(Double creditUSD) {
        this.creditUSD = creditUSD;
    }

    public Double getCfUSD() {
        return this.cfUSD;
    }

    public void setCfUSD(Double cfUSD) {
        this.cfUSD = cfUSD;
    }

    public Double getDebitHKD() {
        return this.debitHKD;
    }

    public void setDebitHKD(Double debitHKD) {
        this.debitHKD = debitHKD;
    }

    public Double getCreditHKD() {
        return this.creditHKD;
    }

    public void setCreditHKD(Double creditHKD) {
        this.creditHKD = creditHKD;
    }

    public Double getCfHKD() {
        return this.cfHKD;
    }

    public void setCfHKD(Double cfHKD) {
        this.cfHKD = cfHKD;
    }

    public int getAffectStrategy() {
        return this.affectStrategy;
    }

    public void setAffectStrategy(int affectStrategy) {
        this.affectStrategy = affectStrategy;
    }
}

