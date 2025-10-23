/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.summary.model.soulution;

public enum SourceDimensionRange {
    SELF(1, "\u672c\u7ea7\u8282\u70b9"),
    DIRECT(2, "\u76f4\u63a5\u4e0b\u7ea7"),
    LEAF(3, "\u6240\u6709\u53f6\u5b50\u8282\u70b9"),
    ALL(4, "\u672c\u7ea7+\u6240\u6709\u4e0b\u7ea7"),
    LIST(5, "\u6839\u636e\u6240\u9009"),
    CONDITION(6, "\u6839\u636e\u6761\u4ef6\u8fc7\u6ee4");

    private int value;
    private String title;

    private SourceDimensionRange(int value, String title) {
        this.value = value;
        this.title = title;
    }

    public int value() {
        return this.value;
    }

    public String title() {
        return this.title;
    }

    public static SourceDimensionRange valueOf(int value) {
        for (SourceDimensionRange type : SourceDimensionRange.values()) {
            if (value != type.value()) continue;
            return type;
        }
        return null;
    }
}

