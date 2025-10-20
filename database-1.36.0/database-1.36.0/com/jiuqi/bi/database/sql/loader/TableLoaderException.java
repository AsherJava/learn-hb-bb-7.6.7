/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.database.sql.loader;

import com.jiuqi.bi.database.DBException;

public class TableLoaderException
extends DBException {
    private static final long serialVersionUID = -4756424924276885183L;

    public TableLoaderException() {
    }

    public TableLoaderException(String message) {
        super(message);
    }

    public TableLoaderException(Throwable cause) {
        super(cause);
    }

    public TableLoaderException(String message, Throwable cause) {
        super(message, cause);
    }
}

