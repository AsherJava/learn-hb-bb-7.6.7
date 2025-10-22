/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.checkdes.obj;

public enum FilterType {
    DIMENSION("\u6309\u7ef4\u5ea6\u8fc7\u6ee4"),
    FORMULA_SCHEME("\u6309\u516c\u5f0f\u65b9\u6848\u8fc7\u6ee4"),
    FORM("\u6309\u62a5\u8868\u8fc7\u6ee4"),
    FORMULA("\u6309\u516c\u5f0f\u8fc7\u6ee4");

    private final String desc;

    private FilterType(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return this.desc;
    }
}

