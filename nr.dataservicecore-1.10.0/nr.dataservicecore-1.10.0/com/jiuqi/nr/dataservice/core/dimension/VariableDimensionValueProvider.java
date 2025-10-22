/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataservice.core.dimension;

import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.dataservice.core.dimension.DimensionContext;
import com.jiuqi.nr.dataservice.core.dimension.VariableDimensionValue;
import com.jiuqi.nr.dataservice.core.dimension.provider.DimensionProviderData;
import java.io.Serializable;
import java.util.List;

public interface VariableDimensionValueProvider
extends Serializable {
    default public List<String> getValues(VariableDimensionValue variableDimensionValue, DimensionCombination dimensionCombination) {
        return this.getValues(null, variableDimensionValue, dimensionCombination);
    }

    public List<String> getValues(DimensionContext var1, VariableDimensionValue var2, DimensionCombination var3);

    default public boolean isDynamicByDW(VariableDimensionValue variableDimensionValue) {
        return false;
    }

    public String getID();

    public DimensionProviderData getDimensionProviderData();

    default public Object getMergeValue() {
        return this.getID();
    }
}

