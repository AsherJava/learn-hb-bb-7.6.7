/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.memdb.api.DBRecord
 */
package com.jiuqi.nr.tds.bdf;

import com.jiuqi.nr.tds.TdRowData;
import com.jiuqi.nr.tds.bdf.BlockFileColumn;
import com.jiuqi.nr.tds.bdf.BlockFileModel;
import com.jiuqi.nvwa.memdb.api.DBRecord;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Spliterator;
import java.util.stream.Stream;

public class DbRowData
implements TdRowData {
    private final DBRecord dbRecord;
    private final Map<String, Integer> name2Index;
    private final BlockFileModel blockFileModel;

    public DbRowData(DBRecord dbRecord, BlockFileModel blockFileModel, Map<String, Integer> name2Index) {
        this.dbRecord = dbRecord;
        this.blockFileModel = blockFileModel;
        this.name2Index = name2Index;
    }

    @Override
    public int length() {
        return this.dbRecord.length();
    }

    @Override
    public boolean isNull(int index) {
        return this.dbRecord.isNull(index);
    }

    @Override
    public boolean isNull(String name) {
        Integer index = this.name2Index.get(name);
        if (index == null) {
            return true;
        }
        return this.isNull(index);
    }

    @Override
    public Object getValue(int index) {
        int size = this.blockFileModel.getColumns().size();
        if (size <= index || index < 0) {
            throw new IllegalArgumentException("column index out of range");
        }
        BlockFileColumn blockFileColumn = this.blockFileModel.getColumns().get(index);
        if (blockFileColumn.getDataType() == 2) {
            Object value = this.dbRecord.getValue(index);
            if (value == null) {
                return null;
            }
            if (value instanceof String) {
                long l = Long.parseLong(String.valueOf(value));
                return new Date(l);
            }
            if (value instanceof Number) {
                return new Date(((Number)value).longValue());
            }
            if (value instanceof Date) {
                return value;
            }
        }
        return this.dbRecord.getValue(index);
    }

    @Override
    public Object getValue(String name) {
        Integer index = this.name2Index.get(name);
        if (index == null) {
            return null;
        }
        return this.getValue(index);
    }

    @Override
    public int getInt(int index) {
        return this.dbRecord.getInt(index);
    }

    @Override
    public long getLong(int index) {
        return this.dbRecord.getLong(index);
    }

    @Override
    public double getDouble(int index) {
        return this.dbRecord.getDouble(index);
    }

    @Override
    public BigDecimal getBigDecimal(int index) {
        return this.dbRecord.getBigDecimal(index);
    }

    @Override
    public String getString(int index) {
        return this.dbRecord.getString(index);
    }

    @Override
    public Object[] toArray() {
        return this.dbRecord.toArray();
    }

    @Override
    public Spliterator<Object> spliterator() {
        return this.dbRecord.spliterator();
    }

    @Override
    public Stream<Object> stream() {
        return this.dbRecord.stream();
    }

    @Override
    public Iterator<Object> iterator() {
        return this.dbRecord.iterator();
    }
}

