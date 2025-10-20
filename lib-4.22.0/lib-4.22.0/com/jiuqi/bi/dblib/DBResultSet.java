/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dblib.DBTrace
 */
package com.jiuqi.bi.dblib;

import com.jiuqi.bi.dblib.DBTrace;
import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.NClob;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Map;

public class DBResultSet
implements ResultSet {
    private static final String NULL_STR = "";
    private ResultSet rs;
    private Statement stmt;

    public DBResultSet(Statement aStmt, ResultSet aResultSet) {
        this.rs = aResultSet;
        this.stmt = aStmt;
        DBTrace.createConnection((Object)this);
    }

    @Override
    public boolean next() throws SQLException {
        try {
            return this.rs.next();
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public void close() throws SQLException {
        try {
            this.rs.close();
            DBTrace.closeConnection((Object)this);
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public boolean wasNull() throws SQLException {
        try {
            return this.rs.wasNull();
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public String getString(int int0) throws SQLException {
        try {
            String value = this.rs.getString(int0);
            return value == null ? NULL_STR : value;
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public boolean getBoolean(int int0) throws SQLException {
        try {
            return this.rs.getBoolean(int0);
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public byte getByte(int int0) throws SQLException {
        try {
            return this.rs.getByte(int0);
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public short getShort(int int0) throws SQLException {
        try {
            return this.rs.getShort(int0);
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public int getInt(int int0) throws SQLException {
        try {
            return this.rs.getInt(int0);
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public long getLong(int int0) throws SQLException {
        try {
            return this.rs.getLong(int0);
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public float getFloat(int int0) throws SQLException {
        try {
            return this.rs.getFloat(int0);
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public double getDouble(int int0) throws SQLException {
        try {
            return this.rs.getDouble(int0);
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public BigDecimal getBigDecimal(int int0, int int1) throws SQLException {
        return this.rs.getBigDecimal(int0, int1);
    }

    @Override
    public byte[] getBytes(int int0) throws SQLException {
        try {
            return this.rs.getBytes(int0);
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public Date getDate(int int0) throws SQLException {
        try {
            return this.rs.getDate(int0);
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public Time getTime(int int0) throws SQLException {
        try {
            return this.rs.getTime(int0);
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public Timestamp getTimestamp(int int0) throws SQLException {
        try {
            return this.rs.getTimestamp(int0);
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public InputStream getAsciiStream(int int0) throws SQLException {
        try {
            return this.rs.getAsciiStream(int0);
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public InputStream getUnicodeStream(int int0) throws SQLException {
        return this.rs.getUnicodeStream(int0);
    }

    @Override
    public InputStream getBinaryStream(int int0) throws SQLException {
        try {
            return this.rs.getBinaryStream(int0);
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public String getString(String string) throws SQLException {
        try {
            String value = this.rs.getString(string);
            return value == null ? NULL_STR : value;
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public boolean getBoolean(String string) throws SQLException {
        try {
            return this.rs.getBoolean(string);
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public byte getByte(String string) throws SQLException {
        try {
            return this.rs.getByte(string);
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public short getShort(String string) throws SQLException {
        try {
            return this.rs.getShort(string);
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public int getInt(String string) throws SQLException {
        try {
            return this.rs.getInt(string);
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public long getLong(String string) throws SQLException {
        try {
            return this.rs.getLong(string);
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public float getFloat(String string) throws SQLException {
        try {
            return this.rs.getFloat(string);
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public double getDouble(String string) throws SQLException {
        try {
            return this.rs.getDouble(string);
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public BigDecimal getBigDecimal(String string, int int1) throws SQLException {
        return this.rs.getBigDecimal(string, int1);
    }

    @Override
    public byte[] getBytes(String string) throws SQLException {
        try {
            return this.rs.getBytes(string);
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public Date getDate(String string) throws SQLException {
        try {
            return this.rs.getDate(string);
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public Time getTime(String string) throws SQLException {
        try {
            return this.rs.getTime(string);
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public Timestamp getTimestamp(String string) throws SQLException {
        try {
            return this.rs.getTimestamp(string);
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public InputStream getAsciiStream(String string) throws SQLException {
        try {
            return this.rs.getAsciiStream(string);
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public InputStream getUnicodeStream(String string) throws SQLException {
        return this.rs.getUnicodeStream(string);
    }

    @Override
    public InputStream getBinaryStream(String string) throws SQLException {
        try {
            return this.rs.getBinaryStream(string);
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public SQLWarning getWarnings() throws SQLException {
        try {
            return this.rs.getWarnings();
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public void clearWarnings() throws SQLException {
        try {
            this.rs.clearWarnings();
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public String getCursorName() throws SQLException {
        try {
            return this.rs.getCursorName();
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public ResultSetMetaData getMetaData() throws SQLException {
        try {
            return this.rs.getMetaData();
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public Object getObject(int int0) throws SQLException {
        try {
            return this.rs.getObject(int0);
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public Object getObject(String string) throws SQLException {
        try {
            return this.rs.getObject(string);
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public int findColumn(String string) throws SQLException {
        try {
            return this.rs.findColumn(string);
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public Reader getCharacterStream(int int0) throws SQLException {
        try {
            return this.rs.getCharacterStream(int0);
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public Reader getCharacterStream(String string) throws SQLException {
        try {
            return this.rs.getCharacterStream(string);
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public BigDecimal getBigDecimal(int int0) throws SQLException {
        try {
            return this.rs.getBigDecimal(int0);
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public BigDecimal getBigDecimal(String string) throws SQLException {
        try {
            return this.rs.getBigDecimal(string);
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public boolean isBeforeFirst() throws SQLException {
        try {
            return this.rs.isBeforeFirst();
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public boolean isAfterLast() throws SQLException {
        try {
            return this.rs.isAfterLast();
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public boolean isFirst() throws SQLException {
        try {
            return this.rs.isFirst();
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public boolean isLast() throws SQLException {
        try {
            return this.rs.isLast();
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public void beforeFirst() throws SQLException {
        try {
            this.rs.beforeFirst();
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public void afterLast() throws SQLException {
        try {
            this.rs.afterLast();
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public boolean first() throws SQLException {
        try {
            return this.rs.first();
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public boolean last() throws SQLException {
        try {
            return this.rs.last();
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public int getRow() throws SQLException {
        try {
            return this.rs.getRow();
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public boolean absolute(int int0) throws SQLException {
        try {
            return this.rs.absolute(int0);
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public boolean relative(int int0) throws SQLException {
        try {
            return this.rs.relative(int0);
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public boolean previous() throws SQLException {
        try {
            return this.rs.previous();
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public void setFetchDirection(int int0) throws SQLException {
        try {
            this.rs.setFetchDirection(int0);
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public int getFetchDirection() throws SQLException {
        try {
            return this.rs.getFetchDirection();
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public void setFetchSize(int int0) throws SQLException {
        try {
            this.rs.setFetchSize(int0);
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public int getFetchSize() throws SQLException {
        try {
            return this.rs.getFetchSize();
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public int getType() throws SQLException {
        try {
            return this.rs.getType();
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public int getConcurrency() throws SQLException {
        try {
            return this.rs.getConcurrency();
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public boolean rowUpdated() throws SQLException {
        try {
            return this.rs.rowUpdated();
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public boolean rowInserted() throws SQLException {
        try {
            return this.rs.rowInserted();
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public boolean rowDeleted() throws SQLException {
        try {
            return this.rs.rowDeleted();
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public void updateNull(int int0) throws SQLException {
        this.rs.updateNull(int0);
    }

    @Override
    public void updateBoolean(int int0, boolean boolean1) throws SQLException {
        this.rs.updateBoolean(int0, boolean1);
    }

    @Override
    public void updateByte(int int0, byte byte1) throws SQLException {
        this.rs.updateByte(int0, byte1);
    }

    @Override
    public void updateShort(int int0, short short1) throws SQLException {
        this.rs.updateShort(int0, short1);
    }

    @Override
    public void updateInt(int int0, int int1) throws SQLException {
        this.rs.updateInt(int0, int1);
    }

    @Override
    public void updateLong(int int0, long long1) throws SQLException {
        this.rs.updateLong(int0, long1);
    }

    @Override
    public void updateFloat(int int0, float float1) throws SQLException {
        this.rs.updateFloat(int0, float1);
    }

    @Override
    public void updateDouble(int int0, double double1) throws SQLException {
        this.rs.updateDouble(int0, double1);
    }

    @Override
    public void updateBigDecimal(int int0, BigDecimal bigDecimal) throws SQLException {
        this.rs.updateBigDecimal(int0, bigDecimal);
    }

    @Override
    public void updateString(int int0, String string) throws SQLException {
        this.rs.updateString(int0, string);
    }

    @Override
    public void updateBytes(int int0, byte[] byteArray) throws SQLException {
        this.rs.updateBytes(int0, byteArray);
    }

    @Override
    public void updateDate(int int0, Date date) throws SQLException {
        this.rs.updateDate(int0, date);
    }

    @Override
    public void updateTime(int int0, Time time) throws SQLException {
        this.rs.updateTime(int0, time);
    }

    @Override
    public void updateTimestamp(int int0, Timestamp timestamp) throws SQLException {
        this.rs.updateTimestamp(int0, timestamp);
    }

    @Override
    public void updateAsciiStream(int int0, InputStream inputStream, int int2) throws SQLException {
        this.rs.updateAsciiStream(int0, inputStream, int2);
    }

    @Override
    public void updateBinaryStream(int int0, InputStream inputStream, int int2) throws SQLException {
        this.rs.updateBinaryStream(int0, inputStream, int2);
    }

    @Override
    public void updateCharacterStream(int int0, Reader reader, int int2) throws SQLException {
        this.rs.updateCharacterStream(int0, reader, int2);
    }

    @Override
    public void updateObject(int int0, Object object, int int2) throws SQLException {
        this.rs.updateObject(int0, object, int2);
    }

    @Override
    public void updateObject(int int0, Object object) throws SQLException {
        this.rs.updateObject(int0, object);
    }

    @Override
    public void updateNull(String string) throws SQLException {
        this.rs.updateNull(string);
    }

    @Override
    public void updateBoolean(String string, boolean boolean1) throws SQLException {
        this.rs.updateBoolean(string, boolean1);
    }

    @Override
    public void updateByte(String string, byte byte1) throws SQLException {
        this.rs.updateByte(string, byte1);
    }

    @Override
    public void updateShort(String string, short short1) throws SQLException {
        this.rs.updateShort(string, short1);
    }

    @Override
    public void updateInt(String string, int int1) throws SQLException {
        this.rs.updateInt(string, int1);
    }

    @Override
    public void updateLong(String string, long long1) throws SQLException {
        this.rs.updateLong(string, long1);
    }

    @Override
    public void updateFloat(String string, float float1) throws SQLException {
        this.rs.updateFloat(string, float1);
    }

    @Override
    public void updateDouble(String string, double double1) throws SQLException {
        this.rs.updateDouble(string, double1);
    }

    @Override
    public void updateBigDecimal(String string, BigDecimal bigDecimal) throws SQLException {
        this.rs.updateBigDecimal(string, bigDecimal);
    }

    @Override
    public void updateString(String string, String string1) throws SQLException {
        this.rs.updateString(string, string1);
    }

    @Override
    public void updateBytes(String string, byte[] byteArray) throws SQLException {
        this.rs.updateBytes(string, byteArray);
    }

    @Override
    public void updateDate(String string, Date date) throws SQLException {
        this.rs.updateDate(string, date);
    }

    @Override
    public void updateTime(String string, Time time) throws SQLException {
        this.rs.updateTime(string, time);
    }

    @Override
    public void updateTimestamp(String string, Timestamp timestamp) throws SQLException {
        this.rs.updateTimestamp(string, timestamp);
    }

    @Override
    public void updateAsciiStream(String string, InputStream inputStream, int int2) throws SQLException {
        this.rs.updateAsciiStream(string, inputStream, int2);
    }

    @Override
    public void updateBinaryStream(String string, InputStream inputStream, int int2) throws SQLException {
        this.rs.updateBinaryStream(string, inputStream, int2);
    }

    @Override
    public void updateCharacterStream(String string, Reader reader, int int2) throws SQLException {
        this.rs.updateCharacterStream(string, reader, int2);
    }

    @Override
    public void updateObject(String string, Object object, int int2) throws SQLException {
        this.rs.updateObject(string, object, int2);
    }

    @Override
    public void updateObject(String string, Object object) throws SQLException {
        this.rs.updateObject(string, object);
    }

    @Override
    public void insertRow() throws SQLException {
        this.rs.insertRow();
    }

    @Override
    public void updateRow() throws SQLException {
        this.rs.updateRow();
    }

    @Override
    public void deleteRow() throws SQLException {
        this.rs.deleteRow();
    }

    @Override
    public void refreshRow() throws SQLException {
        this.rs.refreshRow();
    }

    @Override
    public void cancelRowUpdates() throws SQLException {
        this.rs.cancelRowUpdates();
    }

    @Override
    public void moveToInsertRow() throws SQLException {
        this.rs.moveToInsertRow();
    }

    @Override
    public void moveToCurrentRow() throws SQLException {
        this.rs.moveToCurrentRow();
    }

    @Override
    public Statement getStatement() throws SQLException {
        return this.stmt;
    }

    @Override
    public Ref getRef(int int0) throws SQLException {
        try {
            return this.rs.getRef(int0);
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public Blob getBlob(int int0) throws SQLException {
        try {
            return this.rs.getBlob(int0);
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public Clob getClob(int int0) throws SQLException {
        try {
            return this.rs.getClob(int0);
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public Array getArray(int int0) throws SQLException {
        try {
            return this.rs.getArray(int0);
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public Ref getRef(String string) throws SQLException {
        try {
            return this.rs.getRef(string);
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public Blob getBlob(String string) throws SQLException {
        try {
            return this.rs.getBlob(string);
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public Clob getClob(String string) throws SQLException {
        try {
            return this.rs.getClob(string);
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public Array getArray(String string) throws SQLException {
        try {
            return this.rs.getArray(string);
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public Date getDate(int int0, Calendar calendar) throws SQLException {
        try {
            return this.rs.getDate(int0, calendar);
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public Date getDate(String string, Calendar calendar) throws SQLException {
        try {
            return this.rs.getDate(string, calendar);
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public Time getTime(int int0, Calendar calendar) throws SQLException {
        try {
            return this.rs.getTime(int0, calendar);
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public Time getTime(String string, Calendar calendar) throws SQLException {
        try {
            return this.rs.getTime(string, calendar);
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public Timestamp getTimestamp(int int0, Calendar calendar) throws SQLException {
        try {
            return this.rs.getTimestamp(int0, calendar);
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public Timestamp getTimestamp(String string, Calendar calendar) throws SQLException {
        try {
            return this.rs.getTimestamp(string, calendar);
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public URL getURL(int columnIndex) throws SQLException {
        return this.rs.getURL(columnIndex);
    }

    @Override
    public URL getURL(String columnName) throws SQLException {
        return this.rs.getURL(columnName);
    }

    @Override
    public void updateRef(int columnIndex, Ref x) throws SQLException {
        this.rs.updateRef(columnIndex, x);
    }

    @Override
    public void updateRef(String columnName, Ref x) throws SQLException {
        this.rs.updateRef(columnName, x);
    }

    @Override
    public void updateBlob(int columnIndex, Blob x) throws SQLException {
        this.rs.updateBlob(columnIndex, x);
    }

    @Override
    public void updateBlob(String columnName, Blob x) throws SQLException {
        this.rs.updateBlob(columnName, x);
    }

    @Override
    public void updateClob(int columnIndex, Clob x) throws SQLException {
        this.rs.updateClob(columnIndex, x);
    }

    @Override
    public void updateClob(String columnName, Clob x) throws SQLException {
        this.rs.updateClob(columnName, x);
    }

    @Override
    public void updateArray(int columnIndex, Array x) throws SQLException {
        this.rs.updateArray(columnIndex, x);
    }

    @Override
    public void updateArray(String columnName, Array x) throws SQLException {
        this.rs.updateArray(columnName, x);
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        try {
            return this.rs.unwrap(iface);
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return this.rs.isWrapperFor(iface);
    }

    @Override
    public Object getObject(int columnIndex, Map<String, Class<?>> map) throws SQLException {
        try {
            return this.rs.getObject(columnIndex, map);
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public Object getObject(String columnLabel, Map<String, Class<?>> map) throws SQLException {
        try {
            return this.rs.getObject(columnLabel, map);
        }
        catch (RuntimeException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public RowId getRowId(int columnIndex) throws SQLException {
        return this.rs.getRowId(columnIndex);
    }

    @Override
    public RowId getRowId(String columnLabel) throws SQLException {
        return this.rs.getRowId(columnLabel);
    }

    @Override
    public void updateRowId(int columnIndex, RowId x) throws SQLException {
        this.rs.updateRowId(columnIndex, x);
    }

    @Override
    public void updateRowId(String columnLabel, RowId x) throws SQLException {
        this.rs.updateRowId(columnLabel, x);
    }

    @Override
    public int getHoldability() throws SQLException {
        return this.rs.getHoldability();
    }

    @Override
    public boolean isClosed() throws SQLException {
        return this.rs.isClosed();
    }

    @Override
    public void updateNString(int columnIndex, String nString) throws SQLException {
        this.rs.updateNString(columnIndex, nString);
    }

    @Override
    public void updateNString(String columnLabel, String nString) throws SQLException {
        this.rs.updateNString(columnLabel, nString);
    }

    @Override
    public void updateNClob(int columnIndex, NClob nClob) throws SQLException {
        this.rs.updateNClob(columnIndex, nClob);
    }

    @Override
    public void updateNClob(String columnLabel, NClob nClob) throws SQLException {
        this.rs.updateNClob(columnLabel, nClob);
    }

    @Override
    public NClob getNClob(int columnIndex) throws SQLException {
        return this.rs.getNClob(columnIndex);
    }

    @Override
    public NClob getNClob(String columnLabel) throws SQLException {
        return this.rs.getNClob(columnLabel);
    }

    @Override
    public SQLXML getSQLXML(int columnIndex) throws SQLException {
        return this.rs.getSQLXML(columnIndex);
    }

    @Override
    public SQLXML getSQLXML(String columnLabel) throws SQLException {
        return this.rs.getSQLXML(columnLabel);
    }

    @Override
    public void updateSQLXML(int columnIndex, SQLXML xmlObject) throws SQLException {
        this.rs.updateSQLXML(columnIndex, xmlObject);
    }

    @Override
    public void updateSQLXML(String columnLabel, SQLXML xmlObject) throws SQLException {
        this.rs.updateSQLXML(columnLabel, xmlObject);
    }

    @Override
    public String getNString(int columnIndex) throws SQLException {
        return this.rs.getNString(columnIndex);
    }

    @Override
    public String getNString(String columnLabel) throws SQLException {
        return this.rs.getNString(columnLabel);
    }

    @Override
    public Reader getNCharacterStream(int columnIndex) throws SQLException {
        return this.rs.getNCharacterStream(columnIndex);
    }

    @Override
    public Reader getNCharacterStream(String columnLabel) throws SQLException {
        return this.rs.getNCharacterStream(columnLabel);
    }

    @Override
    public void updateNCharacterStream(int columnIndex, Reader x, long length) throws SQLException {
        this.rs.updateNCharacterStream(columnIndex, x, length);
    }

    @Override
    public void updateNCharacterStream(String columnLabel, Reader reader, long length) throws SQLException {
        this.rs.updateNCharacterStream(columnLabel, reader, length);
    }

    @Override
    public void updateAsciiStream(int columnIndex, InputStream x, long length) throws SQLException {
        this.rs.updateAsciiStream(columnIndex, x, length);
    }

    @Override
    public void updateBinaryStream(int columnIndex, InputStream x, long length) throws SQLException {
        this.rs.updateBinaryStream(columnIndex, x, length);
    }

    @Override
    public void updateCharacterStream(int columnIndex, Reader x, long length) throws SQLException {
        this.rs.updateCharacterStream(columnIndex, x, length);
    }

    @Override
    public void updateAsciiStream(String columnLabel, InputStream x, long length) throws SQLException {
        this.rs.updateAsciiStream(columnLabel, x, length);
    }

    @Override
    public void updateBinaryStream(String columnLabel, InputStream x, long length) throws SQLException {
        this.rs.updateBinaryStream(columnLabel, x, length);
    }

    @Override
    public void updateCharacterStream(String columnLabel, Reader reader, long length) throws SQLException {
        this.rs.updateCharacterStream(columnLabel, reader, length);
    }

    @Override
    public void updateBlob(int columnIndex, InputStream inputStream, long length) throws SQLException {
        this.rs.updateBlob(columnIndex, inputStream, length);
    }

    @Override
    public void updateBlob(String columnLabel, InputStream inputStream, long length) throws SQLException {
        this.rs.updateBlob(columnLabel, inputStream, length);
    }

    @Override
    public void updateClob(int columnIndex, Reader reader, long length) throws SQLException {
        this.rs.updateClob(columnIndex, reader, length);
    }

    @Override
    public void updateClob(String columnLabel, Reader reader, long length) throws SQLException {
        this.rs.updateClob(columnLabel, reader, length);
    }

    @Override
    public void updateNClob(int columnIndex, Reader reader, long length) throws SQLException {
        this.rs.updateNClob(columnIndex, reader, length);
    }

    @Override
    public void updateNClob(String columnLabel, Reader reader, long length) throws SQLException {
        this.rs.updateNClob(columnLabel, reader, length);
    }

    @Override
    public void updateNCharacterStream(int columnIndex, Reader x) throws SQLException {
        this.rs.updateNCharacterStream(columnIndex, x);
    }

    @Override
    public void updateNCharacterStream(String columnLabel, Reader reader) throws SQLException {
        this.rs.updateNCharacterStream(columnLabel, reader);
    }

    @Override
    public void updateAsciiStream(int columnIndex, InputStream x) throws SQLException {
        this.rs.updateAsciiStream(columnIndex, x);
    }

    @Override
    public void updateBinaryStream(int columnIndex, InputStream x) throws SQLException {
        this.rs.updateBinaryStream(columnIndex, x);
    }

    @Override
    public void updateCharacterStream(int columnIndex, Reader x) throws SQLException {
        this.rs.updateCharacterStream(columnIndex, x);
    }

    @Override
    public void updateAsciiStream(String columnLabel, InputStream x) throws SQLException {
        this.rs.updateAsciiStream(columnLabel, x);
    }

    @Override
    public void updateBinaryStream(String columnLabel, InputStream x) throws SQLException {
        this.rs.updateBinaryStream(columnLabel, x);
    }

    @Override
    public void updateCharacterStream(String columnLabel, Reader reader) throws SQLException {
        this.rs.updateCharacterStream(columnLabel, reader);
    }

    @Override
    public void updateBlob(int columnIndex, InputStream inputStream) throws SQLException {
        this.rs.updateBlob(columnIndex, inputStream);
    }

    @Override
    public void updateBlob(String columnLabel, InputStream inputStream) throws SQLException {
        this.rs.updateBlob(columnLabel, inputStream);
    }

    @Override
    public void updateClob(int columnIndex, Reader reader) throws SQLException {
        this.rs.updateClob(columnIndex, reader);
    }

    @Override
    public void updateClob(String columnLabel, Reader reader) throws SQLException {
        this.rs.updateClob(columnLabel, reader);
    }

    @Override
    public void updateNClob(int columnIndex, Reader reader) throws SQLException {
        this.rs.updateNClob(columnIndex, reader);
    }

    @Override
    public void updateNClob(String columnLabel, Reader reader) throws SQLException {
        this.rs.updateNCharacterStream(columnLabel, reader);
    }

    @Override
    public <T> T getObject(int columnIndex, Class<T> type) throws SQLException {
        return null;
    }

    @Override
    public <T> T getObject(String columnLabel, Class<T> type) throws SQLException {
        return null;
    }
}

