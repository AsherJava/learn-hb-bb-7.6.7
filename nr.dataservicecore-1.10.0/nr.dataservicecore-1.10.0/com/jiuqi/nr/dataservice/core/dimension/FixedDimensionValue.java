/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataservice.core.dimension;

import com.jiuqi.nr.dataservice.core.dimension.DimensionValue;
import java.io.Serializable;

public final class FixedDimensionValue
implements Serializable,
DimensionValue {
    private static final long serialVersionUID = -3993102308365528022L;
    private final String name;
    private final Object value;
    private String entityID;

    @Deprecated
    public FixedDimensionValue(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    public FixedDimensionValue(String name, String entityID, Object value) {
        this.name = name;
        this.value = value;
        this.entityID = entityID;
    }

    public Object getValue() {
        return this.value;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getEntityID() {
        return this.entityID;
    }

    public String toString() {
        return "FixedDimensionValue{name='" + this.name + '\'' + ", value=" + this.value + ", entityID='" + this.entityID + '\'' + '}';
    }
}

