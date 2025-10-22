/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.invest.restapi;

public enum InvestRestApiEnum {
    BASE("gc:invest:base", "\u7ba1\u7406");

    private String code;
    private String title;

    private InvestRestApiEnum(String code, String title) {
        this.code = code;
        this.title = title;
    }

    public String getCode() {
        return this.code;
    }

    public String getTitle() {
        return this.title;
    }
}

