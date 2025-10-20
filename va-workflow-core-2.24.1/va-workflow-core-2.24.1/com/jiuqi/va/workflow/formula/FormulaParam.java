/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.workflow.ValueType
 */
package com.jiuqi.va.workflow.formula;

import com.jiuqi.va.domain.workflow.ValueType;

public class FormulaParam {
    private ValueType valueType;
    private Object value;

    public FormulaParam(ValueType valueType, Object value) {
        this.valueType = valueType;
        this.value = value;
    }

    public ValueType getValueType() {
        return this.valueType;
    }

    public Object getValue() {
        return this.value;
    }
}

