/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.dataset.idx;

import com.jiuqi.bi.dataset.BIDataSetImpl;
import com.jiuqi.bi.dataset.idx.DSIndexEntry;
import java.util.ArrayList;

class DSColumnIndex
extends DSIndexEntry {
    protected int colIdx;

    public DSColumnIndex(BIDataSetImpl dataset, int colIdx) {
        super(dataset);
        this.colIdx = colIdx;
    }

    @Override
    protected Object accept(Object data) {
        if (data instanceof Number) {
            data = ((Number)data).intValue();
        }
        return data;
    }

    @Override
    protected void process(int rowIdx, Object[] rowData) {
        ArrayList<Integer> colList;
        Object data = rowData[this.colIdx];
        if (data instanceof Number) {
            data = ((Number)data).intValue();
        }
        if ((colList = (ArrayList<Integer>)this.valueMap.get(data)) == null) {
            colList = new ArrayList<Integer>();
            this.valueMap.put(data, colList);
        }
        colList.add(rowIdx);
    }
}

