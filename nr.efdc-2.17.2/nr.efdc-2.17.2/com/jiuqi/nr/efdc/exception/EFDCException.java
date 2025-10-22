/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.efdc.exception;

public class EFDCException
extends RuntimeException {
    private static final long serialVersionUID = 7417994670644449089L;

    public EFDCException() {
    }

    public EFDCException(String message) {
        super(message);
    }

    public EFDCException(String message, Throwable cause) {
        super(message, cause);
    }

    public EFDCException(Throwable cause) {
        super(cause);
    }
}

