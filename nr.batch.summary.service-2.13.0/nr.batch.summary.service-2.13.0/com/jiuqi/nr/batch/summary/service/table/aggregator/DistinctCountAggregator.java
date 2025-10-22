/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.batch.summary.service.table.aggregator;

import com.jiuqi.nr.batch.summary.service.table.aggregator.IAggregator;
import com.jiuqi.nr.batch.summary.service.table.converter.IConverter;
import java.util.HashSet;

public class DistinctCountAggregator<T>
implements IAggregator<T> {
    private final IConverter<T> converter;

    public DistinctCountAggregator(IConverter<T> converter) {
        this.converter = converter;
    }

    @Override
    public T aggregate(Object[] values) {
        HashSet<T> distinctValues = new HashSet<T>();
        for (Object obj : values) {
            T value = this.converter.convert(obj);
            if (value == null) continue;
            distinctValues.add(value);
        }
        return (T)Integer.valueOf(distinctValues.size());
    }
}

