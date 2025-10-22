/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.financialcheckcore.scheme.enums;

public enum CheckSchemeStateEnum {
    ENABLE(1, "\u542f\u7528"),
    DISABLE(0, "\u7981\u7528"),
    MIXED(2, "\u7981\u7528");

    private Integer code;
    private String title;

    private CheckSchemeStateEnum(Integer code, String title) {
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

