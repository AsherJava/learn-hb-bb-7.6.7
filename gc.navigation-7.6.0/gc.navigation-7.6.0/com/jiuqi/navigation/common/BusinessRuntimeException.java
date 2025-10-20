/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.navigation.common;

public class BusinessRuntimeException
extends RuntimeException {
    private static final long serialVersionUID = -3131398131550932778L;

    public BusinessRuntimeException(String message) {
        super(message);
    }

    public BusinessRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessRuntimeException(Throwable cause) {
        super(cause);
    }
}

