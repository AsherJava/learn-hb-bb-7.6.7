/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.zbquery.model;

public enum SumPosition {
    TOP(0, "\u9876\u90e8"),
    BOTTOM(1, "\u5e95\u90e8");

    private int value;
    private String title;

    private SumPosition(int value, String title) {
        this.value = value;
        this.title = title;
    }

    public int value() {
        return this.value;
    }

    public String title() {
        return this.title;
    }

    public static SumPosition valueOf(int value) {
        for (SumPosition type : SumPosition.values()) {
            if (value != type.value()) continue;
            return type;
        }
        return null;
    }
}

