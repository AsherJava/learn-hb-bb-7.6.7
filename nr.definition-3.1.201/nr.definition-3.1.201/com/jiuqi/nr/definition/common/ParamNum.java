/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.common;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum ParamNum {
    NONE(0),
    TASK(1),
    FORM(2);

    private int intValue;
    private static Map<Integer, ParamNum> mappings;

    private static Map<Integer, ParamNum> getMappings() {
        if (mappings == null) {
            mappings = Arrays.stream(ParamNum.values()).collect(Collectors.toMap(ParamNum::getValue, f -> f));
        }
        return mappings;
    }

    private ParamNum(int value) {
        this.intValue = value;
    }

    public int getValue() {
        return this.intValue;
    }

    public static ParamNum forValue(int value) {
        return ParamNum.getMappings().get(value);
    }
}

