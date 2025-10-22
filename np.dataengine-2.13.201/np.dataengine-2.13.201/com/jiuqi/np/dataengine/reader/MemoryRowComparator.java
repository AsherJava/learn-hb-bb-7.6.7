/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.dataengine.reader;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import java.util.Comparator;

public class MemoryRowComparator
implements Comparator<DimensionValueSet> {
    @Override
    public int compare(DimensionValueSet o1, DimensionValueSet o2) {
        return o1.compareTo(o2);
    }
}

