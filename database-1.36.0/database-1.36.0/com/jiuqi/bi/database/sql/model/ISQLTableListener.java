/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.database.sql.model;

import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.sql.model.SQLModelException;

public interface ISQLTableListener {
    public void beginPrint(StringBuilder var1, IDatabase var2, int var3) throws SQLModelException;

    public void afterSelect(StringBuilder var1, IDatabase var2, int var3) throws SQLModelException;

    public void afterFrom(StringBuilder var1, IDatabase var2, int var3) throws SQLModelException;

    public void afterWhere(StringBuilder var1, IDatabase var2, int var3) throws SQLModelException;

    public void afterGroupBy(StringBuilder var1, IDatabase var2, int var3) throws SQLModelException;

    public void afterHaving(StringBuilder var1, IDatabase var2, int var3) throws SQLModelException;

    public void endPrint(StringBuilder var1, IDatabase var2, int var3) throws SQLModelException;
}

