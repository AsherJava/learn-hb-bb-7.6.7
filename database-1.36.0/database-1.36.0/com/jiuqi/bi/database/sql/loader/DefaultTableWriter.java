/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.database.sql.loader;

import com.jiuqi.bi.database.metadata.LogicField;
import com.jiuqi.bi.database.sql.loader.AbstractTableWriter;
import com.jiuqi.bi.database.sql.loader.Record;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

public class DefaultTableWriter
extends AbstractTableWriter {
    private static final String OPTION_KEY_BATCHSIZE = "batchsize";
    private static final int DEFAULT_BATCH_SIZE = 1000;
    private PreparedStatement stmt;
    private int batchSize = 1000;
    private int counter = 0;
    private ValueWriter[] writers;

    public DefaultTableWriter(Connection conn) {
        super(conn);
    }

    @Override
    public void open() throws SQLException {
        this.writers = new ValueWriter[this.fields.size()];
        StringBuffer sqlBuffer = new StringBuffer();
        sqlBuffer.append("INSERT INTO ").append(this.tableName).append(" (");
        StringBuffer fieldsBuffer = new StringBuffer();
        StringBuffer valuesBuffer = new StringBuffer();
        for (int index = 0; index < this.fields.size(); ++index) {
            LogicField field = (LogicField)this.fields.get(index);
            if (index > 0) {
                fieldsBuffer.append(", ");
                valuesBuffer.append(", ");
            }
            fieldsBuffer.append(field.getFieldName());
            valuesBuffer.append("?");
            this.writers[index] = this.createWriter(field, index);
        }
        sqlBuffer.append(fieldsBuffer).append(") values (").append(valuesBuffer).append(")");
        this.stmt = this.conn.prepareStatement(sqlBuffer.toString());
        String batchSizeStr = this.getOption(OPTION_KEY_BATCHSIZE);
        if (batchSizeStr != null) {
            try {
                this.batchSize = Integer.parseInt(batchSizeStr);
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
    }

    @Override
    public void insert(Record record) throws SQLException {
        ++this.counter;
        try {
            for (ValueWriter writer : this.writers) {
                writer.write(this.stmt, record);
            }
            this.stmt.addBatch();
            if (this.counter == this.batchSize) {
                this.counter = 0;
                this.stmt.executeBatch();
            }
        }
        catch (SQLException e) {
            this.counter = 0;
            throw e;
        }
    }

    @Override
    public void close() throws SQLException {
        try {
            if (this.counter != 0) {
                this.stmt.executeBatch();
            }
        }
        finally {
            this.stmt.close();
        }
    }

    protected ValueWriter createWriter(LogicField field, int index) {
        switch (field.getDataType()) {
            case 1: {
                return new BooleanWriter(index);
            }
            case 5: {
                return new IntegerWriter(index);
            }
            case 3: {
                return new DoubleWriter(index);
            }
            case 6: {
                return new StringWriter(index);
            }
            case 8: {
                return new LongWriter(index);
            }
            case 10: {
                return new BigDecimalWriter(index);
            }
            case 9: {
                return new BytesWriter(index);
            }
            case 2: {
                return new DateWriter(index);
            }
        }
        return new ObjectWriter(index);
    }

    private static class BigDecimalWriter
    extends ValueWriter {
        public BigDecimalWriter(int index) {
            super(index);
        }

        @Override
        public void write(PreparedStatement stmt, Record record) throws SQLException {
            BigDecimal bd = (BigDecimal)record.get(this.index);
            stmt.setBigDecimal(this.index + 1, bd);
        }
    }

    private static class LongWriter
    extends ValueWriter {
        public LongWriter(int index) {
            super(index);
        }

        @Override
        public void write(PreparedStatement stmt, Record record) throws SQLException {
            Number l = (Number)record.get(this.index);
            if (l == null) {
                stmt.setNull(this.index + 1, -5);
            } else {
                stmt.setLong(this.index + 1, l.longValue());
            }
        }
    }

    private static class BytesWriter
    extends ValueWriter {
        public BytesWriter(int index) {
            super(index);
        }

        @Override
        public void write(PreparedStatement stmt, Record record) throws SQLException {
            byte[] b = (byte[])record.get(this.index);
            stmt.setBytes(this.index + 1, b);
        }
    }

    private static class BooleanWriter
    extends ValueWriter {
        public BooleanWriter(int index) {
            super(index);
        }

        @Override
        public void write(PreparedStatement stmt, Record record) throws SQLException {
            Boolean b = (Boolean)record.get(this.index);
            if (b == null) {
                stmt.setNull(this.index + 1, 16);
            } else {
                stmt.setBoolean(this.index + 1, b);
            }
        }
    }

    private static class DateWriter
    extends ValueWriter {
        public DateWriter(int index) {
            super(index);
        }

        @Override
        public void write(PreparedStatement stmt, Record record) throws SQLException {
            Object obj = record.get(this.index);
            if (obj == null) {
                stmt.setNull(this.index + 1, 93);
            } else if (obj instanceof Calendar) {
                Calendar c = (Calendar)obj;
                stmt.setTimestamp(this.index + 1, new Timestamp(c.getTime().getTime()));
            } else if (obj instanceof Date) {
                Date d = (Date)obj;
                stmt.setTimestamp(this.index + 1, new Timestamp(d.getTime()));
            }
        }
    }

    private static class DoubleWriter
    extends ValueWriter {
        public DoubleWriter(int index) {
            super(index);
        }

        @Override
        public void write(PreparedStatement stmt, Record record) throws SQLException {
            Number d = (Number)record.get(this.index);
            if (d == null) {
                stmt.setNull(this.index + 1, 8);
            } else {
                stmt.setDouble(this.index + 1, d.doubleValue());
            }
        }
    }

    private static class IntegerWriter
    extends ValueWriter {
        public IntegerWriter(int index) {
            super(index);
        }

        @Override
        public void write(PreparedStatement stmt, Record record) throws SQLException {
            Number integer = (Number)record.get(this.index);
            if (integer == null) {
                stmt.setNull(this.index + 1, 4);
            } else {
                stmt.setInt(this.index + 1, integer.intValue());
            }
        }
    }

    private static class StringWriter
    extends ValueWriter {
        public StringWriter(int index) {
            super(index);
        }

        @Override
        public void write(PreparedStatement stmt, Record record) throws SQLException {
            String str = (String)record.get(this.index);
            stmt.setString(this.index + 1, str);
        }
    }

    private static class ObjectWriter
    extends ValueWriter {
        public ObjectWriter(int index) {
            super(index);
        }

        @Override
        public void write(PreparedStatement stmt, Record record) throws SQLException {
            stmt.setObject(this.index + 1, record.get(this.index));
        }
    }

    protected static abstract class ValueWriter {
        protected int index;

        public ValueWriter(int index) {
            this.index = index;
        }

        public abstract void write(PreparedStatement var1, Record var2) throws SQLException;
    }
}

