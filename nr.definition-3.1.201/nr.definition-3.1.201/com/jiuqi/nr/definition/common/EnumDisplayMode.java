/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.common;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum EnumDisplayMode {
    DISPLAY_MODE_DEFAULT(0),
    DISPLAY_MODE_IN_CELL(1),
    DISPLAY_NODE_POP(2);

    private int intValue;
    private static Map<Integer, EnumDisplayMode> mappings;

    private static Map<Integer, EnumDisplayMode> getMappings() {
        if (mappings == null) {
            mappings = Arrays.stream(EnumDisplayMode.values()).collect(Collectors.toMap(EnumDisplayMode::getValue, f -> f));
        }
        return mappings;
    }

    private EnumDisplayMode(int value) {
        this.intValue = value;
    }

    public int getValue() {
        return this.intValue;
    }

    public static EnumDisplayMode forValue(int value) {
        return EnumDisplayMode.getMappings().get(value);
    }
}

