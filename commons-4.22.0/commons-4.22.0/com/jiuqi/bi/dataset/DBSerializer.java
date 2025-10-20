/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.bi.dataset;

import com.jiuqi.bi.dataset.BlobValue;
import com.jiuqi.bi.dataset.Column;
import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.DataSet;
import com.jiuqi.bi.util.StringUtils;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DBSerializer {
    private Connection conn;
    private DataSet<?> dataSet;
    private static final int BATCH_SIZE = 512;

    public DBSerializer(Connection conn, DataSet<?> dataSet) {
        this.conn = conn;
        this.dataSet = dataSet;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void insert(String tableName) throws SQLException {
        if (this.dataSet.isEmpty()) {
            return;
        }
        ArrayList<DBOperator> operators = new ArrayList<DBOperator>();
        try (PreparedStatement stmt = this.createInsertStatement(tableName, operators);){
            this.writeRows(stmt, operators);
        }
    }

    private PreparedStatement createInsertStatement(String tableName, List<DBOperator> operators) throws SQLException {
        StringBuilder sql = new StringBuilder();
        Set<String> destFields = this.getDestTableFields(tableName);
        sql.append("INSERT INTO ").append(tableName).append('(');
        int p = 1;
        for (Column<?> column : this.dataSet.getMetadata()) {
            if (!destFields.contains(column.getName().toUpperCase())) {
                operators.add(null);
                continue;
            }
            if (!operators.isEmpty()) {
                sql.append(", ");
            }
            sql.append(column.getName());
            DBOperator operator = this.createOperator(column, p);
            operators.add(operator);
            ++p;
        }
        sql.append(") VALUES(");
        for (int i = 0; i < operators.size(); ++i) {
            if (i > 0) {
                sql.append(", ");
            }
            sql.append('?');
        }
        sql.append(')');
        return this.conn.prepareStatement(sql.toString());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private Set<String> getDestTableFields(String tableName) throws SQLException {
        HashSet<String> fieldNames = new HashSet<String>();
        try (PreparedStatement stmt = this.conn.prepareStatement("SELECT * FROM " + tableName + " WHERE 1=0");
             ResultSet rs = stmt.executeQuery();){
            for (int i = 1; i <= rs.getMetaData().getColumnCount(); ++i) {
                String fieldName = rs.getMetaData().getColumnName(i);
                if (StringUtils.isEmpty((String)fieldName)) {
                    fieldName = rs.getMetaData().getColumnLabel(i);
                }
                fieldNames.add(fieldName);
            }
        }
        return fieldNames;
    }

    private DBOperator createOperator(Column<?> column, int paramIndex) throws SQLException {
        switch (column.getDataType()) {
            case 3: {
                return new DoubleOperator(paramIndex);
            }
            case 6: {
                return new StringOperator(paramIndex);
            }
            case 5: {
                return new IntegerOperator(paramIndex);
            }
            case 1: {
                return new BooleanOperator(paramIndex);
            }
            case 2: {
                return new DateOperator(paramIndex);
            }
            case 10: {
                return new BigDecimalOperator(paramIndex);
            }
            case 9: {
                return new BlobOperator(paramIndex);
            }
        }
        throw new SQLException("\u5b57\u6bb5[" + column.getName() + "]\u7684\u6570\u636e\u7c7b\u578b\u5c1a\u672a\u652f\u6301\uff1a" + column.getDataType());
    }

    private void writeRows(PreparedStatement stmt, List<DBOperator> operators) throws SQLException {
        this.beginProcess(this.dataSet.size());
        int size = 0;
        for (DataRow row : this.dataSet) {
            for (int i = 0; i < operators.size(); ++i) {
                DBOperator operator = operators.get(i);
                if (operator == null) continue;
                Object value = row.getValue(i);
                operator.setParam(stmt, value);
            }
            stmt.addBatch();
            if (++size % 512 != 0) continue;
            stmt.executeBatch();
            if (!this.process(size)) {
                return;
            }
            size = 0;
        }
        if (size > 0) {
            stmt.executeBatch();
            this.process(size);
        }
        this.endProcess();
    }

    protected void beginProcess(int total) {
    }

    protected boolean process(int deltaSize) {
        return true;
    }

    protected void endProcess() {
    }

    private static final class BlobOperator
    extends DBOperator {
        public BlobOperator(int paramIndex) {
            super(paramIndex);
        }

        @Override
        public void setParam(PreparedStatement stmt, Object value) throws SQLException {
            if (value == null) {
                stmt.setNull(this.paramIndex, 2004);
            } else {
                stmt.setBytes(this.paramIndex, ((BlobValue)value).toBytes());
            }
        }
    }

    private static final class StringOperator
    extends DBOperator {
        public StringOperator(int paramIndex) {
            super(paramIndex);
        }

        @Override
        public void setParam(PreparedStatement stmt, Object value) throws SQLException {
            if (value == null) {
                stmt.setNull(this.paramIndex, 12);
            } else {
                stmt.setString(this.paramIndex, value.toString());
            }
        }
    }

    private static final class BigDecimalOperator
    extends DBOperator {
        public BigDecimalOperator(int paramIndex) {
            super(paramIndex);
        }

        @Override
        public void setParam(PreparedStatement stmt, Object value) throws SQLException {
            if (value == null) {
                stmt.setNull(this.paramIndex, 3);
            } else {
                stmt.setBigDecimal(this.paramIndex, (BigDecimal)value);
            }
        }
    }

    private static final class IntegerOperator
    extends DBOperator {
        public IntegerOperator(int paramIndex) {
            super(paramIndex);
        }

        @Override
        public void setParam(PreparedStatement stmt, Object value) throws SQLException {
            if (value == null) {
                stmt.setNull(this.paramIndex, 4);
            } else {
                stmt.setInt(this.paramIndex, ((Number)value).intValue());
            }
        }
    }

    private static final class DoubleOperator
    extends DBOperator {
        public DoubleOperator(int paramIndex) {
            super(paramIndex);
        }

        @Override
        public void setParam(PreparedStatement stmt, Object value) throws SQLException {
            if (value == null) {
                stmt.setNull(this.paramIndex, 8);
            } else {
                stmt.setDouble(this.paramIndex, ((Number)value).doubleValue());
            }
        }
    }

    private static final class DateOperator
    extends DBOperator {
        public DateOperator(int paramIndex) {
            super(paramIndex);
        }

        @Override
        public void setParam(PreparedStatement stmt, Object value) throws SQLException {
            if (value == null) {
                stmt.setNull(this.paramIndex, 91);
            } else {
                Date date = new Date(((Calendar)value).getTimeInMillis());
                stmt.setDate(this.paramIndex, date);
            }
        }
    }

    private static final class BooleanOperator
    extends DBOperator {
        public BooleanOperator(int paramIndex) {
            super(paramIndex);
        }

        @Override
        public void setParam(PreparedStatement stmt, Object value) throws SQLException {
            if (value == null) {
                stmt.setNull(this.paramIndex, 16);
            } else {
                stmt.setBoolean(this.paramIndex, (Boolean)value);
            }
        }
    }

    private static abstract class DBOperator {
        protected final int paramIndex;

        public DBOperator(int paramIndex) {
            this.paramIndex = paramIndex;
        }

        public abstract void setParam(PreparedStatement var1, Object var2) throws SQLException;
    }
}

