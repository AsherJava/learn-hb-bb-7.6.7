/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datacheck.dataanalyze.vo;

public enum CheckModelState {
    SUCCESS(11, "\u5ba1\u6838\u901a\u8fc7"),
    SUCCESS_ERROR(12, "\u5ba1\u6838\u901a\u8fc7-\u9519\u8bef\u8bf4\u660e\u5408\u6cd5"),
    FAILED(2, "\u5ba1\u6838\u672a\u901a\u8fc7");

    private int value;
    private String title;

    private CheckModelState(int value, String title) {
        this.value = value;
        this.title = title;
    }

    public int value() {
        return this.value;
    }

    public String title() {
        return this.title;
    }

    public static CheckModelState valueOf(int value) {
        for (CheckModelState type : CheckModelState.values()) {
            if (value != type.value()) continue;
            return type;
        }
        return null;
    }
}

