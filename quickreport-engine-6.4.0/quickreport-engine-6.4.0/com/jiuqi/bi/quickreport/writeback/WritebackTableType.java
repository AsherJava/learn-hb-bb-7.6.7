/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.quickreport.writeback;

public enum WritebackTableType {
    FIXED(0, "\u56fa\u5b9a\u8868"),
    EXPAND(1, "\u6d6e\u52a8\u8868");

    private int value;
    private String title;

    private WritebackTableType(int value, String title) {
        this.value = value;
        this.title = title;
    }

    public int value() {
        return this.value;
    }

    public String getTitle() {
        return this.title;
    }

    public static WritebackTableType valueOf(int value) {
        if (value == 0) {
            return FIXED;
        }
        return EXPAND;
    }
}

