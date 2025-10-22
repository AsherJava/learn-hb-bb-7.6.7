/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.DataRow
 *  com.jiuqi.bi.dataset.DataSetException
 *  com.jiuqi.bi.dataset.IDataRowFilter
 *  com.jiuqi.bi.syntax.DataType
 */
package com.jiuqi.nr.bql.datasource.rowfilter;

import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.DataSetException;
import com.jiuqi.bi.dataset.IDataRowFilter;
import com.jiuqi.bi.syntax.DataType;
import java.util.List;

public class AllZeroDataRowFilter
implements IDataRowFilter {
    private List<Integer> measureIndexes;

    public AllZeroDataRowFilter(List<Integer> measureIndexes) {
        this.measureIndexes = measureIndexes;
    }

    public boolean filter(DataRow row) throws DataSetException {
        boolean allZero = true;
        for (int colIndex : this.measureIndexes) {
            Number num;
            Object value = row.getValue(colIndex);
            if (value == null || !(value instanceof Number) || DataType.compare((Number)(num = (Number)value), (Number)0) == 0) continue;
            allZero = false;
        }
        return !allZero;
    }
}

