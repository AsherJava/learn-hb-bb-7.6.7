/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.batch.summary.service.table.converter;

import com.jiuqi.nr.batch.summary.service.table.converter.IConverter;
import java.util.UUID;

public class UUIDConverter
implements IConverter<UUID> {
    @Override
    public UUID convert(Object value) {
        if (value instanceof UUID) {
            return (UUID)value;
        }
        if (value instanceof String) {
            return UUID.fromString((String)value);
        }
        return null;
    }
}

