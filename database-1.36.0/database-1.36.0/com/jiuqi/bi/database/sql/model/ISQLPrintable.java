/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.database.sql.model;

import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.sql.model.SQLModelException;

public interface ISQLPrintable {
    public static final int SQL_NO_ALIAS = 1;
    public static final int SQL_NO_PREFIX = 2;

    public void toSQL(StringBuilder var1, IDatabase var2, int var3) throws SQLModelException;

    public String toSQL(IDatabase var1, int var2) throws SQLModelException;
}

