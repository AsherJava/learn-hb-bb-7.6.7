/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datascheme.api.exception;

public class NotFindSchemeDataException
extends RuntimeException {
    private static final long serialVersionUID = -6551108078729129592L;

    public NotFindSchemeDataException() {
        super("\u672a\u627e\u5230\u6570\u636e\u8d44\u6e90");
    }

    public NotFindSchemeDataException(String message) {
        super(message);
    }

    public NotFindSchemeDataException(Throwable e) {
        super(e.getMessage(), e);
    }

    public NotFindSchemeDataException(String message, Throwable e) {
        super(message, e);
    }
}

