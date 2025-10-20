/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.sql.IConnectionProvider
 */
package com.jiuqi.bi.database.temp;

import com.jiuqi.bi.database.metadata.LogicField;
import com.jiuqi.bi.database.temp.ITempTableMeta;
import com.jiuqi.bi.database.temp.ITempTableProvider;
import com.jiuqi.bi.database.temp.TempTableProviderFactory;
import com.jiuqi.bi.sql.IConnectionProvider;
import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class TempTable
implements Closeable {
    private Connection connection;
    private String tableName;
    private ITempTableMeta meta;
    private ITempTableProvider provider;
    private String connName = null;
    private boolean released = false;

    public TempTable(Connection connection, String connName, String tableName, ITempTableMeta meta, ITempTableProvider provider) {
        this.connection = connection;
        this.tableName = tableName;
        this.meta = meta;
        this.provider = provider;
        this.connName = connName;
    }

    public void insertRecords(List<Object[]> records) throws Exception {
        String insertSql = this.createInsertSql();
        if (this.connection == null || this.connection.isClosed()) {
            IConnectionProvider connectionProvider = TempTableProviderFactory.getInstance().getConnectionProvider();
            try (Connection conn = this.connName == null ? connectionProvider.openDefault() : connectionProvider.open(this.connName);){
                this.batchUpdate(conn, insertSql, records);
            }
        } else {
            this.batchUpdate(this.connection, insertSql, records);
        }
    }

    private String createInsertSql() {
        StringBuilder insertSql = new StringBuilder();
        insertSql.append("insert into ").append(this.tableName).append(" (");
        for (LogicField field : this.meta.getLogicFields()) {
            insertSql.append(field.getFieldName()).append(",");
        }
        insertSql.setLength(insertSql.length() - 1);
        insertSql.append(") values (");
        for (int i = 0; i < this.meta.getLogicFields().size(); ++i) {
            insertSql.append("?,");
        }
        insertSql.setLength(insertSql.length() - 1);
        insertSql.append(")");
        return insertSql.toString();
    }

    private void batchUpdate(Connection conn, String sql, List<Object[]> batchValues) throws SQLException {
        try (PreparedStatement prep = null;){
            prep = conn.prepareStatement(sql);
            int batchSize = 10000;
            int count = 0;
            for (Object[] batchValue : batchValues) {
                for (int i = 0; i < batchValue.length; ++i) {
                    prep.setObject(i + 1, batchValue[i]);
                }
                prep.addBatch();
                if (++count % 10000 != 0) continue;
                prep.executeBatch();
            }
            prep.executeBatch();
        }
    }

    @Override
    public void close() throws IOException {
        if (!this.released) {
            try {
                if (this.connection == null || this.connection.isClosed()) {
                    IConnectionProvider connectionProvider = TempTableProviderFactory.getInstance().getConnectionProvider();
                    try (Connection conn = this.connName == null ? connectionProvider.openDefault() : connectionProvider.open(this.connName);){
                        this.provider.releaseTempTable(this, conn);
                    }
                } else {
                    this.provider.releaseTempTable(this, this.connection);
                }
                this.released = true;
            }
            catch (Exception e) {
                throw new IOException(e.getMessage(), e);
            }
        }
    }

    public Connection getConnection() {
        return this.connection;
    }

    public String getTableName() {
        return this.tableName;
    }

    public ITempTableMeta getMeta() {
        return this.meta;
    }
}

