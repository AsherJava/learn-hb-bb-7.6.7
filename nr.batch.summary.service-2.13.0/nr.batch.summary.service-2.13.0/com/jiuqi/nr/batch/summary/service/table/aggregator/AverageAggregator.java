/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.batch.summary.service.table.aggregator;

import com.jiuqi.nr.batch.summary.service.table.aggregator.IAggregator;
import com.jiuqi.nr.batch.summary.service.table.converter.IConverter;

public class AverageAggregator<T extends Number>
implements IAggregator<T> {
    private final IConverter<T> converter;

    public AverageAggregator(IConverter<T> converter) {
        this.converter = converter;
    }

    @Override
    public T aggregate(Object[] values) {
        double sum = 0.0;
        int count = 0;
        for (Object obj : values) {
            Number value = (Number)this.converter.convert(obj);
            if (value == null) continue;
            sum += value.doubleValue();
            ++count;
        }
        return (T)(count == 0 ? (Number)null : (Number)((Number)this.converter.convert(sum / (double)count)));
    }
}

