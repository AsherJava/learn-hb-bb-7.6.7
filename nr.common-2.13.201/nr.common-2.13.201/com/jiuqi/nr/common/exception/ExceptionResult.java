/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.common.exception;

public class ExceptionResult {
    private String stackTrace;
    private String errorCode;
    private String[] data;

    public ExceptionResult(String errorCode) {
        this.setErrorCode(errorCode);
    }

    public ExceptionResult(String errorCode, String[] data) {
        this.setErrorCode(errorCode);
        this.setData(data);
    }

    public ExceptionResult(String stackTrace, String errorCode, String[] data) {
        this(errorCode, data);
        this.setStackTrace(stackTrace);
    }

    public String getErrorCode() {
        return this.errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String[] getData() {
        return this.data;
    }

    public void setData(String[] data) {
        this.data = data;
    }

    public String getStackTrace() {
        return this.stackTrace;
    }

    public void setStackTrace(String stackTrace) {
        this.stackTrace = stackTrace;
    }
}

