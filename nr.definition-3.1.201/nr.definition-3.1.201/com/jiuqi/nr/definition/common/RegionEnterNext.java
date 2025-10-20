/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.common;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum RegionEnterNext {
    TOP(1),
    LEFT(2),
    BOTTOM(3),
    RIGHT(4);

    private int intValue;
    private static Map<Integer, RegionEnterNext> mappings;

    private static Map<Integer, RegionEnterNext> getMappings() {
        if (mappings == null) {
            mappings = Arrays.stream(RegionEnterNext.values()).collect(Collectors.toMap(RegionEnterNext::getValue, f -> f));
        }
        return mappings;
    }

    private RegionEnterNext(int value) {
        this.intValue = value;
    }

    public int getValue() {
        return this.intValue;
    }

    public static RegionEnterNext forValue(int value) {
        return RegionEnterNext.getMappings().get(value);
    }
}

