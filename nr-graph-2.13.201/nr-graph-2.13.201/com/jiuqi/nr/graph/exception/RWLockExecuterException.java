/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.graph.exception;

public class RWLockExecuterException
extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public RWLockExecuterException(String message) {
        super(message);
    }

    public RWLockExecuterException(Exception e) {
        super(e);
    }
}

