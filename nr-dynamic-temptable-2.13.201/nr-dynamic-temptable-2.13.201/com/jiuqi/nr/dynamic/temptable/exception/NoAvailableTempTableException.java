/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dynamic.temptable.exception;

public class NoAvailableTempTableException
extends RuntimeException {
    public NoAvailableTempTableException() {
        super("No available temp table!");
    }

    public NoAvailableTempTableException(String message) {
        super(message);
    }

    public NoAvailableTempTableException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoAvailableTempTableException(Throwable cause) {
        super(cause);
    }

    public NoAvailableTempTableException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

