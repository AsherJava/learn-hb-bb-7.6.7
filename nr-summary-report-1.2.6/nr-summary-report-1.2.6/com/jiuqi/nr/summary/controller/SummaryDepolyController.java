/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob
 *  com.jiuqi.np.asynctask.AsyncTask
 *  com.jiuqi.np.asynctask.AsyncTaskManager
 *  com.jiuqi.np.asynctask.NpRealTimeTaskInfo
 *  com.jiuqi.np.asynctask.TaskState
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.RequestMapping
 */
package com.jiuqi.nr.summary.controller;

import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.np.asynctask.AsyncTask;
import com.jiuqi.np.asynctask.AsyncTaskManager;
import com.jiuqi.np.asynctask.NpRealTimeTaskInfo;
import com.jiuqi.np.asynctask.TaskState;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.nr.summary.executor.deploy.async.SummaryDeployTaskExecutor;
import com.jiuqi.nr.summary.vo.ProgressInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@JQRestController
@RequestMapping(value={"/api/summary_solution"})
public class SummaryDepolyController {
    @Autowired
    private AsyncTaskManager asyncTaskManager;
    @Autowired
    private SummaryDeployTaskExecutor deployTaskExecutor;

    @GetMapping(value={"/deploy/{solutionKey}"})
    public String deploy(@PathVariable String solutionKey) {
        NpRealTimeTaskInfo npRealTimeTaskInfo = new NpRealTimeTaskInfo();
        npRealTimeTaskInfo.setArgs(solutionKey);
        npRealTimeTaskInfo.setAbstractRealTimeJob((AbstractRealTimeJob)this.deployTaskExecutor);
        return this.asyncTaskManager.publishTask(npRealTimeTaskInfo);
    }

    @GetMapping(value={"/deploy/progress/{asyncTaskId}"})
    public ProgressInfo deployProgress(@PathVariable String asyncTaskId) {
        TaskState taskState = this.asyncTaskManager.queryTaskState(asyncTaskId);
        AsyncTask asyncTask = this.asyncTaskManager.queryTask(asyncTaskId);
        return new ProgressInfo(asyncTask);
    }
}

