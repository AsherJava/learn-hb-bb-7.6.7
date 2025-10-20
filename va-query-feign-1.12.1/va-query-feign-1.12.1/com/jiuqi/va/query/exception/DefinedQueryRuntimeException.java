/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.query.exception;

public class DefinedQueryRuntimeException
extends RuntimeException {
    private static final long serialVersionUID = 2335116333782488370L;

    public DefinedQueryRuntimeException() {
    }

    public DefinedQueryRuntimeException(String message) {
        super(message);
    }

    public DefinedQueryRuntimeException(Throwable cause) {
        super(cause);
    }

    public DefinedQueryRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}

