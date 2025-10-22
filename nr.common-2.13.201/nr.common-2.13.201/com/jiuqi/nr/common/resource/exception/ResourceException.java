/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.common.resource.exception;

public class ResourceException
extends RuntimeException {
    private static final long serialVersionUID = -7841427099129882004L;

    public ResourceException() {
    }

    public ResourceException(String message) {
        super(message);
    }

    public ResourceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResourceException(Throwable cause) {
        super(cause);
    }
}

