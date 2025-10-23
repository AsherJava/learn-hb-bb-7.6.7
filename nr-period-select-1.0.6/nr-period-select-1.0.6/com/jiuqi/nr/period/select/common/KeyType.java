/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.period.select.common;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum KeyType {
    TASK(0),
    FORMSCHEME(1),
    DATASCHEME(2);

    private int intValue;
    private static Map<Integer, KeyType> mappings;

    private static Map<Integer, KeyType> getMappings() {
        if (mappings == null) {
            mappings = Arrays.stream(KeyType.values()).collect(Collectors.toMap(KeyType::getValue, f -> f));
        }
        return mappings;
    }

    private KeyType(int value) {
        this.intValue = value;
    }

    public int getValue() {
        return this.intValue;
    }

    public static KeyType forValue(int value) {
        return KeyType.getMappings().get(value);
    }
}

