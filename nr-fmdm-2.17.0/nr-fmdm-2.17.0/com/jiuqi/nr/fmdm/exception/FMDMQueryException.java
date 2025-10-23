/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.fmdm.exception;

public class FMDMQueryException
extends RuntimeException {
    private static final long serialVersionUID = 8383493701632032118L;

    public FMDMQueryException(String message) {
        super(message);
    }

    public FMDMQueryException(Throwable cause) {
        super(cause);
    }

    public FMDMQueryException(String message, Throwable cause) {
        super(message, cause);
    }
}

