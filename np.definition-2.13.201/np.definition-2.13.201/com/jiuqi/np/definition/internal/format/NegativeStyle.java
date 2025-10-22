/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.definition.internal.format;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum NegativeStyle {
    NS_0("0"),
    NS_1("1");

    private final String value;
    public static final String KEY = "negativeStyle";
    private static final Map<String, NegativeStyle> VALUE_MAP;

    private NegativeStyle(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public static NegativeStyle getByValue(String value) {
        if (value == null) {
            return null;
        }
        return VALUE_MAP.get(value);
    }

    static {
        VALUE_MAP = Arrays.stream(NegativeStyle.values()).collect(Collectors.toMap(NegativeStyle::getValue, r -> r));
    }
}

