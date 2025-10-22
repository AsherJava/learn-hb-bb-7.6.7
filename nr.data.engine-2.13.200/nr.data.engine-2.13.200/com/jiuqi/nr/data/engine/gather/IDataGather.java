/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.QueryParam
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IMonitor
 *  com.jiuqi.np.dataengine.intf.IQuerySqlUpdater
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.nr.common.log.UnitReportLog
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 */
package com.jiuqi.nr.data.engine.gather;

import com.jiuqi.np.dataengine.QueryParam;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IMonitor;
import com.jiuqi.np.dataengine.intf.IQuerySqlUpdater;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.common.log.UnitReportLog;
import com.jiuqi.nr.data.engine.gather.GatherCondition;
import com.jiuqi.nr.data.engine.gather.GatherEntityFilterProvider;
import com.jiuqi.nr.data.engine.gather.GatherEntityMap;
import com.jiuqi.nr.data.engine.gather.GatherEventHandler;
import com.jiuqi.nr.data.engine.gather.NodeCheckResult;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import java.util.List;

public interface IDataGather {
    public void setGatherCondition(GatherCondition var1);

    public void setMonitor(IMonitor var1);

    public void setEntityFilterProvider(GatherEntityFilterProvider var1);

    public void setQuerySqlUpdater(IQuerySqlUpdater var1);

    public void setQueryParam(QueryParam var1);

    public void setGatherEventHandler(GatherEventHandler var1);

    public void setUnitReportLog(UnitReportLog var1);

    public void executeNodeGather(ExecutorContext var1, String var2) throws Exception;

    public void executeNodeGatherByDim(ExecutorContext var1, String var2) throws Exception;

    public NodeCheckResult executeNodeCheck(ExecutorContext var1, String var2) throws Exception;

    public NodeCheckResult executeBatchNodeCheck(ExecutorContext var1, List<String> var2) throws Exception;

    public void executeSelectGather(ExecutorContext var1, String var2, List<String> var3) throws Exception;

    public GatherEntityMap getGatherEntityMap(String var1, boolean var2, boolean var3, ExecutorContext var4, String var5, EntityViewDefine var6, String var7) throws Exception;

    public GatherEntityMap getGatherEntityMap(String var1, boolean var2, boolean var3, ExecutorContext var4, EntityViewDefine var5, IEntityTable var6) throws Exception;
}

