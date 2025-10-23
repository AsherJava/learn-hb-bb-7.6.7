/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.period.select.common;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum Mode {
    S("S"),
    M("M"),
    R("R");

    private String intValue;
    private static Map<String, Mode> mappings;

    private static Map<String, Mode> getMappings() {
        if (mappings == null) {
            mappings = Arrays.stream(Mode.values()).collect(Collectors.toMap(Mode::getValue, f -> f));
        }
        return mappings;
    }

    private Mode(String value) {
        this.intValue = value;
    }

    public String getValue() {
        return this.intValue;
    }

    public static Mode forValue(int value) {
        return Mode.getMappings().get(value);
    }
}

