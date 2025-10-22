/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.single.core.syntaxnb.common;

import java.util.HashMap;

public enum NBStatType {
    STAT_NONE(0),
    STAT_SUM(1),
    STAT_COUNT(2),
    STAT_MAX(3),
    STAT_MIN(4),
    STAT_AVG(5),
    STAT_FIRST(6),
    STAT_LAST(7),
    STAT_VRC(8),
    STAT_SCOUNT(9);

    private int intValue;
    private static HashMap<Integer, NBStatType> mappings;

    private static synchronized HashMap<Integer, NBStatType> getMappings() {
        if (mappings == null) {
            mappings = new HashMap();
        }
        return mappings;
    }

    private NBStatType(int value) {
        this.intValue = value;
        NBStatType.getMappings().put(value, this);
    }

    public int getValue() {
        return this.intValue;
    }

    public static NBStatType forValue(int value) {
        return NBStatType.getMappings().get(value);
    }
}

