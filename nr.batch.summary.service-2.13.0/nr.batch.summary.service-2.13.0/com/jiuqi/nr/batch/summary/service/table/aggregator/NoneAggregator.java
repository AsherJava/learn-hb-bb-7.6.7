/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.batch.summary.service.table.aggregator;

import com.jiuqi.nr.batch.summary.service.table.aggregator.IAggregator;

public class NoneAggregator<T>
implements IAggregator<T> {
    private final T anyValue;

    public NoneAggregator(T anyValue) {
        this.anyValue = anyValue;
    }

    @Override
    public T aggregate(Object[] values) {
        return this.anyValue;
    }
}

