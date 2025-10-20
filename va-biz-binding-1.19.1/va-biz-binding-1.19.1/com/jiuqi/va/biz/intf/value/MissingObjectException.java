/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.intf.value;

public class MissingObjectException
extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public MissingObjectException() {
    }

    public MissingObjectException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public MissingObjectException(String message, Throwable cause) {
        super(message, cause);
    }

    public MissingObjectException(String message) {
        super(message);
    }

    public MissingObjectException(Throwable cause) {
        super(cause);
    }
}

