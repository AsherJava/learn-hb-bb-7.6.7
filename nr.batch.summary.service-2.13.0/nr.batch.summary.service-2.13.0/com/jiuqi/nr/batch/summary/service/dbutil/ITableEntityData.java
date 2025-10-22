/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.batch.summary.service.dbutil;

import java.io.Serializable;
import java.util.Iterator;

public interface ITableEntityData
extends Serializable {
    public boolean isEmpty();

    public int getRowCount();

    public String[] getColumns();

    public Object getCellValue(int var1, String var2);

    public Iterator<Object[]> rowIterator();

    public Iterator<Object[]> columnIterator();
}

