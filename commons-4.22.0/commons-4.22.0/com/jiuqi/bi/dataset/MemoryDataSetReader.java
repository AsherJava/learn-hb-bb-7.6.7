/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.dataset;

import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.DataSetException;
import com.jiuqi.bi.dataset.IDataSetReader;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.dataset.Metadata;

public final class MemoryDataSetReader<T>
implements IDataSetReader<T> {
    private MemoryDataSet<T> dataSet = new MemoryDataSet();

    public MemoryDataSet<T> getDataSet() {
        return this.dataSet;
    }

    @Override
    public void start(Metadata<T> metadata) throws DataSetException {
        this.dataSet.getMetadata().copyFrom(metadata);
    }

    @Override
    public boolean process(DataRow row) throws DataSetException {
        return this.dataSet.add(row.getBuffer());
    }

    @Override
    public void finish() throws DataSetException {
    }
}

