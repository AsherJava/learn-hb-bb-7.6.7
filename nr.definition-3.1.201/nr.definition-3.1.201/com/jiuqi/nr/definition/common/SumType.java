/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.common;

public enum SumType {
    UNKNOWN(-1, "UNKNOWN", "\u672a\u77e5"),
    SUM(0, "SUM", "\u7d2f\u52a0"),
    COUNT(1, "COUNT", "\u8ba1\u6570"),
    MAX(2, "MAX", "\u6700\u5927"),
    MIN(3, "MIN", "\u6700\u5c0f"),
    AVG(4, "AVG", "\u5e73\u5747");

    private final int value;
    private final String name;
    private final String title;

    private SumType(int value, String name, String title) {
        this.value = value;
        this.name = name;
        this.title = title;
    }

    public int getValue() {
        return this.value;
    }

    public String getName() {
        return this.name;
    }

    public String getTitle() {
        return this.title;
    }

    public static SumType valueOf(int value) {
        for (SumType state : SumType.values()) {
            if (state.getValue() != value) continue;
            return state;
        }
        return null;
    }
}

