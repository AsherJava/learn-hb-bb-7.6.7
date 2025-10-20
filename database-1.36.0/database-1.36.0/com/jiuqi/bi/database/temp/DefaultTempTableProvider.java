/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.concurrent.Daemon
 *  com.jiuqi.bi.logging.LogManager
 *  com.jiuqi.bi.sql.IConnectionProvider
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.bi.database.temp;

import com.jiuqi.bi.concurrent.Daemon;
import com.jiuqi.bi.database.DatabaseManager;
import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.ddl.DDLException;
import com.jiuqi.bi.database.ddl.DefaultDDLExcecutor;
import com.jiuqi.bi.database.ddl.IDDLExecutor;
import com.jiuqi.bi.database.metadata.LogicField;
import com.jiuqi.bi.database.statement.CreateTableStatement;
import com.jiuqi.bi.database.temp.ITempTableMeta;
import com.jiuqi.bi.database.temp.ITempTableProvider;
import com.jiuqi.bi.database.temp.KeyOrderTempTableMeta;
import com.jiuqi.bi.database.temp.KeyValueTempTableMeta;
import com.jiuqi.bi.database.temp.OneKeyTempTableMeta;
import com.jiuqi.bi.database.temp.TempTable;
import com.jiuqi.bi.database.temp.TempTableProviderFactory;
import com.jiuqi.bi.logging.LogManager;
import com.jiuqi.bi.sql.IConnectionProvider;
import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.bi.util.StringUtils;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultTempTableProvider
implements ITempTableProvider {
    private static final OneKeyTempTableMeta oneKeyTempTableMeta = new OneKeyTempTableMeta();
    private static final KeyValueTempTableMeta keyValueTempTableMeta = new KeyValueTempTableMeta();
    private static final KeyOrderTempTableMeta keyOrderTempTableMeta = new KeyOrderTempTableMeta();
    protected Map<String, ITempTableMeta> metaMap = new ConcurrentHashMap<String, ITempTableMeta>();
    protected Daemon daemon = null;
    protected IDatabase database = null;

    public DefaultTempTableProvider() {
        this.metaMap.put(oneKeyTempTableMeta.getType(), oneKeyTempTableMeta);
        this.metaMap.put(keyValueTempTableMeta.getType(), keyValueTempTableMeta);
        this.metaMap.put(keyOrderTempTableMeta.getType(), keyOrderTempTableMeta);
        String asynDrop = System.getProperty("com.jiuqi.temptable.asynDrop");
        if ("true".equals(asynDrop)) {
            this.daemon = new Daemon("RELEASE-TABLE");
        }
    }

    @Override
    public void registerTempTableMeta(ITempTableMeta meta) throws SQLException {
        if (meta == null || meta.getType() == null) {
            throw new NullPointerException();
        }
        if (this.metaMap.containsKey(meta.getType())) {
            throw new SQLException("\u4e34\u65f6\u8868\u7c7b\u578b\u6807\u8bc6\u5df2\u88ab\u6ce8\u518c!");
        }
        this.metaMap.put(meta.getType(), meta);
    }

    @Override
    public TempTable getOneKeyTempTable(Connection conn) throws SQLException {
        return this.getTempTableByType(conn, oneKeyTempTableMeta.getType());
    }

    @Override
    public TempTable getKeyValueTempTable(Connection conn) throws SQLException {
        return this.getTempTableByType(conn, keyValueTempTableMeta.getType());
    }

    @Override
    public TempTable getKeyOrderTempTable(Connection conn) throws SQLException {
        return this.getTempTableByType(conn, keyOrderTempTableMeta.getType());
    }

    @Override
    public TempTable getTempTableByType(Connection conn, String type) throws SQLException {
        return this.getTempTableByType(conn, null, type);
    }

    protected TempTable getTempTableByType(Connection conn, String connName, String type) throws SQLException {
        ITempTableMeta meta = this.metaMap.get(type);
        if (meta == null) {
            throw new SQLException("\u67e5\u627e\u4e34\u65f6\u8868\u5143\u6570\u636e\u4e0d\u5b58\u5728\uff1a" + type);
        }
        TempTable tempTable = this.createTableByMeta(conn, connName, type, meta);
        return tempTable;
    }

    protected TempTable createTableByMeta(Connection conn, String connName, String type, ITempTableMeta meta) throws SQLException {
        IDatabase database = DatabaseManager.getInstance().findDatabaseByConnection(conn);
        String tableName = this.createTableName(type);
        this.doCreateDBTable(conn, meta, database, tableName);
        TempTable tempTable = new TempTable(connName == null ? conn : null, connName, tableName, meta, this);
        return tempTable;
    }

    protected void doCreateDBTable(Connection conn, ITempTableMeta meta, IDatabase database, String tableName) throws SQLException {
        CreateTableStatement sqlStmt = new CreateTableStatement(null, tableName);
        List<LogicField> fieldList = meta.getLogicFields();
        for (LogicField field : fieldList) {
            sqlStmt.addColumn(field);
        }
        List<String> primaryCols = meta.getPrimaryKeyFields();
        if (primaryCols != null && primaryCols.size() > 0) {
            String primaryKeyName = "PK_".concat(tableName);
            sqlStmt.setPkName(primaryKeyName);
            for (String fieldName : primaryCols) {
                sqlStmt.getPrimaryKeys().add(fieldName);
            }
        }
        try {
            List<String> sqls = sqlStmt.interpret(database, conn);
            for (String sql : sqls) {
                this.executeSql(conn, database, sql);
            }
        }
        catch (Exception e) {
            throw new SQLException(e.getMessage(), e);
        }
    }

    protected void executeSql(Connection conn, IDatabase database, String sql) throws DDLException {
        IDDLExecutor executor = database.createDDLExcecutor(conn);
        executor.execute(sql.toString());
    }

    protected String createTableName(String type) {
        StringBuilder tableName = new StringBuilder("T_");
        if (type.length() <= 10) {
            tableName.append(type.toUpperCase()).append("_");
        }
        tableName.append(OrderGenerator.newOrder());
        SecureRandom rand = new SecureRandom();
        int tableIndex = rand.nextInt(10000);
        tableName.append(String.valueOf(tableIndex));
        return tableName.toString();
    }

    @Override
    public void releaseTempTable(TempTable tempTable, Connection conn) throws SQLException {
        if (this.database == null) {
            this.database = DatabaseManager.getInstance().findDatabaseByConnection(conn);
        }
        if (this.daemon != null) {
            this.daemon.once(() -> {
                try {
                    this.releaseTempTableAsyn(tempTable, null, this.database);
                }
                catch (Exception e) {
                    LogManager.getLogger(DefaultTempTableProvider.class).error(e.getMessage(), (Throwable)e);
                }
            });
        } else {
            this.releaseTempTable(tempTable, conn, this.database);
        }
    }

    @Override
    public TempTable getOneKeyTempTable(String connName) throws SQLException {
        try (Connection connection = TempTableProviderFactory.getInstance().getConnectionProvider().open(connName);){
            TempTable tempTable = this.getTempTableByType(connection, connName, oneKeyTempTableMeta.getType());
            return tempTable;
        }
    }

    @Override
    public TempTable getKeyValueTempTable(String connName) throws SQLException {
        try (Connection connection = TempTableProviderFactory.getInstance().getConnectionProvider().open(connName);){
            TempTable tempTable = this.getTempTableByType(connection, connName, keyValueTempTableMeta.getType());
            return tempTable;
        }
    }

    @Override
    public TempTable getKeyOrderTempTable(String connName) throws SQLException {
        try (Connection connection = TempTableProviderFactory.getInstance().getConnectionProvider().open(connName);){
            TempTable tempTable = this.getTempTableByType(connection, connName, keyOrderTempTableMeta.getType());
            return tempTable;
        }
    }

    @Override
    public TempTable getTempTableByType(String connName, String type) throws SQLException {
        try (Connection connection = TempTableProviderFactory.getInstance().getConnectionProvider().open(connName);){
            TempTable tempTable = this.getTempTableByType(connection, connName, type);
            return tempTable;
        }
    }

    @Override
    public void releaseTempTable(TempTable tempTable, String connName) throws SQLException {
        Throwable throwable;
        Connection connection;
        if (this.database == null) {
            connection = TempTableProviderFactory.getInstance().getConnectionProvider().open(connName);
            throwable = null;
            try {
                this.database = DatabaseManager.getInstance().findDatabaseByConnection(connection);
            }
            catch (Throwable throwable2) {
                throwable = throwable2;
                throw throwable2;
            }
            finally {
                if (connection != null) {
                    if (throwable != null) {
                        try {
                            connection.close();
                        }
                        catch (Throwable throwable3) {
                            throwable.addSuppressed(throwable3);
                        }
                    } else {
                        connection.close();
                    }
                }
            }
        }
        if (this.daemon != null) {
            this.daemon.once(() -> {
                try {
                    this.releaseTempTableAsyn(tempTable, connName, this.database);
                }
                catch (Exception e) {
                    LogManager.getLogger(DefaultTempTableProvider.class).error(e.getMessage(), (Throwable)e);
                }
            });
        } else {
            connection = TempTableProviderFactory.getInstance().getConnectionProvider().open(connName);
            throwable = null;
            try {
                IDatabase connDatabase = DatabaseManager.getInstance().findDatabaseByConnection(connection);
                this.releaseTempTable(tempTable, connection, connDatabase);
            }
            catch (Throwable throwable4) {
                throwable = throwable4;
                throw throwable4;
            }
            finally {
                if (connection != null) {
                    if (throwable != null) {
                        try {
                            connection.close();
                        }
                        catch (Throwable throwable5) {
                            throwable.addSuppressed(throwable5);
                        }
                    } else {
                        connection.close();
                    }
                }
            }
        }
    }

    private void releaseTempTable(TempTable tempTable, Connection conn, IDatabase database) throws SQLException {
        StringBuilder sql = this.buildDropSql(tempTable, database);
        try {
            DefaultDDLExcecutor executor = new DefaultDDLExcecutor(conn, null);
            executor.execute(sql.toString());
        }
        catch (DDLException e) {
            throw new SQLException(e.getMessage(), e);
        }
    }

    private void releaseTempTableAsyn(TempTable tempTable, String connName, IDatabase database) throws SQLException {
        StringBuilder sql = this.buildDropSql(tempTable, database);
        IConnectionProvider connectionProvider = TempTableProviderFactory.getInstance().getConnectionProvider();
        try (Connection connection = StringUtils.isEmpty((String)connName) ? connectionProvider.openDefault() : connectionProvider.open(connName);){
            DefaultDDLExcecutor executor = new DefaultDDLExcecutor(connection, null);
            executor.execute(sql.toString());
        }
        catch (DDLException e) {
            throw new SQLException(e.getMessage(), e);
        }
    }

    private StringBuilder buildDropSql(TempTable tempTable, IDatabase database) {
        StringBuilder sql = new StringBuilder("drop table ");
        sql.append(tempTable.getTableName());
        if (database.isDatabase("ORACLE")) {
            sql.append(" PURGE");
        }
        return sql;
    }
}

