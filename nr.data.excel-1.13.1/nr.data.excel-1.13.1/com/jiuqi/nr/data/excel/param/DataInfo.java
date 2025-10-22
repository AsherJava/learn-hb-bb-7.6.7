/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 */
package com.jiuqi.nr.data.excel.param;

import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;

public class DataInfo {
    protected DimensionCombination dimensionCombination;
    protected String formKey;

    public DataInfo(DimensionCombination dimensionCombination, String formKey) {
        this.dimensionCombination = dimensionCombination;
        this.formKey = formKey;
    }

    public DimensionCombination getDimensionCombination() {
        return this.dimensionCombination;
    }

    public void setDimensionCombination(DimensionCombination dimensionCombination) {
        this.dimensionCombination = dimensionCombination;
    }

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }
}

