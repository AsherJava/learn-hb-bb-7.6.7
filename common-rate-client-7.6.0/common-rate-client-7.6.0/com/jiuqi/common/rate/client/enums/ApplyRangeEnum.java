/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.common.rate.client.enums;

public enum ApplyRangeEnum {
    REPORT("REPORT", "\u8868\u6298"),
    ACCOUNT("ACCOUNT", "\u8d26\u6298"),
    ALL("ALL", "\u8868\u6298/\u8d26\u6298");

    private static final long ID = 1L;
    private String code;
    private String name;

    private ApplyRangeEnum(String code, String name) {
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

