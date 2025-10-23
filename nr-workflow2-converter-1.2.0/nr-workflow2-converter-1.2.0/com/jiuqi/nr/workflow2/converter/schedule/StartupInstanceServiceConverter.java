/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.nr.bpm.instance.bean.QueryGridDataParam
 *  com.jiuqi.nr.bpm.instance.bean.StartStateParam
 *  com.jiuqi.nr.bpm.instance.bean.StartStateParam$Type
 *  com.jiuqi.nr.bpm.instance.service.WorkflowInstanceService
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.AsyncJobResult
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.IOperateResultSet
 *  com.jiuqi.nr.workflow2.engine.core.settings.utils.DefaultEngineVersionJudge
 *  com.jiuqi.nr.workflow2.schedule.bi.jobs.monitor.IProcessStartupAsyncMonitor
 *  com.jiuqi.nr.workflow2.schedule.bi.jobs.param.IProcessStartupRunPara
 *  com.jiuqi.nr.workflow2.schedule.service.impl.ProcessStartupInstanceService
 */
package com.jiuqi.nr.workflow2.converter.schedule;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nr.bpm.instance.bean.QueryGridDataParam;
import com.jiuqi.nr.bpm.instance.bean.StartStateParam;
import com.jiuqi.nr.bpm.instance.service.WorkflowInstanceService;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.workflow2.converter.schedule.StartupAsyncTaskMonitor;
import com.jiuqi.nr.workflow2.engine.core.event.runtime.AsyncJobResult;
import com.jiuqi.nr.workflow2.engine.core.event.runtime.IOperateResultSet;
import com.jiuqi.nr.workflow2.engine.core.settings.utils.DefaultEngineVersionJudge;
import com.jiuqi.nr.workflow2.schedule.bi.jobs.monitor.IProcessStartupAsyncMonitor;
import com.jiuqi.nr.workflow2.schedule.bi.jobs.param.IProcessStartupRunPara;
import com.jiuqi.nr.workflow2.schedule.service.impl.ProcessStartupInstanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class StartupInstanceServiceConverter
extends ProcessStartupInstanceService {
    @Autowired
    protected WorkflowInstanceService workflowInstanceService;
    @Autowired
    protected IRunTimeViewController runTimeViewController;
    @Autowired
    protected DefaultEngineVersionJudge defaultEngineVersionJudge;

    public void startInstances(IProcessStartupRunPara startupRunPara, IOperateResultSet operateResultSet, IProcessStartupAsyncMonitor monitor) {
        TaskDefine taskDefine = this.runTimeViewController.getTask(startupRunPara.getTaskKey());
        if (this.defaultEngineVersionJudge.isTaskVersion_1_0(taskDefine.getKey())) {
            monitor.setJobResult(AsyncJobResult.FAILURE, "\u5f53\u524d\u4efb\u52a1\uff1a\u3010" + taskDefine.getTitle() + "\uff08" + taskDefine.getTaskCode() + "\uff09\u3011\u662f1.0\u7248\u672c\uff0c\u4e0d\u652f\u6301\u81ea\u542f\u52a8\uff01\uff01");
            return;
        }
        if (this.defaultEngineVersionJudge.isDefaultEngineVersion_2_0(taskDefine.getKey())) {
            this.startWithDefaultEngineVersion_2_0(startupRunPara, operateResultSet, monitor);
            return;
        }
        if (this.defaultEngineVersionJudge.isTaskAndEngineVersion_1_0(taskDefine.getKey())) {
            this.startWithTaskAndEngineVersion_1_0(startupRunPara, operateResultSet, monitor);
        }
    }

    protected void startWithTaskAndEngineVersion_1_0(IProcessStartupRunPara startupRunPara, IOperateResultSet operateResultSet, IProcessStartupAsyncMonitor monitor) {
        StartStateParam startStateParam = new StartStateParam();
        startStateParam.setTaskKey(startupRunPara.getTaskKey());
        startStateParam.setPeriod(startupRunPara.getPeriod());
        startStateParam.setOperateType(StartStateParam.Type.START.getType());
        QueryGridDataParam queryGridDataParam = new QueryGridDataParam();
        queryGridDataParam.setAllChecked(true);
        startStateParam.setQueryGridDataParam(queryGridDataParam);
        StartupAsyncTaskMonitor asyncTaskMonitor = new StartupAsyncTaskMonitor(monitor, "ASYNCTASK_OPERATE_WORKFLOW_INSTANCE");
        this.workflowInstanceService.operateWorkflowInstance(startStateParam, (AsyncTaskMonitor)asyncTaskMonitor);
    }
}

