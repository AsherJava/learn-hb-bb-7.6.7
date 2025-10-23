/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.fmdm.exception;

import java.io.Serializable;

public final class ColumnMissException
extends RuntimeException
implements Serializable {
    private static final long serialVersionUID = -4760335476375889452L;
    public static final String INVALID_FIND_ERROR = "\u627e\u4e0d\u5230\u7684\u5c5e\u6027";

    public ColumnMissException() {
        super(INVALID_FIND_ERROR);
    }

    public ColumnMissException(String arg0) {
        super(arg0);
    }

    public ColumnMissException(Exception ex) {
        super(ex);
    }

    public ColumnMissException(String message, Throwable cause) {
        super(message, cause);
    }
}

