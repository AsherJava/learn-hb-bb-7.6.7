/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.summary.model.soulution;

public enum TargetDimensionRange {
    ALL(1, "\u6240\u6709\u8282\u70b9"),
    LIST(2, "\u6839\u636e\u6240\u9009"),
    CONDITION(3, "\u6839\u636e\u6761\u4ef6\u8fc7\u6ee4");

    private int value;
    private String title;

    private TargetDimensionRange(int value, String title) {
        this.value = value;
        this.title = title;
    }

    public int value() {
        return this.value;
    }

    public String title() {
        return this.title;
    }

    public static TargetDimensionRange valueOf(int value) {
        for (TargetDimensionRange type : TargetDimensionRange.values()) {
            if (value != type.value()) continue;
            return type;
        }
        return null;
    }
}

