/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dafafill.model.enums;

public enum EditorType {
    NONE(-1, "\u65e0"),
    CALENDAR(2, "\u65e5\u5386\u63a7\u4ef6"),
    TEXT(3, "\u6587\u672c\u6846"),
    DROPDOWN(4, "\u4e0b\u62c9\u9009\u62e9"),
    POPUP(5, "\u5f39\u51fa\u7a97\u53e3"),
    UNITSELECTOR(6, "\u5355\u4f4d\u9009\u62e9\u5668"),
    DATE(7, "\u65e5\u671f\u63a7\u4ef6");

    private int value;
    private String title;

    private EditorType(int value, String title) {
        this.value = value;
        this.title = title;
    }

    public int value() {
        return this.value;
    }

    public String title() {
        return this.title;
    }

    public static EditorType valueOf(int value) {
        for (EditorType type : EditorType.values()) {
            if (value != type.value()) continue;
            return type;
        }
        return null;
    }
}

