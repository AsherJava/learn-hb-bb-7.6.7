/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDataQuery
 *  com.jiuqi.np.dataengine.intf.IDataTable
 */
package com.jiuqi.nr.efdc.extract;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IDataQuery;
import com.jiuqi.np.dataengine.intf.IDataTable;
import com.jiuqi.nr.efdc.extract.IExtractResult;

public interface IExtractDataUpdator {
    public boolean changeData(IDataTable var1, IDataQuery var2, ExecutorContext var3, IExtractResult var4, DimensionValueSet var5, String var6, String var7);
}

