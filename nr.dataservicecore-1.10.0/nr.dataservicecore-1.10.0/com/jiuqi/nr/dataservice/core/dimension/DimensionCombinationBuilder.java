/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 */
package com.jiuqi.nr.dataservice.core.dimension;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationImpl;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationSetter;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationTableImpl;
import com.jiuqi.nr.dataservice.core.dimension.DimensionTable;
import com.jiuqi.nr.dataservice.core.dimension.FixedDimensionValue;

public class DimensionCombinationBuilder {
    private final DimensionCombinationSetter combination;

    public DimensionCombinationBuilder() {
        this.combination = new DimensionCombinationImpl();
    }

    public DimensionCombinationBuilder(DimensionValueSet dimensionValueSet) {
        this.combination = new DimensionCombinationImpl(dimensionValueSet);
    }

    public DimensionCombinationBuilder(DimensionTable dimensionTable) {
        this.combination = new DimensionCombinationTableImpl(dimensionTable);
    }

    public DimensionCombinationBuilder setValue(FixedDimensionValue fixedDimensionValue) {
        this.combination.setValue(fixedDimensionValue);
        return this;
    }

    @Deprecated
    public DimensionCombinationBuilder setValue(String name, Object value) {
        this.combination.setValue(name, "", value);
        return this;
    }

    public DimensionCombinationBuilder setValue(String name, String entityID, Object value) {
        this.combination.setValue(name, entityID, value);
        return this;
    }

    public DimensionCombinationBuilder setDWValue(FixedDimensionValue fixedDimensionValue) {
        this.combination.setDWValue(fixedDimensionValue);
        return this;
    }

    public DimensionCombinationBuilder setDWValue(String name, String entityID, Object value) {
        this.combination.setDWValue(name, entityID, value);
        return this;
    }

    public DimensionCombination getCombination() {
        this.combination.sortDimension();
        return this.combination;
    }

    public void assignFrom(DimensionCombination d) {
        this.combination.assignFrom(d);
    }
}

