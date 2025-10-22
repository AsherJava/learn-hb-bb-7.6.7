/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.single.core.para.consts;

import java.util.HashMap;

public enum PeriodType {
    YEAR(78),
    HALFYEAR(72),
    SEASON(74),
    MONTH(89),
    TENDAY(88),
    DAY(82),
    WEEK(90),
    CUSTOM(66),
    UNKNOWN(32);

    private int intValue;
    private static HashMap<Integer, PeriodType> mappings;

    private static synchronized HashMap<Integer, PeriodType> getMappings() {
        if (mappings == null) {
            mappings = new HashMap();
        }
        return mappings;
    }

    private PeriodType(int value) {
        this.intValue = value;
        PeriodType.getMappings().put(value, this);
    }

    public int getValue() {
        return this.intValue;
    }

    public static PeriodType forValue(int value) {
        return PeriodType.getMappings().get(value);
    }
}

