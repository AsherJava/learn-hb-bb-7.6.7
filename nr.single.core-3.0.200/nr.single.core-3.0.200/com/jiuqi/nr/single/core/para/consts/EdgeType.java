/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.single.core.para.consts;

import java.util.HashMap;

public enum EdgeType {
    ETSINGLE(2),
    ETNONE(1),
    ETFAINT(3),
    ETDOUBLE(13);

    private int intValue;
    private static HashMap<Integer, EdgeType> mappings;

    private static synchronized HashMap<Integer, EdgeType> getMappings() {
        if (mappings == null) {
            mappings = new HashMap();
        }
        return mappings;
    }

    private EdgeType(int value) {
        this.intValue = value;
        EdgeType.getMappings().put(value, this);
    }

    public int getValue() {
        return this.intValue;
    }

    public static EdgeType forValue(int value) {
        return EdgeType.getMappings().get(value);
    }
}

