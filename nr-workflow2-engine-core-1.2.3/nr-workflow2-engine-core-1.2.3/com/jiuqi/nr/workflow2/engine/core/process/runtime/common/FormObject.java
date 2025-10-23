/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 */
package com.jiuqi.nr.workflow2.engine.core.process.runtime.common;

import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IFormObject;

public final class FormObject
implements IFormObject {
    private final DimensionCombination dimensions;
    private final String formKey;

    public FormObject(DimensionCombination dimensions, String formKey) {
        this.dimensions = dimensions;
        this.formKey = formKey;
    }

    @Override
    public DimensionCombination getDimensions() {
        return this.dimensions;
    }

    @Override
    public String getFormKey() {
        return this.formKey;
    }

    public int hashCode() {
        return this.dimensions.hashCode() + this.formKey.hashCode();
    }

    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof IFormObject)) {
            return false;
        }
        IFormObject another = (IFormObject)obj;
        return this.dimensions.equals(another.getDimensions()) && this.formKey.equals(another.getFormKey());
    }
}

