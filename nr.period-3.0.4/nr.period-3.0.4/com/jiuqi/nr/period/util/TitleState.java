/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.period.util;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum TitleState {
    NONE(0),
    TITLE(1),
    SIMPLE_TITLE(2);

    private int intValue;
    private static Map<Integer, TitleState> mappings;

    private static Map<Integer, TitleState> getMappings() {
        if (mappings == null) {
            mappings = Arrays.stream(TitleState.values()).collect(Collectors.toMap(TitleState::getValue, f -> f));
        }
        return mappings;
    }

    private TitleState(int value) {
        this.intValue = value;
    }

    public int getValue() {
        return this.intValue;
    }

    public static TitleState forValue(int value) {
        return TitleState.getMappings().get(value);
    }
}

