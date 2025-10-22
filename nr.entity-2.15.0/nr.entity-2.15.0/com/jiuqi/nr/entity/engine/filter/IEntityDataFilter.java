/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 */
package com.jiuqi.nr.entity.engine.filter;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.executors.QueryContext;

public interface IEntityDataFilter {
    public String getDataFilter();

    public String getExpression();

    public DimensionValueSet getMasterKey();

    public String getDimensionName();

    public ExecutorContext getContext();

    public QueryContext getQueryContext();

    public String getEntityId();

    public void setEntityId(String var1);

    public Object getCache(String var1);

    public void putCache(String var1, Object var2);
}

