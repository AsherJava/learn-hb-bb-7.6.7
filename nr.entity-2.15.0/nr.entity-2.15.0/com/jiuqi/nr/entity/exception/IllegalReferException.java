/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.entity.exception;

public class IllegalReferException
extends RuntimeException {
    private static final String EXCEPTION = "\u627e\u4e0d\u5230\u5b9e\u4f53\u5173\u8054\u5bf9\u8c61";

    public IllegalReferException() {
    }

    public IllegalReferException(String message) {
        super("\u627e\u4e0d\u5230\u5b9e\u4f53\u5173\u8054\u5bf9\u8c61:" + message);
    }

    public IllegalReferException(String entityId, String attribute, String refer) {
        super(String.format("\u627e\u4e0d\u5230\u5b9e\u4f53%s\u7684\u5c5e\u6027%s\u5173\u8054\u7684\u5b9e\u4f53\u5bf9\u8c61%s", entityId, attribute, refer));
    }
}

