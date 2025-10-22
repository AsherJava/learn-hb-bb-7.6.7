/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataservice.core.dimension;

import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollectionImpl;
import com.jiuqi.nr.dataservice.core.dimension.DimensionContext;
import com.jiuqi.nr.dataservice.core.dimension.VariableDimensionValueProvider;

public class DimensionCollectionBuilder {
    private final DimensionCollectionImpl collection = new DimensionCollectionImpl();

    @Deprecated
    public DimensionCollectionBuilder setValue(String name, Object ... value) {
        this.collection.setValue(name, value);
        return this;
    }

    public DimensionCollectionBuilder setEntityValue(String name, String entityID, Object ... value) {
        this.collection.setEntityValue(name, entityID, value);
        return this;
    }

    public DimensionCollectionBuilder setDWValue(String name, String entityID, Object ... value) {
        this.collection.setDWValue(name, entityID, value);
        return this;
    }

    public DimensionCollectionBuilder addVariableDW(String name, String entityId, VariableDimensionValueProvider provider) {
        this.collection.addVariableDW(name, entityId, provider);
        return this;
    }

    public DimensionCollectionBuilder addVariableDimension(String name, String entityId, VariableDimensionValueProvider provider) {
        this.collection.addVariableDimension(name, entityId, provider);
        return this;
    }

    public DimensionCollectionBuilder setContext(DimensionContext context) {
        this.collection.setContext(context);
        return this;
    }

    public DimensionCollection getCollection() {
        return this.collection;
    }
}

