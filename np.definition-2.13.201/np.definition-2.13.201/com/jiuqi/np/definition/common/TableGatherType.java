/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.definition.common;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum TableGatherType {
    TABLE_GATHER_NONE(0),
    TABLE_GATHER_CLASSIFY(1),
    TABLE_GATHER_LIST(2),
    TABLE_GATHER_CUSTOM(3);

    private int intValue;
    private static Map<Integer, TableGatherType> mappings;

    private static Map<Integer, TableGatherType> getMappings() {
        if (mappings == null) {
            mappings = Arrays.stream(TableGatherType.values()).collect(Collectors.toMap(TableGatherType::getValue, f -> f));
        }
        return mappings;
    }

    private TableGatherType(int value) {
        this.intValue = value;
    }

    public int getValue() {
        return this.intValue;
    }

    public static TableGatherType forValue(int value) {
        return TableGatherType.getMappings().get(value);
    }
}

