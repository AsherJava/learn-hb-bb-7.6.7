/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.common;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum FormViewType {
    FORM_VIEW_FORM_GRID(0),
    FORM_VIEW_FORM(1),
    FORM_VIEW_GRID(2);

    private int intValue;
    private static Map<Integer, FormViewType> mappings;

    private static Map<Integer, FormViewType> getMappings() {
        if (mappings == null) {
            mappings = Arrays.stream(FormViewType.values()).collect(Collectors.toMap(FormViewType::getValue, f -> f));
        }
        return mappings;
    }

    private FormViewType(int value) {
        this.intValue = value;
    }

    public int getValue() {
        return this.intValue;
    }

    public static FormViewType forValue(int value) {
        return FormViewType.getMappings().get(value);
    }
}

