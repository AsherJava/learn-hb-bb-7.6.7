/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.definition.common;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum TableIndexType {
    TABLE_INDEX_NORMAL(0),
    TABLE_INDEX_BITMAP(1),
    TABLE_INDEX_UNIQUE(2),
    TABLE_INDEX_PRIMARY(3);

    private int intValue;
    private static Map<Integer, TableIndexType> mappings;

    private static Map<Integer, TableIndexType> getMappings() {
        if (mappings == null) {
            mappings = Arrays.stream(TableIndexType.values()).collect(Collectors.toMap(TableIndexType::getValue, f -> f));
        }
        return mappings;
    }

    private TableIndexType(int value) {
        this.intValue = value;
    }

    public int getValue() {
        return this.intValue;
    }

    public static TableIndexType forValue(int value) {
        return TableIndexType.getMappings().get(value);
    }
}

