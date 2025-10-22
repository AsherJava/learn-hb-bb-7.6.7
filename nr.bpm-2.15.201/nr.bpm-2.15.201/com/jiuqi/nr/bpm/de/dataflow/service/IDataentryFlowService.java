/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 */
package com.jiuqi.nr.bpm.de.dataflow.service;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.bpm.businesskey.BusinessKey;
import com.jiuqi.nr.bpm.common.TaskContext;
import com.jiuqi.nr.bpm.de.dataflow.bean.ActionParam;
import com.jiuqi.nr.bpm.de.dataflow.bean.ActionState;
import com.jiuqi.nr.bpm.de.dataflow.bean.ActionStateBean;
import com.jiuqi.nr.bpm.de.dataflow.bean.CompleteMsg;
import com.jiuqi.nr.bpm.de.dataflow.bean.DataEntryParam;
import com.jiuqi.nr.bpm.de.dataflow.bean.DeWorkflowBean;
import com.jiuqi.nr.bpm.de.dataflow.bean.ExecuteParam;
import com.jiuqi.nr.bpm.de.dataflow.bean.ReadOnlyBean;
import com.jiuqi.nr.bpm.de.dataflow.bean.SignBootModeParam;
import com.jiuqi.nr.bpm.de.dataflow.bean.SignModeData;
import com.jiuqi.nr.bpm.de.dataflow.bean.UploadStateBean;
import com.jiuqi.nr.bpm.de.dataflow.bean.WorkflowConfig;
import com.jiuqi.nr.bpm.de.dataflow.bean.WorkflowDataBean;
import com.jiuqi.nr.bpm.de.dataflow.bean.WorkflowDataInfo;
import com.jiuqi.nr.bpm.de.dataflow.step.bean.BatchExecuteParam;
import com.jiuqi.nr.bpm.de.dataflow.step.bean.BatchWorkflowDataBean;
import com.jiuqi.nr.bpm.upload.UploadState;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IDataentryFlowService {
    public WorkflowConfig queryWorkflowConfig(String var1);

    public List<WorkflowDataInfo> queryWorkflowDataInfo(WorkflowDataBean var1);

    public Map<DimensionValueSet, LinkedHashMap<String, List<WorkflowDataInfo>>> batchWorkflowDataInfo(BatchWorkflowDataBean var1);

    public ActionStateBean queryReportState(DataEntryParam var1);

    public ActionState queryState(DataEntryParam var1);

    public ActionStateBean queryUnitState(DataEntryParam var1);

    public List<ReadOnlyBean> batchReadOnly(DataEntryParam var1);

    public Map<DimensionValueSet, Map<String, Boolean>> batchReadOnlyMap(DataEntryParam var1);

    public ReadOnlyBean readOnly(DataEntryParam var1);

    public CompleteMsg executeTask(ExecuteParam var1);

    public CompleteMsg batchApplyReturnExecuteTask(BatchExecuteParam var1);

    public CompleteMsg executeRevert(BusinessKey var1);

    public CompleteMsg executeRevert(BusinessKey var1, TaskContext var2);

    public CompleteMsg batchExecuteTask(BatchExecuteParam var1);

    public WorkFlowType queryStartType(String var1);

    public List<UploadStateBean> queryAllactionCode(UploadState var1, String var2);

    public ActionParam actionParam(BatchExecuteParam var1, Map<String, DimensionValue> var2);

    public Set<String> getTaskActors(FormSchemeDefine var1, DimensionValueSet var2, String var3, String var4);

    public Map<DimensionValueSet, ActionStateBean> getStateMap(String var1, DimensionValueSet var2);

    public DeWorkflowBean getDeWorkflow(DataEntryParam var1);

    public boolean isExistData(String var1);

    public List<SignModeData> getSignModeData(SignBootModeParam var1);
}

