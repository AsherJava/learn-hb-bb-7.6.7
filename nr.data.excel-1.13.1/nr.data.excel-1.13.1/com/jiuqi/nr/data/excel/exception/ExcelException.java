/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.excel.exception;

public class ExcelException
extends RuntimeException {
    private static final long serialVersionUID = -398750169089524254L;

    public ExcelException(String message) {
        super(message);
    }

    public ExcelException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExcelException(Throwable cause) {
        super(cause);
    }
}

