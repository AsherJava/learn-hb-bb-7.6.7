/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.dataset;

import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.DataSet;
import com.jiuqi.bi.dataset.DataSetError;
import com.jiuqi.bi.dataset.DataSetException;
import com.jiuqi.bi.dataset.DataSetOptions;
import com.jiuqi.bi.dataset.MemoryDataRow;
import com.jiuqi.bi.dataset.Metadata;
import com.jiuqi.bi.util.collection.PageArrayList;
import java.io.Externalizable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;

public final class MemoryDataSet<T>
extends DataSet<T>
implements Cloneable,
Externalizable {
    private transient List<Object[]> rows;
    private transient MemoryDataRow curRow;

    public MemoryDataSet() {
        this.rows = new ArrayList<Object[]>();
        this.curRow = new MemoryDataRow();
    }

    public MemoryDataSet(Metadata<T> metadata) {
        this(null, metadata);
    }

    public MemoryDataSet(int initialCapacity) {
        this.rows = new ArrayList<Object[]>(initialCapacity);
        this.curRow = new MemoryDataRow();
    }

    public MemoryDataSet(Scale scale) {
        this.rows = scale == Scale.LARGE ? new PageArrayList() : new ArrayList();
        this.curRow = new MemoryDataRow();
    }

    public MemoryDataSet(Class<T> infoClass) {
        super(infoClass);
        this.rows = new ArrayList<Object[]>();
        this.curRow = new MemoryDataRow();
    }

    public MemoryDataSet(Class<T> infoClass, Metadata<T> metadata) {
        super(infoClass, metadata);
        this.rows = new ArrayList<Object[]>();
        this.curRow = new MemoryDataRow();
    }

    public MemoryDataSet(Class<T> infoClass, int initialCapacity) {
        super(infoClass);
        this.rows = new ArrayList<Object[]>(initialCapacity);
        this.curRow = new MemoryDataRow();
    }

    public MemoryDataSet(Class<T> infoClass, Scale scale) {
        super(infoClass);
        this.rows = scale == Scale.LARGE ? new PageArrayList() : new ArrayList();
        this.curRow = new MemoryDataRow();
    }

    public void resetScale(Scale scale) {
        List<Object[]> raw = this.rows;
        this.rows = scale == Scale.LARGE ? new PageArrayList<Object[]>(raw) : new ArrayList<Object[]>(raw);
    }

    @Override
    void doAddColumns(int start, int count) throws DataSetException {
        for (int i = 0; i < this.rows.size(); ++i) {
            Object[] buffer = this.rows.get(i);
            if (buffer == null) continue;
            Object[] newBuffer = new Object[buffer.length + count];
            if (start > 0) {
                System.arraycopy(buffer, 0, newBuffer, 0, start);
            }
            if (start < buffer.length) {
                System.arraycopy(buffer, start, newBuffer, start + count, buffer.length - start);
            }
            this.rows.set(i, newBuffer);
        }
    }

    @Override
    void doRemoveColumns(int start, int count) throws DataSetException {
        for (int i = 0; i < this.rows.size(); ++i) {
            Object[] buffer = this.rows.get(i);
            if (buffer == null) continue;
            Object[] newBuffer = new Object[buffer.length - count];
            if (start > 0) {
                System.arraycopy(buffer, 0, newBuffer, 0, start);
            }
            if (start + count < buffer.length) {
                System.arraycopy(buffer, start + count, newBuffer, start, buffer.length - start - count);
            }
            this.rows.set(i, newBuffer);
        }
    }

    private void checkDataSetSize() {
        if (DataSetOptions.MAX_DATA_SIZE > 0 && this.rows.size() >= DataSetOptions.MAX_DATA_SIZE) {
            throw new DataSetError("\u5185\u5b58\u6570\u636e\u96c6\u5927\u5c0f\u8d85\u51fa\u4e86\u7cfb\u7edf\u8d1f\u8f7d\u9650\u5236(" + DataSetOptions.MAX_DATA_SIZE + ")\uff0c\u8bf7\u51cf\u5c11\u67e5\u8be2\u6570\u636e\u91cf\u6216\u8054\u7cfb\u7ba1\u7406\u5458\u3002");
        }
    }

    @Override
    public DataRow add() {
        this.checkDataSetSize();
        Object[] buffer = new Object[this.getMetadata().size()];
        this.rows.add(buffer);
        return new MemoryDataRow(buffer);
    }

    @Override
    public boolean add(Object[] rowData) throws DataSetException {
        this.checkDataSetSize();
        return this.rows.add(rowData);
    }

    public DataRow add(int index) {
        this.checkDataSetSize();
        Object[] buffer = new Object[this.getMetadata().size()];
        this.rows.add(index, buffer);
        return new MemoryDataRow(buffer);
    }

    @Override
    public void add(DataSet<?> dataset) throws DataSetException {
        this.checkDataSetSize();
        if (dataset instanceof MemoryDataSet) {
            List<Object[]> srcRows = ((MemoryDataSet)dataset).rows;
            for (Object[] row : srcRows) {
                this.rows.add(row);
            }
        } else {
            super.add(dataset);
        }
    }

    public DataRow get(int index) {
        return new MemoryDataRow(this.rows.get(index));
    }

    public DataRow at(int index) {
        this.curRow._setBuffer(this.rows.get(index));
        return this.curRow;
    }

    public Object[] getBuffer(int index) {
        return this.rows.get(index);
    }

    public void remove(int index) {
        this.rows.remove(index);
    }

    @Override
    public void clear() {
        this.rows.clear();
        this.curRow._setBuffer(null);
    }

    @Override
    public Iterator<DataRow> iterator() {
        return new DataRowIterator(this.rows);
    }

    @Override
    public Spliterator<DataRow> spliterator() {
        return Spliterators.spliterator(this.iterator(), (long)this.size(), 0);
    }

    @Override
    public int size() {
        return this.rows.size();
    }

    public void setSize(int newSize) {
        block3: {
            block2: {
                if (newSize >= this.size()) break block2;
                for (int i = this.size() - 1; i >= newSize; --i) {
                    this.rows.remove(i);
                }
                break block3;
            }
            if (newSize <= this.size()) break block3;
            for (int i = newSize; i < this.size(); ++i) {
                this.rows.add(new Object[this.metadata.size()]);
            }
        }
    }

    @Override
    public boolean isEmpty() {
        return this.rows.isEmpty();
    }

    @Override
    public void sort() {
        Collections.sort(this.rows, new Comparator<Object[]>(){

            @Override
            public int compare(Object[] o1, Object[] o2) {
                for (int i = 0; i < MemoryDataSet.this.getMetadata().size(); ++i) {
                    if (o1[i] == o2[i]) continue;
                    if (o1[i] == null) {
                        return -1;
                    }
                    if (o2[i] == null) {
                        return 1;
                    }
                    int c = ((Comparable)o1[i]).compareTo(o2[i]);
                    if (c == 0) continue;
                    return c;
                }
                return 0;
            }
        });
    }

    @Override
    public void sort(final Comparator<DataRow> c) {
        final MemoryDataRow row1 = new MemoryDataRow();
        final MemoryDataRow row2 = new MemoryDataRow();
        Collections.sort(this.rows, new Comparator<Object[]>(){

            @Override
            public int compare(Object[] o1, Object[] o2) {
                row1._setBuffer(o1);
                row2._setBuffer(o2);
                return c.compare(row1, row2);
            }
        });
    }

    @Override
    public MemoryDataSet<T> toMemory() throws DataSetException {
        return this;
    }

    @Override
    public Object clone() {
        MemoryDataSet result = (MemoryDataSet)super.clone();
        result.curRow = new MemoryDataRow();
        result.rows = new ArrayList<Object[]>(this.rows.size());
        for (Object[] buffer : this.rows) {
            result.rows.add((Object[])buffer.clone());
        }
        return result;
    }

    public static enum Scale {
        NORMAL,
        LARGE;

    }

    private static final class DataRowIterator
    implements Iterator<DataRow> {
        private Iterator<Object[]> itr;

        public DataRowIterator(List<Object[]> rows) {
            this.itr = rows.iterator();
        }

        @Override
        public boolean hasNext() {
            return this.itr.hasNext();
        }

        @Override
        public DataRow next() {
            return new MemoryDataRow(this.itr.next());
        }

        @Override
        public void remove() {
            this.itr.remove();
        }
    }
}

