/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.common;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum RegionPartitionType {
    REGION_PARTITION_LINK(0),
    REGION_PARTITION_FIELD(1);

    private int intValue;
    private static Map<Integer, RegionPartitionType> mappings;

    private static Map<Integer, RegionPartitionType> getMappings() {
        if (mappings == null) {
            mappings = Arrays.stream(RegionPartitionType.values()).collect(Collectors.toMap(RegionPartitionType::getValue, f -> f));
        }
        return mappings;
    }

    private RegionPartitionType(int value) {
        this.intValue = value;
    }

    public int getValue() {
        return this.intValue;
    }

    public static RegionPartitionType forValue(int value) {
        return RegionPartitionType.getMappings().get(value);
    }
}

