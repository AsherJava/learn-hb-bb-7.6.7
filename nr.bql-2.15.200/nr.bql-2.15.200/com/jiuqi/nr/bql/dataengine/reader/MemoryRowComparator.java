/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 */
package com.jiuqi.nr.bql.dataengine.reader;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import java.util.Comparator;

public class MemoryRowComparator
implements Comparator<DimensionValueSet> {
    @Override
    public int compare(DimensionValueSet o1, DimensionValueSet o2) {
        return o1.compareTo(o2);
    }
}

