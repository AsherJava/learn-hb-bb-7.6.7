/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.multcheck2.common;

public enum ResultType {
    SINGLEORG(1, "\u5355\u5355\u4f4d"),
    SINGLESCHEME(2, "\u591a\u5355\u4f4d\u5355\u65b9\u6848"),
    MULTIPLESCHEME(3, "\u591a\u5355\u4f4d\u591a\u65b9\u6848"),
    GATHERAUTO(4, "\u81ea\u52a8\u6c47\u603b\u4efb\u52a1-\u5168\u90e8\u5408\u5e76\u8282\u70b9-\u672a\u6267\u884c\u5ba1\u6838\u573a\u666f");

    int value;
    String title;

    private ResultType(int value, String title) {
        this.value = value;
        this.title = title;
    }

    public int value() {
        return this.value;
    }

    public String title() {
        return this.title;
    }
}

