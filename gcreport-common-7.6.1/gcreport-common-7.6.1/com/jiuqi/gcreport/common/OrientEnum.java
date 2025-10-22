/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.common;

public enum OrientEnum {
    D("D", 1, "\u501f"),
    C("C", -1, "\u8d37");

    private final String code;
    private final Integer value;
    private final String title;

    private OrientEnum(String code, Integer value, String title) {
        this.code = code;
        this.value = value;
        this.title = title;
    }

    public String getCode() {
        return this.code;
    }

    public Integer getValue() {
        return this.value;
    }

    public String getTitle() {
        return this.title;
    }

    public static OrientEnum valueOf(Integer value) {
        if (OrientEnum.D.value.equals(value)) {
            return D;
        }
        if (OrientEnum.C.value.equals(value)) {
            return C;
        }
        return null;
    }
}

