/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.calculate.common;

public class GcConcurrentLogRuntimeException
extends RuntimeException {
    private static final long serialVersionUID = -3131398131550932778L;

    public GcConcurrentLogRuntimeException(String message) {
        super(message);
    }

    public GcConcurrentLogRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public GcConcurrentLogRuntimeException(Throwable cause) {
        super(cause);
    }
}

