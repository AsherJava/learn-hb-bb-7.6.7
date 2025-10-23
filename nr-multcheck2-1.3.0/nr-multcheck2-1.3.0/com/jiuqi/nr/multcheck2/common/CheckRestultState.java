/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.multcheck2.common;

public enum CheckRestultState {
    FAIL(0, "\u5931\u8d25"),
    SUCCESS(1, "\u6210\u529f"),
    WARN(2, "\u5f85\u6838\u5bf9"),
    IGNORE(3, "\u4e0d\u9002\u7528"),
    SUCCESS_ERROR(4, "\u901a\u8fc7-\u5b58\u5728\u8bf4\u660e");

    private int value;
    private String title;

    private CheckRestultState(int value, String title) {
        this.value = value;
        this.title = title;
    }

    public int value() {
        return this.value;
    }

    public String title() {
        return this.title;
    }

    public static CheckRestultState fromValue(int value) {
        for (CheckRestultState myEnum : CheckRestultState.values()) {
            if (myEnum.value != value) continue;
            return myEnum;
        }
        return null;
    }
}

