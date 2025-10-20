/*
 * Decompiled with CFR 0.152.
 */
package net.ttddyy.dsproxy.transform;

import java.io.InputStream;
import java.io.Reader;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Date;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.Ref;
import java.sql.RowId;
import java.sql.SQLXML;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Map;
import net.ttddyy.dsproxy.proxy.ParameterKey;
import net.ttddyy.dsproxy.proxy.ParameterSetOperation;

public class ParameterReplacer {
    private Map<ParameterKey, ParameterSetOperation> parameters = new LinkedHashMap<ParameterKey, ParameterSetOperation>();
    private boolean modified = false;

    public ParameterReplacer() {
    }

    public ParameterReplacer(Map<ParameterKey, ParameterSetOperation> parameters) {
        this.parameters.putAll(parameters);
    }

    public <T> T getValue(int index) {
        ParameterKey parameterKey = new ParameterKey(index);
        return (T)this.parameters.get(parameterKey).getArgs()[1];
    }

    public <T> T getValue(String paramName) {
        ParameterKey parameterKey = new ParameterKey(paramName);
        return (T)this.parameters.get(parameterKey).getArgs()[1];
    }

    public void clearParameters() {
        this.parameters.clear();
        this.modified = true;
    }

    private Method getDeclaredMethod(Class<?> clazz, String methodName, Class<?> ... parameterTypes) {
        try {
            return clazz.getDeclaredMethod(methodName, parameterTypes);
        }
        catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    private void record(int parameterIndex, Method paramMethod, Object ... args) {
        ParameterKey parameterKey = new ParameterKey(parameterIndex);
        this.parameters.put(parameterKey, new ParameterSetOperation(paramMethod, args));
        this.modified = true;
    }

    private void recordByName(String parameterName, Method paramMethod, Object ... args) {
        ParameterKey parameterKey = new ParameterKey(parameterName);
        this.parameters.put(parameterKey, new ParameterSetOperation(paramMethod, args));
        this.modified = true;
    }

    public boolean isModified() {
        return this.modified;
    }

    public Map<ParameterKey, ParameterSetOperation> getModifiedParameters() {
        return this.parameters;
    }

    public void setNull(int parameterIndex, int sqlType) {
        this.record(parameterIndex, this.getDeclaredMethod(PreparedStatement.class, "setNull", Integer.TYPE, Integer.TYPE), parameterIndex, sqlType);
    }

    public void setBoolean(int parameterIndex, boolean x) {
        this.record(parameterIndex, this.getDeclaredMethod(PreparedStatement.class, "setBoolean", Integer.TYPE, Boolean.TYPE), parameterIndex, x);
    }

    public void setByte(int parameterIndex, byte x) {
        this.record(parameterIndex, this.getDeclaredMethod(PreparedStatement.class, "setByte", Integer.TYPE, Byte.TYPE), parameterIndex, x);
    }

    public void setShort(int parameterIndex, short x) {
        this.record(parameterIndex, this.getDeclaredMethod(PreparedStatement.class, "setShort", Integer.TYPE, Short.TYPE), parameterIndex, x);
    }

    public void setInt(int parameterIndex, int x) {
        this.record(parameterIndex, this.getDeclaredMethod(PreparedStatement.class, "setInt", Integer.TYPE, Integer.TYPE), parameterIndex, x);
    }

    public void setLong(int parameterIndex, long x) {
        this.record(parameterIndex, this.getDeclaredMethod(PreparedStatement.class, "setLong", Integer.TYPE, Long.TYPE), parameterIndex, x);
    }

    public void setFloat(int parameterIndex, float x) {
        this.record(parameterIndex, this.getDeclaredMethod(PreparedStatement.class, "setFloat", Integer.TYPE, Float.TYPE), parameterIndex, Float.valueOf(x));
    }

    public void setDouble(int parameterIndex, double x) {
        this.record(parameterIndex, this.getDeclaredMethod(PreparedStatement.class, "setDouble", Integer.TYPE, Double.TYPE), parameterIndex, x);
    }

    public void setBigDecimal(int parameterIndex, BigDecimal x) {
        this.record(parameterIndex, this.getDeclaredMethod(PreparedStatement.class, "setBigDecimal", Integer.TYPE, BigDecimal.class), parameterIndex, x);
    }

    public void setString(int parameterIndex, String x) {
        this.record(parameterIndex, this.getDeclaredMethod(PreparedStatement.class, "setString", Integer.TYPE, String.class), parameterIndex, x);
    }

    public void setBytes(int parameterIndex, byte[] x) {
        this.record(parameterIndex, this.getDeclaredMethod(PreparedStatement.class, "setBytes", Integer.TYPE, byte[].class), parameterIndex, x);
    }

    public void setDate(int parameterIndex, Date x) {
        this.record(parameterIndex, this.getDeclaredMethod(PreparedStatement.class, "setDate", Integer.TYPE, Date.class), parameterIndex, x);
    }

    public void setTime(int parameterIndex, Time x) {
        this.record(parameterIndex, this.getDeclaredMethod(PreparedStatement.class, "setTime", Integer.TYPE, Time.class), parameterIndex, x);
    }

    public void setTimestamp(int parameterIndex, Timestamp x) {
        this.record(parameterIndex, this.getDeclaredMethod(PreparedStatement.class, "setTimestamp", Integer.TYPE, Timestamp.class), parameterIndex, x);
    }

    public void setAsciiStream(int parameterIndex, InputStream x, int length) {
        this.record(parameterIndex, this.getDeclaredMethod(PreparedStatement.class, "setAsciiStream", Integer.TYPE, InputStream.class, Integer.TYPE), parameterIndex, x, length);
    }

    public void setUnicodeStream(int parameterIndex, InputStream x, int length) {
        this.record(parameterIndex, this.getDeclaredMethod(PreparedStatement.class, "setUnicodeStream", Integer.TYPE, InputStream.class, Integer.TYPE), parameterIndex, x, length);
    }

    public void setBinaryStream(int parameterIndex, InputStream x, int length) {
        this.record(parameterIndex, this.getDeclaredMethod(PreparedStatement.class, "setBinaryStream", Integer.TYPE, InputStream.class, Integer.TYPE), parameterIndex, x, length);
    }

    public void setObject(int parameterIndex, Object x, int targetSqlType) {
        this.record(parameterIndex, this.getDeclaredMethod(PreparedStatement.class, "setObject", Integer.TYPE, Object.class, Integer.TYPE, Integer.TYPE), parameterIndex, x, targetSqlType);
    }

    public void setObject(int parameterIndex, Object x) {
        this.record(parameterIndex, this.getDeclaredMethod(PreparedStatement.class, "setObject", Integer.TYPE, Object.class), parameterIndex, x);
    }

    public void setCharacterStream(int parameterIndex, Reader reader, int length) {
        this.record(parameterIndex, this.getDeclaredMethod(PreparedStatement.class, "setCharacterStream", Integer.TYPE, Reader.class, Integer.TYPE), parameterIndex, reader, length);
    }

    public void setRef(int parameterIndex, Ref x) {
        this.record(parameterIndex, this.getDeclaredMethod(PreparedStatement.class, "setRef", Integer.TYPE, Ref.class), parameterIndex, x);
    }

    public void setBlob(int parameterIndex, Blob x) {
        this.record(parameterIndex, this.getDeclaredMethod(PreparedStatement.class, "setBlob", Integer.TYPE, Blob.class), parameterIndex, x);
    }

    public void setClob(int parameterIndex, Clob x) {
        this.record(parameterIndex, this.getDeclaredMethod(PreparedStatement.class, "setClob", Integer.TYPE, Clob.class), parameterIndex, x);
    }

    public void setArray(int parameterIndex, Array x) {
        this.record(parameterIndex, this.getDeclaredMethod(PreparedStatement.class, "setArray", Integer.TYPE, Array.class), parameterIndex, x);
    }

    public void setDate(int parameterIndex, Date x, Calendar cal) {
        this.record(parameterIndex, this.getDeclaredMethod(PreparedStatement.class, "setDate", Integer.TYPE, Date.class, Calendar.class), parameterIndex, x, cal);
    }

    public void setTime(int parameterIndex, Time x, Calendar cal) {
        this.record(parameterIndex, this.getDeclaredMethod(PreparedStatement.class, "setTime", Integer.TYPE, Time.class, Calendar.class), parameterIndex, x, cal);
    }

    public void setTimestamp(int parameterIndex, Timestamp x, Calendar cal) {
        this.record(parameterIndex, this.getDeclaredMethod(PreparedStatement.class, "setTimestamp", Integer.TYPE, Timestamp.class, Calendar.class), parameterIndex, x, cal);
    }

    public void setNull(int parameterIndex, int sqlType, String typeName) {
        this.record(parameterIndex, this.getDeclaredMethod(PreparedStatement.class, "setNull", Integer.TYPE, Integer.TYPE, String.class), parameterIndex, sqlType, typeName);
    }

    public void setURL(int parameterIndex, URL x) {
        this.record(parameterIndex, this.getDeclaredMethod(PreparedStatement.class, "setURL", Integer.TYPE, URL.class), parameterIndex, x);
    }

    public void setRowId(int parameterIndex, RowId x) {
        this.record(parameterIndex, this.getDeclaredMethod(PreparedStatement.class, "setRowId", Integer.TYPE, RowId.class), parameterIndex, x);
    }

    public void setNString(int parameterIndex, String value) {
        this.record(parameterIndex, this.getDeclaredMethod(PreparedStatement.class, "setNString", Integer.TYPE, String.class), parameterIndex, value);
    }

    public void setNCharacterStream(int parameterIndex, Reader value, long length) {
        this.record(parameterIndex, this.getDeclaredMethod(PreparedStatement.class, "setNCharacterStream", Integer.TYPE, Reader.class, Long.TYPE), parameterIndex, value, length);
    }

    public void setNClob(int parameterIndex, NClob value) {
        this.record(parameterIndex, this.getDeclaredMethod(PreparedStatement.class, "setNClob", Integer.TYPE, NClob.class), parameterIndex, value);
    }

    public void setClob(int parameterIndex, Reader reader, long length) {
        this.record(parameterIndex, this.getDeclaredMethod(PreparedStatement.class, "setClob", Integer.TYPE, Reader.class, Long.TYPE), parameterIndex, reader, length);
    }

    public void setBlob(int parameterIndex, InputStream inputStream, long length) {
        this.record(parameterIndex, this.getDeclaredMethod(PreparedStatement.class, "setBlob", Integer.TYPE, InputStream.class, Long.TYPE), parameterIndex, inputStream, length);
    }

    public void setNClob(int parameterIndex, Reader reader, long length) {
        this.record(parameterIndex, this.getDeclaredMethod(PreparedStatement.class, "setNClob", Integer.TYPE, Reader.class, Long.TYPE), parameterIndex, reader, length);
    }

    public void setSQLXML(int parameterIndex, SQLXML xmlObject) {
        this.record(parameterIndex, this.getDeclaredMethod(PreparedStatement.class, "setSQLXML", Integer.TYPE, SQLXML.class), parameterIndex, xmlObject);
    }

    public void setObject(int parameterIndex, Object x, int targetSqlType, int scaleOrLength) {
        this.record(parameterIndex, this.getDeclaredMethod(PreparedStatement.class, "setObject", Integer.TYPE, Object.class, Integer.TYPE, Integer.TYPE), parameterIndex, x, targetSqlType, scaleOrLength);
    }

    public void setAsciiStream(int parameterIndex, InputStream x, long length) {
        this.record(parameterIndex, this.getDeclaredMethod(PreparedStatement.class, "setAsciiStream", Integer.TYPE, InputStream.class, Long.TYPE), parameterIndex, x, length);
    }

    public void setBinaryStream(int parameterIndex, InputStream x, long length) {
        this.record(parameterIndex, this.getDeclaredMethod(PreparedStatement.class, "setBinaryStream", Integer.TYPE, InputStream.class, Long.TYPE), parameterIndex, x, length);
    }

    public void setCharacterStream(int parameterIndex, Reader reader, long length) {
        this.record(parameterIndex, this.getDeclaredMethod(PreparedStatement.class, "setCharacterStream", Integer.TYPE, Reader.class, Long.TYPE), parameterIndex, reader, length);
    }

    public void setAsciiStream(int parameterIndex, InputStream x) {
        this.record(parameterIndex, this.getDeclaredMethod(PreparedStatement.class, "setAsciiStream", Integer.TYPE, InputStream.class), parameterIndex, x);
    }

    public void setBinaryStream(int parameterIndex, InputStream x) {
        this.record(parameterIndex, this.getDeclaredMethod(PreparedStatement.class, "setBinaryStream", Integer.TYPE, InputStream.class), parameterIndex, x);
    }

    public void setCharacterStream(int parameterIndex, Reader reader) {
        this.record(parameterIndex, this.getDeclaredMethod(PreparedStatement.class, "setCharacterStream", Integer.TYPE, Reader.class), parameterIndex, reader);
    }

    public void setNCharacterStream(int parameterIndex, Reader value) {
        this.record(parameterIndex, this.getDeclaredMethod(PreparedStatement.class, "setNCharacterStream", Integer.TYPE, Reader.class), parameterIndex, value);
    }

    public void setClob(int parameterIndex, Reader reader) {
        this.record(parameterIndex, this.getDeclaredMethod(PreparedStatement.class, "setClob", Integer.TYPE, Reader.class), parameterIndex, reader);
    }

    public void setBlob(int parameterIndex, InputStream inputStream) {
        this.record(parameterIndex, this.getDeclaredMethod(PreparedStatement.class, "setBlob", Integer.TYPE, InputStream.class), parameterIndex, inputStream);
    }

    public void setNClob(int parameterIndex, Reader reader) {
        this.record(parameterIndex, this.getDeclaredMethod(PreparedStatement.class, "setNClob", Integer.TYPE, Reader.class), parameterIndex, reader);
    }

    public void setNull(String parameterName, int sqlType) {
        this.recordByName(parameterName, this.getDeclaredMethod(CallableStatement.class, "setNull", String.class, Integer.TYPE), parameterName, sqlType);
    }

    public void setBoolean(String parameterName, boolean x) {
        this.recordByName(parameterName, this.getDeclaredMethod(CallableStatement.class, "setBoolean", String.class, Boolean.TYPE), parameterName, x);
    }

    public void setByte(String parameterName, byte x) {
        this.recordByName(parameterName, this.getDeclaredMethod(CallableStatement.class, "setByte", String.class, Byte.TYPE), parameterName, x);
    }

    public void setShort(String parameterName, short x) {
        this.recordByName(parameterName, this.getDeclaredMethod(CallableStatement.class, "setShort", String.class, Short.TYPE), parameterName, x);
    }

    public void setInt(String parameterName, int x) {
        this.recordByName(parameterName, this.getDeclaredMethod(CallableStatement.class, "setInt", String.class, Integer.TYPE), parameterName, x);
    }

    public void setLong(String parameterName, long x) {
        this.recordByName(parameterName, this.getDeclaredMethod(CallableStatement.class, "setLong", String.class, Long.TYPE), parameterName, x);
    }

    public void setFloat(String parameterName, float x) {
        this.recordByName(parameterName, this.getDeclaredMethod(CallableStatement.class, "setFloat", String.class, Float.TYPE), parameterName, Float.valueOf(x));
    }

    public void setDouble(String parameterName, double x) {
        this.recordByName(parameterName, this.getDeclaredMethod(CallableStatement.class, "setDouble", String.class, Double.TYPE), parameterName, x);
    }

    public void setBigDecimal(String parameterName, BigDecimal x) {
        this.recordByName(parameterName, this.getDeclaredMethod(CallableStatement.class, "setBigDecimal", String.class, BigDecimal.class), parameterName, x);
    }

    public void setString(String parameterName, String x) {
        this.recordByName(parameterName, this.getDeclaredMethod(CallableStatement.class, "setString", String.class, String.class), parameterName, x);
    }

    public void setBytes(String parameterName, byte[] x) {
        this.recordByName(parameterName, this.getDeclaredMethod(CallableStatement.class, "setBytes", String.class, byte[].class), parameterName, x);
    }

    public void setDate(String parameterName, Date x) {
        this.recordByName(parameterName, this.getDeclaredMethod(CallableStatement.class, "setDate", String.class, Date.class), parameterName, x);
    }

    public void setTime(String parameterName, Time x) {
        this.recordByName(parameterName, this.getDeclaredMethod(CallableStatement.class, "setTime", String.class, Time.class), parameterName, x);
    }

    public void setTimestamp(String parameterName, Timestamp x) {
        this.recordByName(parameterName, this.getDeclaredMethod(CallableStatement.class, "setTimestamp", String.class, Timestamp.class), parameterName, x);
    }

    public void setAsciiStream(String parameterName, InputStream x, int length) {
        this.recordByName(parameterName, this.getDeclaredMethod(CallableStatement.class, "setAsciiStream", String.class, InputStream.class, Integer.TYPE), parameterName, x, length);
    }

    public void setBinaryStream(String parameterName, InputStream x, int length) {
        this.recordByName(parameterName, this.getDeclaredMethod(CallableStatement.class, "setBinaryStream", String.class, InputStream.class, Integer.TYPE), parameterName, x, length);
    }

    public void setObject(String parameterName, Object x, int targetSqlType, int scale) {
        this.recordByName(parameterName, this.getDeclaredMethod(CallableStatement.class, "setObject", String.class, Object.class, Integer.TYPE, Integer.TYPE), parameterName, x, targetSqlType, scale);
    }

    public void setObject(String parameterName, Object x, int targetSqlType) {
        this.recordByName(parameterName, this.getDeclaredMethod(CallableStatement.class, "setObject", String.class, Object.class, Integer.TYPE), parameterName, x, targetSqlType);
    }

    public void setObject(String parameterName, Object x) {
        this.recordByName(parameterName, this.getDeclaredMethod(CallableStatement.class, "setObject", String.class, Object.class), parameterName, x);
    }

    public void setCharacterStream(String parameterName, Reader reader, int length) {
        this.recordByName(parameterName, this.getDeclaredMethod(CallableStatement.class, "setCharacterStream", String.class, Reader.class, Integer.TYPE), parameterName, reader, length);
    }

    public void setDate(String parameterName, Date x, Calendar cal) {
        this.recordByName(parameterName, this.getDeclaredMethod(CallableStatement.class, "setDate", String.class, Date.class, Calendar.class), parameterName, x, cal);
    }

    public void setTime(String parameterName, Time x, Calendar cal) {
        this.recordByName(parameterName, this.getDeclaredMethod(CallableStatement.class, "setTime", String.class, Time.class, Calendar.class), parameterName, x, cal);
    }

    public void setTimestamp(String parameterName, Timestamp x, Calendar cal) {
        this.recordByName(parameterName, this.getDeclaredMethod(CallableStatement.class, "setTimestamp", String.class, Timestamp.class, Calendar.class), parameterName, x, cal);
    }

    public void setNull(String parameterName, int sqlType, String typeName) {
        this.recordByName(parameterName, this.getDeclaredMethod(CallableStatement.class, "setNull", String.class, Integer.TYPE, String.class), parameterName, sqlType, typeName);
    }

    public void setRowId(String parameterName, RowId x) {
        this.recordByName(parameterName, this.getDeclaredMethod(CallableStatement.class, "setRowId", String.class, RowId.class), parameterName, x);
    }

    public void setNString(String parameterName, String value) {
        this.recordByName(parameterName, this.getDeclaredMethod(CallableStatement.class, "setNString", String.class, String.class), parameterName, value);
    }

    public void setNCharacterStream(String parameterName, Reader value, long length) {
        this.recordByName(parameterName, this.getDeclaredMethod(CallableStatement.class, "setNCharacterStream", String.class, Reader.class, Long.TYPE), parameterName, value, length);
    }

    public void setNClob(String parameterName, NClob value) {
        this.recordByName(parameterName, this.getDeclaredMethod(CallableStatement.class, "setNClob", String.class, NClob.class), parameterName, value);
    }

    public void setClob(String parameterName, Reader reader, long length) {
        this.recordByName(parameterName, this.getDeclaredMethod(CallableStatement.class, "setClob", String.class, Reader.class, Long.TYPE), parameterName, reader, length);
    }

    public void setBlob(String parameterName, InputStream inputStream, long length) {
        this.recordByName(parameterName, this.getDeclaredMethod(CallableStatement.class, "setBlob", String.class, InputStream.class, Long.TYPE), parameterName, inputStream, length);
    }

    public void setNClob(String parameterName, Reader reader, long length) {
        this.recordByName(parameterName, this.getDeclaredMethod(CallableStatement.class, "setNClob", String.class, Reader.class, Long.TYPE), parameterName, reader, length);
    }

    public void setSQLXML(String parameterName, SQLXML xmlObject) {
        this.recordByName(parameterName, this.getDeclaredMethod(CallableStatement.class, "setSQLXML", String.class, SQLXML.class), parameterName, xmlObject);
    }

    public void setBlob(String parameterName, Blob x) {
        this.recordByName(parameterName, this.getDeclaredMethod(CallableStatement.class, "setBlob", String.class, Blob.class), parameterName, x);
    }

    public void setClob(String parameterName, Clob x) {
        this.recordByName(parameterName, this.getDeclaredMethod(CallableStatement.class, "setClob", String.class, Clob.class), parameterName, x);
    }

    public void setAsciiStream(String parameterName, InputStream x, long length) {
        this.recordByName(parameterName, this.getDeclaredMethod(CallableStatement.class, "setAsciiStream", String.class, InputStream.class, Long.TYPE), parameterName, x, length);
    }

    public void setBinaryStream(String parameterName, InputStream x, long length) {
        this.recordByName(parameterName, this.getDeclaredMethod(CallableStatement.class, "setBinaryStream", String.class, InputStream.class), parameterName, x, length);
    }

    public void setCharacterStream(String parameterName, Reader reader, long length) {
        this.recordByName(parameterName, this.getDeclaredMethod(CallableStatement.class, "setCharacterStream", String.class, Reader.class, Long.TYPE), parameterName, reader, length);
    }

    public void setAsciiStream(String parameterName, InputStream x) {
        this.recordByName(parameterName, this.getDeclaredMethod(CallableStatement.class, "setAsciiStream", String.class, InputStream.class), parameterName, x);
    }

    public void setBinaryStream(String parameterName, InputStream x) {
        this.recordByName(parameterName, this.getDeclaredMethod(CallableStatement.class, "setBinaryStream", String.class, InputStream.class), parameterName, x);
    }

    public void setCharacterStream(String parameterName, Reader reader) {
        this.recordByName(parameterName, this.getDeclaredMethod(CallableStatement.class, "setCharacterStream", String.class, Reader.class), parameterName, reader);
    }

    public void setNCharacterStream(String parameterName, Reader value) {
        this.recordByName(parameterName, this.getDeclaredMethod(CallableStatement.class, "setNCharacterStream", String.class, Reader.class), parameterName, value);
    }

    public void setClob(String parameterName, Reader reader) {
        this.recordByName(parameterName, this.getDeclaredMethod(CallableStatement.class, "setClob", String.class, Reader.class), parameterName, reader);
    }

    public void setBlob(String parameterName, InputStream inputStream) {
        this.recordByName(parameterName, this.getDeclaredMethod(CallableStatement.class, "setBlob", String.class, InputStream.class), parameterName, inputStream);
    }

    public void setNClob(String parameterName, Reader reader) {
        this.recordByName(parameterName, this.getDeclaredMethod(CallableStatement.class, "setNClob", String.class, Reader.class), parameterName, reader);
    }

    public void registerOutParameter(int parameterIndex, int sqlType) {
        this.record(parameterIndex, this.getDeclaredMethod(CallableStatement.class, "registerOutParameter", Integer.TYPE, Integer.TYPE), parameterIndex, sqlType);
    }

    public void registerOutParameter(int parameterIndex, int sqlType, int scale) {
        this.record(parameterIndex, this.getDeclaredMethod(CallableStatement.class, "registerOutParameter", Integer.TYPE, Integer.TYPE, Integer.TYPE), parameterIndex, sqlType, scale);
    }

    public void registerOutParameter(int parameterIndex, int sqlType, String typeName) {
        this.record(parameterIndex, this.getDeclaredMethod(CallableStatement.class, "registerOutParameter", Integer.TYPE, Integer.TYPE, Integer.TYPE), parameterIndex, sqlType, typeName);
    }

    public void registerOutParameter(String parameterName, int sqlType) {
        this.recordByName(parameterName, this.getDeclaredMethod(CallableStatement.class, "registerOutParameter", String.class, Integer.TYPE), parameterName, sqlType);
    }

    public void registerOutParameter(String parameterName, int sqlType, int scale) {
        this.recordByName(parameterName, this.getDeclaredMethod(CallableStatement.class, "registerOutParameter", String.class, Integer.TYPE, Integer.TYPE), parameterName, sqlType, scale);
    }

    public void registerOutParameter(String parameterName, int sqlType, String typeName) {
        this.recordByName(parameterName, this.getDeclaredMethod(CallableStatement.class, "registerOutParameter", String.class, Integer.TYPE, String.class), parameterName, sqlType, typeName);
    }
}

