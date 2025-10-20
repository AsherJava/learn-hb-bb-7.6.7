/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.database.sql.model;

import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.sql.model.ISQLTableListener;
import com.jiuqi.bi.database.sql.model.SQLModelException;

public class SQLTableListener
implements ISQLTableListener {
    @Override
    public void beginPrint(StringBuilder buffer, IDatabase database, int options) throws SQLModelException {
    }

    @Override
    public void afterSelect(StringBuilder buffer, IDatabase database, int options) throws SQLModelException {
    }

    @Override
    public void afterFrom(StringBuilder buffer, IDatabase database, int options) throws SQLModelException {
    }

    @Override
    public void afterWhere(StringBuilder buffer, IDatabase database, int options) throws SQLModelException {
    }

    @Override
    public void afterGroupBy(StringBuilder buffer, IDatabase database, int options) throws SQLModelException {
    }

    @Override
    public void afterHaving(StringBuilder buffer, IDatabase database, int options) throws SQLModelException {
    }

    @Override
    public void endPrint(StringBuilder buffer, IDatabase database, int options) throws SQLModelException {
    }
}

