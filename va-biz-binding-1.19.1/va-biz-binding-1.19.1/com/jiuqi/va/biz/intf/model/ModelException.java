/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.intf.model;

public class ModelException
extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ModelException() {
    }

    public ModelException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public ModelException(String message, Throwable cause) {
        super(message, cause);
    }

    public ModelException(String message) {
        super(message);
    }

    public ModelException(Throwable cause) {
        super(cause);
    }
}

