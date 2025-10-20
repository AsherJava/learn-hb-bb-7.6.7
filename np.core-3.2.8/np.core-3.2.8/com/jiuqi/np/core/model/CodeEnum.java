/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.core.model;

public enum CodeEnum {
    SUCCESS("200"),
    FAIL("400"),
    UNAUTHORIZED("401"),
    INTERNAL_SERVER_ERROR("500");

    private String code;

    private CodeEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }
}

