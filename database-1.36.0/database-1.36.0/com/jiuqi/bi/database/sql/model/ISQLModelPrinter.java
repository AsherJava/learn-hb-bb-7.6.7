/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.database.sql.model;

import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.sql.model.ISQLPrintable;
import com.jiuqi.bi.database.sql.model.SQLModelException;

public interface ISQLModelPrinter {
    public void printSQL(StringBuilder var1, IDatabase var2, ISQLPrintable var3, int var4) throws SQLModelException;
}

