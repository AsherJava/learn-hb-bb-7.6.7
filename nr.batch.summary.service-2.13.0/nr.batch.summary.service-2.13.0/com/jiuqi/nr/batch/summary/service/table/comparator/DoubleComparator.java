/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.batch.summary.service.table.comparator;

import com.jiuqi.nr.batch.summary.service.table.comparator.IComparator;

public class DoubleComparator
implements IComparator<Double> {
    @Override
    public int compare(Double a, Double b) {
        return Double.compare(a, b);
    }
}

