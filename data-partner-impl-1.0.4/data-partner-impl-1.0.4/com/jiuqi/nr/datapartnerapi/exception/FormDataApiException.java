/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datapartnerapi.exception;

public class FormDataApiException
extends RuntimeException {
    public FormDataApiException(String message) {
        super(message);
    }

    public FormDataApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public FormDataApiException(Throwable cause) {
        super(cause);
    }

    protected FormDataApiException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

