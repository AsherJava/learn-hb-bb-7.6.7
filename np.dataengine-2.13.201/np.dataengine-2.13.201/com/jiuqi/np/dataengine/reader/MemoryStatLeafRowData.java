/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.DataRow
 */
package com.jiuqi.np.dataengine.reader;

import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.reader.MemoryRowData;
import com.jiuqi.np.dataengine.reader.MemoryStatLeafColumn;
import com.jiuqi.np.dataengine.reader.QueryFieldInfo;
import java.util.ArrayList;
import java.util.List;

public class MemoryStatLeafRowData
extends MemoryRowData {
    private List<MemoryStatLeafColumn> statColumns;
    private boolean loaded = false;

    public MemoryStatLeafRowData(DimensionValueSet rowKey, List<QueryFieldInfo> columns) {
        super(rowKey, columns.size());
        this.statColumns = new ArrayList<MemoryStatLeafColumn>(columns.size());
        for (QueryFieldInfo queryFieldInfo : columns) {
            MemoryStatLeafColumn statColumn = new MemoryStatLeafColumn(queryFieldInfo);
            this.statColumns.add(statColumn);
        }
    }

    @Override
    public void loadData(DataRow row, boolean sumDatas, int memeryStartIndex, int rowKeyStartIndex, int bizKeyOrderIndex) {
        for (int i = 0; i < rowKeyStartIndex; ++i) {
            Object value = row.getValue(i);
            this.statColumns.get(i).statistic(value);
        }
        if (bizKeyOrderIndex >= 0) {
            this.setRecKey((String)row.getValue(bizKeyOrderIndex));
        }
    }

    @Override
    public Object[] getDatas() {
        if (!this.loaded) {
            for (int i = 0; i < this.statColumns.size(); ++i) {
                this.datas[i] = this.statColumns.get(i).getValue();
            }
            this.loaded = true;
        }
        return this.datas;
    }
}

