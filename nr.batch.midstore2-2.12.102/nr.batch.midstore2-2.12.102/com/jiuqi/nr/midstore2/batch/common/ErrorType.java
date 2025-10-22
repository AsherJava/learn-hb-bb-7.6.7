/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.midstore2.batch.common;

public enum ErrorType {
    UNIT("01", "\u5355\u4f4d\u9519\u8bef"),
    FORM("02", "\u8868\u5355\u9519\u8bef");

    private String value;
    private String title;

    private ErrorType(String value, String title) {
        this.value = value;
        this.title = title;
    }

    public String value() {
        return this.value;
    }

    public String title() {
        return this.title;
    }
}

