/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bde.common.exception;

public class BdeRuntimeException
extends RuntimeException {
    private static final long serialVersionUID = -3131398131550932778L;

    public BdeRuntimeException(String message) {
        super(message);
    }

    public BdeRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public BdeRuntimeException(Throwable cause) {
        super(cause);
    }
}

