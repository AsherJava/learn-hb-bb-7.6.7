/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.bi.database.sql.command;

import com.jiuqi.bi.database.DatabaseManager;
import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.metadata.LogicField;
import com.jiuqi.bi.database.metadata.SQLMetadataException;
import com.jiuqi.bi.database.sql.command.SQLCommand;
import com.jiuqi.bi.database.sql.command.SQLCommandType;
import com.jiuqi.bi.util.StringUtils;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CSVLoadCommand
extends SQLCommand {
    private String tableName;
    private List<List<String>> records = new ArrayList<List<String>>();

    public CSVLoadCommand(String tableName) {
        this.tableName = tableName;
        this.type = SQLCommandType.LOAD;
    }

    public void addRecord(List<String> record) {
        this.records.add(record);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean execute(Connection conn) throws SQLException {
        List<LogicField> fields;
        IDatabase db = DatabaseManager.getInstance().findDatabaseByConnection(conn);
        try {
            fields = db.createMetadata(conn).getFieldsByTableName(this.tableName);
        }
        catch (SQLMetadataException e) {
            throw new SQLException(e);
        }
        StringBuilder b = new StringBuilder();
        b.append("INSERT INTO ").append(this.tableName).append(" VALUES(");
        for (int i = 0; i < fields.size(); ++i) {
            if (i != 0) {
                b.append(",");
            }
            b.append("?");
        }
        b.append(")");
        ArrayList<FieldWriter> writers = new ArrayList<FieldWriter>();
        for (int i = 0; i < fields.size(); ++i) {
            writers.add(this.createWriter(fields.get(i), i));
        }
        try (PreparedStatement ps = conn.prepareStatement(b.toString());){
            for (List<String> record : this.records) {
                for (int i = 0; i < record.size(); ++i) {
                    ((FieldWriter)writers.get(i)).write(ps, record.get(i));
                }
                ps.addBatch();
            }
            ps.executeBatch();
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        b.append("LOAD FROM CSV INSERT INTO ").append(this.tableName).append(StringUtils.LINE_SEPARATOR);
        for (List<String> linerow : this.records) {
            for (int i = 0; i < linerow.size(); ++i) {
                if (i != 0) {
                    b.append(", ");
                }
                b.append(linerow.get(i));
            }
            b.append(StringUtils.LINE_SEPARATOR);
        }
        return b.toString();
    }

    FieldWriter createWriter(LogicField field, int index) {
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
            case 9: {
                return new BytesWriter(index);
            }
            case 2: {
                return new DateWriter(index);
            }
        }
        return null;
    }

    private static class LongWriter
    extends FieldWriter {
        public LongWriter(int index) {
            super(index);
        }

        @Override
        public void write(PreparedStatement stmt, String value) throws SQLException {
            if (StringUtils.isEmpty((String)value)) {
                stmt.setNull(this.index + 1, -5);
            } else {
                stmt.setLong(this.index + 1, Long.parseLong(value));
            }
        }
    }

    private static class BytesWriter
    extends FieldWriter {
        public BytesWriter(int index) {
            super(index);
        }

        @Override
        public void write(PreparedStatement stmt, String value) throws SQLException {
            try {
                stmt.setBytes(this.index + 1, value.getBytes("utf-8"));
            }
            catch (UnsupportedEncodingException e) {
                throw new SQLException("\u7cfb\u7edf\u4e0d\u652f\u6301UTF-8\u7f16\u7801");
            }
        }
    }

    private static class BooleanWriter
    extends FieldWriter {
        public BooleanWriter(int index) {
            super(index);
        }

        @Override
        public void write(PreparedStatement stmt, String value) throws SQLException {
            if (StringUtils.isEmpty((String)value)) {
                stmt.setNull(this.index + 1, 16);
            } else {
                stmt.setBoolean(this.index + 1, Boolean.parseBoolean(value));
            }
        }
    }

    private static class DateWriter
    extends FieldWriter {
        private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        public DateWriter(int index) {
            super(index);
        }

        @Override
        public void write(PreparedStatement stmt, String value) throws SQLException {
            Date date;
            try {
                date = this.df.parse(value);
            }
            catch (ParseException e) {
                throw new SQLException("\u65e5\u671f\u683c\u5f0f\u9519\u8bef\uff1a" + value);
            }
            if (date == null) {
                stmt.setNull(this.index + 1, 93);
            } else {
                stmt.setTimestamp(this.index + 1, new Timestamp(date.getTime()));
            }
        }
    }

    private static class DoubleWriter
    extends FieldWriter {
        public DoubleWriter(int index) {
            super(index);
        }

        @Override
        public void write(PreparedStatement stmt, String value) throws SQLException {
            if (StringUtils.isEmpty((String)value)) {
                stmt.setNull(this.index + 1, 8);
            } else {
                stmt.setDouble(this.index + 1, Double.parseDouble(value));
            }
        }
    }

    private static class IntegerWriter
    extends FieldWriter {
        public IntegerWriter(int index) {
            super(index);
        }

        @Override
        public void write(PreparedStatement stmt, String value) throws SQLException {
            if (StringUtils.isEmpty((String)value)) {
                stmt.setNull(this.index + 1, 4);
            } else {
                stmt.setInt(this.index + 1, Integer.parseInt(value));
            }
        }
    }

    private static class StringWriter
    extends FieldWriter {
        public StringWriter(int index) {
            super(index);
        }

        @Override
        public void write(PreparedStatement stmt, String value) throws SQLException {
            stmt.setString(this.index + 1, value);
        }
    }

    static abstract class FieldWriter {
        protected int index;

        public FieldWriter(int index) {
            this.index = index;
        }

        public abstract void write(PreparedStatement var1, String var2) throws SQLException;
    }
}

