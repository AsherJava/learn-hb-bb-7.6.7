/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 */
package com.jiuqi.nr.snapshot.input;

import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.snapshot.message.DataRange;

public class QueryPeriodDataSourceContext {
    private DimensionCombination dimensionCombination;
    private String formSchemeKey;
    private DataRange dataRange;
    private String dataName;

    public QueryPeriodDataSourceContext() {
    }

    public QueryPeriodDataSourceContext(DimensionCombination dimensionCombination, String formSchemeKey, DataRange dataRange, String dataName) {
        this.dimensionCombination = dimensionCombination;
        this.formSchemeKey = formSchemeKey;
        this.dataRange = dataRange;
        this.dataName = dataName;
    }

    public DimensionCombination getDimensionCombination() {
        return this.dimensionCombination;
    }

    public void setDimensionCombination(DimensionCombination dimensionCombination) {
        this.dimensionCombination = dimensionCombination;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public DataRange getDataRange() {
        return this.dataRange;
    }

    public void setDataRange(DataRange dataRange) {
        this.dataRange = dataRange;
    }

    public String getDataName() {
        return this.dataName;
    }

    public void setDataName(String dataName) {
        this.dataName = dataName;
    }
}

