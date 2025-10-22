/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.excel.obj;

public enum FormShow {
    TITLE("0"),
    CODE("1"),
    SERIAL_NUM("2");

    private final String key;

    private FormShow(String key) {
        this.key = key;
    }

    public String getKey() {
        return this.key;
    }
}

