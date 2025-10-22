/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.definition.internal.format;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum FixMode {
    FIX_0("0"),
    FIX_1("1"),
    FIX_2("2");

    private final String value;
    public static final String KEY = "fixMode";
    private static final Map<String, FixMode> VALUE_MAP;

    private FixMode(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public static FixMode getByValue(String value) {
        if (value == null) {
            return null;
        }
        return VALUE_MAP.get(value);
    }

    static {
        VALUE_MAP = Arrays.stream(FixMode.values()).collect(Collectors.toMap(FixMode::getValue, r -> r));
    }
}

