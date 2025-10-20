/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.bde.fetch.impl.automation.intf;

public class FetchAutomationResult {
    private boolean success;
    private String message;
    private String errorMessage;

    public FetchAutomationResult success() {
        this.success = true;
        return this;
    }

    public FetchAutomationResult success(String message) {
        this.success = true;
        this.message = message;
        return this;
    }

    public FetchAutomationResult failure(String errorMessage) {
        this.success = false;
        this.errorMessage = errorMessage;
        return this;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public String getMessage() {
        return this.message;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public String toString() {
        return "FetchAutomationResult [success=" + this.success + ", message=" + this.message + ", errorMessage=" + this.errorMessage + "]";
    }
}

