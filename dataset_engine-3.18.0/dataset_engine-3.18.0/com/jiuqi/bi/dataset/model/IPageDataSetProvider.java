/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.MemoryDataSet
 */
package com.jiuqi.bi.dataset.model;

import com.jiuqi.bi.dataset.BIDataSetException;
import com.jiuqi.bi.dataset.BIDataSetFieldInfo;
import com.jiuqi.bi.dataset.IDSContext;
import com.jiuqi.bi.dataset.MemoryDataSet;

public interface IPageDataSetProvider {
    public void setPageSize(int var1);

    public int open(MemoryDataSet<BIDataSetFieldInfo> var1, int var2, IDSContext var3) throws BIDataSetException;

    public int getRecordCount(IDSContext var1) throws BIDataSetException;

    public int getPageCount(IDSContext var1) throws BIDataSetException;
}

