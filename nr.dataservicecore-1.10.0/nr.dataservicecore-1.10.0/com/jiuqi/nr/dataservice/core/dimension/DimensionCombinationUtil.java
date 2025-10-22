/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataservice.core.dimension;

import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollectionBuilder;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationImpl;
import com.jiuqi.nr.dataservice.core.dimension.FixedDimensionValue;
import java.util.Collection;

public class DimensionCombinationUtil {
    private DimensionCombinationUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static DimensionCombination subtract(DimensionCombination minuend, DimensionCombination subtrahend) {
        DimensionCombinationImpl difference = new DimensionCombinationImpl();
        if (subtrahend.getSize() == 0) {
            difference.assignFrom(minuend);
            return difference;
        }
        Collection<String> minuendNames = minuend.getNames();
        Collection<String> subtrahendNames = subtrahend.getNames();
        FixedDimensionValue dwDimensionValue = minuend.getDWDimensionValue();
        if (dwDimensionValue != null && subtrahendNames.add(dwDimensionValue.getName())) {
            difference.setDWValue(dwDimensionValue);
        }
        for (String name : minuendNames) {
            if (name == null || subtrahendNames.contains(name)) continue;
            difference.setValue(minuend.getFixedDimensionValue(name));
        }
        return difference;
    }

    public static DimensionCollection convertToCollection(DimensionCombination combination) {
        DimensionCollectionBuilder builder = new DimensionCollectionBuilder();
        FixedDimensionValue dwDimensionValue = combination.getDWDimensionValue();
        if (dwDimensionValue != null) {
            builder.setDWValue(dwDimensionValue.getName(), dwDimensionValue.getEntityID(), dwDimensionValue.getValue());
        }
        Collection<FixedDimensionValue> dimensionValues = combination.getDimensionValues();
        for (FixedDimensionValue dimensionValue : dimensionValues) {
            if (dwDimensionValue != null && dwDimensionValue.getName().equalsIgnoreCase(dimensionValue.getName())) continue;
            builder.setEntityValue(dimensionValue.getName(), dimensionValue.getEntityID(), dimensionValue.getValue());
        }
        return builder.getCollection();
    }
}

