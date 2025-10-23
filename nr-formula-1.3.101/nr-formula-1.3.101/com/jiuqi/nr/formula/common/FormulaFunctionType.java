/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$FormulaType
 */
package com.jiuqi.nr.formula.common;

import com.jiuqi.np.dataengine.common.DataEngineConsts;

public enum FormulaFunctionType {
    CHECK(DataEngineConsts.FormulaType.CHECK),
    CALCULATE(DataEngineConsts.FormulaType.CALCULATE),
    BALANCE(DataEngineConsts.FormulaType.BALANCE),
    EXPRESSION(DataEngineConsts.FormulaType.EXPRESSION),
    OTHER(99, "OTHER");

    private final String title;
    private final int value;

    private FormulaFunctionType(DataEngineConsts.FormulaType type) {
        this.title = type.getTitle();
        this.value = type.getValue();
    }

    private FormulaFunctionType(int value, String title) {
        this.title = title;
        this.value = value;
    }

    public String getTitle() {
        return this.title;
    }

    public int getValue() {
        return this.value;
    }
}

