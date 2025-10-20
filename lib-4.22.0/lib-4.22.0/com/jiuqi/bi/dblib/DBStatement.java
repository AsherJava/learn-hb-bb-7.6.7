/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dblib.DBSQLTrace
 *  com.jiuqi.bi.dblib.DBTrace
 *  com.jiuqi.bi.dblib.TraceSqlEntry
 */
package com.jiuqi.bi.dblib;

import com.jiuqi.bi.dblib.DBResultSet;
import com.jiuqi.bi.dblib.DBSQLTrace;
import com.jiuqi.bi.dblib.DBTrace;
import com.jiuqi.bi.dblib.TraceSqlEntry;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;

public class DBStatement
implements Statement {
    private Statement stmt;
    private Connection conn;

    public DBStatement(Statement aStmt, Connection aConn) {
        this.stmt = aStmt;
        this.conn = aConn;
        DBTrace.createConnection((Object)this);
    }

    @Override
    public ResultSet executeQuery(String string) throws SQLException {
        boolean isSelf = this.stmt instanceof DBStatement;
        try {
            if (isSelf) {
                return this.stmt.executeQuery(string);
            }
            TraceSqlEntry entry = DBSQLTrace.createSqls((String)string);
            try {
                ResultSet rs = this.stmt.executeQuery(string);
                DBSQLTrace.updateSqls((TraceSqlEntry)entry, (int)1, (boolean)true, null);
                return new DBResultSet(this, rs);
            }
            catch (SQLException e) {
                DBSQLTrace.updateSqls((TraceSqlEntry)entry, (int)1, (boolean)false, (Exception)e);
                throw e;
            }
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public int executeUpdate(String string) throws SQLException {
        boolean isSelf = this.stmt instanceof DBStatement;
        try {
            if (isSelf) {
                return this.stmt.executeUpdate(string);
            }
            TraceSqlEntry entry = DBSQLTrace.createSqls((String)string);
            try {
                int result = this.stmt.executeUpdate(string);
                DBSQLTrace.updateSqls((TraceSqlEntry)entry, (int)1, (boolean)true, null);
                return result;
            }
            catch (SQLException e) {
                DBSQLTrace.updateSqls((TraceSqlEntry)entry, (int)1, (boolean)false, (Exception)e);
                throw e;
            }
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public void close() throws SQLException {
        try {
            this.stmt.close();
            DBTrace.closeConnection((Object)this);
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public int getMaxFieldSize() throws SQLException {
        try {
            return this.stmt.getMaxFieldSize();
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public void setMaxFieldSize(int int0) throws SQLException {
        try {
            this.stmt.setMaxFieldSize(int0);
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public int getMaxRows() throws SQLException {
        try {
            return this.stmt.getMaxRows();
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public void setMaxRows(int int0) throws SQLException {
        try {
            this.stmt.setMaxRows(int0);
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public void setEscapeProcessing(boolean boolean0) throws SQLException {
        try {
            this.stmt.setEscapeProcessing(boolean0);
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public int getQueryTimeout() throws SQLException {
        try {
            return this.stmt.getQueryTimeout();
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public void setQueryTimeout(int int0) throws SQLException {
        try {
            this.stmt.setQueryTimeout(int0);
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public void cancel() throws SQLException {
        try {
            this.stmt.cancel();
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public SQLWarning getWarnings() throws SQLException {
        try {
            return this.stmt.getWarnings();
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public void clearWarnings() throws SQLException {
        try {
            this.stmt.clearWarnings();
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public void setCursorName(String string) throws SQLException {
        try {
            this.stmt.setCursorName(string);
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public boolean execute(String string) throws SQLException {
        boolean isSelf = this.stmt instanceof DBStatement;
        try {
            if (isSelf) {
                return this.stmt.execute(string);
            }
            TraceSqlEntry entry = DBSQLTrace.createSqls((String)string);
            try {
                boolean result = this.stmt.execute(string);
                DBSQLTrace.updateSqls((TraceSqlEntry)entry, (int)1, (boolean)true, null);
                return result;
            }
            catch (SQLException e) {
                DBSQLTrace.updateSqls((TraceSqlEntry)entry, (int)1, (boolean)false, (Exception)e);
                throw e;
            }
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public ResultSet getResultSet() throws SQLException {
        try {
            return this.stmt.getResultSet();
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public int getUpdateCount() throws SQLException {
        try {
            return this.stmt.getUpdateCount();
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public boolean getMoreResults() throws SQLException {
        try {
            return this.stmt.getMoreResults();
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public void setFetchDirection(int int0) throws SQLException {
        try {
            this.stmt.setFetchDirection(int0);
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public int getFetchDirection() throws SQLException {
        try {
            return this.stmt.getFetchDirection();
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public void setFetchSize(int int0) throws SQLException {
        try {
            this.stmt.setFetchSize(int0);
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public int getFetchSize() throws SQLException {
        try {
            return this.stmt.getFetchSize();
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public int getResultSetConcurrency() throws SQLException {
        try {
            return this.stmt.getResultSetConcurrency();
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public int getResultSetType() throws SQLException {
        try {
            return this.stmt.getResultSetType();
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public void addBatch(String string) throws SQLException {
        try {
            this.stmt.addBatch(string);
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public void clearBatch() throws SQLException {
        try {
            this.stmt.clearBatch();
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public int[] executeBatch() throws SQLException {
        try {
            return this.stmt.executeBatch();
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public Connection getConnection() throws SQLException {
        return this.conn;
    }

    @Override
    public boolean getMoreResults(int int0) throws SQLException {
        try {
            return this.stmt.getMoreResults(int0);
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public ResultSet getGeneratedKeys() throws SQLException {
        try {
            return this.stmt.getGeneratedKeys();
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public int executeUpdate(String sql, int autoGeneratedKeys) throws SQLException {
        boolean isSelf = this.stmt instanceof DBStatement;
        try {
            if (isSelf) {
                return this.stmt.executeUpdate(sql, autoGeneratedKeys);
            }
            TraceSqlEntry entry = DBSQLTrace.createSqls((String)sql);
            try {
                int result = this.stmt.executeUpdate(sql, autoGeneratedKeys);
                DBSQLTrace.updateSqls((TraceSqlEntry)entry, (int)1, (boolean)true, null);
                return result;
            }
            catch (SQLException e) {
                DBSQLTrace.updateSqls((TraceSqlEntry)entry, (int)1, (boolean)false, (Exception)e);
                throw e;
            }
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public int executeUpdate(String sql, int[] columnIndexes) throws SQLException {
        boolean isSelf = this.stmt instanceof DBStatement;
        try {
            if (isSelf) {
                return this.stmt.executeUpdate(sql, columnIndexes);
            }
            TraceSqlEntry entry = DBSQLTrace.createSqls((String)sql);
            try {
                int result = this.stmt.executeUpdate(sql, columnIndexes);
                DBSQLTrace.updateSqls((TraceSqlEntry)entry, (int)1, (boolean)true, null);
                return result;
            }
            catch (SQLException e) {
                DBSQLTrace.updateSqls((TraceSqlEntry)entry, (int)1, (boolean)false, (Exception)e);
                throw e;
            }
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public int executeUpdate(String sql, String[] columnNames) throws SQLException {
        boolean isSelf = this.stmt instanceof DBStatement;
        try {
            if (isSelf) {
                return this.stmt.executeUpdate(sql, columnNames);
            }
            TraceSqlEntry entry = DBSQLTrace.createSqls((String)sql);
            try {
                int result = this.stmt.executeUpdate(sql, columnNames);
                DBSQLTrace.updateSqls((TraceSqlEntry)entry, (int)1, (boolean)true, null);
                return result;
            }
            catch (SQLException e) {
                DBSQLTrace.updateSqls((TraceSqlEntry)entry, (int)1, (boolean)false, (Exception)e);
                throw e;
            }
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public boolean execute(String sql, int autoGeneratedKeys) throws SQLException {
        boolean isSelf = this.stmt instanceof DBStatement;
        try {
            if (isSelf) {
                return this.stmt.execute(sql, autoGeneratedKeys);
            }
            TraceSqlEntry entry = DBSQLTrace.createSqls((String)sql);
            try {
                boolean result = this.stmt.execute(sql, autoGeneratedKeys);
                DBSQLTrace.updateSqls((TraceSqlEntry)entry, (int)1, (boolean)true, null);
                return result;
            }
            catch (SQLException e) {
                DBSQLTrace.updateSqls((TraceSqlEntry)entry, (int)1, (boolean)false, (Exception)e);
                throw e;
            }
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public boolean execute(String sql, int[] columnIndexes) throws SQLException {
        boolean isSelf = this.stmt instanceof DBStatement;
        try {
            if (isSelf) {
                return this.stmt.execute(sql, columnIndexes);
            }
            TraceSqlEntry entry = DBSQLTrace.createSqls((String)sql);
            try {
                boolean result = this.stmt.execute(sql, columnIndexes);
                DBSQLTrace.updateSqls((TraceSqlEntry)entry, (int)1, (boolean)true, null);
                return result;
            }
            catch (SQLException e) {
                DBSQLTrace.updateSqls((TraceSqlEntry)entry, (int)1, (boolean)false, (Exception)e);
                throw e;
            }
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public boolean execute(String sql, String[] columnNames) throws SQLException {
        boolean isSelf = this.stmt instanceof DBStatement;
        try {
            if (isSelf) {
                return this.stmt.execute(sql, columnNames);
            }
            TraceSqlEntry entry = DBSQLTrace.createSqls((String)sql);
            try {
                boolean result = this.stmt.execute(sql, columnNames);
                DBSQLTrace.updateSqls((TraceSqlEntry)entry, (int)1, (boolean)true, null);
                return result;
            }
            catch (SQLException e) {
                DBSQLTrace.updateSqls((TraceSqlEntry)entry, (int)1, (boolean)false, (Exception)e);
                throw e;
            }
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public int getResultSetHoldability() throws SQLException {
        try {
            return this.stmt.getResultSetHoldability();
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        try {
            return this.stmt.unwrap(iface);
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        try {
            return this.stmt.isWrapperFor(iface);
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public boolean isClosed() throws SQLException {
        try {
            return this.stmt.isClosed();
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public void setPoolable(boolean poolable) throws SQLException {
        try {
            this.stmt.setPoolable(poolable);
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public boolean isPoolable() throws SQLException {
        try {
            return this.stmt.isPoolable();
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public void closeOnCompletion() throws SQLException {
    }

    @Override
    public boolean isCloseOnCompletion() throws SQLException {
        return false;
    }
}

