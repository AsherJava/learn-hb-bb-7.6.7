/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.common;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum SubmitCheckStrategy {
    CHECK_STRATEGY_DEFAULT(0),
    CHECK_STRATEGY_MUST_PASSED(1);

    private int intValue;
    private static Map<Integer, SubmitCheckStrategy> mappings;

    private static Map<Integer, SubmitCheckStrategy> getMappings() {
        if (mappings == null) {
            mappings = Arrays.stream(SubmitCheckStrategy.values()).collect(Collectors.toMap(SubmitCheckStrategy::getValue, f -> f));
        }
        return mappings;
    }

    private SubmitCheckStrategy(int value) {
        this.intValue = value;
    }

    public int getValue() {
        return this.intValue;
    }

    public static SubmitCheckStrategy forValue(int value) {
        return SubmitCheckStrategy.getMappings().get(value);
    }
}

