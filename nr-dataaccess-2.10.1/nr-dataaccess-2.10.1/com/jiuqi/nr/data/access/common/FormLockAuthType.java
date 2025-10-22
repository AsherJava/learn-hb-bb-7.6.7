/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.access.common;

public enum FormLockAuthType {
    DISABLED("0", "DISABLED"),
    WRITE("1", "WRITE"),
    UPLOAD("2", "UPLOAD"),
    AUDIT("3", "AUDIT"),
    FORCEUNLOCK("4", "FORCEUNLOCK");

    private String code;
    private String title;

    private FormLockAuthType(String code, String title) {
        this.code = code;
        this.title = title;
    }

    public String getCode() {
        return this.code;
    }

    public String getTitle() {
        return this.title;
    }

    public static FormLockAuthType getTypeByCode(String code) {
        for (FormLockAuthType value : FormLockAuthType.values()) {
            if (!value.code.equals(code)) continue;
            return value;
        }
        return null;
    }
}

