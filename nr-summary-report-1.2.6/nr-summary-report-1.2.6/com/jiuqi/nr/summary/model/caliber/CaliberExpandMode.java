/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.summary.model.caliber;

public enum CaliberExpandMode {
    ALL(1, "\u6240\u6709\u8282\u70b9"),
    LEVEL(2, "\u9009\u62e9\u8282\u70b9\u7ea7\u6b21"),
    LIST(3, "\u6839\u636e\u6240\u9009"),
    FILTER(4, "\u6839\u636e\u6761\u4ef6\u8fc7\u6ee4"),
    SELF(5, "\u672c\u7ea7"),
    SELF_DIRECT(6, "\u672c\u7ea7+\u76f4\u63a5\u4e0b\u7ea7"),
    SELF_ALL(7, "\u672c\u7ea7+\u6240\u6709\u4e0b\u7ea7"),
    DIRECT(8, "\u76f4\u63a5\u4e0b\u7ea7"),
    ALL_CHILD(9, "\u6240\u6709\u4e0b\u7ea7");

    private int value;
    private String title;

    private CaliberExpandMode(int value, String title) {
        this.value = value;
        this.title = title;
    }

    public int value() {
        return this.value;
    }

    public String title() {
        return this.title;
    }

    public static CaliberExpandMode valueOf(int value) {
        for (CaliberExpandMode type : CaliberExpandMode.values()) {
            if (value != type.value()) continue;
            return type;
        }
        return null;
    }
}

