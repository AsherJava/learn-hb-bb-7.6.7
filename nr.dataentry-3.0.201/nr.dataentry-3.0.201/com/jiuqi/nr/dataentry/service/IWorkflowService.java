/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.nr.bpm.de.dataflow.bean.ActionState
 *  com.jiuqi.nr.bpm.de.dataflow.bean.CompleteMsg
 *  com.jiuqi.nr.bpm.de.dataflow.bean.ReadOnlyBean
 *  com.jiuqi.nr.bpm.de.dataflow.bean.SignBootModeParam
 *  com.jiuqi.nr.bpm.de.dataflow.bean.SignModeData
 *  com.jiuqi.nr.bpm.de.dataflow.step.bean.BatchExecuteParam
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 */
package com.jiuqi.nr.dataentry.service;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nr.bpm.de.dataflow.bean.ActionState;
import com.jiuqi.nr.bpm.de.dataflow.bean.CompleteMsg;
import com.jiuqi.nr.bpm.de.dataflow.bean.ReadOnlyBean;
import com.jiuqi.nr.bpm.de.dataflow.bean.SignBootModeParam;
import com.jiuqi.nr.bpm.de.dataflow.bean.SignModeData;
import com.jiuqi.nr.bpm.de.dataflow.step.bean.BatchExecuteParam;
import com.jiuqi.nr.dataentry.bean.BatchExecuteTaskParam;
import com.jiuqi.nr.dataentry.bean.DWorkflowConfig;
import com.jiuqi.nr.dataentry.bean.DWorkflowData;
import com.jiuqi.nr.dataentry.bean.ExecuteTaskParam;
import com.jiuqi.nr.dataentry.bean.WorkflowActionInfo;
import com.jiuqi.nr.dataentry.bean.WorkflowParam;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import java.util.List;

public interface IWorkflowService {
    public DWorkflowConfig getWorkflowConfig(String var1);

    public List<DWorkflowData> getUserActions(JtableContext var1);

    public CompleteMsg executeTask(ExecuteTaskParam var1);

    public void batchExecuteTask(BatchExecuteTaskParam var1, AsyncTaskMonitor var2);

    public ActionState queryWorkflowState(WorkflowParam var1);

    public ReadOnlyBean workflowReadOnly(JtableContext var1);

    public List<ReadOnlyBean> batchWorkflowReadOnly(JtableContext var1);

    public CompleteMsg executeFormRejectOrUpload(ExecuteTaskParam var1);

    public CompleteMsg applyReturn(BatchExecuteParam var1);

    public WorkflowActionInfo getRejectData(JtableContext var1);

    public List<SignModeData> getSignBootModeDatas(SignBootModeParam var1);
}

