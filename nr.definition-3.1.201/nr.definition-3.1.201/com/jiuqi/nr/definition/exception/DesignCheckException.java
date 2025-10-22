/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.exception;

public class DesignCheckException
extends RuntimeException {
    private static final String EXCEPTION_PREFIX = "\u4efb\u52a1\u8bbe\u8ba1\u53c2\u6570\u6821\u9a8c\u5f02\u5e38: ";

    public DesignCheckException(String message) {
        super(EXCEPTION_PREFIX + message);
    }

    public DesignCheckException(String message, Throwable cause) {
        super(EXCEPTION_PREFIX + message, cause);
    }
}

