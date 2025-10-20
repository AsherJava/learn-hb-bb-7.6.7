/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.common.params;

import java.util.HashMap;

public enum DimensionType {
    DIMENSION_NOMAL(0),
    DIMENSION_UNIT(1),
    DIMENSION_PERIOD(2),
    DIMENSION_VERSION(3);

    private int intValue;
    private static HashMap<Integer, DimensionType> mappings;

    private static synchronized HashMap<Integer, DimensionType> getMappings() {
        if (mappings == null) {
            mappings = new HashMap();
        }
        return mappings;
    }

    private DimensionType(int value) {
        this.intValue = value;
        DimensionType.getMappings().put(value, this);
    }

    public int getValue() {
        return this.intValue;
    }

    public static DimensionType forValue(int value) {
        return DimensionType.getMappings().get(value);
    }
}

