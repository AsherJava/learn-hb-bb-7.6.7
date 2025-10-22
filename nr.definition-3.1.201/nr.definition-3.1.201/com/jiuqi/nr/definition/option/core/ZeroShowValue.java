/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.option.core;

public enum ZeroShowValue {
    ORIGINAL_VALUE("\u539f\u503c", "\u539f\u503c"),
    NULL_VALUE("\u7a7a\u503c", "\u7a7a\u503c"),
    SYMBOL_ONE_VALUE("\u2014\u2014", "\u2014\u2014"),
    SYMBOL_TWO_VALUE("-", "-"),
    NUMBER_ZERO_VALUE("\u6574\u65700", "0"),
    CHINESE_ZERO_VALUE("\u96f6", "\u96f6");

    private final String title;
    private final String value;

    private ZeroShowValue(String title, String value) {
        this.title = title;
        this.value = value;
    }

    public String getTitle() {
        return this.title;
    }

    public String getValue() {
        return this.value;
    }

    public static ZeroShowValue getEnumByValue(String value) {
        for (ZeroShowValue v : ZeroShowValue.values()) {
            if (!v.getValue().equals(value)) continue;
            return v;
        }
        return null;
    }
}

