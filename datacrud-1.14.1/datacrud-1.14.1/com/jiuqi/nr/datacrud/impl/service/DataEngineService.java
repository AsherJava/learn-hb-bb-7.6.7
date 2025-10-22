/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.IDatabase
 *  com.jiuqi.np.dataengine.QueryParam
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDataAssist
 *  com.jiuqi.np.dataengine.intf.IDataQuery
 *  com.jiuqi.np.dataengine.intf.IGroupingQuery
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 */
package com.jiuqi.nr.datacrud.impl.service;

import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.np.dataengine.QueryParam;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IDataAssist;
import com.jiuqi.np.dataengine.intf.IDataQuery;
import com.jiuqi.np.dataengine.intf.IGroupingQuery;
import com.jiuqi.nr.datacrud.impl.RegionRelation;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;

public interface DataEngineService {
    public IDataQuery getDataQuery();

    public IDataQuery getDataQuery(RegionRelation var1);

    public ExecutorContext getExecutorContext(RegionRelation var1, DimensionCombination var2);

    public ExecutorContext getExecutorContext(RegionRelation var1, DimensionValueSet var2);

    public AbstractData expressionEvaluate(String var1, ExecutorContext var2, DimensionValueSet var3);

    public AbstractData expressionEvaluate(String var1, ExecutorContext var2, DimensionValueSet var3, RegionRelation var4);

    public IGroupingQuery getGroupingQuery(RegionRelation var1);

    public IDataAssist getDataAssist(ExecutorContext var1);

    public QueryParam getQueryParam();

    public IDatabase getDatabase();
}

