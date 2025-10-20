/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.intf.data;

public class DataException
extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public DataException() {
    }

    public DataException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public DataException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataException(String message) {
        super(message);
    }

    public DataException(Throwable cause) {
        super(cause);
    }
}

