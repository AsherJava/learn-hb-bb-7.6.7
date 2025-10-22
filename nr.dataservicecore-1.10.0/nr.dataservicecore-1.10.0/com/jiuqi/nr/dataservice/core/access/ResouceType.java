/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataservice.core.access;

public enum ResouceType {
    FORM(1),
    ZB(2),
    NONE(3);

    private final int code;

    private ResouceType(int code) {
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }
}

