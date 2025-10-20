/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn$DBType
 *  com.jiuqi.gcreport.definition.impl.anno.DBTable
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 */
package com.jiuqi.gcreport.invest.investbillcarryover.entity;

import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;

@DBTable(name="GC_INVESTCARRYOVERSETTING", title="\u6295\u8d44\u5e74\u7ed3\u8bbe\u7f6e")
public class InvestBillCarryOverSettingEO
extends DefaultTableEntity {
    public static final String TABLENAME = "GC_INVESTCARRYOVERSETTING";
    @DBColumn(nameInDB="CARRYOVERSCHEMEID", dbType=DBColumn.DBType.Varchar, length=36)
    private String carryOverSchemeId;
    @DBColumn(nameInDB="TARGETFIELD", dbType=DBColumn.DBType.Varchar, length=32)
    private String targetField;
    @DBColumn(nameInDB="SOURCEFIELD", dbType=DBColumn.DBType.Varchar, length=32)
    private String sourceField;
    @DBColumn(nameInDB="SOURCEBEGINFIELD", dbType=DBColumn.DBType.Varchar, length=32)
    private String sourceBeginField;
    @DBColumn(nameInDB="SOURCEADDFIELD", dbType=DBColumn.DBType.Varchar, length=32)
    private String sourceAddField;
    @DBColumn(nameInDB="SOURCEREDUCEFIELD", dbType=DBColumn.DBType.Varchar, length=32)
    private String sourceReduceField;
    @DBColumn(nameInDB="FORMULA", dbType=DBColumn.DBType.Varchar, length=100)
    private String formula;
    @DBColumn(nameInDB="DESCRIPTION", dbType=DBColumn.DBType.Varchar, length=100)
    private String description;
    @DBColumn(nameInDB="ACCOUNTTYPE", dbType=DBColumn.DBType.Varchar, length=20)
    private String accountType;
    @DBColumn(nameInDB="CARRYOVERMODE", dbType=DBColumn.DBType.Varchar, length=20)
    private String carryOverMode;
    @DBColumn(nameInDB="ORDINAL", title="\u6392\u5e8f\u5b57\u6bb5", dbType=DBColumn.DBType.Numeric)
    private Double ordinal;

    public String getCarryOverSchemeId() {
        return this.carryOverSchemeId;
    }

    public void setCarryOverSchemeId(String carryOverSchemeId) {
        this.carryOverSchemeId = carryOverSchemeId;
    }

    public String getTargetField() {
        return this.targetField;
    }

    public void setTargetField(String targetField) {
        this.targetField = targetField;
    }

    public String getSourceField() {
        return this.sourceField;
    }

    public void setSourceField(String sourceField) {
        this.sourceField = sourceField;
    }

    public String getSourceBeginField() {
        return this.sourceBeginField;
    }

    public void setSourceBeginField(String sourceBeginField) {
        this.sourceBeginField = sourceBeginField;
    }

    public String getSourceAddField() {
        return this.sourceAddField;
    }

    public void setSourceAddField(String sourceAddField) {
        this.sourceAddField = sourceAddField;
    }

    public String getSourceReduceField() {
        return this.sourceReduceField;
    }

    public void setSourceReduceField(String sourceReduceField) {
        this.sourceReduceField = sourceReduceField;
    }

    public String getFormula() {
        return this.formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAccountType() {
        return this.accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getCarryOverMode() {
        return this.carryOverMode;
    }

    public void setCarryOverMode(String carryOverMode) {
        this.carryOverMode = carryOverMode;
    }

    public Double getOrdinal() {
        return this.ordinal;
    }

    public void setOrdinal(Double ordinal) {
        this.ordinal = ordinal;
    }
}

