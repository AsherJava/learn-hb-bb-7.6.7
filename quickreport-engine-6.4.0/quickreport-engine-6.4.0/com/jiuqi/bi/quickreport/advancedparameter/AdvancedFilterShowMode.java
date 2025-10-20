/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.quickreport.advancedparameter;

public enum AdvancedFilterShowMode {
    NOTSHOW(0, "\u4e0d\u8fc7\u6ee4"),
    DEFAULTSHOW(1, "\u9ed8\u8ba4\u663e\u793a"),
    GROUPSHOW(2, "\u5206\u7ec4\u663e\u793a");

    private int value;
    private String title;

    private AdvancedFilterShowMode(int value, String title) {
        this.value = value;
        this.title = title;
    }

    public int value() {
        return this.value;
    }

    public String title() {
        return this.title;
    }

    public static AdvancedFilterShowMode valueOf(int value) {
        if (value == 0) {
            return NOTSHOW;
        }
        if (value == 1) {
            return DEFAULTSHOW;
        }
        if (value == 2) {
            return GROUPSHOW;
        }
        return NOTSHOW;
    }
}

