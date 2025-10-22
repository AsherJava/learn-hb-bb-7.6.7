/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.etl.common;

public class EtlExecuteInfo {
    private String errorMessage;
    private String detailMessage;
    private String result;
    private int statusCode;

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getDetailMessage() {
        return this.detailMessage;
    }

    public void setDetailMessage(String detailMessage) {
        this.detailMessage = detailMessage;
    }

    public String getResult() {
        return this.result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getStatusCode() {
        return this.statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}

