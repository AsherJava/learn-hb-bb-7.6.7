/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.database.ddl;

import com.jiuqi.bi.database.DBException;

public class DDLException
extends DBException {
    private static final long serialVersionUID = 1L;

    public DDLException() {
    }

    public DDLException(String message) {
        super(message);
    }

    public DDLException(String message, Throwable cause) {
        super(message, cause);
    }

    public DDLException(Throwable cause) {
        super(cause);
    }
}

