/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn$DBType
 *  com.jiuqi.gcreport.definition.impl.anno.DBTable
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 */
package com.jiuqi.gcreport.multicriteria.entity;

import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;

@DBTable(name="GC_MULCRIAFTERAMT", title="\u591a\u51c6\u5219\u8f6c\u6362\u540e\u91d1\u989d\u8868")
public class GcMulCriAfterAmtEO
extends DefaultTableEntity {
    public static final String TABLENAME = "GC_MULCRIAFTERAMT";
    @DBColumn(title="\u8f6c\u6362\u540e\u6307\u6807ID", dbType=DBColumn.DBType.Varchar, isRequired=true)
    private String mcAfterZbId;
    @DBColumn(title="\u5355\u4f4d", nameInDB="MDCODE", dbType=DBColumn.DBType.Varchar, isRequired=true)
    private String mdOrg;
    @DBColumn(title="\u5355\u4f4d\u7248\u672c", nameInDB="md_gcOrgType", dbType=DBColumn.DBType.Varchar, isRequired=true)
    private String gcOrgType;
    @DBColumn(title="\u5e01\u79cd", nameInDB="md_currency", dbType=DBColumn.DBType.Varchar, isRequired=true)
    private String currency;
    @DBColumn(title="\u65f6\u671f", nameInDB="DATATIME", dbType=DBColumn.DBType.Varchar, isRequired=true)
    private String defaultPeriod;
    @DBColumn(title="\u8f6c\u6362\u540e\u91d1\u989d", dbType=DBColumn.DBType.Numeric, scale=2)
    private Double afterZbAmt;
    @DBColumn(nameInDB="ADJUST", title="\u8c03\u6574\u671f", dbType=DBColumn.DBType.NVarchar, length=100)
    private String adjust;

    public String getMcAfterZbId() {
        return this.mcAfterZbId;
    }

    public void setMcAfterZbId(String mcAfterZbId) {
        this.mcAfterZbId = mcAfterZbId;
    }

    public String getMdOrg() {
        return this.mdOrg;
    }

    public void setMdOrg(String mdOrg) {
        this.mdOrg = mdOrg;
    }

    public String getGcOrgType() {
        return this.gcOrgType;
    }

    public void setGcOrgType(String gcOrgType) {
        this.gcOrgType = gcOrgType;
    }

    public String getCurrency() {
        return this.currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getDefaultPeriod() {
        return this.defaultPeriod;
    }

    public void setDefaultPeriod(String defaultPeriod) {
        this.defaultPeriod = defaultPeriod;
    }

    public Double getAfterZbAmt() {
        return this.afterZbAmt;
    }

    public void setAfterZbAmt(Double afterZbAmt) {
        this.afterZbAmt = afterZbAmt;
    }

    public String getAdjust() {
        return this.adjust;
    }

    public void setAdjust(String adjust) {
        this.adjust = adjust;
    }
}

