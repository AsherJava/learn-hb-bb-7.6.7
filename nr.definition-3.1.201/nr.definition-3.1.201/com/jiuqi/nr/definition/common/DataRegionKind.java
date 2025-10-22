/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.common;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum DataRegionKind {
    DATA_REGION_SIMPLE(0),
    DATA_REGION_SCROLL(1),
    DATA_REGION_COLUMN_LIST(2),
    DATA_REGION_ROW_LIST(3),
    DATA_REGION_COLUMN_AND_ROW_LIST(4);

    private int intValue;
    private static Map<Integer, DataRegionKind> mappings;

    private static Map<Integer, DataRegionKind> getMappings() {
        if (mappings == null) {
            mappings = Arrays.stream(DataRegionKind.values()).collect(Collectors.toMap(DataRegionKind::getValue, f -> f));
        }
        return mappings;
    }

    private DataRegionKind(int value) {
        this.intValue = value;
    }

    public int getValue() {
        return this.intValue;
    }

    public static DataRegionKind forValue(int value) {
        return DataRegionKind.getMappings().get(value);
    }
}

