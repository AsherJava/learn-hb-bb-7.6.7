/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.form.formcopy.common;

import java.util.HashMap;
import java.util.Map;

public enum SchemeType {
    FORMULA_SCHEME(1, "\u516c\u5f0f\u65b9\u6848"),
    PRINT_SCHEME(3, "\u6253\u5370\u516c\u5f0f\u65b9\u6848");

    private final int value;
    private final String title;
    private static final Map<Integer, SchemeType> valueMap;

    private SchemeType(int value, String title) {
        this.value = value;
        this.title = title;
    }

    public int getValue() {
        return this.value;
    }

    public String getTitle() {
        return this.title;
    }

    public static SchemeType valueOf(int value) {
        return valueMap.get(value);
    }

    static {
        valueMap = new HashMap<Integer, SchemeType>();
        for (SchemeType type : SchemeType.values()) {
            valueMap.put(type.getValue(), type);
        }
    }
}

