/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datacrud.impl.nested;

import com.jiuqi.nr.datacrud.IRowData;
import com.jiuqi.nr.datacrud.LinkSort;
import com.jiuqi.nr.datacrud.impl.nested.NestedRow;
import java.util.List;

public class DataRowWrapper
extends NestedRow {
    private IRowData rowData;

    public DataRowWrapper(List<LinkSort> sorts) {
        super(sorts);
    }

    public IRowData getRowData() {
        return this.rowData;
    }

    public void setRowData(IRowData rowData) {
        this.rowData = rowData;
    }
}

