/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.zbquery.model;

public enum NullRowDisplayMode {
    DEFAULT(0, "\u9ed8\u8ba4"),
    DISPLAY_ALLNULL(1, "\u663e\u793a\u5168\u7a7a\u6570\u636e\u5355\u4f4d"),
    HIDE_ALLNULL(2, "\u4e0d\u663e\u793a\u5168\u7a7a\u6570\u636e\u5355\u4f4d"),
    HIDE_ALLZERO(3, "\u4e0d\u663e\u793a\u5168\u7a7a\u548c\u51680\u6570\u636e\u5355\u4f4d");

    private int value;
    private String title;

    private NullRowDisplayMode(int value, String title) {
        this.value = value;
        this.title = title;
    }

    public int value() {
        return this.value;
    }

    public String title() {
        return this.title;
    }

    public static NullRowDisplayMode valueOf(int value) {
        for (NullRowDisplayMode type : NullRowDisplayMode.values()) {
            if (value != type.value()) continue;
            return type;
        }
        return null;
    }
}

