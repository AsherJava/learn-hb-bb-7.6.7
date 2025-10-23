/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.period.select.common;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum RunType {
    DESIGNER(0),
    RUNTIME(1);

    private int intValue;
    private static Map<Integer, RunType> mappings;

    private static Map<Integer, RunType> getMappings() {
        if (mappings == null) {
            mappings = Arrays.stream(RunType.values()).collect(Collectors.toMap(RunType::getValue, f -> f));
        }
        return mappings;
    }

    private RunType(int value) {
        this.intValue = value;
    }

    public int getValue() {
        return this.intValue;
    }

    public static RunType forValue(int value) {
        return RunType.getMappings().get(value);
    }
}

