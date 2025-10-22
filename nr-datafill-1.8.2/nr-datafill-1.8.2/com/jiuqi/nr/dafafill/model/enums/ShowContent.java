/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dafafill.model.enums;

public enum ShowContent {
    NONE(-1, "\u65e0"),
    CODE(2, "\u4ee3\u7801"),
    TITLE(3, "\u540d\u79f0"),
    CODE_TITLE(4, "\u4ee3\u7801|\u540d\u79f0"),
    TITLE_CODE(5, "\u540d\u79f0|\u4ee3\u7801");

    private int value;
    private String title;

    private ShowContent(int value, String title) {
        this.value = value;
        this.title = title;
    }

    public int value() {
        return this.value;
    }

    public String title() {
        return this.title;
    }

    public static ShowContent valueOf(int value) {
        for (ShowContent type : ShowContent.values()) {
            if (value != type.value()) continue;
            return type;
        }
        return null;
    }
}

