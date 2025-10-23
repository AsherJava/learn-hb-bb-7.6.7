/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datascheme.fix.common;

public enum DeployFixResultType {
    FIX_SUCCESS(401, "\u4fee\u590d\u6210\u529f"),
    FIX_FAIL(402, "\u4fee\u590d\u5931\u8d25"),
    UNKNOW_FIXSCHEME(403, "\u672a\u627e\u5230\u4fee\u590d\u65b9\u6848"),
    NOT_FIXED(404, "\u672a\u4fee\u590d");

    private final int value;
    private final String title;

    private DeployFixResultType(int value, String title) {
        this.value = value;
        this.title = title;
    }

    public int getValue() {
        return this.value;
    }

    public String getTitle() {
        return this.title;
    }
}

