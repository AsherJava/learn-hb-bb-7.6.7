/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.office;

public class OfficeError
extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public OfficeError() {
    }

    public OfficeError(String message) {
        super(message);
    }

    public OfficeError(Throwable cause) {
        super(cause);
    }

    public OfficeError(String message, Throwable cause) {
        super(message, cause);
    }

    public OfficeError(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

