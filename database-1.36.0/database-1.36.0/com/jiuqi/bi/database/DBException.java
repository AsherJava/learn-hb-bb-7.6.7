/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.database;

public class DBException
extends Exception {
    private static final long serialVersionUID = 4962362273178414448L;

    public DBException() {
    }

    public DBException(String message) {
        super(message);
    }

    public DBException(String message, Throwable cause) {
        super(message, cause);
    }

    public DBException(Throwable cause) {
        super(cause);
    }
}

