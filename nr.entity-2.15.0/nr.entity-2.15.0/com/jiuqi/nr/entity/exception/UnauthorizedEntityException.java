/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.entity.exception;

public class UnauthorizedEntityException
extends Exception {
    private static final long serialVersionUID = -2075905822741806750L;
    public static final String UNAUTHORIZED_ENTITY = "\u4e3b\u4f53\u672a\u542f\u7528\u6743\u9650";

    public UnauthorizedEntityException() {
        super(UNAUTHORIZED_ENTITY);
    }

    public UnauthorizedEntityException(String message) {
        super(message);
    }

    public UnauthorizedEntityException(Throwable cause) {
        super(cause);
    }
}

