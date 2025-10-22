/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.definition.common;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum InheritMode {
    INHERIT_MODE_SELF(0),
    INHERIT_MODE_PARENT(1),
    INHERIT_MODE_ALL(2);

    private int intValue;
    private static Map<Integer, InheritMode> mappings;

    private static Map<Integer, InheritMode> getMappings() {
        if (mappings == null) {
            mappings = Arrays.stream(InheritMode.values()).collect(Collectors.toMap(InheritMode::getValue, f -> f));
        }
        return mappings;
    }

    private InheritMode(int value) {
        this.intValue = value;
    }

    public int getValue() {
        return this.intValue;
    }

    public static InheritMode forValue(int value) {
        return InheritMode.getMappings().get(value);
    }
}

