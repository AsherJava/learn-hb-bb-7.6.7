/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.DataRow
 *  com.jiuqi.bi.dataset.MemoryDataRow
 *  com.jiuqi.bi.dataset.MemoryDataSet
 */
package com.jiuqi.bi.parameter;

import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.MemoryDataRow;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.parameter.model.ParameterColumnInfo;
import java.util.Iterator;

public class ParameterDataRowIterator
implements Iterator<DataRow> {
    private MemoryDataSet<ParameterColumnInfo> dataSet;
    private Iterator<DataRow> iterator;

    public ParameterDataRowIterator(MemoryDataSet<ParameterColumnInfo> dataSet) {
        this.dataSet = dataSet;
        this.iterator = dataSet.iterator();
    }

    @Override
    public boolean hasNext() {
        return this.iterator.hasNext();
    }

    @Override
    public DataRow next() {
        return this.cloneDataRow(this.iterator.next());
    }

    @Override
    public void remove() {
        this.iterator.remove();
    }

    private DataRow cloneDataRow(DataRow row) {
        int colCount = this.dataSet.getMetadata().size();
        Object[] buffer = new Object[colCount];
        MemoryDataRow clonedRow = new MemoryDataRow(buffer);
        for (int i = 0; i < colCount; ++i) {
            clonedRow.setValue(i, row.getValue(i));
        }
        return clonedRow;
    }
}

