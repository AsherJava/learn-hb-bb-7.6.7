/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.zbquery.model;

import com.jiuqi.nr.zbquery.model.FormulaType;
import com.jiuqi.nr.zbquery.model.QueryField;
import com.jiuqi.nr.zbquery.model.QueryObjectType;
import com.jiuqi.nr.zbquery.model.ZBFieldType;

public class FormulaField
extends QueryField {
    private FormulaType formulaType = FormulaType.CUSTOM;
    private String formula;
    private String srcField;
    private ZBFieldType srcFieldZbType = null;

    public FormulaField() {
        this.setType(QueryObjectType.FORMULA);
    }

    public FormulaType getFormulaType() {
        return this.formulaType;
    }

    public void setFormulaType(FormulaType formulaType) {
        this.formulaType = formulaType;
    }

    public String getFormula() {
        return this.formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public String getSrcField() {
        return this.srcField;
    }

    public void setSrcField(String srcField) {
        this.srcField = srcField;
    }

    public ZBFieldType getSrcFieldZbType() {
        return this.srcFieldZbType;
    }

    public void setSrcFieldZbType(ZBFieldType srcFieldZbType) {
        this.srcFieldZbType = srcFieldZbType;
    }
}

