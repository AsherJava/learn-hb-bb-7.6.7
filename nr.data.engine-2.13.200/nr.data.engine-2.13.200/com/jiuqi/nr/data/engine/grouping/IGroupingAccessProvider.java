/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.IDataQueryFactory
 *  com.jiuqi.np.dataengine.QueryEnvironment
 *  com.jiuqi.np.dataengine.intf.IGroupingQuery
 */
package com.jiuqi.nr.data.engine.grouping;

import com.jiuqi.np.dataengine.IDataQueryFactory;
import com.jiuqi.np.dataengine.QueryEnvironment;
import com.jiuqi.np.dataengine.intf.IGroupingQuery;

public interface IGroupingAccessProvider {
    public void registerDataQuery(IDataQueryFactory var1);

    public IGroupingQuery newGroupingQuery();

    public IGroupingQuery newGroupingQuery(QueryEnvironment var1);
}

