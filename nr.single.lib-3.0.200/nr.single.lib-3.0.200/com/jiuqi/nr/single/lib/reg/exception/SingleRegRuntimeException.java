/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.single.lib.reg.exception;

public class SingleRegRuntimeException
extends RuntimeException {
    private static final long serialVersionUID = 5525702168988518380L;

    public SingleRegRuntimeException() {
    }

    public SingleRegRuntimeException(String message) {
        super(message);
    }

    public SingleRegRuntimeException(Throwable cause) {
        super(cause);
    }

    public SingleRegRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}

