/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.grid2.GridEnums$DataType
 */
package com.jiuqi.budget.domain;

import com.jiuqi.nvwa.grid2.GridEnums;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import org.springframework.util.StringUtils;

public enum DataType {
    NUM{

        @Override
        public String getTitle() {
            return "\u6570\u503c\u578b";
        }

        @Override
        public Integer getDataLengthMax() {
            return 38;
        }

        @Override
        public Integer getDataPrecisionMax() {
            return 37;
        }

        @Override
        public byte getTextAlignments() {
            return 2;
        }

        @Override
        public void setPreparedStatementValue(PreparedStatement preparedStatement, int index, Object value) throws SQLException {
            preparedStatement.setBigDecimal(index, (BigDecimal)value);
        }

        @Override
        public Object getValueFromRs(ResultSet resultSet, String columnName) throws SQLException {
            return resultSet.getBigDecimal(columnName);
        }

        @Override
        public int getGridDataType() {
            return GridEnums.DataType.Number.ordinal();
        }
    }
    ,
    MONEY{

        @Override
        public String getTitle() {
            return "\u8d27\u5e01\u578b";
        }

        @Override
        public Integer getDataLengthMax() {
            return 38;
        }

        @Override
        public Integer getDataPrecisionMax() {
            return 37;
        }

        @Override
        public byte getTextAlignments() {
            return 2;
        }

        @Override
        public void setPreparedStatementValue(PreparedStatement preparedStatement, int index, Object value) throws SQLException {
            preparedStatement.setBigDecimal(index, (BigDecimal)value);
        }

        @Override
        public Object getValueFromRs(ResultSet resultSet, String columnName) throws SQLException {
            return resultSet.getBigDecimal(columnName);
        }

        @Override
        public int getGridDataType() {
            return GridEnums.DataType.Number.ordinal();
        }
    }
    ,
    STR{

        @Override
        public String getTitle() {
            return "\u5b57\u7b26\u578b";
        }

        @Override
        public Integer getDataLengthMax() {
            return 2000;
        }

        @Override
        public Integer getDataPrecisionMax() {
            return null;
        }

        @Override
        public byte getTextAlignments() {
            return 1;
        }

        @Override
        public void setPreparedStatementValue(PreparedStatement preparedStatement, int index, Object value) throws SQLException {
            preparedStatement.setString(index, (String)value);
        }

        @Override
        public Object getValueFromRs(ResultSet resultSet, String columnName) throws SQLException {
            return resultSet.getString(columnName);
        }

        @Override
        public int getGridDataType() {
            return GridEnums.DataType.Text.ordinal();
        }
    }
    ,
    INT{

        @Override
        public String getTitle() {
            return "\u6574\u6570\u578b";
        }

        @Override
        public Integer getDataLengthMax() {
            return 10;
        }

        @Override
        public Integer getDataPrecisionMax() {
            return null;
        }

        @Override
        public byte getTextAlignments() {
            return 2;
        }

        @Override
        public void setPreparedStatementValue(PreparedStatement preparedStatement, int index, Object value) throws SQLException {
            preparedStatement.setBigDecimal(index, (BigDecimal)value);
        }

        @Override
        public Object getValueFromRs(ResultSet resultSet, String columnName) throws SQLException {
            return resultSet.getBigDecimal(columnName);
        }

        @Override
        public int getGridDataType() {
            return GridEnums.DataType.Number.ordinal();
        }
    }
    ,
    PERCENT{

        @Override
        public String getTitle() {
            return "\u767e\u5206\u6bd4\u578b";
        }

        @Override
        public Integer getDataLengthMax() {
            return 38;
        }

        @Override
        public Integer getDataPrecisionMax() {
            return 37;
        }

        @Override
        public byte getTextAlignments() {
            return 2;
        }

        @Override
        public void setPreparedStatementValue(PreparedStatement preparedStatement, int index, Object value) throws SQLException {
            preparedStatement.setBigDecimal(index, (BigDecimal)value);
        }

        @Override
        public Object getValueFromRs(ResultSet resultSet, String columnName) throws SQLException {
            return resultSet.getBigDecimal(columnName);
        }

        @Override
        public int getGridDataType() {
            return GridEnums.DataType.Number.ordinal();
        }
    }
    ,
    PERMILLE{

        @Override
        public String getTitle() {
            return "\u5343\u5206\u6bd4\u578b";
        }

        @Override
        public Integer getDataLengthMax() {
            return 38;
        }

        @Override
        public Integer getDataPrecisionMax() {
            return 37;
        }

        @Override
        public byte getTextAlignments() {
            return 2;
        }

        @Override
        public void setPreparedStatementValue(PreparedStatement preparedStatement, int index, Object value) throws SQLException {
            preparedStatement.setBigDecimal(index, (BigDecimal)value);
        }

        @Override
        public Object getValueFromRs(ResultSet resultSet, String columnName) throws SQLException {
            return resultSet.getBigDecimal(columnName);
        }

        @Override
        public int getGridDataType() {
            return GridEnums.DataType.Number.ordinal();
        }
    }
    ,
    BOOLEAN{

        @Override
        public String getTitle() {
            return "\u5e03\u5c14\u578b";
        }

        @Override
        public Integer getDataLengthMax() {
            return null;
        }

        @Override
        public Integer getDataPrecisionMax() {
            return null;
        }

        @Override
        public byte getTextAlignments() {
            return 3;
        }

        @Override
        public void setPreparedStatementValue(PreparedStatement preparedStatement, int index, Object value) throws SQLException {
            if (value == null) {
                preparedStatement.setNull(index, 4);
            } else {
                preparedStatement.setInt(index, (Integer)value);
            }
        }

        @Override
        public Object getValueFromRs(ResultSet resultSet, String columnName) throws SQLException {
            Object result = resultSet.getObject(columnName);
            if (result == null) {
                return null;
            }
            if (result instanceof Integer) {
                return result;
            }
            if (result instanceof BigDecimal) {
                return Integer.valueOf(((BigDecimal)result).toPlainString());
            }
            return resultSet.getObject(columnName, Integer.class);
        }

        @Override
        public int getGridDataType() {
            return GridEnums.DataType.Boolean.ordinal();
        }
    }
    ,
    DATE{

        @Override
        public String getTitle() {
            return "\u65e5\u671f\u578b";
        }

        @Override
        public Integer getDataLengthMax() {
            return null;
        }

        @Override
        public Integer getDataPrecisionMax() {
            return null;
        }

        @Override
        public byte getTextAlignments() {
            return 3;
        }

        @Override
        public void setPreparedStatementValue(PreparedStatement preparedStatement, int index, Object value) throws SQLException {
            preparedStatement.setDate(index, (Date)value);
        }

        @Override
        public Object getValueFromRs(ResultSet resultSet, String columnName) throws SQLException {
            return resultSet.getDate(columnName);
        }

        @Override
        public int getGridDataType() {
            return GridEnums.DataType.DateTime.ordinal();
        }
    }
    ,
    DATETIME{

        @Override
        public String getTitle() {
            return "\u65e5\u671f\u65f6\u95f4\u578b";
        }

        @Override
        public Integer getDataLengthMax() {
            return null;
        }

        @Override
        public Integer getDataPrecisionMax() {
            return null;
        }

        @Override
        public byte getTextAlignments() {
            return 3;
        }

        @Override
        public void setPreparedStatementValue(PreparedStatement preparedStatement, int index, Object value) throws SQLException {
            preparedStatement.setTimestamp(index, (Timestamp)value);
        }

        @Override
        public Object getValueFromRs(ResultSet resultSet, String columnName) throws SQLException {
            return resultSet.getTimestamp(columnName);
        }

        @Override
        public int getGridDataType() {
            return GridEnums.DataType.DateTime.ordinal();
        }
    }
    ,
    REMARK{

        @Override
        public String getTitle() {
            return "\u5907\u6ce8\u578b";
        }

        @Override
        public Integer getDataLengthMax() {
            return null;
        }

        @Override
        public Integer getDataPrecisionMax() {
            return null;
        }

        @Override
        public byte getTextAlignments() {
            return 3;
        }

        @Override
        public void setPreparedStatementValue(PreparedStatement preparedStatement, int index, Object value) throws SQLException {
            preparedStatement.setString(index, (String)value);
        }

        @Override
        public Object getValueFromRs(ResultSet resultSet, String columnName) throws SQLException {
            return resultSet.getString(columnName);
        }

        @Override
        public int getGridDataType() {
            return GridEnums.DataType.Text.ordinal();
        }
    }
    ,
    USER{

        @Override
        public String getTitle() {
            return "\u7528\u6237\u578b";
        }

        @Override
        public Integer getDataLengthMax() {
            return 2000;
        }

        @Override
        public Integer getDataPrecisionMax() {
            return null;
        }

        @Override
        public byte getTextAlignments() {
            return 1;
        }

        @Override
        public void setPreparedStatementValue(PreparedStatement preparedStatement, int index, Object value) throws SQLException {
            preparedStatement.setString(index, (String)value);
        }

        @Override
        public Object getValueFromRs(ResultSet resultSet, String columnName) throws SQLException {
            return resultSet.getString(columnName);
        }

        @Override
        public int getGridDataType() {
            return GridEnums.DataType.Text.ordinal();
        }
    }
    ,
    ATTACHMENT{

        @Override
        public String getTitle() {
            return "\u9644\u4ef6\u578b";
        }

        @Override
        public Integer getDataLengthMax() {
            return null;
        }

        @Override
        public Integer getDataPrecisionMax() {
            return null;
        }

        @Override
        public byte getTextAlignments() {
            return 1;
        }

        @Override
        public void setPreparedStatementValue(PreparedStatement preparedStatement, int index, Object value) throws SQLException {
            preparedStatement.setString(index, (String)value);
        }

        @Override
        public Object getValueFromRs(ResultSet resultSet, String columnName) throws SQLException {
            return resultSet.getString(columnName);
        }

        @Override
        public int getGridDataType() {
            return GridEnums.DataType.Text.ordinal();
        }
    };


    public abstract String getTitle();

    public abstract Integer getDataLengthMax();

    public abstract Integer getDataPrecisionMax();

    public abstract byte getTextAlignments();

    public abstract void setPreparedStatementValue(PreparedStatement var1, int var2, Object var3) throws SQLException;

    public abstract Object getValueFromRs(ResultSet var1, String var2) throws SQLException;

    public abstract int getGridDataType();

    public static DataType getDataTypeByTitle(String title) {
        DataType[] values;
        if (!StringUtils.hasLength(title)) {
            return null;
        }
        for (DataType value : values = DataType.values()) {
            if (!value.getTitle().equals(title)) continue;
            return value;
        }
        return null;
    }
}

