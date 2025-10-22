/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 */
package com.jiuqi.nr.data.logic.internal.obj;

import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import java.util.List;

public class FmlDimForm {
    private DimensionCollection dimensionCollection;
    private List<String> formKeys;

    public FmlDimForm() {
    }

    public FmlDimForm(DimensionCollection dimensionCollection, List<String> formKeys) {
        this.dimensionCollection = dimensionCollection;
        this.formKeys = formKeys;
    }

    public DimensionCollection getDimensionCollection() {
        return this.dimensionCollection;
    }

    public void setDimensionCollection(DimensionCollection dimensionCollection) {
        this.dimensionCollection = dimensionCollection;
    }

    public List<String> getFormKeys() {
        return this.formKeys;
    }

    public void setFormKeys(List<String> formKeys) {
        this.formKeys = formKeys;
    }
}

