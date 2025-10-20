/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.database.temp;

import com.jiuqi.bi.database.temp.ITempTableMeta;
import com.jiuqi.bi.database.temp.TempTable;
import java.sql.Connection;
import java.sql.SQLException;

public interface ITempTableProvider {
    public void registerTempTableMeta(ITempTableMeta var1) throws SQLException;

    public TempTable getOneKeyTempTable(Connection var1) throws SQLException;

    public TempTable getKeyValueTempTable(Connection var1) throws SQLException;

    public TempTable getKeyOrderTempTable(Connection var1) throws SQLException;

    public TempTable getTempTableByType(Connection var1, String var2) throws SQLException;

    public void releaseTempTable(TempTable var1, Connection var2) throws SQLException;

    public TempTable getOneKeyTempTable(String var1) throws SQLException;

    public TempTable getKeyValueTempTable(String var1) throws SQLException;

    public TempTable getKeyOrderTempTable(String var1) throws SQLException;

    public TempTable getTempTableByType(String var1, String var2) throws SQLException;

    public void releaseTempTable(TempTable var1, String var2) throws SQLException;
}

