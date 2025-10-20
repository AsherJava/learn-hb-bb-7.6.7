/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.conversion.conversionrate.vo;

public class RateTypeItemVO {
    private String title;
    private String code;
    private String value;

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public RateTypeItemVO() {
    }

    public RateTypeItemVO(String code, String title, String value) {
        this.code = code;
        this.value = value;
        this.title = title;
    }
}

