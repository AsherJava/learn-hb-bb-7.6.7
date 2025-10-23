/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.zbquery.model;

public enum FilterType {
    NULL(1, "\u7a7a\u503c"),
    NOT_NULL(2, "\u975e\u7a7a\u503c"),
    POSITIVE(3, "\u6b63\u6570"),
    NEGATIVE(4, "\u8d1f\u6570"),
    ZERO(5, "0\u503c"),
    TRUE(6, "\u662f"),
    FALSE(7, "\u5426");

    private int value;
    private String title;

    private FilterType(int value, String title) {
        this.value = value;
        this.title = title;
    }

    public int value() {
        return this.value;
    }

    public String title() {
        return this.title;
    }

    public static FilterType valueOf(int value) {
        for (FilterType type : FilterType.values()) {
            if (value != type.value()) continue;
            return type;
        }
        return null;
    }
}

