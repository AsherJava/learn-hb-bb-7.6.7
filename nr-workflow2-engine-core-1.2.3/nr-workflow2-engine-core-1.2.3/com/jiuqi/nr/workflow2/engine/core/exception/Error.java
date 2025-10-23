/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.engine.core.exception;

import com.jiuqi.nr.workflow2.engine.core.exception.ErrorCode;

public abstract class Error {
    private final ErrorCode errorCode;
    private final Object errorData;
    public static final Error NOERROR = new ErrorWithoutData(ErrorCode.UNKNOW);

    protected Error(ErrorCode errorCode, Object errorData) {
        this.errorCode = errorCode;
        this.errorData = errorData;
    }

    public ErrorCode getErrorCode() {
        return this.errorCode;
    }

    public Object getErrorData() {
        return this.errorData;
    }

    public static Error fromErrorCode(ErrorCode errorCode) {
        return new ErrorWithoutData(errorCode);
    }

    public static class ErrorWithoutData
    extends Error {
        protected ErrorWithoutData(ErrorCode errorCode) {
            super(errorCode, null);
        }
    }
}

