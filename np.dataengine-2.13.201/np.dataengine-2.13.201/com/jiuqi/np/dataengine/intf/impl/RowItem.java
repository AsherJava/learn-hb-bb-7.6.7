/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.dataengine.intf.impl;

import com.jiuqi.np.dataengine.intf.impl.DataRowImpl;

public class RowItem {
    private DataRowImpl dataRowImpl;
    private int rowIndex;

    public RowItem(DataRowImpl dataRowImpl, int rowIndex) {
        this.dataRowImpl = dataRowImpl;
        this.rowIndex = rowIndex;
    }

    public DataRowImpl getDataRowImpl() {
        return this.dataRowImpl;
    }

    public int getRowIndex() {
        return this.rowIndex;
    }
}

