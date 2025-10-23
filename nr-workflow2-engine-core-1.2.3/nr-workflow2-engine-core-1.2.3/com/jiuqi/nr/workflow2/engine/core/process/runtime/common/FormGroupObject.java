/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 */
package com.jiuqi.nr.workflow2.engine.core.process.runtime.common;

import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IFormGroupObject;

public final class FormGroupObject
implements IFormGroupObject {
    private final DimensionCombination dimensions;
    private final String formGroupKey;

    public FormGroupObject(DimensionCombination dimensions, String formGroupKey) {
        this.dimensions = dimensions;
        this.formGroupKey = formGroupKey;
    }

    @Override
    public DimensionCombination getDimensions() {
        return this.dimensions;
    }

    @Override
    public String getFormGroupKey() {
        return this.formGroupKey;
    }

    public int hashCode() {
        return this.dimensions.hashCode() + this.formGroupKey.hashCode();
    }

    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof IFormGroupObject)) {
            return false;
        }
        IFormGroupObject another = (IFormGroupObject)obj;
        return this.dimensions.equals(another.getDimensions()) && this.formGroupKey.equals(another.getFormGroupKey());
    }
}

