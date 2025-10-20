/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.financialcheckcore.scheme.enums;

public enum SchemeTypeEnum {
    ROOT(0, "\u6839\u8282\u70b9"),
    GROUP(1, "\u5206\u7ec4"),
    SCHEME(2, "\u65b9\u6848");

    private Integer code;
    private String title;

    private SchemeTypeEnum(Integer code, String title) {
        this.code = code;
        this.title = title;
    }

    public Integer getCode() {
        return this.code;
    }

    public String getTitle() {
        return this.title;
    }

    public static SchemeTypeEnum getEnumByCode(Integer checkTypeCode) {
        for (SchemeTypeEnum schemeTypeEnum : SchemeTypeEnum.values()) {
            if (!schemeTypeEnum.getCode().equals(checkTypeCode)) continue;
            return schemeTypeEnum;
        }
        return null;
    }
}

