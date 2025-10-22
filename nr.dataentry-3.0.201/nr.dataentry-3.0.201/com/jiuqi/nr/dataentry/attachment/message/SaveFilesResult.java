/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataentry.attachment.message;

public class SaveFilesResult {
    private boolean result;
    private String message;
    private String errors;

    public SaveFilesResult(boolean result, String message, String errors) {
        this.result = result;
        this.message = message;
        this.errors = errors;
    }

    public boolean isResult() {
        return this.result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getErrors() {
        return this.errors;
    }

    public void setErrors(String errors) {
        this.errors = errors;
    }
}

