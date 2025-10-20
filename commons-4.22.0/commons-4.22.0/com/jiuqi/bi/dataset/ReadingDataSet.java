/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.dataset;

import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.DataSet;
import com.jiuqi.bi.dataset.DataSetException;
import com.jiuqi.bi.dataset.IDataSetReader;
import java.util.Iterator;

public class ReadingDataSet<T>
extends DataSet<T> {
    private IDataSetReader<T> reader;
    private int count;

    public ReadingDataSet(IDataSetReader<T> reader) {
        this.reader = reader;
    }

    public ReadingDataSet(IDataSetReader<T> reader, Class<T> infoClass) {
        super(infoClass);
        this.reader = reader;
    }

    @Override
    public Iterator<DataRow> iterator() {
        return new Iterator<DataRow>(){

            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public DataRow next() {
                throw new UnsupportedOperationException();
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    @Override
    public DataRow add() throws DataSetException {
        return new ReadingDataRow(this.getMetadata().size());
    }

    @Override
    public boolean add(Object[] rowData) throws DataSetException {
        ReadingDataRow row = new ReadingDataRow(rowData);
        return ((DataRow)row).commit();
    }

    @Override
    public void clear() throws DataSetException {
    }

    @Override
    void beginUpdate() throws DataSetException {
        this.reader.start(this.getMetadata());
        this.count = 0;
    }

    @Override
    void endUpdate() throws DataSetException {
        this.reader.finish();
    }

    @Override
    public int size() {
        return this.count;
    }

    private final class ReadingDataRow
    extends DataRow {
        public ReadingDataRow(int length) {
            super(length);
        }

        public ReadingDataRow(Object[] buffer) {
            super(buffer);
        }

        @Override
        public boolean commit() throws DataSetException {
            boolean success = ReadingDataSet.this.reader.process(this);
            if (success) {
                ReadingDataSet.this.count++;
            }
            return success;
        }
    }
}

