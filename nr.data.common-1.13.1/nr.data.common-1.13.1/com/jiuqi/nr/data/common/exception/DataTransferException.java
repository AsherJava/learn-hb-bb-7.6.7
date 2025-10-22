/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.common.exception;

public class DataTransferException
extends Exception {
    public DataTransferException() {
    }

    public DataTransferException(String message) {
        super(message);
    }

    public DataTransferException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataTransferException(Throwable cause) {
        super(cause);
    }

    public DataTransferException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

