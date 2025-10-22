/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.financialcheckcore.scheme.enums;

public enum BusinessRoleEnum {
    ASSET(1, "\u503a\u6743\u65b9\u3001\u6d41\u5165\u65b9\u3001\u9500\u552e\u65b9"),
    DEBT(-1, "\u503a\u52a1\u65b9\u3001\u91c7\u8d2d\u65b9\u3001\u6d41\u51fa\u65b9"),
    ALL(0, "\u5168\u90e8");

    private Integer code;
    private String title;

    private BusinessRoleEnum(Integer code, String title) {
        this.code = code;
        this.title = title;
    }

    public Integer getCode() {
        return this.code;
    }

    public String getTitle() {
        return this.title;
    }

    public static BusinessRoleEnum getEnumByCode(Integer code) {
        for (BusinessRoleEnum subjectCategoryEnum : BusinessRoleEnum.values()) {
            if (!subjectCategoryEnum.getCode().equals(code)) continue;
            return subjectCategoryEnum;
        }
        return null;
    }
}

