/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.entity.exception;

public class NotExistNvwaColumnException
extends RuntimeException {
    private static final String EXCEPTION = "\u627e\u4e0d\u5230\u7684\u5973\u5a32\u5217\u4fe1\u606f";

    public NotExistNvwaColumnException() {
    }

    public NotExistNvwaColumnException(String message) {
        super("\u627e\u4e0d\u5230\u7684\u5973\u5a32\u5217\u4fe1\u606f:" + message);
    }
}

