/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.base.common.jdbc.sqlhandler.impl;

import com.jiuqi.dc.base.common.jdbc.sqlhandler.impl.PostgreSQLSqlHandler;

public class UxDBSqlHandler
extends PostgreSQLSqlHandler {
    @Override
    public String newUUID() {
        return "uuid()";
    }
}

