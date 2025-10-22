/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.excel.obj;

public enum SplitChar {
    SPACE("0"),
    UNDERLINE("1"),
    AND("2");

    private final String key;

    private SplitChar(String key) {
        this.key = key;
    }

    public String getKey() {
        return this.key;
    }
}

