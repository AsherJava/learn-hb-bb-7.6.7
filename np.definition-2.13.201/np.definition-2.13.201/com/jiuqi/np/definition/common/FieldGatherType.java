/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.definition.common;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum FieldGatherType {
    FIELD_GATHER_NONE(0),
    FIELD_GATHER_SUM(1),
    FIELD_GATHER_COUNT(2),
    FIELD_GATHER_AVG(3),
    FIELD_GATHER_MIN(4),
    FIELD_GATHER_MAX(5),
    FIELD_GATHER_RECALCULATE(6),
    FIELD_GATHER_DISTINCT_COUNT(7),
    FIELD_GATHER_LIST(8),
    FIELD_GATHER_DISTINCT_LIST(9),
    FIELD_GATHER_LAST_LEVEL_LIST(10);

    private int intValue;
    private static Map<Integer, FieldGatherType> mappings;

    private static Map<Integer, FieldGatherType> getMappings() {
        if (mappings == null) {
            mappings = Arrays.stream(FieldGatherType.values()).collect(Collectors.toMap(FieldGatherType::getValue, f -> f));
        }
        return mappings;
    }

    private FieldGatherType(int value) {
        this.intValue = value;
    }

    public int getValue() {
        return this.intValue;
    }

    public static FieldGatherType forValue(int value) {
        return FieldGatherType.getMappings().get(value);
    }
}

