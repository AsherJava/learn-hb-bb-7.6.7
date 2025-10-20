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
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.NClob;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;

public class DBPreparedStatement
implements PreparedStatement {
    private PreparedStatement stmt;
    private Connection conn;
    private String sqlTemp;

    public DBPreparedStatement(PreparedStatement aStmt, Connection aConn) {
        this.stmt = aStmt;
        this.conn = aConn;
        DBTrace.createConnection((Object)this);
    }

    public DBPreparedStatement(PreparedStatement aStmt, Connection aConn, String sql) {
        this.stmt = aStmt;
        this.conn = aConn;
        this.sqlTemp = sql;
        DBTrace.createConnection((Object)this);
    }

    @Override
    public ResultSet executeQuery(String string) throws SQLException {
        boolean isSelf = this.stmt instanceof DBPreparedStatement;
        try {
            if (isSelf) {
                ResultSet rs = this.stmt.executeQuery(string);
                return new DBResultSet(this, rs);
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
        boolean isSelf = this.stmt instanceof DBPreparedStatement;
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
        boolean isSelf = this.stmt instanceof DBPreparedStatement;
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
        boolean isSelf = this.stmt instanceof DBPreparedStatement;
        try {
            if (isSelf) {
                return this.stmt.executeBatch();
            }
            TraceSqlEntry entry = DBSQLTrace.createSqls((String)this.sqlTemp);
            try {
                int[] result = this.stmt.executeBatch();
                int length = -1;
                if (result != null) {
                    length = result.length;
                }
                DBSQLTrace.updateSqls((TraceSqlEntry)entry, (int)length, (boolean)true, null);
                return result;
            }
            catch (SQLException e) {
                DBSQLTrace.updateSqls((TraceSqlEntry)entry, (int)0, (boolean)false, (Exception)e);
                throw e;
            }
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
    public ResultSet executeQuery() throws SQLException {
        boolean isSelf = this.stmt instanceof DBPreparedStatement;
        try {
            if (isSelf) {
                return this.stmt.executeQuery();
            }
            TraceSqlEntry entry = DBSQLTrace.createSqls((String)this.sqlTemp);
            try {
                ResultSet rs = this.stmt.executeQuery();
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
    public int executeUpdate() throws SQLException {
        boolean isSelf = this.stmt instanceof DBPreparedStatement;
        try {
            if (isSelf) {
                return this.stmt.executeUpdate();
            }
            TraceSqlEntry entry = DBSQLTrace.createSqls((String)this.sqlTemp);
            try {
                int result = this.stmt.executeUpdate();
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
    public void setNull(int int0, int int1) throws SQLException {
        try {
            this.stmt.setNull(int0, int1);
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public void setBoolean(int int0, boolean boolean1) throws SQLException {
        try {
            this.stmt.setBoolean(int0, boolean1);
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public void setByte(int int0, byte byte1) throws SQLException {
        try {
            this.stmt.setByte(int0, byte1);
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public void setShort(int int0, short short1) throws SQLException {
        try {
            this.stmt.setShort(int0, short1);
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public void setInt(int int0, int int1) throws SQLException {
        try {
            this.stmt.setInt(int0, int1);
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public void setLong(int int0, long long1) throws SQLException {
        try {
            this.stmt.setLong(int0, long1);
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public void setFloat(int int0, float float1) throws SQLException {
        try {
            this.stmt.setFloat(int0, float1);
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public void setDouble(int int0, double double1) throws SQLException {
        try {
            this.stmt.setDouble(int0, double1);
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public void setBigDecimal(int int0, BigDecimal bigDecimal) throws SQLException {
        try {
            this.stmt.setBigDecimal(int0, bigDecimal);
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public void setString(int int0, String string) throws SQLException {
        try {
            this.stmt.setString(int0, string);
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void setBytes(int int0, byte[] byteArray) throws SQLException {
        block9: {
            byte[] bytes = byteArray == null ? new byte[]{} : byteArray;
            try {
                if (bytes.length == 0) {
                    this.stmt.setBytes(int0, null);
                    break block9;
                }
                if (bytes.length == 16) {
                    this.stmt.setBytes(int0, byteArray);
                    break block9;
                }
                try (ByteArrayInputStream stream = new ByteArrayInputStream(bytes);){
                    this.stmt.setBinaryStream(int0, (InputStream)stream, ((InputStream)stream).available());
                }
                catch (Exception ex) {
                    throw new SQLException("\u5199\u5165\u6d41\u5931\u8d25" + ex.getMessage());
                }
            }
            catch (RuntimeException e) {
                throw new SQLException(e);
            }
        }
    }

    @Override
    public void setDate(int int0, Date date) throws SQLException {
        try {
            this.stmt.setDate(int0, date);
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public void setTime(int int0, Time time) throws SQLException {
        try {
            this.stmt.setTime(int0, time);
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public void setTimestamp(int int0, Timestamp timestamp) throws SQLException {
        try {
            this.stmt.setTimestamp(int0, timestamp);
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public void setAsciiStream(int int0, InputStream inputStream, int int2) throws SQLException {
        try {
            this.stmt.setAsciiStream(int0, inputStream, int2);
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public void setUnicodeStream(int int0, InputStream inputStream, int int2) throws SQLException {
        this.stmt.setUnicodeStream(int0, inputStream, int2);
    }

    @Override
    public void setBinaryStream(int int0, InputStream inputStream, int int2) throws SQLException {
        try {
            this.stmt.setBinaryStream(int0, inputStream, int2);
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public void clearParameters() throws SQLException {
        try {
            this.stmt.clearParameters();
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public void setObject(int int0, Object object, int int2, int int3) throws SQLException {
        try {
            this.stmt.setObject(int0, object, int2, int3);
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public void setObject(int int0, Object object, int int2) throws SQLException {
        try {
            this.stmt.setObject(int0, object, int2);
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public void setObject(int int0, Object object) throws SQLException {
        try {
            if (object != null && object instanceof byte[]) {
                this.setBytes(int0, (byte[])object);
            } else {
                this.stmt.setObject(int0, object);
            }
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public boolean execute() throws SQLException {
        boolean isSelf = this.stmt instanceof DBPreparedStatement;
        try {
            if (isSelf) {
                return this.stmt.execute();
            }
            TraceSqlEntry entry = DBSQLTrace.createSqls((String)this.sqlTemp);
            try {
                boolean result = this.stmt.execute();
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
    public void addBatch() throws SQLException {
        try {
            this.stmt.addBatch();
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public void setCharacterStream(int int0, Reader reader, int int2) throws SQLException {
        try {
            this.stmt.setCharacterStream(int0, reader, int2);
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public void setRef(int int0, Ref ref) throws SQLException {
        try {
            this.stmt.setRef(int0, ref);
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public void setBlob(int int0, Blob blob) throws SQLException {
        try {
            this.stmt.setBlob(int0, blob);
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public void setClob(int int0, Clob clob) throws SQLException {
        try {
            this.stmt.setClob(int0, clob);
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public void setArray(int int0, Array array) throws SQLException {
        try {
            this.stmt.setArray(int0, array);
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public ResultSetMetaData getMetaData() throws SQLException {
        try {
            return this.stmt.getMetaData();
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public void setDate(int int0, Date date, Calendar calendar) throws SQLException {
        try {
            this.stmt.setDate(int0, date, calendar);
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public void setTime(int int0, Time time, Calendar calendar) throws SQLException {
        try {
            this.stmt.setTime(int0, time, calendar);
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public void setTimestamp(int int0, Timestamp timestamp, Calendar calendar) throws SQLException {
        try {
            this.stmt.setTimestamp(int0, timestamp, calendar);
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public void setNull(int int0, int int1, String string) throws SQLException {
        try {
            this.stmt.setNull(int0, int1, string);
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public void setURL(int parameterIndex, URL x) throws SQLException {
        this.stmt.setURL(parameterIndex, x);
    }

    @Override
    public boolean getMoreResults(int int0) throws SQLException {
        return this.stmt.getMoreResults(int0);
    }

    @Override
    public ResultSet getGeneratedKeys() throws SQLException {
        return this.stmt.getGeneratedKeys();
    }

    @Override
    public int executeUpdate(String sql, int autoGeneratedKeys) throws SQLException {
        boolean isSelf = this.stmt instanceof DBPreparedStatement;
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
        boolean isSelf = this.stmt instanceof DBPreparedStatement;
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
        boolean isSelf = this.stmt instanceof DBPreparedStatement;
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

    @Override
    public boolean execute(String sql, int autoGeneratedKeys) throws SQLException {
        boolean isSelf = this.stmt instanceof DBPreparedStatement;
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
        boolean isSelf = this.stmt instanceof DBPreparedStatement;
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

    @Override
    public boolean execute(String sql, String[] columnNames) throws SQLException {
        boolean isSelf = this.stmt instanceof DBPreparedStatement;
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
    public ParameterMetaData getParameterMetaData() throws SQLException {
        try {
            return this.stmt.getParameterMetaData();
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
    public void setRowId(int parameterIndex, RowId x) throws SQLException {
        try {
            this.stmt.setRowId(parameterIndex, x);
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public void setNString(int parameterIndex, String value) throws SQLException {
        try {
            this.stmt.setNString(parameterIndex, value);
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public void setNCharacterStream(int parameterIndex, Reader value, long length) throws SQLException {
        try {
            this.stmt.setNCharacterStream(parameterIndex, value, length);
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public void setNClob(int parameterIndex, NClob value) throws SQLException {
        try {
            this.stmt.setNClob(parameterIndex, value);
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public void setClob(int parameterIndex, Reader reader, long length) throws SQLException {
        try {
            this.stmt.setClob(parameterIndex, reader, length);
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public void setBlob(int parameterIndex, InputStream inputStream, long length) throws SQLException {
        try {
            this.stmt.setBlob(parameterIndex, inputStream, length);
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public void setNClob(int parameterIndex, Reader reader, long length) throws SQLException {
        try {
            this.stmt.setNClob(parameterIndex, reader, length);
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public void setSQLXML(int parameterIndex, SQLXML xmlObject) throws SQLException {
        try {
            this.stmt.setSQLXML(parameterIndex, xmlObject);
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public void setAsciiStream(int parameterIndex, InputStream x, long length) throws SQLException {
        try {
            this.stmt.setAsciiStream(parameterIndex, x, length);
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public void setBinaryStream(int parameterIndex, InputStream x, long length) throws SQLException {
        try {
            this.stmt.setBinaryStream(parameterIndex, x, length);
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public void setCharacterStream(int parameterIndex, Reader reader, long length) throws SQLException {
        try {
            this.stmt.setCharacterStream(parameterIndex, reader, length);
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public void setAsciiStream(int parameterIndex, InputStream x) throws SQLException {
        try {
            this.stmt.setAsciiStream(parameterIndex, x);
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public void setBinaryStream(int parameterIndex, InputStream x) throws SQLException {
        try {
            this.stmt.setBinaryStream(parameterIndex, x);
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public void setCharacterStream(int parameterIndex, Reader reader) throws SQLException {
        try {
            this.stmt.setCharacterStream(parameterIndex, reader);
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public void setNCharacterStream(int parameterIndex, Reader value) throws SQLException {
        try {
            this.stmt.setNCharacterStream(parameterIndex, value);
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public void setClob(int parameterIndex, Reader reader) throws SQLException {
        try {
            this.stmt.setClob(parameterIndex, reader);
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public void setBlob(int parameterIndex, InputStream inputStream) throws SQLException {
        try {
            this.stmt.setBlob(parameterIndex, inputStream);
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public void setNClob(int parameterIndex, Reader reader) throws SQLException {
        try {
            this.stmt.setNClob(parameterIndex, reader);
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }
}

