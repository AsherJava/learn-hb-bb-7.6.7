/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.temp.ITempTableMeta
 */
package com.jiuqi.nr.common.temptable;

import com.jiuqi.bi.database.temp.ITempTableMeta;
import java.io.Closeable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface ITempTable
extends Closeable {
    public void insertRecords(List<Object[]> var1) throws SQLException;

    public void insertRecords(Connection var1, List<Object[]> var2) throws SQLException;

    public void close(Connection var1) throws SQLException;

    public boolean isClosed() throws SQLException;

    public String getTableName();

    public ITempTableMeta getMeta();

    public String getRealColName(String var1);

    public void deleteAll() throws SQLException;
}

