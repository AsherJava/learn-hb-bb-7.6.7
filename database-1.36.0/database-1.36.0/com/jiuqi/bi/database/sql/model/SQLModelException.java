/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.database.sql.model;

import com.jiuqi.bi.database.DBException;

public class SQLModelException
extends DBException {
    private static final long serialVersionUID = -3414342691141794087L;

    public SQLModelException() {
    }

    public SQLModelException(Throwable cause) {
        super(cause);
    }

    public SQLModelException(String message) {
        super(message);
    }

    public SQLModelException(String message, Throwable cause) {
        super(message, cause);
    }
}

