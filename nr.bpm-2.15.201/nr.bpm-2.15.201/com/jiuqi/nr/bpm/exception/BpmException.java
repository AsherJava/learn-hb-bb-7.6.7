/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.exception;

public class BpmException
extends RuntimeException {
    private static final long serialVersionUID = 5258486732208838802L;

    public BpmException() {
    }

    public BpmException(String message) {
        super(message);
    }

    public BpmException(String message, Throwable cause) {
        super(message, cause);
    }

    public BpmException(Throwable cause) {
        super(cause);
    }
}

