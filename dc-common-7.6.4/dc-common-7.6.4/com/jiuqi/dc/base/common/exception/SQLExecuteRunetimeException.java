/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.base.common.exception;

import java.sql.SQLException;

public class SQLExecuteRunetimeException
extends RuntimeException {
    private static final long serialVersionUID = -6905214814890242891L;
    public final String sql;

    public SQLExecuteRunetimeException(String message, SQLException cause, String sql) {
        super(message, cause);
        this.sql = sql;
    }
}

