/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.multcheck2.common;

public enum CheckSource {
    APP_SCHEME(0, "APP\u6267\u884c_\u7ed1\u5b9a\u65b9\u6848 "),
    APP_FLOW(1, "APP\u6267\u884c_\u6d41\u7a0b\u5b9a\u4f4d"),
    ENTRY(2, "\u5f55\u5165\u6309\u94ae"),
    FLOW(3, "\u4e0a\u62a5\u6267\u884c");

    private final int value;
    private final String title;

    private CheckSource(int value, String title) {
        this.value = value;
        this.title = title;
    }

    public int value() {
        return this.value;
    }

    public String title() {
        return this.title;
    }

    public static CheckSource fromValue(int value) {
        for (CheckSource myEnum : CheckSource.values()) {
            if (myEnum.value != value) continue;
            return myEnum;
        }
        return null;
    }
}

