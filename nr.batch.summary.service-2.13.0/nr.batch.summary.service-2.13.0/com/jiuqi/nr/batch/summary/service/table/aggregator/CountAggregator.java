/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.batch.summary.service.table.aggregator;

import com.jiuqi.nr.batch.summary.service.table.aggregator.IAggregator;

public class CountAggregator<T>
implements IAggregator<T> {
    @Override
    public T aggregate(Object[] values) {
        int count = 0;
        for (Object obj : values) {
            if (obj == null) continue;
            ++count;
        }
        return (T)Integer.valueOf(count);
    }
}

