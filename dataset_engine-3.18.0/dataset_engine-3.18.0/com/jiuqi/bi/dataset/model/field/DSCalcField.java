/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.dataset.model.field;

import com.jiuqi.bi.dataset.DataType;
import com.jiuqi.bi.dataset.model.field.AggregationType;
import com.jiuqi.bi.dataset.model.field.ApplyType;
import com.jiuqi.bi.dataset.model.field.CalcMode;
import com.jiuqi.bi.dataset.model.field.DSField;
import com.jiuqi.bi.dataset.model.field.FieldType;

public class DSCalcField
extends DSField {
    private String formula;
    private CalcMode calcMode;

    public DSCalcField() {
        this.setFieldType(FieldType.MEASURE);
        this.setAggregation(AggregationType.SUM);
        this.setApplyType(ApplyType.PERIOD);
        this.setValType(DataType.DOUBLE.value());
        this.setCalcMode(CalcMode.CALC_THEN_AGGR);
    }

    public String getFormula() {
        return this.formula;
    }

    public CalcMode getCalcMode() {
        return this.calcMode;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public void setCalcMode(CalcMode calcMode) {
        this.calcMode = calcMode;
    }

    @Override
    public DSCalcField clone() {
        return (DSCalcField)super.clone();
    }
}

