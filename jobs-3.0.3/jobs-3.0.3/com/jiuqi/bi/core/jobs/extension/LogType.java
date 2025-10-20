/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.core.jobs.extension;

public enum LogType {
    DEBUG(10, "\u8c03\u8bd5"),
    INFO(20, "\u4fe1\u606f"),
    WARN(30, "\u8b66\u544a"),
    ERROR(40, "\u9519\u8bef");

    private int value;
    private String title;

    public int getValue() {
        return this.value;
    }

    public String getTitle() {
        return this.title;
    }

    private LogType(int value, String title) {
        this.value = value;
        this.title = title;
    }
}

