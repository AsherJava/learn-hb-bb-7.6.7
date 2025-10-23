/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.transmission.data.exception;

public class UserNameAuthException
extends RuntimeException {
    public UserNameAuthException() {
    }

    public UserNameAuthException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserNameAuthException(String userName) {
        super(String.format("\u6267\u884c\u88c5\u5165\u7684\u7528\u6237:[%s]\u6743\u9650\u6821\u9a8c\u5931\u8d25,\u4e0b\u7ea7\u670d\u52a1\u540c\u6b65\u7684\u4efb\u52a1\u4e0d\u662f\u8be5\u670d\u52a1\u7684\u5173\u8054\u7528\u6237\uff0c\u8bf7\u68c0\u67e5\u591a\u7ea7\u90e8\u7f72\u914d\u7f6e", userName));
    }
}

