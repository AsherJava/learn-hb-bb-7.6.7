/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.JobContext
 *  com.jiuqi.bi.core.jobs.JobsException
 *  com.jiuqi.bi.core.jobs.realtime.imdiately.ImmediatelyRealTimeJobContext
 *  com.jiuqi.bi.monitor.IProgressMonitor
 *  com.jiuqi.bi.monitor.ProgressException
 *  com.jiuqi.np.log.BeanUtils
 */
package com.jiuqi.np.asynctask;

import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.bi.core.jobs.JobsException;
import com.jiuqi.bi.core.jobs.realtime.imdiately.ImmediatelyRealTimeJobContext;
import com.jiuqi.bi.monitor.IProgressMonitor;
import com.jiuqi.bi.monitor.ProgressException;
import com.jiuqi.np.asynctask.AsyncTask;
import com.jiuqi.np.asynctask.AsyncTaskManager;
import com.jiuqi.np.asynctask.AsyncTaskStepMonitor;
import com.jiuqi.np.asynctask.TaskResultEnum;
import com.jiuqi.np.asynctask.TaskState;
import com.jiuqi.np.log.BeanUtils;
import com.jiuqi.nr.common.asynctask.entity.SubJobMessageInfo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AsyncTaskStepMonitorImpl
implements AsyncTaskStepMonitor {
    private final JobContext jobContext;
    private final IProgressMonitor monitor;
    private final Logger logger = LoggerFactory.getLogger(AsyncTaskStepMonitorImpl.class);

    public AsyncTaskStepMonitorImpl(JobContext jobContext) {
        this.jobContext = jobContext;
        this.monitor = jobContext.getMonitor();
    }

    @Override
    public void startTask(String taskName, int stepCount) {
        this.monitor.startTask(taskName, stepCount);
    }

    @Override
    public void startTask(String taskName, int stepCount, String message) {
        this.monitor.startTask(taskName, stepCount);
        this.jobContext.setResult(1, message);
    }

    @Override
    public void startTask(String taskName, int[] steps) {
        this.monitor.startTask(taskName, steps);
    }

    @Override
    public void startTask(String taskName, int[] steps, String message) {
        this.monitor.startTask(taskName, steps);
        this.jobContext.setResult(1, message);
    }

    @Override
    public void stepIn(String result) {
        this.monitor.stepIn();
        this.jobContext.setResult(1, result);
    }

    @Override
    public void finishTask(String taskName, String result, String detail) throws ProgressException {
        this.finishTask(taskName, result, detail, TaskResultEnum.SUCCESS);
    }

    @Override
    public void finishTask(String taskName, String result, String detail, TaskResultEnum taskResult) throws ProgressException {
        this.jobContext.setResult(taskResult.getValue(), result);
        try {
            if (!(this.jobContext instanceof ImmediatelyRealTimeJobContext)) {
                this.jobContext.setInstanceDetail(detail);
            }
        }
        catch (JobsException e) {
            this.logger.error(e.getMessage(), e);
        }
        this.monitor.finishTask(taskName);
    }

    @Override
    public boolean isCanceled() {
        return this.monitor.isCanceled();
    }

    @Override
    public void cancel(String result, String detail) {
        if (!(this.jobContext instanceof ImmediatelyRealTimeJobContext)) {
            try {
                this.jobContext.setInstanceDetail(detail);
            }
            catch (JobsException e) {
                this.logger.error(e.getMessage(), e);
            }
        }
        this.monitor.cancel();
    }

    @Override
    public void waitParallelTask(List<String> taskIdList) {
        this.waitParallelTask(new ArrayList<String>(taskIdList), null);
    }

    @Override
    public void waitAndUpdateParallelTaskMessage(List<SubJobMessageInfo> subJobMessageInfos) {
        ArrayList<String> taskIdList = new ArrayList<String>();
        HashMap<String, String> messageMap = new HashMap<String, String>();
        for (SubJobMessageInfo subJobMessageInfo : subJobMessageInfos) {
            String taskId = subJobMessageInfo.getTaskId();
            String message = subJobMessageInfo.getMessage();
            taskIdList.add(taskId);
            messageMap.put(taskId, message);
        }
        this.waitParallelTask(taskIdList, messageMap);
    }

    private void waitParallelTask(List<String> taskIdList, Map<String, String> messageMap) {
        String timestamp = String.valueOf(System.currentTimeMillis());
        this.monitor.startTask("StartParallelTask_" + timestamp, taskIdList.size() * 100);
        Map<String, Integer> doneProgressMap = taskIdList.stream().collect(Collectors.toMap(key -> key, value -> 0));
        while (!taskIdList.isEmpty()) {
            Map<String, AsyncTask> tasks = this.queryTasks(taskIdList);
            for (Map.Entry<String, AsyncTask> entry : tasks.entrySet()) {
                int i;
                String taskId = entry.getKey();
                AsyncTask asyncTask = entry.getValue();
                int doneProgress = doneProgressMap.get(taskId);
                int nowProgress = (int)(asyncTask.getProcess() * 100.0);
                for (i = 0; i < nowProgress - doneProgress; ++i) {
                    this.monitor.stepIn();
                }
                if (asyncTask.getState().equals((Object)TaskState.WAITING) || asyncTask.getState().equals((Object)TaskState.PROCESSING)) {
                    doneProgressMap.put(taskId, nowProgress);
                    continue;
                }
                if (asyncTask.getState().equals((Object)TaskState.ERROR)) {
                    for (i = 0; i < 100 - nowProgress; ++i) {
                        this.monitor.stepIn();
                    }
                }
                if (Objects.nonNull(messageMap) && Objects.nonNull(messageMap.get(taskId))) {
                    this.monitor.prompt(messageMap.get(taskId));
                    messageMap.remove(taskId);
                }
                taskIdList.remove(taskId);
                doneProgressMap.remove(taskId);
            }
            try {
                Thread.sleep(1000L);
            }
            catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        this.monitor.finishTask("StartParallelTask_" + timestamp);
    }

    private Map<String, AsyncTask> queryTasks(List<String> taskIds) {
        AsyncTaskManager asyncTaskManager = (AsyncTaskManager)BeanUtils.getBean(AsyncTaskManager.class);
        HashMap<String, AsyncTask> tasks = new HashMap<String, AsyncTask>();
        for (String taskId : taskIds) {
            AsyncTask asyncTask = asyncTaskManager.queryTask(taskId);
            tasks.put(taskId, asyncTask);
        }
        return tasks;
    }
}

