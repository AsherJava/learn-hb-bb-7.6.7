/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.invest.investworkpaper.enums;

public enum DataSourceEnum {
    INVESTDATA("investData", "\u53f0\u8d26\u6570\u636e"),
    FORMDATA("formData", "\u62a5\u8868\u6570\u636e"),
    OFFSETDATA("offsetData", "\u5206\u5f55\u6570\u636e");

    private String code;
    private String title;

    private DataSourceEnum(String code, String title) {
        this.code = code;
        this.title = title;
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

