/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.base.common.enums;

public enum TableCheckTypeEnum {
    DESIGN_CHECK("DesignCheck", "\u8bbe\u8ba1\u671f\u68c0\u67e5"),
    RUNNING_CHECK("RunningCheck", "\u8fd0\u884c\u671f\u68c0\u67e5"),
    DB_CHECK("DbCheck", "\u7269\u7406\u8868\u68c0\u67e5");

    private final String code;
    private final String name;

    private TableCheckTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }
}

