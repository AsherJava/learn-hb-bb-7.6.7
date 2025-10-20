/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.base.common.jdbc.sqlhandler.impl;

import com.jiuqi.dc.base.common.jdbc.sqlhandler.impl.PostgreSQLSqlHandler;

public class PolarDBSqlHandler
extends PostgreSQLSqlHandler {
    @Override
    public String multiRowMerge(String field, String separator, boolean distinct) {
        return String.format("STRING_AGG(%s%s, '%s')", distinct ? "DISTINCT " : "", field, separator);
    }
}

