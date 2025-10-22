/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.entity.exception;

public class NotExistNvwaTableException
extends RuntimeException {
    private static final String EXCEPTION = "\u627e\u4e0d\u5230\u7684\u5973\u5a32\u8868\u5b9a\u4e49\u4fe1\u606f";

    public NotExistNvwaTableException() {
    }

    public NotExistNvwaTableException(String message) {
        super("\u627e\u4e0d\u5230\u7684\u5973\u5a32\u8868\u5b9a\u4e49\u4fe1\u606f:" + message);
    }
}

