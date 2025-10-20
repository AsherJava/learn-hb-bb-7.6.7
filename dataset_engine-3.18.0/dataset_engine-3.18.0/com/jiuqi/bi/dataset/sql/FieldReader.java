/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.Column
 *  com.jiuqi.bi.dataset.DataRow
 *  com.jiuqi.bi.dataset.Metadata
 */
package com.jiuqi.bi.dataset.sql;

import com.jiuqi.bi.dataset.BIDataSetFieldInfo;
import com.jiuqi.bi.dataset.Column;
import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.Metadata;
import com.jiuqi.bi.dataset.model.field.DSCalcField;
import com.jiuqi.bi.dataset.model.field.DSField;
import com.jiuqi.bi.dataset.model.field.FieldType;
import com.jiuqi.bi.dataset.sql.SQLModel;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class FieldReader {
    protected final int srcIndex;
    protected final int destIndex;

    public FieldReader(int srcIndex, int destIndex) {
        this.srcIndex = srcIndex;
        this.destIndex = destIndex;
    }

    public abstract void read(ResultSet var1, DataRow var2) throws SQLException;

    public static final List<FieldReader> buildMemoryDataSet(SQLModel model, ResultSetMetaData metadata) throws SQLException {
        List<DSField> fields = model.getFields();
        Map<String, Integer> nameFinder = FieldReader.createNameFinder(metadata);
        Map<String, Integer> labelFinder = FieldReader.createLabelFinder(metadata);
        ArrayList<FieldReader> readers = new ArrayList<FieldReader>();
        block7: for (int i = 0; i < fields.size(); ++i) {
            DSField field = fields.get(i);
            if (field instanceof DSCalcField) continue;
            String fieldName = field.getName().toUpperCase();
            Integer srcIndex = labelFinder.get(fieldName);
            if (srcIndex == null) {
                srcIndex = nameFinder.get(fieldName);
            }
            if (srcIndex == null) {
                throw new SQLException("\u67e5\u627e\u5b57\u6bb5\u4e0d\u5b58\u5728\uff1a" + field.getName());
            }
            switch (field.getValType()) {
                case 1: {
                    readers.add(new BooleanFieldReader(srcIndex, i));
                    continue block7;
                }
                case 5: {
                    readers.add(new IntegerFieldReader(srcIndex, i));
                    continue block7;
                }
                case 6: {
                    readers.add(new StringFieldReader(srcIndex, i));
                    continue block7;
                }
                case 3: 
                case 8: {
                    readers.add(new DoubleFieldReader(srcIndex, i));
                    continue block7;
                }
                case 2: {
                    if (field.getFieldType() == FieldType.GENERAL_DIM || field.getFieldType() == FieldType.TIME_DIM) {
                        readers.add(new DateFieldDimReader(srcIndex, i, metadata.getColumnType(i + 1)));
                        continue block7;
                    }
                    readers.add(new DateFieldReader(srcIndex, i, metadata.getColumnType(i + 1)));
                    continue block7;
                }
                default: {
                    throw new SQLException("\u8bfb\u53d6\u5b57\u6bb5[" + field.getName() + "]\u65f6\u9047\u5230\u672a\u652f\u6301\u7684\u6570\u636e\u7c7b\u578b\uff1a" + field.getValType());
                }
            }
        }
        return readers;
    }

    public static final List<FieldReader> buildMemoryDataSet(Metadata<BIDataSetFieldInfo> modelMetadata, ResultSetMetaData rsMetadata) throws SQLException {
        List columns = modelMetadata.getColumns();
        Map<String, Integer> nameFinder = FieldReader.createNameFinder(rsMetadata);
        Map<String, Integer> labelFinder = FieldReader.createLabelFinder(rsMetadata);
        ArrayList<FieldReader> readers = new ArrayList<FieldReader>();
        block7: for (int i = 0; i < columns.size(); ++i) {
            BIDataSetFieldInfo info = (BIDataSetFieldInfo)((Column)columns.get(i)).getInfo();
            if (info.isCalcField()) continue;
            String fieldName = info.getName().toUpperCase();
            Integer srcIndex = nameFinder.get(fieldName);
            if (srcIndex == null) {
                srcIndex = labelFinder.get(fieldName);
            }
            if (srcIndex == null) {
                throw new SQLException("\u67e5\u627e\u5b57\u6bb5\u4e0d\u5b58\u5728\uff1a" + info.getName());
            }
            switch (info.getValType()) {
                case 1: {
                    readers.add(new BooleanFieldReader(srcIndex, i));
                    continue block7;
                }
                case 5: {
                    readers.add(new IntegerFieldReader(srcIndex, i));
                    continue block7;
                }
                case 6: {
                    readers.add(new StringFieldReader(srcIndex, i));
                    continue block7;
                }
                case 3: {
                    readers.add(new DoubleFieldReader(srcIndex, i));
                    continue block7;
                }
                case 2: {
                    if (info.getFieldType() == FieldType.GENERAL_DIM || info.getFieldType() == FieldType.TIME_DIM) {
                        readers.add(new DateFieldDimReader(srcIndex, i, rsMetadata.getColumnType(i + 1)));
                        continue block7;
                    }
                    readers.add(new DateFieldReader(srcIndex, i, rsMetadata.getColumnType(i + 1)));
                    continue block7;
                }
                default: {
                    throw new SQLException("\u8bfb\u53d6\u5b57\u6bb5[" + info.getName() + "]\u65f6\u9047\u5230\u672a\u652f\u6301\u7684\u6570\u636e\u7c7b\u578b\uff1a" + info.getValType());
                }
            }
        }
        return readers;
    }

    private static Map<String, Integer> createNameFinder(ResultSetMetaData metadata) throws SQLException {
        HashMap<String, Integer> finder = new HashMap<String, Integer>();
        for (int i = 1; i <= metadata.getColumnCount(); ++i) {
            String fieldName = metadata.getColumnName(i);
            if (fieldName == null) continue;
            finder.put(fieldName.toUpperCase(), i);
        }
        return finder;
    }

    private static Map<String, Integer> createLabelFinder(ResultSetMetaData metadata) throws SQLException {
        HashMap<String, Integer> finder = new HashMap<String, Integer>();
        for (int i = 1; i <= metadata.getColumnCount(); ++i) {
            String labelName = metadata.getColumnLabel(i);
            if (labelName == null) continue;
            finder.put(labelName.toUpperCase(), i);
        }
        return finder;
    }

    private static final class DateFieldDimReader
    extends FieldReader {
        public DateFieldDimReader(int srcIndex, int destIndex, int sqlType) {
            super(srcIndex, destIndex);
        }

        @Override
        public void read(ResultSet rs, DataRow row) throws SQLException {
            Timestamp timestamp = rs.getTimestamp(this.srcIndex);
            if (rs.wasNull()) {
                row.setNull(this.destIndex);
            } else {
                Calendar c = Calendar.getInstance();
                c.setTimeInMillis(timestamp.getTime());
                c.set(11, 0);
                c.set(12, 0);
                c.set(13, 0);
                c.set(14, 0);
                row.setDate(this.destIndex, c);
            }
        }
    }

    private static final class DateFieldReader
    extends FieldReader {
        public DateFieldReader(int srcIndex, int destIndex, int sqlType) {
            super(srcIndex, destIndex);
        }

        @Override
        public void read(ResultSet rs, DataRow row) throws SQLException {
            Timestamp valueObj = rs.getTimestamp(this.srcIndex);
            if (valueObj == null) {
                row.setNull(this.destIndex);
            } else {
                Calendar c = Calendar.getInstance();
                c.setTimeInMillis(valueObj.getTime());
                c.set(11, 0);
                c.set(12, 0);
                c.set(13, 0);
                c.set(14, 0);
                row.setDate(this.destIndex, c);
            }
        }
    }

    private static final class BooleanFieldReader
    extends FieldReader {
        public BooleanFieldReader(int srcIndex, int destIndex) {
            super(srcIndex, destIndex);
        }

        @Override
        public void read(ResultSet rs, DataRow row) throws SQLException {
            boolean value = rs.getBoolean(this.srcIndex);
            if (rs.wasNull()) {
                row.setNull(this.destIndex);
            } else {
                row.setBoolean(this.destIndex, value);
            }
        }
    }

    private static final class IntegerFieldReader
    extends FieldReader {
        public IntegerFieldReader(int srcIndex, int destIndex) {
            super(srcIndex, destIndex);
        }

        @Override
        public void read(ResultSet rs, DataRow row) throws SQLException {
            int value = rs.getInt(this.srcIndex);
            if (rs.wasNull()) {
                row.setNull(this.destIndex);
            } else {
                row.setInt(this.destIndex, value);
            }
        }
    }

    private static final class DoubleFieldReader
    extends FieldReader {
        public DoubleFieldReader(int srcIndex, int destIndex) {
            super(srcIndex, destIndex);
        }

        @Override
        public void read(ResultSet rs, DataRow row) throws SQLException {
            double value = rs.getDouble(this.srcIndex);
            if (rs.wasNull()) {
                row.setNull(this.destIndex);
            } else {
                row.setDouble(this.destIndex, value);
            }
        }
    }

    private static final class StringFieldReader
    extends FieldReader {
        private Map<String, String> stringPool = new HashMap<String, String>();

        public StringFieldReader(int srcIndex, int destIndex) {
            super(srcIndex, destIndex);
        }

        @Override
        public void read(ResultSet rs, DataRow row) throws SQLException {
            String value = rs.getString(this.srcIndex);
            if (rs.wasNull()) {
                row.setString(this.destIndex, null);
            } else {
                String str = this.stringPool.get(value);
                if (str == null) {
                    this.stringPool.put(value, value);
                    str = value;
                }
                row.setString(this.destIndex, str);
            }
        }
    }
}

