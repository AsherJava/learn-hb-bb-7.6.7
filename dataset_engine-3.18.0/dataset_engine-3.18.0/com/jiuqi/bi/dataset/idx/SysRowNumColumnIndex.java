/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.dataset.idx;

import com.jiuqi.bi.dataset.BIDataSetImpl;
import com.jiuqi.bi.dataset.idx.DSColumnIndex;
import java.util.Collections;
import java.util.List;

public class SysRowNumColumnIndex
extends DSColumnIndex {
    public SysRowNumColumnIndex(BIDataSetImpl dataset, int colIdx) {
        super(dataset, colIdx);
    }

    @Override
    protected Object accept(Object data) {
        return data;
    }

    @Override
    protected void process(int rowIdx, Object[] rowData) {
    }

    @Override
    public List<Integer> search(Object data) {
        int pos;
        if (data instanceof Number && (pos = ((Number)data).intValue() - 1) >= 0) {
            return Collections.singletonList(pos);
        }
        return Collections.emptyList();
    }
}

