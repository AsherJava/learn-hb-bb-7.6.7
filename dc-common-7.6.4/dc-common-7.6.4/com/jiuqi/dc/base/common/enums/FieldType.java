/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.StringUtils
 */
package com.jiuqi.dc.base.common.enums;

import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.StringUtils;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

public enum FieldType {
    STRING("\u5b57\u7b26\u578b", new int[]{1, 12, -1}){

        @Override
        public void setArgumentValue(PreparedStatement pstmt, int parameterIndex, Object argValue) throws SQLException {
            if (StringUtils.isNull((String)String.valueOf(argValue))) {
                pstmt.setObject(parameterIndex, null);
                return;
            }
            pstmt.setString(parameterIndex, argValue.toString());
        }

        @Override
        public Object getFieldValue(ResultSet rs, int columnIndex) throws SQLException {
            if (rs.getObject(columnIndex) == null) {
                return null;
            }
            return rs.getString(columnIndex);
        }
    }
    ,
    NUMERIC("\u6570\u503c\u578b", new int[]{2}){

        @Override
        public void setArgumentValue(PreparedStatement pstmt, int parameterIndex, Object argValue) throws SQLException {
            if (StringUtils.isNull((String)String.valueOf(argValue))) {
                pstmt.setObject(parameterIndex, null);
                return;
            }
            if (argValue instanceof Double) {
                pstmt.setDouble(parameterIndex, (Double)argValue);
            } else if (argValue instanceof Float) {
                pstmt.setFloat(parameterIndex, ((Float)argValue).floatValue());
            } else if (argValue instanceof BigDecimal) {
                pstmt.setBigDecimal(parameterIndex, (BigDecimal)argValue);
            } else if (argValue instanceof Integer) {
                pstmt.setInt(parameterIndex, (Integer)argValue);
            } else if (argValue instanceof Long) {
                pstmt.setLong(parameterIndex, (Long)argValue);
            } else {
                pstmt.setDouble(parameterIndex, Double.valueOf(argValue.toString()));
            }
        }

        @Override
        public Object getFieldValue(ResultSet rs, int columnIndex) throws SQLException {
            if (rs.getObject(columnIndex) == null) {
                return null;
            }
            return rs.getDouble(columnIndex);
        }
    }
    ,
    INT("\u6574\u578b", new int[]{4, -6, 5}){

        @Override
        public void setArgumentValue(PreparedStatement pstmt, int parameterIndex, Object argValue) throws SQLException {
            if (StringUtils.isNull((String)String.valueOf(argValue))) {
                pstmt.setObject(parameterIndex, null);
                return;
            }
            if (argValue instanceof Number) {
                pstmt.setInt(parameterIndex, ((Number)argValue).intValue());
            } else {
                pstmt.setInt(parameterIndex, Integer.valueOf(argValue.toString()));
            }
        }

        @Override
        public Object getFieldValue(ResultSet rs, int columnIndex) throws SQLException {
            if (rs.getObject(columnIndex) == null) {
                return null;
            }
            return rs.getInt(columnIndex);
        }
    }
    ,
    DATE("\u65e5\u671f\u578b", new int[]{91}){

        @Override
        public void setArgumentValue(PreparedStatement pstmt, int parameterIndex, Object argValue) throws SQLException {
            if (StringUtils.isNull((String)String.valueOf(argValue))) {
                pstmt.setObject(parameterIndex, null);
                return;
            }
            if (argValue instanceof Date) {
                pstmt.setDate(parameterIndex, new java.sql.Date(((Date)argValue).getTime()));
            } else {
                Date date = DateUtils.parse((String)argValue.toString(), null);
                if (date == null) {
                    throw new IllegalArgumentException("\u4f20\u5165\u53c2\u6570\u4e0d\u5408\u6cd5\uff1a\u4e0d\u662f\u65e5\u671f\u7c7b\u578b\u3002");
                }
                pstmt.setDate(parameterIndex, new java.sql.Date(date.getTime()));
            }
        }

        @Override
        public Object getFieldValue(ResultSet rs, int columnIndex) throws SQLException {
            if (rs.getObject(columnIndex) == null) {
                return null;
            }
            return new Date(rs.getDate(columnIndex).getTime());
        }
    }
    ,
    TIMESTAMP("\u65e5\u671f\u65f6\u95f4\u578b", new int[]{93}){

        @Override
        public void setArgumentValue(PreparedStatement pstmt, int parameterIndex, Object argValue) throws SQLException {
            if (StringUtils.isNull((String)String.valueOf(argValue))) {
                pstmt.setObject(parameterIndex, null);
                return;
            }
            if (argValue instanceof Timestamp) {
                pstmt.setTimestamp(parameterIndex, (Timestamp)argValue);
            } else if (argValue instanceof Date) {
                pstmt.setTimestamp(parameterIndex, new Timestamp(((Date)argValue).getTime()));
            } else if (argValue instanceof Long) {
                pstmt.setTimestamp(parameterIndex, new Timestamp((Long)argValue));
            } else {
                throw new IllegalArgumentException("\u4f20\u5165\u53c2\u6570\u4e0d\u5408\u6cd5\uff1a\u4e0d\u662f\u65e5\u671f\u65f6\u95f4\u7c7b\u578b\u3002");
            }
        }

        @Override
        public Object getFieldValue(ResultSet rs, int columnIndex) throws SQLException {
            if (rs.getObject(columnIndex) == null) {
                return null;
            }
            return new Timestamp(rs.getTimestamp(columnIndex).getTime());
        }
    }
    ,
    BOOLEAN("\u5e03\u5c14\u578b", new int[]{16}){

        @Override
        public void setArgumentValue(PreparedStatement pstmt, int parameterIndex, Object argValue) throws SQLException {
            if (StringUtils.isNull((String)String.valueOf(argValue))) {
                pstmt.setObject(parameterIndex, null);
                return;
            }
            if (argValue instanceof Boolean) {
                pstmt.setBoolean(parameterIndex, (Boolean)argValue);
            } else {
                pstmt.setBoolean(parameterIndex, StringUtils.convertToBoolean((String)argValue.toString()));
            }
        }

        @Override
        public Object getFieldValue(ResultSet rs, int columnIndex) throws SQLException {
            if (rs.getObject(columnIndex) == null) {
                return null;
            }
            return rs.getBoolean(columnIndex);
        }
    }
    ,
    LONG("\u957f\u6574\u578b", new int[]{-5}){

        @Override
        public void setArgumentValue(PreparedStatement pstmt, int parameterIndex, Object argValue) throws SQLException {
            if (StringUtils.isNull((String)String.valueOf(argValue))) {
                pstmt.setObject(parameterIndex, null);
                return;
            }
            if (argValue instanceof Number) {
                pstmt.setLong(parameterIndex, ((Number)argValue).longValue());
            } else {
                pstmt.setLong(parameterIndex, Long.valueOf(argValue.toString()));
            }
        }

        @Override
        public Object getFieldValue(ResultSet rs, int columnIndex) throws SQLException {
            if (rs.getObject(columnIndex) == null) {
                return null;
            }
            return rs.getLong(columnIndex);
        }
    };

    private int[] sqlTypes;
    private String description;

    private FieldType(String description, int ... sqlTypes) {
        this.sqlTypes = sqlTypes;
        this.description = description;
    }

    public int[] getSqlType() {
        return this.sqlTypes;
    }

    public String getDescription() {
        return this.description;
    }

    public FieldType getFieldType(int sqlType) {
        return null;
    }

    public abstract void setArgumentValue(PreparedStatement var1, int var2, Object var3) throws SQLException;

    public abstract Object getFieldValue(ResultSet var1, int var2) throws SQLException;
}

