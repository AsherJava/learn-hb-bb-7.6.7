/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.dataset;

import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.DataSetException;

public final class MemoryDataRow
extends DataRow {
    public MemoryDataRow() {
    }

    public MemoryDataRow(int length) {
        super(length);
    }

    public MemoryDataRow(Object[] buffer) {
        super(buffer);
    }

    @Override
    public boolean commit() throws DataSetException {
        return true;
    }
}

