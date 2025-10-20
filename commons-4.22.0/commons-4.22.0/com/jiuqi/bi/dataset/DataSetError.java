/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.dataset;

public class DataSetError
extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public DataSetError() {
    }

    public DataSetError(String message) {
        super(message);
    }

    public DataSetError(Throwable cause) {
        super(cause);
    }

    public DataSetError(String message, Throwable cause) {
        super(message, cause);
    }
}

