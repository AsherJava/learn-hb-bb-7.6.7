/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datacheck.dataanalyze.vo;

public enum CheckUnitState {
    SUCCESS(1, "\u5ba1\u6838\u901a\u8fc7"),
    FAILED(2, "\u5ba1\u6838\u672a\u901a\u8fc7"),
    FAILED_ERROR_LEGAL(11, "\u672a\u901a\u8fc7-\u9519\u8bef\u8bf4\u660e\u5408\u6cd5"),
    FAILED_ERROR_ILLEGAL(22, "\u672a\u901a\u8fc7-\u9519\u8bef\u8bf4\u660e\u975e\u6cd5\u5408\u6cd5"),
    FAILED_ERROR_NODE(23, "\u672a\u901a\u8fc7-\u9519\u8bef\u8bf4\u660e\u4e3a\u7a7a");

    private int value;
    private String title;

    private CheckUnitState(int value, String title) {
        this.value = value;
        this.title = title;
    }

    public int value() {
        return this.value;
    }

    public String title() {
        return this.title;
    }

    public static CheckUnitState valueOf(int value) {
        for (CheckUnitState type : CheckUnitState.values()) {
            if (value != type.value()) continue;
            return type;
        }
        return null;
    }
}

