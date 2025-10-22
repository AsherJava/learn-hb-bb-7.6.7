/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.text.param;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum TextType {
    TEXTTYPE_TXT(0),
    TEXTTYPE_CSV(1);

    private int intValue;
    private static Map<Integer, TextType> mappings;

    private static Map<Integer, TextType> getMappings() {
        if (mappings == null) {
            mappings = Arrays.stream(TextType.values()).collect(Collectors.toMap(TextType::getValue, f -> f));
        }
        return mappings;
    }

    private TextType(int value) {
        this.intValue = value;
    }

    public int getValue() {
        return this.intValue;
    }

    public static TextType forValue(int value) {
        return TextType.getMappings().get(value);
    }
}

