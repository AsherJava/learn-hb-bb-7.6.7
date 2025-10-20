/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.unionrule.enums;

public enum LeaseFetchTypeEnum {
    CUSTOMIZE("CUSTOMIZE", "\u81ea\u5b9a\u4e49\u79d1\u76ee"),
    FORMULA("FORMULA", "\u79d1\u76ee\u516c\u5f0f");

    private String code;
    private String name;

    private LeaseFetchTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

