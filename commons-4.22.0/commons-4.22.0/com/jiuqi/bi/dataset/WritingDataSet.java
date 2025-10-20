/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.dataset;

import com.jiuqi.bi.dataset.BIFFSerializer;
import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.DataSet;
import com.jiuqi.bi.dataset.DataSetException;
import com.jiuqi.bi.dataset.Metadata;
import com.jiuqi.bi.io.BIFFWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class WritingDataSet<T>
extends DataSet<T> {
    private BIFFWriter writer;
    private BIFFSerializer serializer;
    private BIFFSerializer.Context context;
    private Set<String> dictColumns = new HashSet<String>();
    private int count;

    public WritingDataSet(OutputStream output) {
        this.writer = new BIFFWriter(output);
    }

    public WritingDataSet(OutputStream output, Metadata<T> metadata) {
        super(null, metadata);
        this.writer = new BIFFWriter(output);
    }

    public WritingDataSet(OutputStream output, Class<T> infoClass) {
        super(infoClass);
        this.writer = new BIFFWriter(output);
    }

    public Set<String> getDictColumns() {
        return this.dictColumns;
    }

    public void beginWriting() throws DataSetException {
        this.serializer = new BIFFSerializer(this);
        this.serializer.getDictColumns().addAll(this.dictColumns);
        try {
            this.context = this.serializer.createWriteContext(this.writer);
        }
        catch (IOException e) {
            throw new DataSetException(e);
        }
        this.count = 0;
    }

    public void endWriting() throws DataSetException {
        this.context = null;
        this.serializer = null;
        try {
            this.writer.close();
        }
        catch (IOException e) {
            throw new DataSetException(e);
        }
        this.writer = null;
    }

    public void setError(String message) throws DataSetException {
        try {
            this.writer.writeError(message);
        }
        catch (IOException e) {
            throw new DataSetException(e);
        }
    }

    public void setError(Throwable error) throws DataSetException {
        try {
            this.writer.writeError(error);
        }
        catch (IOException e) {
            throw new DataSetException(e);
        }
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
        return new WritingDataRow(this.getMetadata().size());
    }

    @Override
    public boolean add(Object[] rowData) throws DataSetException {
        WritingDataRow row = new WritingDataRow(rowData);
        return ((DataRow)row).commit();
    }

    @Override
    public void clear() throws DataSetException {
    }

    @Override
    public int size() {
        return this.count;
    }

    @Override
    public boolean isEmpty() {
        return this.count > 0;
    }

    private final class WritingDataRow
    extends DataRow {
        public WritingDataRow(int length) {
            super(length);
        }

        public WritingDataRow(Object[] buffer) {
            super(buffer);
        }

        @Override
        public boolean commit() throws DataSetException {
            try {
                WritingDataSet.this.context.write(WritingDataSet.this.writer, this);
            }
            catch (IOException e) {
                throw new DataSetException(e);
            }
            WritingDataSet.this.count++;
            return true;
        }
    }
}

