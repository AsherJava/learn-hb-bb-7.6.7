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
import com.jiuqi.bi.dataset.model.IDataSetProvider;

public interface IDataSetWithDistinctProvider
extends IDataSetProvider {
    public void distinct(MemoryDataSet<BIDataSetFieldInfo> var1, IDSContext var2) throws BIDataSetException;
}

