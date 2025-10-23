/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.period.select.common;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum ParamType {
    DEFAULT(0),
    RECORD_STATE(1);

    private int intValue;
    private static Map<Integer, ParamType> mappings;

    private static Map<Integer, ParamType> getMappings() {
        if (mappings == null) {
            mappings = Arrays.stream(ParamType.values()).collect(Collectors.toMap(ParamType::getValue, f -> f));
        }
        return mappings;
    }

    private ParamType(int value) {
        this.intValue = value;
    }

    public int getValue() {
        return this.intValue;
    }

    public static ParamType forValue(int value) {
        return ParamType.getMappings().get(value);
    }
}

