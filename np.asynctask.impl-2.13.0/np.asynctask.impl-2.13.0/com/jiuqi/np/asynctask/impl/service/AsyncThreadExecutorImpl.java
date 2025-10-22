/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.realtime.RealTimeJob
 *  com.jiuqi.np.asynctask.AsyncTask
 *  com.jiuqi.np.asynctask.AsyncThreadExecutor
 *  com.jiuqi.np.asynctask.NpRealTimeTaskInfo
 *  com.jiuqi.np.asynctask.TaskState
 */
package com.jiuqi.np.asynctask.impl.service;

import com.jiuqi.bi.core.jobs.realtime.RealTimeJob;
import com.jiuqi.np.asynctask.AsyncTask;
import com.jiuqi.np.asynctask.AsyncThreadExecutor;
import com.jiuqi.np.asynctask.NpRealTimeTaskInfo;
import com.jiuqi.np.asynctask.TaskState;
import com.jiuqi.np.asynctask.impl.service.AsyncTaskManagerImpl;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AsyncThreadExecutorImpl
implements AsyncThreadExecutor {
    @Autowired
    private AsyncTaskManagerImpl asyncTaskManager;
    private final Logger logger = LoggerFactory.getLogger(AsyncThreadExecutorImpl.class);

    public String executeTask(NpRealTimeTaskInfo npRealTimeTaskInfo) {
        try {
            RealTimeJob realTimeJob = npRealTimeTaskInfo.getAbstractRealTimeJob().getClass().getAnnotation(RealTimeJob.class);
            return this.asyncTaskManager.publishRealTimeTask(npRealTimeTaskInfo, realTimeJob.group(), false, true);
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), e);
            return null;
        }
    }

    public List<String> executorTasks(List<NpRealTimeTaskInfo> npRealTimeTaskInfos) {
        ArrayList<String> taskIds = new ArrayList<String>();
        for (NpRealTimeTaskInfo npRealTimeTaskInfo : npRealTimeTaskInfos) {
            String taskId = null;
            try {
                taskId = this.executeTask(npRealTimeTaskInfo);
            }
            catch (Exception e) {
                this.logger.error(e.getMessage(), e);
            }
            taskIds.add(taskId);
        }
        return taskIds;
    }

    public void cancelTask(String taskId) {
        this.asyncTaskManager.cancelTask(taskId);
    }

    public AsyncTask queryTask(String taskId) {
        return this.asyncTaskManager.queryTask(taskId);
    }

    public Double queryProcess(String taskId) {
        return this.asyncTaskManager.queryProcess(taskId);
    }

    public TaskState queryTaskState(String taskId) {
        return this.asyncTaskManager.queryTaskState(taskId);
    }

    public String queryResult(String taskId) {
        return this.asyncTaskManager.queryResult(taskId);
    }
}

