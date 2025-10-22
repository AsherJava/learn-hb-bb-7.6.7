/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.snapshot.output;

public class ResultInfo {
    private boolean success;
    private String message;
    private String errorMessage;

    public boolean isSuccess() {
        return this.success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}

