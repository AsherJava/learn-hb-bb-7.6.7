/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 */
package com.jiuqi.nr.definition.util;

import com.jiuqi.np.dataengine.common.DimensionValueSet;

public class FormDimensionValue {
    private String formKey;
    private DimensionValueSet dimensionValueSet;

    public FormDimensionValue(String formKey, DimensionValueSet dimensionValueSet) {
        this.formKey = formKey;
        this.dimensionValueSet = dimensionValueSet;
    }

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public DimensionValueSet getDimensionValueSet() {
        return this.dimensionValueSet;
    }

    public void setDimensionValueSet(DimensionValueSet dimensionValueSet) {
        this.dimensionValueSet = dimensionValueSet;
    }
}

