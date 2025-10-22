/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.common.paramcheck.ext;

public enum ErrorTypeEnum {
    FORM_ERROR("\u62a5\u8868\u9519\u8bef"),
    ZB_EROR("\u6307\u6807\u9519\u8bef"),
    GHOST_ERROR("\u65e0\u6548\u53c2\u6570\u9519\u8bef");

    private String error;

    public String getError() {
        return this.error;
    }

    public void setError(String error) {
        this.error = error;
    }

    private ErrorTypeEnum(String error) {
        this.error = error;
    }
}

