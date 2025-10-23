/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.events.helper;

import com.jiuqi.nr.workflow2.events.helper.CurrencyFilterCondition;
import com.jiuqi.nr.workflow2.events.helper.UnitFilterCondition;

public class DimensionBuilderCondition {
    private CurrencyFilterCondition currencyFilterCondition;
    private UnitFilterCondition unitFilterCondition;

    public DimensionBuilderCondition() {
    }

    public DimensionBuilderCondition(CurrencyFilterCondition currencyFilterCondition) {
        this.currencyFilterCondition = currencyFilterCondition;
    }

    public DimensionBuilderCondition(UnitFilterCondition unitFilterCondition, CurrencyFilterCondition currencyFilterCondition) {
        this(currencyFilterCondition);
        this.unitFilterCondition = unitFilterCondition;
    }

    public CurrencyFilterCondition getCurrencyFilterCondition() {
        return this.currencyFilterCondition;
    }

    public void setCurrencyFilterCondition(CurrencyFilterCondition currencyFilterCondition) {
        this.currencyFilterCondition = currencyFilterCondition;
    }

    public UnitFilterCondition getUnitFilterCondition() {
        return this.unitFilterCondition;
    }

    public void setUnitFilterCondition(UnitFilterCondition unitFilterCondition) {
        this.unitFilterCondition = unitFilterCondition;
    }
}

