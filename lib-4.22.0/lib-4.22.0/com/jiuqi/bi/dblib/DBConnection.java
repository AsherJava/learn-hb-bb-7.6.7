/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dblib.DBTrace
 *  com.jiuqi.bi.dblib.IConnection
 */
package com.jiuqi.bi.dblib;

import com.jiuqi.bi.dblib.DBPreparedStatement;
import com.jiuqi.bi.dblib.DBStatement;
import com.jiuqi.bi.dblib.DBTrace;
import com.jiuqi.bi.dblib.IConnection;
import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

public class DBConnection
implements Connection {
    private Connection conn;
    private boolean isTransManaged = false;

    public DBConnection(IConnection aConn) {
        this.isTransManaged = false;
        this.conn = aConn.getConnection();
        DBTrace.createConnection((Object)this);
    }

    public DBConnection(Connection aConn) {
        this.isTransManaged = false;
        this.conn = aConn;
        if (!(aConn instanceof DBConnection)) {
            DBTrace.createConnection((Object)this);
        }
    }

    public DBConnection(IConnection aConn, boolean transManaged, boolean autoCommit) throws SQLException {
        this.isTransManaged = transManaged;
        try {
            this.conn = aConn.getConnection();
            this.conn.setAutoCommit(autoCommit);
            DBTrace.createConnection((Object)this);
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    public DBConnection(Connection aConn, boolean transManaged, boolean autoCommit) throws SQLException {
        this.isTransManaged = transManaged;
        try {
            this.conn = aConn;
            this.conn.setAutoCommit(autoCommit);
            DBTrace.createConnection((Object)this);
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public Statement createStatement() throws SQLException {
        try {
            Statement stmt = this.conn.createStatement();
            return new DBStatement(stmt, this);
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public PreparedStatement prepareStatement(String string) throws SQLException {
        try {
            PreparedStatement stmt = this.conn.prepareStatement(string);
            return new DBPreparedStatement(stmt, this, string);
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public CallableStatement prepareCall(String string) throws SQLException {
        try {
            return this.conn.prepareCall(string);
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public String nativeSQL(String string) throws SQLException {
        try {
            return this.conn.nativeSQL(string);
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public void setAutoCommit(boolean boolean0) throws SQLException {
        if (this.isTransManaged) {
            return;
        }
        try {
            this.conn.setAutoCommit(boolean0);
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public boolean getAutoCommit() throws SQLException {
        try {
            return this.conn.getAutoCommit();
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public void commit() throws SQLException {
        if (this.isTransManaged) {
            return;
        }
        try {
            this.conn.commit();
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public void rollback() throws SQLException {
        if (this.isTransManaged) {
            return;
        }
        try {
            this.conn.rollback();
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public void close() throws SQLException {
        try {
            DBTrace.closeConnection((Object)this);
            if (this.isTransManaged) {
                return;
            }
            this.conn.close();
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public boolean isClosed() throws SQLException {
        try {
            return this.conn.isClosed();
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public DatabaseMetaData getMetaData() throws SQLException {
        try {
            return this.conn.getMetaData();
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public void setReadOnly(boolean boolean0) throws SQLException {
        try {
            this.conn.setReadOnly(boolean0);
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public boolean isReadOnly() throws SQLException {
        try {
            return this.conn.isReadOnly();
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public void setCatalog(String string) throws SQLException {
        try {
            this.conn.setCatalog(string);
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public String getCatalog() throws SQLException {
        try {
            return this.conn.getCatalog();
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public void setTransactionIsolation(int int0) throws SQLException {
        try {
            this.conn.setTransactionIsolation(int0);
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public int getTransactionIsolation() throws SQLException {
        try {
            return this.conn.getTransactionIsolation();
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public SQLWarning getWarnings() throws SQLException {
        try {
            return this.conn.getWarnings();
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public void clearWarnings() throws SQLException {
        try {
            this.conn.clearWarnings();
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public Statement createStatement(int int0, int int1) throws SQLException {
        try {
            Statement stmt = this.conn.createStatement(int0, int1);
            return new DBStatement(stmt, this.conn);
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public PreparedStatement prepareStatement(String string, int int1, int int2) throws SQLException {
        try {
            PreparedStatement stmt = this.conn.prepareStatement(string, int1, int2);
            return new DBPreparedStatement(stmt, this, string);
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public CallableStatement prepareCall(String string, int int1, int int2) throws SQLException {
        try {
            return this.conn.prepareCall(string, int1, int2);
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public Map<String, Class<?>> getTypeMap() throws SQLException {
        try {
            return this.conn.getTypeMap();
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public void setHoldability(int holdability) throws SQLException {
        try {
            this.conn.setHoldability(holdability);
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public int getHoldability() throws SQLException {
        try {
            return this.conn.getHoldability();
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public Savepoint setSavepoint() throws SQLException {
        try {
            return this.conn.setSavepoint();
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public Savepoint setSavepoint(String name) throws SQLException {
        try {
            return this.conn.setSavepoint(name);
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public void rollback(Savepoint savepoint) throws SQLException {
        try {
            this.conn.rollback(savepoint);
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public void releaseSavepoint(Savepoint savepoint) throws SQLException {
        try {
            this.conn.releaseSavepoint(savepoint);
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        try {
            Statement stmt = this.conn.createStatement(resultSetType, resultSetConcurrency);
            return new DBStatement(stmt, this);
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        try {
            PreparedStatement stmt = this.conn.prepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
            return new DBPreparedStatement(stmt, this, sql);
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        try {
            return this.conn.prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
        try {
            PreparedStatement stmt = this.conn.prepareStatement(sql, autoGeneratedKeys);
            return new DBPreparedStatement(stmt, this, sql);
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
        try {
            PreparedStatement stmt = this.conn.prepareStatement(sql, columnIndexes);
            return new DBPreparedStatement(stmt, this, sql);
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
        try {
            PreparedStatement stmt = this.conn.prepareStatement(sql, columnNames);
            return new DBPreparedStatement(stmt, this, sql);
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        try {
            return this.conn.unwrap(iface);
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        try {
            return this.conn.isWrapperFor(iface);
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
        try {
            this.conn.setTypeMap(map);
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public Clob createClob() throws SQLException {
        try {
            return this.conn.createClob();
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public Blob createBlob() throws SQLException {
        try {
            return this.conn.createBlob();
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public NClob createNClob() throws SQLException {
        try {
            return this.conn.createNClob();
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public SQLXML createSQLXML() throws SQLException {
        try {
            return this.conn.createSQLXML();
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public boolean isValid(int timeout) throws SQLException {
        try {
            return this.conn.isValid(timeout);
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public void setClientInfo(String name, String value) throws SQLClientInfoException {
        this.conn.setClientInfo(name, value);
    }

    @Override
    public void setClientInfo(Properties properties) throws SQLClientInfoException {
        this.conn.setClientInfo(properties);
    }

    @Override
    public String getClientInfo(String name) throws SQLException {
        return this.conn.getClientInfo(name);
    }

    @Override
    public Properties getClientInfo() throws SQLException {
        return this.conn.getClientInfo();
    }

    @Override
    public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
        return this.conn.createArrayOf(typeName, elements);
    }

    @Override
    public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
        return this.conn.createStruct(typeName, attributes);
    }

    @Override
    public void setSchema(String schema) throws SQLException {
    }

    @Override
    public String getSchema() throws SQLException {
        return null;
    }

    @Override
    public void abort(Executor executor) throws SQLException {
    }

    @Override
    public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {
    }

    @Override
    public int getNetworkTimeout() throws SQLException {
        return 0;
    }

    public Connection getOriginalConnection() {
        if (this.conn instanceof DBConnection) {
            return ((DBConnection)this.conn).getOriginalConnection();
        }
        return this.conn;
    }
}

