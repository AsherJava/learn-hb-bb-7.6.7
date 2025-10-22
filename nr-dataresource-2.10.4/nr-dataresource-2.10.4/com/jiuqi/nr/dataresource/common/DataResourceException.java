/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataresource.common;

public class DataResourceException
extends RuntimeException {
    private static final long serialVersionUID = -814240088416967267L;

    public DataResourceException() {
        super("\u6570\u636e\u4e0d\u6b63\u786e");
    }

    public DataResourceException(String message) {
        super(message);
    }

    public DataResourceException(Throwable e) {
        super(e.getMessage(), e);
    }

    public DataResourceException(String message, Throwable e) {
        super(message, e);
    }
}

