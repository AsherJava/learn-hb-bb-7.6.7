/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.batch.summary.service.table.converter;

import com.jiuqi.nr.batch.summary.service.table.converter.IConverter;

public class DoubleConverter
implements IConverter<Double> {
    @Override
    public Double convert(Object obj) {
        if (obj instanceof Number) {
            return ((Number)obj).doubleValue();
        }
        if (obj instanceof String) {
            return Double.parseDouble((String)obj);
        }
        return null;
    }
}

