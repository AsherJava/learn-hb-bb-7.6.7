/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.formula.common.exception;

public class FormulaException
extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public FormulaException() {
    }

    public FormulaException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public FormulaException(String message, Throwable cause) {
        super(message, cause);
    }

    public FormulaException(String message) {
        super(message);
    }

    public FormulaException(Throwable cause) {
        super(cause);
    }
}

