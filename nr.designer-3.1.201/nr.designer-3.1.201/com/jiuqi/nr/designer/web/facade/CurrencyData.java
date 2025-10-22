/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.designer.web.facade;

public class CurrencyData {
    private String code;
    private String title;

    public CurrencyData(String code, String title) {
        this.code = code;
        this.title = title;
    }

    public CurrencyData() {
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

