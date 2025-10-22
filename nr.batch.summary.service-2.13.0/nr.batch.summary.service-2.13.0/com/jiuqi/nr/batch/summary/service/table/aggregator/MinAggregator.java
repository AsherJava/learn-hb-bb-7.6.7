/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.batch.summary.service.table.aggregator;

import com.jiuqi.nr.batch.summary.service.table.aggregator.IAggregator;
import com.jiuqi.nr.batch.summary.service.table.comparator.IComparator;
import com.jiuqi.nr.batch.summary.service.table.converter.IConverter;

public class MinAggregator<T>
implements IAggregator<T> {
    private final IConverter<T> converter;
    private final IComparator<T> comparator;

    public MinAggregator(IConverter<T> converter, IComparator<T> comparator) {
        this.converter = converter;
        this.comparator = comparator;
    }

    @Override
    public T aggregate(Object[] values) {
        Object min = null;
        for (Object obj : values) {
            T value = this.converter.convert(obj);
            if (min != null && this.comparator.compare(value, min) > 0) continue;
            min = value;
        }
        return (T)min;
    }
}

