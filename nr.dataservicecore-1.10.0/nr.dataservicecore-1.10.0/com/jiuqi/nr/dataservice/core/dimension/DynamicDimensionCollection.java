/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataservice.core.dimension;

import com.jiuqi.nr.dataservice.core.dimension.ArrayDimensionValue;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.FixedDimensionValue;
import com.jiuqi.nr.dataservice.core.dimension.VariableDimensionValue;
import java.util.Collections;
import java.util.List;

public interface DynamicDimensionCollection
extends DimensionCollection {
    public List<VariableDimensionValue> getVariableDimensionList();

    default public List<FixedDimensionValue> getFixDimensionList() {
        return Collections.EMPTY_LIST;
    }

    default public List<ArrayDimensionValue> getArrayDimensionList() {
        return Collections.EMPTY_LIST;
    }
}

