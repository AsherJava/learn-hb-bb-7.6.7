/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataservice.core.dimension;

import com.jiuqi.nr.dataservice.core.dimension.DimensionValue;
import com.jiuqi.nr.dataservice.core.dimension.VariableDimensionValueProvider;
import java.io.Serializable;

public class VariableDimensionValue
implements Serializable,
DimensionValue {
    private static final long serialVersionUID = -5877661035776009718L;
    private final String name;
    private final String entityId;
    private final VariableDimensionValueProvider provider;

    public VariableDimensionValue(String name, String entityId, VariableDimensionValueProvider provider) {
        this.name = name;
        this.entityId = entityId;
        this.provider = provider;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getEntityID() {
        return this.entityId;
    }

    public VariableDimensionValueProvider getProvider() {
        return this.provider;
    }

    public String toString() {
        return "VariableDimensionValue{name='" + this.name + '\'' + ", entityId='" + this.entityId + '\'' + ", provider=" + this.provider + '}';
    }
}

