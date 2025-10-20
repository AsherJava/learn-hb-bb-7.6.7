/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.oauth2.exception;

public class UserNotFoundException
extends RuntimeException {
    private static final long serialVersionUID = 4868144242930662197L;

    public UserNotFoundException() {
    }

    public UserNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(Throwable cause) {
        super(cause);
    }
}

