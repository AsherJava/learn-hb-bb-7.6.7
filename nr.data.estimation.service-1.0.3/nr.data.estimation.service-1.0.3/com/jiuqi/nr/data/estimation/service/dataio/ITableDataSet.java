/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.estimation.service.dataio;

import com.jiuqi.nr.data.estimation.service.dataio.ITableCellValue;
import java.io.Serializable;
import java.util.Iterator;

public interface ITableDataSet
extends Serializable {
    public boolean isEmpty();

    public int getRowCount();

    public String[] getColumns();

    public ITableCellValue getCellValue(int var1, String var2);

    public Iterator<ITableCellValue[]> rowIterator();

    public Iterator<ITableCellValue[]> columnIterator();
}

