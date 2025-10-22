/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 */
package com.jiuqi.nr.dataservice.core.datastatus.obj;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import java.util.List;

public class DataStatusPresetInfo {
    private boolean singleDim;
    private List<String> emptyForms;
    private List<String> fullForms;
    private DimensionValueSet dimensionValueSet;

    public boolean isSingleDim() {
        return this.singleDim;
    }

    public void setSingleDim(boolean singleDim) {
        this.singleDim = singleDim;
    }

    public List<String> getEmptyForms() {
        return this.emptyForms;
    }

    public void setEmptyForms(List<String> emptyForms) {
        this.emptyForms = emptyForms;
    }

    public List<String> getFullForms() {
        return this.fullForms;
    }

    public void setFullForms(List<String> fullForms) {
        this.fullForms = fullForms;
    }

    public DimensionValueSet getDimensionValueSet() {
        return this.dimensionValueSet;
    }

    public void setDimensionValueSet(DimensionValueSet dimensionValueSet) {
        this.dimensionValueSet = dimensionValueSet;
    }
}

