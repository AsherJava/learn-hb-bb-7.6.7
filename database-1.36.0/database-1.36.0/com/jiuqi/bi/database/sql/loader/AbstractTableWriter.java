/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.types.DataTypes
 */
package com.jiuqi.bi.database.sql.loader;

import com.jiuqi.bi.database.metadata.LogicField;
import com.jiuqi.bi.database.sql.loader.ITableWriter;
import com.jiuqi.bi.database.sql.loader.Record;
import com.jiuqi.bi.types.DataTypes;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractTableWriter
implements ITableWriter {
    protected List<LogicField> fields = new ArrayList<LogicField>();
    protected String tableName;
    private Map<String, String> options = new HashMap<String, String>();
    protected Connection conn;

    public AbstractTableWriter(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    @Override
    public List<LogicField> getFields() {
        return this.fields;
    }

    @Override
    public void addField(String fieldName, String fieldTitle, int dataType) {
        LogicField field = new LogicField();
        field.setFieldName(fieldName);
        field.setFieldTitle(fieldTitle);
        field.setDataType(dataType);
        field.setRawType(DataTypes.toJavaSQLType((int)dataType));
        this.fields.add(field);
    }

    @Override
    public abstract void open() throws SQLException;

    @Override
    public abstract void insert(Record var1) throws SQLException;

    @Override
    public abstract void close() throws SQLException;

    @Override
    public void setOption(String key, String value) {
        if (key == null) {
            return;
        }
        this.options.put(key.toUpperCase(), value);
    }

    @Override
    public String getOption(String key) {
        if (key == null) {
            return null;
        }
        return this.options.get(key.toUpperCase());
    }

    @Override
    public int insertResultSet(ResultSet rs) throws SQLException {
        return this.insertResultSet(rs, null);
    }

    @Override
    public int insertResultSet(ResultSet rs, Map<String, String> fieldMap) throws SQLException {
        if (this.fields.size() == 0) {
            throw new SQLException("\u672a\u8bbe\u7f6e\u5b57\u6bb5");
        }
        ResultSetMetaData md = rs.getMetaData();
        ResultSetReader[] readers = new ResultSetReader[this.fields.size()];
        block10: for (int index = 0; index < this.fields.size(); ++index) {
            int i;
            LogicField field = this.fields.get(index);
            String fieldNameInResultSet = field.getFieldName();
            if (fieldMap != null) {
                fieldNameInResultSet = fieldMap.get(field.getFieldName());
            }
            if (fieldNameInResultSet == null) {
                throw new SQLException("\u65e0\u6cd5\u627e\u5230\u5b57\u6bb5[" + field.getFieldName() + "]\u7684\u6620\u5c04\u5b57\u6bb5[" + fieldNameInResultSet + "]");
            }
            int srcIndex = -1;
            for (i = 1; i < md.getColumnCount(); ++i) {
                if (!fieldNameInResultSet.equalsIgnoreCase(md.getColumnLabel(i))) continue;
                srcIndex = i;
            }
            if (srcIndex == -1) {
                for (i = 1; i < md.getColumnCount(); ++i) {
                    if (!fieldNameInResultSet.equalsIgnoreCase(md.getColumnName(i))) continue;
                    srcIndex = i;
                }
            }
            if (srcIndex == -1) {
                throw new SQLException("\u65e0\u6cd5\u5728resultset\u4e2d\u627e\u5230\u5b57\u6bb5[" + fieldNameInResultSet + "]");
            }
            switch (field.getDataType()) {
                case 1: {
                    readers[index] = new BooleanReader(srcIndex, index);
                    continue block10;
                }
                case 5: {
                    readers[index] = new IntegerReader(srcIndex, index);
                    continue block10;
                }
                case 3: {
                    readers[index] = new DoubleReader(srcIndex, index);
                    continue block10;
                }
                case 6: {
                    readers[index] = new StringReader(srcIndex, index);
                    continue block10;
                }
                case 8: {
                    readers[index] = new LongReader(srcIndex, index);
                    continue block10;
                }
                case 10: {
                    readers[index] = new BigDecimalReader(srcIndex, index);
                    continue block10;
                }
                case 9: {
                    readers[index] = new BytesReader(srcIndex, index);
                    continue block10;
                }
                case 2: {
                    readers[index] = new DateReader(srcIndex, index);
                    continue block10;
                }
                default: {
                    readers[index] = new ObjectReader(srcIndex, index);
                }
            }
        }
        this.open();
        int count = 0;
        while (rs.next()) {
            ++count;
            Record record = new Record(this.fields.size());
            for (ResultSetReader reader : readers) {
                reader.read(rs, record);
            }
            this.insert(record);
        }
        this.close();
        return count;
    }

    private static class BigDecimalReader
    extends ResultSetReader {
        public BigDecimalReader(int srcIndex, int targetIndex) {
            super(srcIndex, targetIndex);
        }

        @Override
        public void read(ResultSet rs, Record record) throws SQLException {
            BigDecimal bd = rs.getBigDecimal(this.srcIndex);
            if (rs.wasNull()) {
                record.set(this.targetIndex, null);
            } else {
                record.set(this.targetIndex, bd);
            }
        }
    }

    private static class LongReader
    extends ResultSetReader {
        public LongReader(int srcIndex, int targetIndex) {
            super(srcIndex, targetIndex);
        }

        @Override
        public void read(ResultSet rs, Record record) throws SQLException {
            long l = rs.getLong(this.srcIndex);
            if (rs.wasNull()) {
                record.set(this.targetIndex, null);
            } else {
                record.set(this.targetIndex, l);
            }
        }
    }

    private static class BytesReader
    extends ResultSetReader {
        public BytesReader(int srcIndex, int targetIndex) {
            super(srcIndex, targetIndex);
        }

        @Override
        public void read(ResultSet rs, Record record) throws SQLException {
            byte[] bytes = rs.getBytes(this.srcIndex);
            if (rs.wasNull()) {
                record.set(this.targetIndex, null);
            } else {
                record.set(this.targetIndex, bytes);
            }
        }
    }

    private static class BooleanReader
    extends ResultSetReader {
        public BooleanReader(int srcIndex, int targetIndex) {
            super(srcIndex, targetIndex);
        }

        @Override
        public void read(ResultSet rs, Record record) throws SQLException {
            boolean b = rs.getBoolean(this.srcIndex);
            if (rs.wasNull()) {
                record.set(this.targetIndex, null);
            } else {
                record.set(this.targetIndex, b);
            }
        }
    }

    private static class DateReader
    extends ResultSetReader {
        public DateReader(int srcIndex, int targetIndex) {
            super(srcIndex, targetIndex);
        }

        @Override
        public void read(ResultSet rs, Record record) throws SQLException {
            Timestamp timestamp = rs.getTimestamp(this.srcIndex);
            if (rs.wasNull()) {
                record.set(this.targetIndex, null);
            } else {
                record.set(this.targetIndex, timestamp);
            }
        }
    }

    private static class DoubleReader
    extends ResultSetReader {
        public DoubleReader(int srcIndex, int targetIndex) {
            super(srcIndex, targetIndex);
        }

        @Override
        public void read(ResultSet rs, Record record) throws SQLException {
            double d = rs.getDouble(this.srcIndex);
            if (rs.wasNull()) {
                record.set(this.targetIndex, null);
            } else {
                record.set(this.targetIndex, d);
            }
        }
    }

    private static class IntegerReader
    extends ResultSetReader {
        public IntegerReader(int srcIndex, int targetIndex) {
            super(srcIndex, targetIndex);
        }

        @Override
        public void read(ResultSet rs, Record record) throws SQLException {
            int i = rs.getInt(this.srcIndex);
            if (rs.wasNull()) {
                record.set(this.targetIndex, null);
            } else {
                record.set(this.targetIndex, i);
            }
        }
    }

    private static class StringReader
    extends ResultSetReader {
        public StringReader(int srcIndex, int targetIndex) {
            super(srcIndex, targetIndex);
        }

        @Override
        public void read(ResultSet rs, Record record) throws SQLException {
            String value = rs.getString(this.srcIndex);
            record.set(this.targetIndex, value);
        }
    }

    private static class ObjectReader
    extends ResultSetReader {
        public ObjectReader(int srcIndex, int targetIndex) {
            super(srcIndex, targetIndex);
        }

        @Override
        public void read(ResultSet rs, Record record) throws SQLException {
            Object value = rs.getObject(this.srcIndex);
            if (rs.wasNull()) {
                record.set(this.targetIndex, this.srcIndex);
            } else {
                record.set(this.targetIndex, value);
            }
        }
    }

    private static abstract class ResultSetReader {
        protected int srcIndex;
        protected int targetIndex;

        public ResultSetReader(int srcIndex, int targetIndex) {
            this.srcIndex = srcIndex;
            this.targetIndex = targetIndex;
        }

        public abstract void read(ResultSet var1, Record var2) throws SQLException;
    }
}

