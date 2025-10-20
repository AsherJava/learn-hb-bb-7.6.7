/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.BIDataSetException
 *  com.jiuqi.bi.dataset.DataRow
 *  com.jiuqi.bi.dataset.IDSFilter
 *  com.jiuqi.bi.util.ArrayKey
 */
package com.jiuqi.bi.quickreport.engine.parser.field;

import com.jiuqi.bi.dataset.BIDataSetException;
import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.IDSFilter;
import com.jiuqi.bi.util.ArrayKey;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class DistinctFilter
implements IDSFilter {
    private final List<Integer> cols;
    private Set<ArrayKey> keys;

    public DistinctFilter(List<Integer> cols) {
        this.cols = cols;
        this.keys = new HashSet<ArrayKey>();
    }

    public boolean judge(DataRow row) throws BIDataSetException {
        Object[] buffer = new Object[this.cols.size()];
        for (int i = 0; i < this.cols.size(); ++i) {
            buffer[i] = row.getValue(this.cols.get(i).intValue());
        }
        ArrayKey key = new ArrayKey(buffer);
        return this.keys.add(key);
    }
}

