/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.dataengine;

import com.jiuqi.np.dataengine.QueryEnvironment;
import com.jiuqi.np.dataengine.intf.IDataQuery;
import com.jiuqi.np.dataengine.intf.IGroupingQuery;

public interface IDataQueryFactory {
    public IDataQuery getDataQuery(QueryEnvironment var1);

    public IGroupingQuery getGroupingQuery(QueryEnvironment var1);
}

