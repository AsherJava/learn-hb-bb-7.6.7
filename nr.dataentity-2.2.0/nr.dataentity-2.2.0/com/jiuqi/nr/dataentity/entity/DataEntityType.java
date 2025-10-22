/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataentity.entity;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum DataEntityType {
    DataEntity(0),
    DataEntityPeriod(1),
    DataEntityAdjust(2);

    private int intValue;
    private static Map<Integer, DataEntityType> mappings;

    private static Map<Integer, DataEntityType> getMappings() {
        if (mappings == null) {
            mappings = Arrays.stream(DataEntityType.values()).collect(Collectors.toMap(DataEntityType::getValue, f -> f));
        }
        return mappings;
    }

    private DataEntityType(int value) {
        this.intValue = value;
    }

    public int getValue() {
        return this.intValue;
    }

    public static DataEntityType forValue(int value) {
        return DataEntityType.getMappings().get(value);
    }
}

