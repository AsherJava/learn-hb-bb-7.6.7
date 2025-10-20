/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.financialcheckcore.scheme.enums;

public enum CheckToleranceEnum {
    ENABLE(1, "\u662f"),
    DISABLE(0, "\u5426");

    private Integer code;
    private String title;

    private CheckToleranceEnum(Integer code, String title) {
        this.code = code;
        this.title = title;
    }

    public Integer getCode() {
        return this.code;
    }

    public String getTitle() {
        return this.title;
    }

    public static CheckToleranceEnum getEnumByCode(Integer code) {
        for (CheckToleranceEnum checkToleranceEnum : CheckToleranceEnum.values()) {
            if (!checkToleranceEnum.getCode().equals(code)) continue;
            return checkToleranceEnum;
        }
        return null;
    }
}

