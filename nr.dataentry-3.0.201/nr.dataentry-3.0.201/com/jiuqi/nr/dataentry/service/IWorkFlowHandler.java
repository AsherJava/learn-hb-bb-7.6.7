/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.bpm.de.dataflow.bean.WorkflowDataInfo
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 *  com.jiuqi.nr.jtable.params.output.ReturnInfo
 *  com.jiuqi.nr.jtable.params.output.UploadReturnInfo
 */
package com.jiuqi.nr.dataentry.service;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.bpm.de.dataflow.bean.WorkflowDataInfo;
import com.jiuqi.nr.dataentry.bean.BatchExecuteTaskParam;
import com.jiuqi.nr.dataentry.bean.ExecuteTaskParam;
import com.jiuqi.nr.dataentry.paramInfo.BatchWorkFlowInfo;
import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import com.jiuqi.nr.jtable.params.output.ReturnInfo;
import com.jiuqi.nr.jtable.params.output.UploadReturnInfo;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public interface IWorkFlowHandler {
    public boolean beforeExecuteTask(ExecuteTaskParam var1, AsyncTaskMonitor var2);

    public void beforeBatchExecuteTask(List<BatchWorkFlowInfo> var1, List<ReturnInfo> var2, UploadReturnInfo var3, BatchExecuteTaskParam var4, AsyncTaskMonitor var5, Map<DimensionValueSet, LinkedHashMap<String, List<WorkflowDataInfo>>> var6, WorkFlowType var7, String var8, String var9);
}

