/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.access.param;

public class AccessCode {
    private String name;
    private String code = "1";

    public AccessCode(String name) {
        this.name = name;
    }

    public AccessCode(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

    protected void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }
}

