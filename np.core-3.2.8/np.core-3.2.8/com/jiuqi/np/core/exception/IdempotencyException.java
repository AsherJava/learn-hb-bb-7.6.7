/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.core.exception;

public class IdempotencyException
extends RuntimeException {
    private static final long serialVersionUID = -8163355221798900510L;

    public IdempotencyException(String message) {
        super(message);
    }
}

