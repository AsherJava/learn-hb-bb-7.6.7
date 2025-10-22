/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.annotation.JsonDeserialize
 *  com.fasterxml.jackson.databind.annotation.JsonSerialize
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 */
package com.jiuqi.nr.dataservice.core.dimension;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.dataservice.core.dimension.FixedDimensionValue;
import com.jiuqi.nr.dataservice.core.dimension.serial.DimensionCombinationDeserialize;
import com.jiuqi.nr.dataservice.core.dimension.serial.DimensionCombinationSeralize;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

@JsonSerialize(using=DimensionCombinationSeralize.class)
@JsonDeserialize(using=DimensionCombinationDeserialize.class)
public interface DimensionCombination
extends Serializable,
Iterable<FixedDimensionValue> {
    public Object getValue(String var1);

    public boolean hasValue(String var1);

    public void assignFrom(DimensionCombination var1);

    public DimensionValueSet toDimensionValueSet();

    public FixedDimensionValue getFixedDimensionValue(String var1);

    public FixedDimensionValue getDWDimensionValue();

    public FixedDimensionValue getPeriodDimensionValue();

    public int getSize();

    public Collection<String> getNames();

    default public Collection<FixedDimensionValue> getDimensionValues() {
        ArrayList<FixedDimensionValue> dimensionValues = new ArrayList<FixedDimensionValue>();
        Collection<String> names = this.getNames();
        for (String name : names) {
            dimensionValues.add(this.getFixedDimensionValue(name));
        }
        return dimensionValues;
    }

    public void sortDimension();
}

