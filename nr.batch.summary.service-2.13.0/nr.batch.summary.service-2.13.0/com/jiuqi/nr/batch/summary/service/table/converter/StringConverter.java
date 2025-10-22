/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.batch.summary.service.table.converter;

import com.jiuqi.nr.batch.summary.service.table.converter.IConverter;

public class StringConverter
implements IConverter<String> {
    @Override
    public String convert(Object obj) {
        return obj != null ? obj.toString() : null;
    }
}

