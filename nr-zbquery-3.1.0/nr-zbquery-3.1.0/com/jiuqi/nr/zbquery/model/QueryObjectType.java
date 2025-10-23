/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.zbquery.model;

public enum QueryObjectType {
    DIMENSION(1, "\u7ef4\u5ea6"),
    DIMENSIONATTRIBUTE(2, "\u7ef4\u5c5e\u6027"),
    ZB(3, "\u6307\u6807"),
    FORMULA(4, "\u516c\u5f0f"),
    GROUP(99, "\u5206\u7ec4");

    private int value;
    private String title;

    private QueryObjectType(int value, String title) {
        this.value = value;
        this.title = title;
    }

    public int value() {
        return this.value;
    }

    public String title() {
        return this.title;
    }

    public static QueryObjectType valueOf(int value) {
        for (QueryObjectType type : QueryObjectType.values()) {
            if (value != type.value()) continue;
            return type;
        }
        return null;
    }
}

