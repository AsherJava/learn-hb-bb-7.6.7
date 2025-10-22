/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.IDatabase
 *  com.jiuqi.bi.dataset.BlobValue
 *  com.jiuqi.bi.dataset.Column
 *  com.jiuqi.bi.dataset.DataRow
 *  com.jiuqi.bi.dataset.DataSet
 *  com.jiuqi.bi.dataset.DataSetException
 *  com.jiuqi.bi.dataset.MemoryDataRow
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.bi.types.DataTypes
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.np.dataengine.query;

import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.dataset.BlobValue;
import com.jiuqi.bi.dataset.Column;
import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.DataSet;
import com.jiuqi.bi.dataset.DataSetException;
import com.jiuqi.bi.dataset.MemoryDataRow;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.types.DataTypes;
import com.jiuqi.bi.util.StringUtils;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public final class DBResultSet<T>
extends DataSet<T>
implements AutoCloseable {
    private ResultSet resultSet;
    private List<FieldReader> readers;

    public DBResultSet(ResultSet resultSet) throws DataSetException {
        this(resultSet, null);
    }

    public DBResultSet(ResultSet resultSet, Class<T> infoClass) throws DataSetException {
        super(infoClass);
        this.resultSet = resultSet;
        this.createMetadata(null);
    }

    public DBResultSet(ResultSet resultSet, IDatabase database, Class<T> infoClass) throws DataSetException {
        super(infoClass);
        this.resultSet = resultSet;
        this.createMetadata(database);
    }

    private void createMetadata(IDatabase database) throws DataSetException {
        try {
            boolean needTypeName = database == null || !database.isDatabase("POSTGRESQL") && !database.isDatabase("KINGBASE") && !database.isDatabase("KINGBASE8");
            ResultSetMetaData dbMetadata = this.resultSet.getMetaData();
            for (int i = 1; i <= dbMetadata.getColumnCount(); ++i) {
                String colName = dbMetadata.getColumnName(i);
                if (StringUtils.isEmpty((String)colName)) {
                    colName = dbMetadata.getColumnLabel(i);
                }
                int colType = DBResultSet.fromJavaSQLType(dbMetadata, i, needTypeName);
                Column col = new Column(colName, colType);
                col.setTitle(colName);
                this.getMetadata().addColumn(col);
            }
        }
        catch (SQLException e) {
            throw new DataSetException((Throwable)e);
        }
        this.readers = new ArrayList<FieldReader>();
        for (Column col : this.getMetadata()) {
            FieldReader reader = this.createFieldReader(col);
            this.readers.add(reader);
        }
    }

    public static int fromJavaSQLType(ResultSetMetaData metadata, int index) throws SQLException {
        return DBResultSet.fromJavaSQLType(metadata, index, true);
    }

    private static int fromJavaSQLType(ResultSetMetaData metadata, int index, boolean needTypeName) throws SQLException {
        int sqlType = metadata.getColumnType(index);
        if (sqlType == 2 || sqlType == 3) {
            int precision = metadata.getPrecision(index);
            if (metadata.getScale(index) == 0 && precision <= 10 && precision > 0) {
                return 5;
            }
            if (precision > 15) {
                return 10;
            }
        }
        return DataTypes.fromJavaSQLType((int)sqlType, (String)(needTypeName ? metadata.getColumnTypeName(index) : ""));
    }

    private FieldReader createFieldReader(Column<T> col) throws DataSetException {
        switch (col.getDataType()) {
            case 6: {
                return new FieldReader(){

                    @Override
                    public Object read(ResultSet rs, int index) throws SQLException {
                        return rs.getString(index);
                    }
                };
            }
            case 3: {
                return new FieldReader(){

                    @Override
                    public Object read(ResultSet rs, int index) throws SQLException {
                        double v = rs.getDouble(index);
                        return rs.wasNull() ? null : Double.valueOf(v);
                    }
                };
            }
            case 5: {
                return new FieldReader(){

                    @Override
                    public Object read(ResultSet rs, int index) throws SQLException {
                        int v = rs.getInt(index);
                        return rs.wasNull() ? null : Integer.valueOf(v);
                    }
                };
            }
            case 8: {
                return new FieldReader(){

                    @Override
                    public Object read(ResultSet rs, int index) throws SQLException {
                        long v = rs.getLong(index);
                        return rs.wasNull() ? null : Long.valueOf(v);
                    }
                };
            }
            case 2: {
                return new FieldReader(){

                    @Override
                    public Object read(ResultSet rs, int index) throws SQLException {
                        Timestamp v = rs.getTimestamp(index);
                        if (rs.wasNull()) {
                            return null;
                        }
                        Calendar cal = Calendar.getInstance();
                        cal.setTimeInMillis(((Date)v).getTime());
                        return cal;
                    }
                };
            }
            case 10: {
                return new FieldReader(){

                    @Override
                    public Object read(ResultSet rs, int index) throws SQLException {
                        BigDecimal v = rs.getBigDecimal(index);
                        return rs.wasNull() ? null : v;
                    }
                };
            }
            case 1: {
                return new FieldReader(){

                    @Override
                    public Object read(ResultSet rs, int index) throws SQLException {
                        boolean v = rs.getBoolean(index);
                        return rs.wasNull() ? null : Boolean.valueOf(v);
                    }
                };
            }
            case 9: {
                return new FieldReader(){

                    @Override
                    public Object read(ResultSet rs, int index) throws SQLException {
                        byte[] v = rs.getBytes(index);
                        return rs.wasNull() ? null : new BlobValue(v);
                    }
                };
            }
        }
        throw new DataSetException("\u5904\u7406\u5b57\u6bb5" + col.getName() + "\u65f6\u9047\u5230\u5c1a\u672a\u652f\u6301\u7684\u6570\u636e\u7c7b\u578b\uff1a" + col.getDataType());
    }

    public Iterator<DataRow> iterator() {
        if (this.readers == null || this.readers.isEmpty()) {
            return new Iterator<DataRow>(){

                @Override
                public boolean hasNext() {
                    return false;
                }

                @Override
                public DataRow next() {
                    return null;
                }

                @Override
                public void remove() {
                }
            };
        }
        return new Itr();
    }

    public DataRow add() throws DataSetException {
        throw new DataSetException("\u6570\u636e\u96c6\u5c01\u88c5\u53ea\u80fd\u8fdb\u884c\u53ea\u8bfb\u64cd\u4f5c\u3002");
    }

    public void clear() throws DataSetException {
        throw new DataSetException("\u6570\u636e\u96c6\u5c01\u88c5\u53ea\u80fd\u8fdb\u884c\u53ea\u8bfb\u64cd\u4f5c\u3002");
    }

    public MemoryDataSet<T> toMemory() throws DataSetException {
        MemoryDataSet mds = new MemoryDataSet(this.infoClass, this.metadata.clone());
        Iterator<DataRow> iterator = this.iterator();
        while (iterator.hasNext()) {
            DataRow row = iterator.next();
            mds.add(row.getBuffer());
        }
        return mds;
    }

    @Override
    public void close() throws Exception {
        if (this.resultSet != null) {
            this.resultSet.close();
            this.resultSet = null;
        }
        this.readers = null;
    }

    private final class Itr
    implements Iterator<DataRow> {
        private Itr() {
        }

        @Override
        public boolean hasNext() {
            try {
                return DBResultSet.this.resultSet.next();
            }
            catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public DataRow next() {
            MemoryDataRow row = new MemoryDataRow();
            row._setBuffer(new Object[DBResultSet.this.readers.size()]);
            int index = 1;
            for (FieldReader reader : DBResultSet.this.readers) {
                Object value;
                try {
                    value = reader.read(DBResultSet.this.resultSet, index);
                }
                catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                row.setValue(index - 1, value);
                ++index;
            }
            return row;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("\u6570\u636e\u96c6\u5c01\u88c5\u53ea\u80fd\u8fdb\u884c\u53ea\u8bfb\u64cd\u4f5c\u3002");
        }
    }

    private static abstract class FieldReader {
        private FieldReader() {
        }

        public abstract Object read(ResultSet var1, int var2) throws SQLException;
    }
}

