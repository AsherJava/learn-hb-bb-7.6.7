/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.custom.bean;

public class CurrencyDefine {
    private String code;
    private String title;

    public CurrencyDefine(String code, String title) {
        this.code = code;
        this.title = title;
    }

    public CurrencyDefine() {
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

