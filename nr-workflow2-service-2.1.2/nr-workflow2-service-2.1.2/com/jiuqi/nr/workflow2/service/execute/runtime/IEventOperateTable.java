/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.IEventOperateInfo
 */
package com.jiuqi.nr.workflow2.service.execute.runtime;

import com.jiuqi.nr.workflow2.engine.core.event.runtime.IEventOperateInfo;
import java.util.Iterator;
import java.util.Map;

public interface IEventOperateTable {
    public void incrementRowCount(int var1);

    public void incrementColNames(String[] var1);

    public void insertRowData(int var1, Map<String, IEventOperateInfo> var2);

    public void updateRowData(int var1, Map<String, IEventOperateInfo> var2);

    public IEventOperateInfo[] removeRowData(int var1);

    public void insertColData(String var1, String var2, IEventOperateInfo[] var3);

    public void updateColData(String var1, IEventOperateInfo[] var2);

    public IEventOperateInfo[] removeColData(String var1);

    public void setCellValue(int var1, String var2, IEventOperateInfo var3);

    public IEventOperateInfo getCellValue(int var1, String var2);

    public IEventOperateInfo[] getRowData(int var1);

    public IEventOperateInfo[] getColData(String var1);

    public String[] getColNames();

    public int getRowCount();

    public Map<String, Integer> getColIdxMap();

    public boolean isEmpty();

    public void mergeATable(IEventOperateTable var1);

    public IEventOperateTable subTable(int var1, int var2);

    public Iterator<IEventOperateInfo[]> rowIterator();

    public Iterator<IEventOperateInfo[]> columnIterator();
}

