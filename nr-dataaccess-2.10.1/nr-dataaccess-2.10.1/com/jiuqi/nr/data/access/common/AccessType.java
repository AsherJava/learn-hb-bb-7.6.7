/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.access.common;

import java.util.Arrays;

public enum AccessType {
    VISIT(1, "\u8bbf\u95ee\u6743\u9650"),
    READ(2, "\u8bfb\u6743\u9650"),
    WRITE(3, "\u5199\u6743\u9650"),
    SYS_WRITE(4, "\u7cfb\u7edf\u5199\u6743\u9650");

    private int code;
    private String value;

    private AccessType(int code, String value) {
        this.code = code;
        this.value = value;
    }

    public static AccessType getAccessTypeByCode(int code) {
        return Arrays.asList(AccessType.values()).stream().filter(e -> e.getCode() == code).findAny().get();
    }

    public int getCode() {
        return this.code;
    }

    public String getValue() {
        return this.value;
    }
}

