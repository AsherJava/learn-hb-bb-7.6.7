/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDataQuery
 *  com.jiuqi.np.dataengine.intf.IGroupingQuery
 *  com.jiuqi.np.dataengine.intf.IGroupingTable
 *  com.jiuqi.np.dataengine.intf.IReadonlyTable
 */
package com.jiuqi.nr.data.engine.dataquery;

import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IDataQuery;
import com.jiuqi.np.dataengine.intf.IGroupingQuery;
import com.jiuqi.np.dataengine.intf.IGroupingTable;
import com.jiuqi.np.dataengine.intf.IReadonlyTable;
import java.util.List;

public interface IMultiPeriodQuery {
    public IGroupingTable getMultiPeriodTable(ExecutorContext var1, List<String> var2, IGroupingQuery var3) throws Exception;

    public IReadonlyTable getMultiPeriodTable(ExecutorContext var1, List<String> var2, IDataQuery var3) throws Exception;
}

