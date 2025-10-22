/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.midstore2.dataentry.web.vo;

public class FailedForm {
    private String code;
    private String title;
    private String message;

    public FailedForm() {
    }

    public FailedForm(String code, String title, String message) {
        this.code = code;
        this.title = title;
        this.message = message;
    }

    public FailedForm(String code, String title) {
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

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

