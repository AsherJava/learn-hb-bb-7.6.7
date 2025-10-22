/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.period.common.language;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum LanguageType {
    Chinese(0, "zh"),
    English(1, "en");

    private int value;
    private String code;
    private static Map<Integer, LanguageType> mappings;

    private static Map<Integer, LanguageType> getMappings() {
        if (mappings == null) {
            mappings = Arrays.stream(LanguageType.values()).collect(Collectors.toMap(LanguageType::getValue, f -> f));
        }
        return mappings;
    }

    private LanguageType(int value, String code) {
        this.value = value;
        this.code = code;
    }

    public int getValue() {
        return this.value;
    }

    public String getCode() {
        return this.code;
    }

    public static LanguageType forValue(int value) {
        return LanguageType.getMappings().get(value);
    }
}

