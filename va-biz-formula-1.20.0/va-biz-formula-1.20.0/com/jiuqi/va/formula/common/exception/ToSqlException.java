/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.formula.common.exception;

public class ToSqlException
extends Exception {
    private static final long serialVersionUID = 1L;

    public ToSqlException() {
    }

    public ToSqlException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public ToSqlException(String message, Throwable cause) {
        super(message, cause);
    }

    public ToSqlException(String message) {
        super(message);
    }

    public ToSqlException(Throwable cause) {
        super(cause);
    }
}

