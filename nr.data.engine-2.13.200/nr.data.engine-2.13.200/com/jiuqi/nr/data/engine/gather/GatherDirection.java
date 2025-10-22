/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.engine.gather;

import java.util.HashMap;

public enum GatherDirection {
    GATHER_TO_GROUP(0),
    GATHER_TO_MINUS(1);

    private int intValue;
    private static HashMap<Integer, GatherDirection> mappings;

    private static synchronized HashMap<Integer, GatherDirection> getMappings() {
        if (mappings == null) {
            mappings = new HashMap();
        }
        return mappings;
    }

    private GatherDirection(int value) {
        this.intValue = value;
        GatherDirection.getMappings().put(value, this);
    }

    public int getValue() {
        return this.intValue;
    }

    public static GatherDirection forValue(int value) {
        return GatherDirection.getMappings().get(value);
    }
}

