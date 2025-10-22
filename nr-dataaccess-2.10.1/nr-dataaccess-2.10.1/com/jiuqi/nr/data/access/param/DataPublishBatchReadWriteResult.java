/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 */
package com.jiuqi.nr.data.access.param;

import com.jiuqi.np.dataengine.common.DimensionValueSet;

public class DataPublishBatchReadWriteResult {
    private DimensionValueSet dimensionSet;
    private String formKey;
    private boolean isPublished;

    public DimensionValueSet getDimensionSet() {
        return this.dimensionSet;
    }

    public void setDimensionSet(DimensionValueSet dimensionSet) {
        this.dimensionSet = dimensionSet;
    }

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public boolean isPublished() {
        return this.isPublished;
    }

    public void setPublished(boolean isPublished) {
        this.isPublished = isPublished;
    }
}

