/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.formula.web.vo;

public class R<T> {
    private Boolean success;
    private String message;
    private T data;

    public Boolean getSuccess() {
        return this.success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }
}

