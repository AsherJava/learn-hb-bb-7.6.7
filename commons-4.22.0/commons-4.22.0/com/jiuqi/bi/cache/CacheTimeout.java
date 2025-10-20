/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.cache;

public class CacheTimeout
extends Exception {
    private static final long serialVersionUID = -7449799228872812607L;

    public CacheTimeout() {
    }

    public CacheTimeout(String message) {
        super(message);
    }

    public CacheTimeout(Throwable cause) {
        super(cause);
    }

    public CacheTimeout(String message, Throwable cause) {
        super(message, cause);
    }
}

