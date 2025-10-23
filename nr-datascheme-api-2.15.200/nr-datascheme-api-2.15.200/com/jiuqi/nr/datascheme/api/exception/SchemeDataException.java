/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datascheme.api.exception;

public class SchemeDataException
extends RuntimeException {
    private static final long serialVersionUID = -814240088416967267L;

    public SchemeDataException() {
        super("\u6570\u636e\u4e0d\u6b63\u786e");
    }

    public SchemeDataException(String message) {
        super(message);
    }

    public SchemeDataException(Throwable e) {
        super(e.getMessage(), e);
    }

    public SchemeDataException(String message, Throwable e) {
        super(message, e);
    }
}

