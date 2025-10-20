/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.intf.value;

import com.jiuqi.va.biz.intf.value.Convert;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.UUID;

public enum ValueType {
    AUTO((Class)Object.class, 2000){

        @Override
        public Object getValue(ResultSet rs, int columnIndex) throws SQLException {
            return rs.getObject(columnIndex);
        }
    }
    ,
    BOOLEAN((Class)Boolean.class, 5){

        @Override
        public Object getValue(ResultSet rs, int columnIndex) throws SQLException {
            int result = rs.getInt(columnIndex);
            if (rs.wasNull()) {
                return null;
            }
            return result == 1;
        }
    }
    ,
    BYTE((Class)Byte.class, -6){

        @Override
        public Object getValue(ResultSet rs, int columnIndex) throws SQLException {
            byte result = rs.getByte(columnIndex);
            if (rs.wasNull()) {
                return null;
            }
            return result;
        }
    }
    ,
    SHORT((Class)Short.class, 5){

        @Override
        public Object getValue(ResultSet rs, int columnIndex) throws SQLException {
            short result = rs.getShort(columnIndex);
            if (rs.wasNull()) {
                return null;
            }
            return result;
        }
    }
    ,
    INTEGER((Class)Integer.class, 4){

        @Override
        public Object getValue(ResultSet rs, int columnIndex) throws SQLException {
            int result = rs.getInt(columnIndex);
            if (rs.wasNull()) {
                return null;
            }
            return result;
        }
    }
    ,
    LONG((Class)Long.class, -5){

        @Override
        public Object getValue(ResultSet rs, int columnIndex) throws SQLException {
            long result = rs.getLong(columnIndex);
            if (rs.wasNull()) {
                return null;
            }
            return result;
        }
    }
    ,
    DECIMAL((Class)BigDecimal.class, 3){

        @Override
        public Object getValue(ResultSet rs, int columnIndex) throws SQLException {
            return rs.getBigDecimal(columnIndex);
        }
    }
    ,
    DOUBLE((Class)Double.class, 8){

        @Override
        public Object getValue(ResultSet rs, int columnIndex) throws SQLException {
            double result = rs.getDouble(columnIndex);
            if (rs.wasNull()) {
                return null;
            }
            return result;
        }
    }
    ,
    DATE((Class)Date.class, 91){

        @Override
        public Object getValue(ResultSet rs, int columnIndex) throws SQLException {
            return rs.getDate(columnIndex);
        }
    }
    ,
    DATETIME((Class)Date.class, 93){

        @Override
        public Object getValue(ResultSet rs, int columnIndex) throws SQLException {
            return rs.getTimestamp(columnIndex);
        }
    }
    ,
    TIME((Class)Date.class, 92){

        @Override
        public Object getValue(ResultSet rs, int columnIndex) throws SQLException {
            return rs.getTime(columnIndex);
        }
    }
    ,
    IDENTIFY((Class)UUID.class, 1){

        @Override
        public Object getValue(ResultSet rs, int columnIndex) throws SQLException {
            return rs.getString(columnIndex);
        }
    }
    ,
    STRING((Class)String.class, -9){

        @Override
        public Object getValue(ResultSet rs, int columnIndex) throws SQLException {
            return rs.getString(columnIndex);
        }
    }
    ,
    BINARY((Class)byte[].class, 2004){

        @Override
        public Object getValue(ResultSet rs, int columnIndex) throws SQLException {
            return rs.getBytes(columnIndex);
        }
    }
    ,
    TEXT((Class)String.class, 2005){

        @Override
        public Object getValue(ResultSet rs, int columnIndex) throws SQLException {
            return rs.getString(columnIndex);
        }
    };

    private Class<?> valueClass;
    private int sqlType;

    private ValueType(Class<?> valueClass, int sqlType) {
        this.valueClass = valueClass;
        this.sqlType = sqlType;
    }

    public Class<?> getValueClass() {
        return this.valueClass;
    }

    public int getSqlType() {
        return this.sqlType;
    }

    public Object cast(Object value) {
        return Convert.cast(value, this.valueClass);
    }

    public abstract Object getValue(ResultSet var1, int var2) throws SQLException;
}

