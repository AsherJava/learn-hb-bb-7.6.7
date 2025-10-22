/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.definition.common;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum TableKind {
    TABLE_KIND_UNDEFINED(0),
    TABLE_KIND_SYSTEM(1),
    TABLE_KIND_ENTITY(2),
    TABLE_KIND_DICTIONARY(3),
    TABLE_KIND_BIZDATA(4),
    TABLE_KIND_USER_PROFILE(5),
    TABLE_KIND_ENTITY_PERIOD(6),
    TABLE_KIND_MEASUREMENT_UNIT(7),
    TABLE_KIND_CALIBER(8),
    TABLE_KIND_EXTERNAL(9),
    TABLE_KIND_ENTITY_VERSION(10);

    private int intValue;
    private static Map<Integer, TableKind> mappings;

    private static Map<Integer, TableKind> getMappings() {
        if (mappings == null) {
            mappings = Arrays.stream(TableKind.values()).collect(Collectors.toMap(TableKind::getValue, f -> f));
        }
        return mappings;
    }

    private TableKind(int value) {
        this.intValue = value;
    }

    public int getValue() {
        return this.intValue;
    }

    public static TableKind forValue(int value) {
        return TableKind.getMappings().get(value);
    }
}

