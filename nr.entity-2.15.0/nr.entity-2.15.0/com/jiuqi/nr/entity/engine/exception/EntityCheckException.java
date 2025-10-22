/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.entity.engine.exception;

public class EntityCheckException
extends RuntimeException {
    private static final long serialVersionUID = 5776352120373405023L;

    public EntityCheckException(String message) {
        super(message);
    }

    public EntityCheckException(Throwable cause) {
        super(cause);
    }

    public EntityCheckException(String message, Throwable cause) {
        super(message, cause);
    }
}

