/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataservice.core.dimension;

import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollectionSoftImpl;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.dataservice.core.dimension.DimensionValue;
import java.util.List;

public class DimensionCollectionUtil {
    private DimensionCollectionUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static DimensionValue getDimensionValue(DimensionCollection collection, String name) {
        return collection.getDimensionValue(name);
    }

    public static DimensionCollection buildCollection(List<DimensionCombination> combinations) {
        return new DimensionCollectionSoftImpl(combinations);
    }
}

