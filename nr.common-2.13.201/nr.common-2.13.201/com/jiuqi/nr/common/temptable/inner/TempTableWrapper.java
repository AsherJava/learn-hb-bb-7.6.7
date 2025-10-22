/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.temp.ITempTableMeta
 *  com.jiuqi.bi.database.temp.ITempTableProvider
 *  com.jiuqi.bi.database.temp.TempTable
 *  com.jiuqi.bi.database.temp.TempTableProviderFactory
 *  com.jiuqi.bi.sql.IConnectionProvider
 */
package com.jiuqi.nr.common.temptable.inner;

import com.jiuqi.bi.database.temp.ITempTableMeta;
import com.jiuqi.bi.database.temp.ITempTableProvider;
import com.jiuqi.bi.database.temp.TempTable;
import com.jiuqi.bi.database.temp.TempTableProviderFactory;
import com.jiuqi.bi.sql.IConnectionProvider;
import com.jiuqi.nr.common.temptable.ITempTable;
import com.jiuqi.nr.common.temptable.inner.BaseTempTable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TempTableWrapper
extends BaseTempTable
implements ITempTable {
    private final Logger LOGGER = LoggerFactory.getLogger(TempTableWrapper.class);

    public TempTableWrapper(ITempTableProvider tempTableProvider, TempTable tempTable) {
        super(tempTableProvider, tempTable);
    }

    @Override
    public void insertRecords(List<Object[]> records) throws SQLException {
        try {
            this.tempTable.insertRecords(records);
        }
        catch (SQLException e) {
            throw e;
        }
        catch (Exception e) {
            throw new SQLException(e);
        }
    }

    @Override
    public void insertRecords(Connection connection, List<Object[]> records) throws SQLException {
        this.batchUpdate(connection, this.createInsertSql(), records);
    }

    @Override
    public String getTableName() {
        return this.tempTable.getTableName();
    }

    @Override
    public ITempTableMeta getMeta() {
        return this.tempTable.getMeta();
    }

    @Override
    public void close() throws IOException {
        if (this.closed) {
            return;
        }
        String tableName = this.tempTable.getTableName();
        this.tempTable.close();
        this.closed = true;
        if (this.LOGGER.isDebugEnabled()) {
            this.LOGGER.debug("\u91ca\u653e\u4e34\u65f6\u8868 {}", (Object)tableName);
        }
    }

    @Override
    public void close(Connection connection) throws SQLException {
        if (this.closed) {
            return;
        }
        TempTable tempTable = new TempTable(connection, null, this.tempTable.getTableName(), this.tempTable.getMeta(), this.tempTableProvider);
        String tableName = tempTable.getTableName();
        try {
            tempTable.close();
            this.closed = true;
        }
        catch (IOException e) {
            throw new SQLException(e);
        }
        if (this.LOGGER.isDebugEnabled()) {
            this.LOGGER.debug("\u91ca\u653e\u4e34\u65f6\u8868 {}", (Object)tableName);
        }
    }

    @Override
    public String getRealColName(String colName) {
        return colName;
    }

    @Override
    public void deleteAll() throws SQLException {
        block41: {
            Connection connection = this.tempTable.getConnection();
            if (connection != null && !connection.isClosed()) {
                try (PreparedStatement prep = connection.prepareStatement("DELETE FROM " + this.tempTable.getTableName());){
                    prep.execute();
                    break block41;
                }
                catch (Exception e) {
                    throw new SQLException("\u5220\u9664\u6570\u636e\u5931\u8d25" + e.getMessage(), e);
                }
            }
            IConnectionProvider connectionProvider = TempTableProviderFactory.getInstance().getConnectionProvider();
            try (Connection conn = connectionProvider.openDefault();){
                try (PreparedStatement prep = conn.prepareStatement("DELETE FROM " + this.tempTable.getTableName());){
                    prep.execute();
                }
                catch (Exception e) {
                    throw new SQLException("\u5220\u9664\u6570\u636e\u5931\u8d25" + e.getMessage(), e);
                }
            }
        }
    }
}

