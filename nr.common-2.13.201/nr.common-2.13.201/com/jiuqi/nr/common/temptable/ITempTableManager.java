/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.temp.ITempTableMeta
 */
package com.jiuqi.nr.common.temptable;

import com.jiuqi.bi.database.temp.ITempTableMeta;
import com.jiuqi.nr.common.temptable.BaseTempTableDefine;
import com.jiuqi.nr.common.temptable.ITempTable;
import java.sql.Connection;
import java.sql.SQLException;

public interface ITempTableManager {
    public ITempTable getOneKeyTempTable() throws SQLException;

    public ITempTable getKeyValueTempTable() throws SQLException;

    public ITempTable getKeyOrderTempTable() throws SQLException;

    public ITempTable getTempTableByType(String var1) throws SQLException;

    public ITempTable getTempTableByMeta(ITempTableMeta var1) throws SQLException;

    public ITempTable getTempTableByMeta(BaseTempTableDefine var1) throws SQLException;

    public ITempTable getOneKeyTempTable(Connection var1) throws SQLException;

    public ITempTable getKeyValueTempTable(Connection var1) throws SQLException;

    public ITempTable getKeyOrderTempTable(Connection var1) throws SQLException;

    public ITempTable getTempTableByType(Connection var1, String var2) throws SQLException;

    public ITempTable getTempTableByMeta(Connection var1, ITempTableMeta var2) throws SQLException;

    public ITempTable getTempTableByMeta(Connection var1, BaseTempTableDefine var2) throws SQLException;
}

