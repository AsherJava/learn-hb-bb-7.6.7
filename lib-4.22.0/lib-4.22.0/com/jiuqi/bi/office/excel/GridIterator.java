/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.office.excel;

import com.jiuqi.bi.grid.GridData;
import com.jiuqi.bi.office.excel.ExcelException;

public interface GridIterator {
    public boolean next() throws ExcelException;

    public String getTitle();

    public GridData getGridData();
}

