/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.asynctask;

public enum TaskResultEnum {
    SUCCESS(100, "\u6210\u529f"),
    FAILURE(-100, "\u5931\u8d25"),
    EXCEPTION(4, "\u4efb\u52a1\u5f02\u5e38");

    private final int value;
    private final String title;

    private TaskResultEnum(int value, String title) {
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

