/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.batch.summary.service.table.aggregator;

import com.jiuqi.nr.batch.summary.service.table.aggregator.IAggregator;
import com.jiuqi.nr.batch.summary.service.table.comparator.IComparator;
import com.jiuqi.nr.batch.summary.service.table.converter.IConverter;

public class MaxAggregator<T>
implements IAggregator<T> {
    private final IConverter<T> converter;
    private final IComparator<T> comparator;

    public MaxAggregator(IConverter<T> converter, IComparator<T> comparator) {
        this.converter = converter;
        this.comparator = comparator;
    }

    @Override
    public T aggregate(Object[] values) {
        Object max = null;
        for (Object obj : values) {
            T convert = this.converter.convert(obj);
            if (max != null && this.comparator.compare(convert, max) < 0) continue;
            max = convert;
        }
        return (T)max;
    }
}

