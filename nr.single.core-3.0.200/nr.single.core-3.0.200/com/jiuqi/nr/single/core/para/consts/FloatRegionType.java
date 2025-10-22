/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.single.core.para.consts;

import java.util.HashMap;

public enum FloatRegionType {
    FLOAT_DIRECTION_ROW_FLOAT(82),
    FLOAT_DIRECTION_COL_FLOAT(67);

    private int intValue;
    private static HashMap<Integer, FloatRegionType> mappings;

    private static synchronized HashMap<Integer, FloatRegionType> getMappings() {
        if (mappings == null) {
            mappings = new HashMap();
        }
        return mappings;
    }

    private FloatRegionType(int value) {
        this.intValue = value;
        FloatRegionType.getMappings().put(value, this);
    }

    public int getValue() {
        return this.intValue;
    }

    public static FloatRegionType forValue(int value) {
        return FloatRegionType.getMappings().get(value);
    }
}

