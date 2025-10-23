/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 */
package com.jiuqi.nr.workflow2.service.helper;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;

public interface IProcessEntityQueryHelper {
    public IEntityTable getIEntityTable(String var1);

    public IEntityTable getIEntityTable(String var1, String var2) throws Exception;

    public IEntityTable getIEntityTable(String var1, String var2, DimensionValueSet var3) throws Exception;

    public IEntityTable getIEntityTable(IEntityQuery var1, ExecutorContext var2) throws Exception;

    public IEntityQuery makeIEntityQuery(String var1, String var2);

    public ExecutorContext makeExecuteContext(String var1, String var2);
}

