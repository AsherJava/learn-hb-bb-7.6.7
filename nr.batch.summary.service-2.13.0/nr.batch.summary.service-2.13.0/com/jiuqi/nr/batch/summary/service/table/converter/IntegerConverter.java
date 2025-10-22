/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.batch.summary.service.table.converter;

import com.jiuqi.nr.batch.summary.service.table.converter.IConverter;

public class IntegerConverter
implements IConverter<Integer> {
    @Override
    public Integer convert(Object value) {
        if (value instanceof Integer) {
            return (Integer)value;
        }
        if (value instanceof String) {
            return Integer.parseInt((String)value);
        }
        if (value instanceof Number) {
            return ((Number)value).intValue();
        }
        return null;
    }
}

