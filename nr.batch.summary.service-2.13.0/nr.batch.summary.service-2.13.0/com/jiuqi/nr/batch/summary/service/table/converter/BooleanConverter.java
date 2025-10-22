/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.batch.summary.service.table.converter;

import com.jiuqi.nr.batch.summary.service.table.converter.IConverter;

public class BooleanConverter
implements IConverter<Boolean> {
    @Override
    public Boolean convert(Object value) {
        if (value instanceof Boolean) {
            return (Boolean)value;
        }
        if (value instanceof String) {
            return Boolean.parseBoolean((String)value);
        }
        if (value instanceof Number) {
            return ((Number)value).intValue() != 0;
        }
        return null;
    }
}

