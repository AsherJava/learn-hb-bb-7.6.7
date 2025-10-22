/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.batch.summary.service.table.aggregator;

import com.jiuqi.nr.batch.summary.service.table.aggregator.IAggregator;
import com.jiuqi.nr.batch.summary.service.table.converter.IConverter;

public class SumAggregator<T extends Number>
implements IAggregator<T> {
    private final IConverter<T> converter;

    public SumAggregator(IConverter<T> converter) {
        this.converter = converter;
    }

    @Override
    public T aggregate(Object[] values) {
        double sum = 0.0;
        for (Object obj : values) {
            Number value = (Number)this.converter.convert(obj);
            if (value == null) continue;
            sum += value.doubleValue();
        }
        return (T)((Number)this.converter.convert(sum));
    }
}

