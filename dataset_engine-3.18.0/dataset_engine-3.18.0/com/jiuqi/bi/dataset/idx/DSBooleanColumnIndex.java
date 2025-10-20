/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.dataset.idx;

import com.jiuqi.bi.dataset.BIDataSetImpl;
import com.jiuqi.bi.dataset.idx.DSColumnIndex;

class DSBooleanColumnIndex
extends DSColumnIndex {
    public DSBooleanColumnIndex(BIDataSetImpl dataset, int colIdx) {
        super(dataset, colIdx);
    }

    @Override
    protected Object accept(Object data) {
        if (data instanceof Number) {
            int intVal = ((Number)data).intValue();
            return intVal == 0 ? Boolean.FALSE : Boolean.TRUE;
        }
        return data;
    }
}

