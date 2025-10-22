/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.grid.GridData
 */
package com.jiuqi.np.office.excel;

import com.jiuqi.np.grid.GridData;
import com.jiuqi.np.office.excel.ExcelException;

public interface GridIterator {
    public boolean next() throws ExcelException;

    public String getTitle();

    public GridData getGridData();
}

