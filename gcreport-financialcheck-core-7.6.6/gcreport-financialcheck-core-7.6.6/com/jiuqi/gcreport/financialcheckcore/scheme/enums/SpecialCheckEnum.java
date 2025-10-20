/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.financialcheckcore.scheme.enums;

public enum SpecialCheckEnum {
    SPECIAL(1, "\u662f"),
    NORMAL(0, "\u5426");

    private Integer code;
    private String title;

    private SpecialCheckEnum(Integer code, String title) {
        this.code = code;
        this.title = title;
    }

    public Integer getCode() {
        return this.code;
    }

    public String getTitle() {
        return this.title;
    }
}

