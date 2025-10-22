/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 */
package com.jiuqi.nr.dataservice.core.dimension;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.dataservice.core.dimension.DimensionValue;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.springframework.util.CollectionUtils;

public interface DimensionCollection
extends Iterable<DimensionValueSet> {
    public List<DimensionCombination> getDimensionCombinations();

    @Override
    public Iterator<DimensionValueSet> iterator();

    public DimensionValueSet combineWithoutVarDim();

    public DimensionValueSet combineDim();

    default public DimensionValue getDimensionValue(String dimensionName) {
        List<DimensionCombination> dimensionCombinations = this.getDimensionCombinations();
        if (CollectionUtils.isEmpty(dimensionCombinations)) {
            return null;
        }
        DimensionCombination combination = dimensionCombinations.get(0);
        return combination.getFixedDimensionValue(dimensionName);
    }

    default public Collection<DimensionValue> getDimensionValues() {
        List<DimensionCombination> dimensionCombinations = this.getDimensionCombinations();
        if (CollectionUtils.isEmpty(dimensionCombinations)) {
            return null;
        }
        DimensionCombination combination = dimensionCombinations.get(0);
        return new ArrayList<DimensionValue>(combination.getDimensionValues());
    }
}

