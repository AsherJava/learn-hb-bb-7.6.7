/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.grid;

public class GridError
extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public GridError() {
    }

    public GridError(String message) {
        super(message);
    }

    public GridError(Throwable cause) {
        super(cause);
    }

    public GridError(String message, Throwable cause) {
        super(message, cause);
    }

    public GridError(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

