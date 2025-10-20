/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.clbrbill.enums;

public enum ClbrThirdOptionsEnum {
    YES(1, "\u662f"),
    NO(0, "\u5426");

    private Integer code;
    private String title;

    private ClbrThirdOptionsEnum(Integer code, String title) {
        this.code = code;
        this.title = title;
    }

    public Integer getCode() {
        return this.code;
    }

    public String getTitle() {
        return this.title;
    }
}

