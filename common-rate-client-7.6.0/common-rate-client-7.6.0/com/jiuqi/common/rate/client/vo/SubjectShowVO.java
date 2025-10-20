/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.common.rate.client.vo;

public class SubjectShowVO {
    private String code;
    private String label;
    private String title;
    private String currency;

    public SubjectShowVO() {
    }

    public SubjectShowVO(String code, String label) {
        this.code = code;
        this.label = label;
        this.title = label;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLabel() {
        return this.label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getCurrency() {
        return this.currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

