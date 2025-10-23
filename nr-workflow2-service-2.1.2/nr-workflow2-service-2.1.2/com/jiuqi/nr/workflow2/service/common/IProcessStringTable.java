/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.service.common;

import java.util.Iterator;
import java.util.Map;

public interface IProcessStringTable {
    public void incrementRowCount(int var1);

    public void incrementColNames(String[] var1);

    public void insertRowData(int var1, Map<String, String> var2);

    public void updateRowData(int var1, Map<String, String> var2);

    public String[] removeRowData(int var1);

    public void insertColData(String var1, String var2, String[] var3);

    public void updateColData(String var1, String[] var2);

    public String[] removeColData(String var1);

    public void mergeATable(IProcessStringTable var1);

    public void setCellValue(int var1, String var2, String var3);

    public String getCellValue(int var1, String var2);

    public String[] getRowData(int var1);

    public String[] getColData(String var1);

    public int[] findRowIndex(Map<String, String> var1);

    public String[] getColNames();

    public int getRowCount();

    public boolean isEmpty();

    public IProcessStringTable andConditionRows(Map<String, String> var1);

    public IProcessStringTable subTable(int var1, int var2);

    public Iterator<String[]> rowIterator();

    public Iterator<String[]> columnIterator();

    public Map<String, Integer> getColIdxMap();
}

