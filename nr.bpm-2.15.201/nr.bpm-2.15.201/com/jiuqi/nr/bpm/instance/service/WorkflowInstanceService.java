/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 */
package com.jiuqi.nr.bpm.instance.service;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nr.bpm.instance.bean.CorporateData;
import com.jiuqi.nr.bpm.instance.bean.GridDataResult;
import com.jiuqi.nr.bpm.instance.bean.QueryGridDataParam;
import com.jiuqi.nr.bpm.instance.bean.ReportDataParam;
import com.jiuqi.nr.bpm.instance.bean.ReportDataResult;
import com.jiuqi.nr.bpm.instance.bean.StartStateParam;
import com.jiuqi.nr.bpm.instance.bean.TaskNode;
import com.jiuqi.nr.bpm.instance.bean.WorkflowBaseInfoResult;
import com.jiuqi.nr.bpm.instance.bean.WorkflowBaseOtherInfo;
import com.jiuqi.nr.bpm.instance.bean.WorkflowDefine;
import com.jiuqi.nr.bpm.instance.bean.WorkflowDefineResult;
import com.jiuqi.nr.bpm.instance.bean.WorkflowRelation;
import java.util.List;

public interface WorkflowInstanceService {
    public WorkflowBaseInfoResult queryBaseInfo(String var1, String var2);

    public List<TaskNode> queryTasks();

    public List<WorkflowDefine> queryWorkflowDefines();

    public WorkflowBaseOtherInfo queryBaseOtherInfo(String var1, String var2);

    public GridDataResult queryGridDatas(QueryGridDataParam var1);

    public void operateWorkflowInstance(StartStateParam var1, AsyncTaskMonitor var2);

    public boolean savaWorkflowRelation(WorkflowRelation var1);

    public String queryWorkflowKey(WorkflowRelation var1);

    public List<ReportDataResult> queryReportDataResult(ReportDataParam var1);

    public List<WorkflowDefineResult> queryWorkflows(String var1);

    public void refreshStrategicPartici(String var1, String var2);

    public List<CorporateData> queryCorporateList(String var1);

    public void refreshStrategicPartici(StartStateParam var1, AsyncTaskMonitor var2);
}

