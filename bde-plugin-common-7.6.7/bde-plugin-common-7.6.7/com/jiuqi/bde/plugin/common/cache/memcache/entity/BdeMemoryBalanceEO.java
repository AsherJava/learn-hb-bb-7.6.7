/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.budget.param.hypermodel.domain.enums.ModelType
 *  com.jiuqi.dc.base.common.definition.DcDefaultTableEntity
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn$DBType
 *  com.jiuqi.gcreport.definition.impl.anno.DBIndex
 *  com.jiuqi.gcreport.definition.impl.anno.DBIndex$TableIndexType
 *  com.jiuqi.gcreport.definition.impl.anno.DBTable
 *  com.jiuqi.gcreport.definition.impl.anno.DBTableGroup
 *  com.jiuqi.nvwa.definition.common.TableModelKind
 */
package com.jiuqi.bde.plugin.common.cache.memcache.entity;

import com.jiuqi.budget.param.hypermodel.domain.enums.ModelType;
import com.jiuqi.dc.base.common.definition.DcDefaultTableEntity;
import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBIndex;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.anno.DBTableGroup;
import com.jiuqi.nvwa.definition.common.TableModelKind;
import java.math.BigDecimal;

@DBTable(name="BDE_BALANCE", title="\u79d1\u76ee\u4f59\u989d\u8868", primaryRequired=false, kind=TableModelKind.SYSTEM_EXTEND, ownerGroupID=@DBTableGroup(id="11000000-0000-0000-0000-000000000002", code="table_group_bde", title="BDE"), indexs={@DBIndex(name="IDX_BDE_BALANCE_BIZKEY", type=DBIndex.TableIndexType.TABLE_INDEX_NORMAL, columnsFields={"BIZCOMBID", "SUBJECTCODE", "CURRENCYCODE"})}, convertToBudModel=false, modelType=ModelType.OTHER, dataSource="jiuqi.gcreport.mdd.datasource", sourceTable="")
public class BdeMemoryBalanceEO
extends DcDefaultTableEntity {
    private static final long serialVersionUID = 71073165792355641L;
    @DBColumn(nameInDB="BIZCOMBID", title="\u4e1a\u52a1\u7ec4\u5408\u6807\u8bc6", dbType=DBColumn.DBType.NVarchar, length=200, isRequired=true, order=2)
    private String bizCombId;
    @DBColumn(nameInDB="SUBJECTCODE", title="\u79d1\u76ee", dbType=DBColumn.DBType.NVarchar, length=60, defaultValue="'#'", order=3)
    private String subjectCode;
    @DBColumn(nameInDB="CURRENCYCODE", title="\u5e01\u522b", dbType=DBColumn.DBType.NVarchar, length=60, defaultValue="'#'", order=4)
    private String currencyCode;
    @DBColumn(nameInDB="ORIENT", title="\u79d1\u76ee\u65b9\u5411", dbType=DBColumn.DBType.Int, length=2, isRequired=true, order=5)
    private Integer orient;
    @DBColumn(nameInDB="NC", title="\u5e74\u521d", dbType=DBColumn.DBType.Numeric, precision=19, scale=2, isRequired=true, defaultValue="0.00", order=6)
    private BigDecimal nc;
    @DBColumn(nameInDB="C", title="\u671f\u521d", dbType=DBColumn.DBType.Numeric, precision=19, scale=2, isRequired=true, defaultValue="0.00", order=7)
    private BigDecimal c;
    @DBColumn(nameInDB="JF", title="\u501f\u65b9", dbType=DBColumn.DBType.Numeric, precision=19, scale=2, isRequired=true, defaultValue="0.00", order=8)
    private BigDecimal jf;
    @DBColumn(nameInDB="DF", title="\u8d37\u65b9", dbType=DBColumn.DBType.Numeric, precision=19, scale=2, isRequired=true, defaultValue="0.00", order=9)
    private BigDecimal df;
    @DBColumn(nameInDB="JL", title="\u501f\u7d2f", dbType=DBColumn.DBType.Numeric, precision=19, scale=2, isRequired=true, defaultValue="0.00", order=10)
    private BigDecimal jl;
    @DBColumn(nameInDB="DL", title="\u8d37\u7d2f", dbType=DBColumn.DBType.Numeric, precision=19, scale=2, isRequired=true, defaultValue="0.00", order=11)
    private BigDecimal dl;
    @DBColumn(nameInDB="YE", title="\u4f59\u989d", dbType=DBColumn.DBType.Numeric, precision=19, scale=2, isRequired=true, defaultValue="0.00", order=12)
    private BigDecimal ye;
    @DBColumn(nameInDB="WNC", title="\u539f\u5e01\u5e74\u521d", dbType=DBColumn.DBType.Numeric, precision=19, scale=2, isRequired=true, defaultValue="0.00", order=13)
    private BigDecimal wnc;
    @DBColumn(nameInDB="WC", title="\u539f\u5e01\u671f\u521d", dbType=DBColumn.DBType.Numeric, precision=19, scale=2, isRequired=true, defaultValue="0.00", order=14)
    private BigDecimal wc;
    @DBColumn(nameInDB="WJF", title="\u539f\u5e01\u501f\u65b9", dbType=DBColumn.DBType.Numeric, precision=19, scale=2, isRequired=true, defaultValue="0.00", order=15)
    private BigDecimal wjf;
    @DBColumn(nameInDB="WDF", title="\u539f\u5e01\u8d37\u65b9", dbType=DBColumn.DBType.Numeric, precision=19, scale=2, isRequired=true, defaultValue="0.00", order=16)
    private BigDecimal wdf;
    @DBColumn(nameInDB="WJL", title="\u539f\u5e01\u501f\u7d2f", dbType=DBColumn.DBType.Numeric, precision=19, scale=2, isRequired=true, defaultValue="0.00", order=17)
    private BigDecimal wjl;
    @DBColumn(nameInDB="WDL", title="\u539f\u5e01\u8d37\u7d2f", dbType=DBColumn.DBType.Numeric, precision=19, scale=2, isRequired=true, defaultValue="0.00", order=18)
    private BigDecimal wdl;
    @DBColumn(nameInDB="WYE", title="\u539f\u5e01\u4f59\u989d", dbType=DBColumn.DBType.Numeric, precision=19, scale=2, isRequired=true, defaultValue="0.00", order=19)
    private BigDecimal wye;

    public String getTableName() {
        return "BDE_BALANCE";
    }

    public String getBizCombId() {
        return this.bizCombId;
    }

    public void setBizCombId(String bizCombId) {
        this.bizCombId = bizCombId;
    }

    public String getSubjectCode() {
        return this.subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getCurrencyCode() {
        return this.currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public Integer getOrient() {
        return this.orient;
    }

    public void setOrient(Integer orient) {
        this.orient = orient;
    }

    public BigDecimal getNc() {
        return this.nc;
    }

    public void setNc(BigDecimal nc) {
        this.nc = nc;
    }

    public BigDecimal getC() {
        return this.c;
    }

    public void setC(BigDecimal c) {
        this.c = c;
    }

    public BigDecimal getJf() {
        return this.jf;
    }

    public void setJf(BigDecimal jf) {
        this.jf = jf;
    }

    public BigDecimal getDf() {
        return this.df;
    }

    public void setDf(BigDecimal df) {
        this.df = df;
    }

    public BigDecimal getJl() {
        return this.jl;
    }

    public void setJl(BigDecimal jl) {
        this.jl = jl;
    }

    public BigDecimal getDl() {
        return this.dl;
    }

    public void setDl(BigDecimal dl) {
        this.dl = dl;
    }

    public BigDecimal getYe() {
        return this.ye;
    }

    public void setYe(BigDecimal ye) {
        this.ye = ye;
    }

    public BigDecimal getWnc() {
        return this.wnc;
    }

    public void setWnc(BigDecimal wnc) {
        this.wnc = wnc;
    }

    public BigDecimal getWc() {
        return this.wc;
    }

    public void setWc(BigDecimal wc) {
        this.wc = wc;
    }

    public BigDecimal getWjf() {
        return this.wjf;
    }

    public void setWjf(BigDecimal wjf) {
        this.wjf = wjf;
    }

    public BigDecimal getWdf() {
        return this.wdf;
    }

    public void setWdf(BigDecimal wdf) {
        this.wdf = wdf;
    }

    public BigDecimal getWjl() {
        return this.wjl;
    }

    public void setWjl(BigDecimal wjl) {
        this.wjl = wjl;
    }

    public BigDecimal getWdl() {
        return this.wdl;
    }

    public void setWdl(BigDecimal wdl) {
        this.wdl = wdl;
    }

    public BigDecimal getWye() {
        return this.wye;
    }

    public void setWye(BigDecimal wye) {
        this.wye = wye;
    }
}

