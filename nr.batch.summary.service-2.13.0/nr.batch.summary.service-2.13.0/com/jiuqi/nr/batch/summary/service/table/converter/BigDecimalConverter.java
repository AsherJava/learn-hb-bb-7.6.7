/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.batch.summary.service.table.converter;

import com.jiuqi.nr.batch.summary.service.table.converter.IConverter;
import java.math.BigDecimal;

public class BigDecimalConverter
implements IConverter<BigDecimal> {
    @Override
    public BigDecimal convert(Object value) {
        if (value instanceof BigDecimal) {
            return (BigDecimal)value;
        }
        if (value instanceof String) {
            return new BigDecimal((String)value);
        }
        if (value instanceof Number) {
            return BigDecimal.valueOf(((Number)value).doubleValue());
        }
        return null;
    }
}

