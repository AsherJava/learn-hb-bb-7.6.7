/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.DataRow
 *  com.jiuqi.bi.dataset.DataSetException
 *  com.jiuqi.bi.dataset.IDataRowFilter
 */
package com.jiuqi.bi.publicparam.datasource.timegranularity.filter;

import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.DataSetException;
import com.jiuqi.bi.dataset.IDataRowFilter;
import com.jiuqi.bi.publicparam.datasource.timegranularity.filter.TimegranularityFilter;
import java.util.ArrayList;
import java.util.List;

public class MemoryRowFilter
implements IDataRowFilter {
    private List<TimegranularityFilter> filters = new ArrayList<TimegranularityFilter>();

    public void addFilter(TimegranularityFilter filter) {
        this.filters.add(filter);
    }

    public boolean filter(DataRow row) throws DataSetException {
        for (TimegranularityFilter filter : this.filters) {
            if (filter.judge(row)) continue;
            return false;
        }
        return true;
    }

    public boolean isEmpty() {
        return this.filters.isEmpty();
    }
}

