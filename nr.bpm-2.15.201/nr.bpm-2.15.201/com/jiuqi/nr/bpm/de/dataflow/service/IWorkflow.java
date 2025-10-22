/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.data.engine.condition.IConditionCache
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 */
package com.jiuqi.nr.bpm.de.dataflow.service;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.bpm.ProcessEngine;
import com.jiuqi.nr.bpm.businesskey.BusinessKey;
import com.jiuqi.nr.bpm.common.Task;
import com.jiuqi.nr.bpm.common.UploadRecordNew;
import com.jiuqi.nr.bpm.de.dataflow.bean.ProcessAction;
import com.jiuqi.nr.bpm.de.dataflow.bean.ProcessTaskNode;
import com.jiuqi.nr.bpm.de.dataflow.bean.WorkflowAction;
import com.jiuqi.nr.data.engine.condition.IConditionCache;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface IWorkflow {
    public WorkFlowType queryStartType(String var1);

    public boolean bindProcess(String var1, DimensionValueSet var2, String var3, String var4);

    public boolean bindProcess(String var1, DimensionValueSet var2, String var3);

    public String getFormOrGroupKey(String var1, String var2, String var3);

    public List<String> getFormOrGroupKey(String var1, List<String> var2, List<String> var3);

    public boolean isDefaultWorkflow(String var1);

    public Optional<ProcessEngine> getProcessEngine(String var1);

    public List<Task> queryTasks(String var1, String var2, String var3, DimensionValueSet var4, BusinessKey var5, boolean var6);

    public String queryTaskId(String var1, DimensionValueSet var2, String var3, String var4);

    public String queryRevertTaskId(List<UploadRecordNew> var1, Task var2, BusinessKey var3);

    public List<Task> queryTasks(String var1, BusinessKey var2);

    public List<Task> queryTasks(String var1, String var2, String var3, DimensionValueSet var4, BusinessKey var5, boolean var6, IConditionCache var7);

    public String getSubmitActionCode(String var1);

    public String getUploadActionCode(String var1);

    public String getConfirmActionCode(String var1);

    public String getReturnActionCode(String var1);

    public String getRejectActionCode(String var1);

    public String getTaskCode(String var1, String var2);

    public boolean isTwoTree();

    public List<ProcessTaskNode> getProcessTaskNodes(FormSchemeDefine var1);

    public List<ProcessAction> getProcessAction(FormSchemeDefine var1, String var2, String var3);

    public List<WorkflowAction> getExecuteActions(String var1, String var2, String var3);

    public String getMessageId(String var1, String var2, String var3, String var4, String var5, String var6, WorkFlowType var7, String var8, String var9);

    public boolean hasStatisticalNode(String var1);

    public Map<String, List<String>> getStatisticalStates(String var1);

    public Map<String, List<String>> getStatisticalStates(String var1, Map<String, String> var2);
}

