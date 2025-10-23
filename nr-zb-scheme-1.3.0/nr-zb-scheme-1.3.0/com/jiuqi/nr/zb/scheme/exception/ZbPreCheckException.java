/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.zb.scheme.exception;

public class ZbPreCheckException
extends RuntimeException {
    public ZbPreCheckException() {
    }

    public ZbPreCheckException(String message) {
        super(message);
    }

    public ZbPreCheckException(String message, Throwable cause) {
        super(message, cause);
    }

    public ZbPreCheckException(Throwable cause) {
        super(cause);
    }

    public ZbPreCheckException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

