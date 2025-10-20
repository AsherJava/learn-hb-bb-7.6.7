/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.sql.DataTypes
 */
package com.jiuqi.gcreport.definition.impl.basic.base.template.wapper;

import com.jiuqi.bi.sql.DataTypes;
import com.jiuqi.gcreport.definition.impl.basic.base.provider.EntityTableDeclarator;
import java.math.BigDecimal;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.springframework.util.ObjectUtils;

public class EntResultSetWrapper {
    private ResultSet rs;

    public EntResultSetWrapper(ResultSet aResultSet) {
        this.rs = aResultSet;
    }

    public boolean next() throws SQLException {
        return this.rs.next();
    }

    public Map<String, Object> getRowData(EntityTableDeclarator<?> tableDeclarator) throws SQLException {
        ResultSetMetaData metaData = this.getResultSet().getMetaData();
        int columnCount = metaData.getColumnCount();
        HashMap<String, Object> result = new HashMap<String, Object>(columnCount);
        for (int i = 1; i <= columnCount; ++i) {
            Class<?> fieldType;
            String columnLabel = metaData.getColumnLabel(i);
            if (ObjectUtils.isEmpty(columnLabel)) {
                columnLabel = metaData.getColumnName(i);
            }
            Class<?> clazz = fieldType = tableDeclarator == null ? null : tableDeclarator.getFieldType(columnLabel);
            if (fieldType == null) {
                result.put(columnLabel.toUpperCase(), this.getFieldValue(i));
                continue;
            }
            result.put(columnLabel.toUpperCase(), this.getObject(i, fieldType));
        }
        return result;
    }

    private Object getFieldValue(int columnIndex) throws SQLException {
        int biType = DataTypes.fromJavaSQLType((ResultSetMetaData)this.rs.getMetaData(), (int)columnIndex);
        switch (biType) {
            case 5: {
                Class<Integer> cls1 = Integer.class;
                return this.getObject(columnIndex, cls1);
            }
            case 10: {
                Class<BigDecimal> cls2 = BigDecimal.class;
                return this.getObject(columnIndex, cls2);
            }
            case 3: {
                Class<Double> cls3 = Double.class;
                return this.getObject(columnIndex, cls3);
            }
            case 1: {
                Class<Boolean> cls4 = Boolean.class;
                return this.getObject(columnIndex, cls4);
            }
            case 9: {
                Class<byte[]> cls5 = byte[].class;
                return this.getObject(columnIndex, cls5);
            }
            case 2: {
                Class<Timestamp> cls6 = Timestamp.class;
                Timestamp timestamp = this.getObject(columnIndex, cls6);
                if (timestamp == null) {
                    return null;
                }
                return new java.util.Date(timestamp.getTime());
            }
            case 33: {
                Class<byte[]> cls7 = byte[].class;
                byte[] object = this.getObject(columnIndex, cls7);
                return this.toUuid(object);
            }
        }
        Class<String> cls8 = String.class;
        return this.getObject(columnIndex, cls8);
    }

    public Object getObject(int columnIndex) throws SQLException {
        return this.rs.getObject(columnIndex);
    }

    public Object getObject(String columnLabel) throws SQLException {
        return this.rs.getObject(columnLabel);
    }

    public <K> K getObject(int columnIndex, Class<K> type) throws SQLException {
        if (type == null) {
            throw new SQLException("Type parameter can not be null");
        }
        if (type.equals(String.class)) {
            return (K)this.rs.getString(columnIndex);
        }
        if (type.equals(BigDecimal.class)) {
            return (K)this.rs.getBigDecimal(columnIndex);
        }
        if (type.equals(Boolean.class) || type.equals(Boolean.TYPE)) {
            return (K)Boolean.valueOf(this.rs.getBoolean(columnIndex));
        }
        if (type.equals(Integer.class) || type.equals(Integer.TYPE)) {
            return (K)Integer.valueOf(this.rs.getInt(columnIndex));
        }
        if (type.equals(Long.class) || type.equals(Long.TYPE)) {
            return (K)Long.valueOf(this.rs.getLong(columnIndex));
        }
        if (type.equals(Float.class) || type.equals(Float.TYPE)) {
            return (K)Float.valueOf(this.rs.getFloat(columnIndex));
        }
        if (type.equals(Double.class) || type.equals(Double.TYPE)) {
            return (K)Double.valueOf(this.rs.getDouble(columnIndex));
        }
        if (type.equals(byte[].class)) {
            try {
                String value = this.rs.getString(columnIndex);
                if (value == null) {
                    return null;
                }
                return (K)value.getBytes(StandardCharsets.UTF_8);
            }
            catch (Exception e1) {
                return null;
            }
        }
        if (type.equals(Date.class)) {
            return (K)this.rs.getDate(columnIndex);
        }
        if (type.equals(java.util.Date.class)) {
            Timestamp timestamp = this.rs.getTimestamp(columnIndex);
            if (timestamp == null) {
                return null;
            }
            return (K)new java.util.Date(timestamp.getTime());
        }
        if (type.equals(Time.class)) {
            return (K)this.rs.getTime(columnIndex);
        }
        if (type.equals(Timestamp.class)) {
            return (K)this.rs.getTimestamp(columnIndex);
        }
        if (type.equals(Clob.class)) {
            return (K)this.rs.getClob(columnIndex);
        }
        if (type.equals(Blob.class)) {
            return (K)this.rs.getBlob(columnIndex);
        }
        if (type.equals(Array.class)) {
            return (K)this.rs.getArray(columnIndex);
        }
        if (type.equals(Ref.class)) {
            return (K)this.rs.getRef(columnIndex);
        }
        if (type.equals(URL.class)) {
            return (K)this.rs.getURL(columnIndex);
        }
        if (type.equals(UUID.class)) {
            return (K)this.toUuid(this.rs.getBytes(columnIndex));
        }
        try {
            return type.cast(this.rs.getObject(columnIndex));
        }
        catch (ClassCastException cce) {
            throw new SQLException("Conversion not supported for type " + type.getName(), cce);
        }
    }

    public <T> T getObject(String columnLabel, Class<T> type) throws SQLException {
        return this.getObject(this.rs.findColumn(columnLabel), type);
    }

    private ResultSet getResultSet() {
        return this.rs;
    }

    private UUID toUuid(byte[] value) {
        long lsb;
        if (value == null) {
            return null;
        }
        int length = value.length;
        if (length == 0) {
            return new UUID(0L, 0L);
        }
        int c = length > 8 ? 8 : length;
        int index = 0;
        long msb = value[index++] & 0xFF;
        while (index < c) {
            msb = msb << 8 | (long)(value[index++] & 0xFF);
        }
        if (index < length) {
            lsb = value[index++] & 0xFF;
            while (index < length) {
                lsb = lsb << 8 | (long)(value[index++] & 0xFF);
            }
        } else {
            lsb = 0L;
        }
        if (msb == 0L && lsb == 0L) {
            return new UUID(0L, 0L);
        }
        return new UUID(msb, lsb);
    }
}

