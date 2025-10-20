/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.common;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum PeriodMatchingType {
    PERIOD_TYPE_ALL(0),
    PERIOD_TYPE_CURRENT(1),
    PERIOD_TYPE_OFFSET(5),
    PERIOD_TYPE_PREVIOUS(2),
    PERIOD_TYPE_NEXT(3),
    PERIOD_TYPE_SPECIFIED(4),
    PERIOD_TYPE_PREYEAR(6);

    private int intValue;
    private static Map<Integer, PeriodMatchingType> mappings;

    private static Map<Integer, PeriodMatchingType> getMappings() {
        if (mappings == null) {
            mappings = Arrays.stream(PeriodMatchingType.values()).collect(Collectors.toMap(PeriodMatchingType::getValue, f -> f));
        }
        return mappings;
    }

    private PeriodMatchingType(int value) {
        this.intValue = value;
    }

    public int getValue() {
        return this.intValue;
    }

    public static PeriodMatchingType forValue(int value) {
        return PeriodMatchingType.getMappings().get(value);
    }
}

