/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.single.core.syntax.common;

import java.util.HashMap;

public enum TAttractType {
    ATTRACT_ANY(0),
    ATTRACT_STRING(1),
    ATTRACT_REAL(2);

    private int intValue;
    private static HashMap<Integer, TAttractType> mappings;

    private static synchronized HashMap<Integer, TAttractType> getMappings() {
        if (mappings == null) {
            mappings = new HashMap();
        }
        return mappings;
    }

    private TAttractType(int value) {
        this.intValue = value;
        TAttractType.getMappings().put(value, this);
    }

    public int getValue() {
        return this.intValue;
    }

    public static TAttractType forValue(int value) {
        return TAttractType.getMappings().get(value);
    }
}

