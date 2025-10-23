/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.zbquery.model;

public enum DisplayContent {
    CODE(1, "\u4ee3\u7801"),
    TITLE(2, "\u540d\u79f0"),
    CODE_TITLE(3, "\u4ee3\u7801+\u540d\u79f0");

    private int value;
    private String title;

    private DisplayContent(int value, String title) {
        this.value = value;
        this.title = title;
    }

    public int value() {
        return this.value;
    }

    public String title() {
        return this.title;
    }

    public static DisplayContent valueOf(int value) {
        for (DisplayContent type : DisplayContent.values()) {
            if (value != type.value()) continue;
            return type;
        }
        return null;
    }
}

