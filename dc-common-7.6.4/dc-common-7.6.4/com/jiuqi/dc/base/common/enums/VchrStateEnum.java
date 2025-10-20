/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.base.common.enums;

public enum VchrStateEnum {
    ADD(1, "\u65b0\u589e"),
    UPDATE(2, "\u4fee\u6539"),
    DELETE(3, "\u5220\u9664");

    private Integer code;
    private String name;

    private VchrStateEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }
}

