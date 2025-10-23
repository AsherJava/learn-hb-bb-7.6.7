/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 */
package com.jiuqi.nr.workflow2.engine.core.process.runtime.common;

import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IDimensionObject;

public final class DimensionObject
implements IDimensionObject {
    private final DimensionCombination dimensions;

    public DimensionObject(DimensionCombination dimensions) {
        this.dimensions = dimensions;
    }

    @Override
    public DimensionCombination getDimensions() {
        return this.dimensions;
    }

    public int hashCode() {
        return this.dimensions.hashCode();
    }

    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof IDimensionObject)) {
            return false;
        }
        IDimensionObject another = (IDimensionObject)obj;
        return this.dimensions.equals(another.getDimensions());
    }
}

