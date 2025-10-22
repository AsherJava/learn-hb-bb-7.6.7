/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.DataRow
 *  com.jiuqi.bi.dataset.DataSetException
 *  com.jiuqi.bi.dataset.IDataRowFilter
 */
package com.jiuqi.nr.bql.datasource.rowfilter;

import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.DataSetException;
import com.jiuqi.bi.dataset.IDataRowFilter;
import java.util.List;

public class AllNullDataRowFilter
implements IDataRowFilter {
    private List<Integer> measureIndexes;

    public AllNullDataRowFilter(List<Integer> measureIndexes) {
        this.measureIndexes = measureIndexes;
    }

    public boolean filter(DataRow row) throws DataSetException {
        boolean allNull = true;
        for (int colIndex : this.measureIndexes) {
            Object value = row.getValue(colIndex);
            if (value == null) continue;
            allNull = false;
            break;
        }
        return !allNull;
    }
}

