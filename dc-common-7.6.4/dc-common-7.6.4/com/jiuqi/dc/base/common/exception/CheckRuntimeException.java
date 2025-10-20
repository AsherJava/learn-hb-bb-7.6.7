/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.base.common.exception;

public class CheckRuntimeException
extends RuntimeException {
    private static final long serialVersionUID = 5983217969550330859L;

    public CheckRuntimeException(String message) {
        super(message);
    }

    public CheckRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public CheckRuntimeException(Throwable cause) {
        super(cause);
    }
}

