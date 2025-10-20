/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.onekeymerge.vo;

public class ReturnObject {
    private boolean isSuccess;
    private String errorMessage;
    private Object data;

    public static ReturnObject ofSuccess(Object data) {
        return new ReturnObject(true, data);
    }

    public static ReturnObject ofFailed(String errorMessage, Object data) {
        return new ReturnObject(false, errorMessage, data);
    }

    public ReturnObject(boolean isSuccess, String errorMessage, Object data) {
        this.isSuccess = isSuccess;
        this.errorMessage = errorMessage;
        this.data = data;
    }

    public ReturnObject(boolean isSuccess, String errorMessage) {
        this.isSuccess = isSuccess;
        this.errorMessage = errorMessage;
    }

    public ReturnObject(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public ReturnObject(boolean isSuccess, Object data) {
        this.isSuccess = isSuccess;
        this.data = data;
    }

    public ReturnObject() {
    }

    public boolean isSuccess() {
        return this.isSuccess;
    }

    public void setSuccess(boolean success) {
        this.isSuccess = success;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Object getData() {
        return this.data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}

