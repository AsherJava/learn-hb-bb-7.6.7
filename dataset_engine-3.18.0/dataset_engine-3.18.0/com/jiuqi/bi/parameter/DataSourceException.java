/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.parameter;

public class DataSourceException
extends Exception {
    private static final long serialVersionUID = 7494105435293899824L;

    public DataSourceException() {
    }

    public DataSourceException(Throwable cause) {
        super(cause.getMessage(), cause);
    }

    public DataSourceException(String msg) {
        super(msg);
    }

    public DataSourceException(String msg, Throwable cause) {
        super(msg, cause);
    }
}

