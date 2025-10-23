/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.zbquery.model;

public enum QueryDimensionType {
    MASTER(1, "\u4e3b\u7ef4\u5ea6"),
    PERIOD(2, "\u65f6\u671f"),
    SCENE(3, "\u60c5\u666f"),
    INNER(4, "\u8868\u5185"),
    CHILD(5, "\u5b50\u7ef4\u5ea6"),
    MDINFO(6, "\u5355\u4f4d\u4fe1\u606f");

    private int value;
    private String title;

    private QueryDimensionType(int value, String title) {
        this.value = value;
        this.title = title;
    }

    public int value() {
        return this.value;
    }

    public String title() {
        return this.title;
    }

    public static QueryDimensionType valueOf(int value) {
        for (QueryDimensionType type : QueryDimensionType.values()) {
            if (value != type.value()) continue;
            return type;
        }
        return null;
    }
}

