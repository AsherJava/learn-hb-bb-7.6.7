/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.util;

public class NpRollbackException
extends Exception {
    private static final long serialVersionUID = -1644541448896138914L;

    public NpRollbackException() {
    }

    public NpRollbackException(String message) {
        super(message);
    }

    public NpRollbackException(String message, Throwable cause) {
        super(message, cause);
    }

    public NpRollbackException(Throwable cause) {
        super(cause);
    }

    protected NpRollbackException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

