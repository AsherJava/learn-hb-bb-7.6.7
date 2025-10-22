/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 */
package com.jiuqi.nr.integritycheck.message;

import com.jiuqi.np.dataengine.common.DimensionValueSet;

public class DimAndFormMessage {
    private DimensionValueSet dimensionValueSet;
    private String formKey;

    public DimAndFormMessage(DimensionValueSet dimensionValueSet, String formKey) {
        this.dimensionValueSet = dimensionValueSet;
        this.formKey = formKey;
    }

    public DimensionValueSet getDimensionValueSet() {
        return this.dimensionValueSet;
    }

    public void setDimensionValueSet(DimensionValueSet dimensionValueSet) {
        this.dimensionValueSet = dimensionValueSet;
    }

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }
}

