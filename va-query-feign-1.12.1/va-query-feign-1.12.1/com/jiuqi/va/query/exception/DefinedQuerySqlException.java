/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.query.exception;

public class DefinedQuerySqlException
extends RuntimeException {
    private static final long serialVersionUID = 2335116323782488370L;

    public DefinedQuerySqlException() {
    }

    public DefinedQuerySqlException(Throwable cause) {
        super(cause);
    }

    public DefinedQuerySqlException(String message) {
        super(message);
    }

    public DefinedQuerySqlException(String message, Throwable cause) {
        super(message, cause);
    }

    protected DefinedQuerySqlException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

