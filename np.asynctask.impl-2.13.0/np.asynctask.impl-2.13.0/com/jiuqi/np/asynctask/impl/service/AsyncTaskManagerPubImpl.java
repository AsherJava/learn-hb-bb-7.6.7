/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.JobsException
 *  com.jiuqi.bi.core.jobs.realtime.RealTimeJobManager
 *  com.jiuqi.bi.core.jobs.realtime.imdiately.ImmediatelyJobInfo
 *  com.jiuqi.bi.core.jobs.realtime.imdiately.ImmediatelyJobRunner
 *  com.jiuqi.np.asynctask.AsyncTask
 *  com.jiuqi.np.asynctask.AsyncTaskManagerPub
 *  com.jiuqi.np.asynctask.TaskState
 *  com.jiuqi.np.asynctask.event.TaskCancelEvent
 *  com.jiuqi.np.asynctask.exception.NpAsyncTaskExecption
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.np.asynctask.impl.service;

import com.jiuqi.bi.core.jobs.JobsException;
import com.jiuqi.bi.core.jobs.realtime.RealTimeJobManager;
import com.jiuqi.bi.core.jobs.realtime.imdiately.ImmediatelyJobInfo;
import com.jiuqi.bi.core.jobs.realtime.imdiately.ImmediatelyJobRunner;
import com.jiuqi.np.asynctask.AsyncTask;
import com.jiuqi.np.asynctask.AsyncTaskManagerPub;
import com.jiuqi.np.asynctask.TaskState;
import com.jiuqi.np.asynctask.dao.AsyncTaskDao;
import com.jiuqi.np.asynctask.event.TaskCancelEvent;
import com.jiuqi.np.asynctask.exception.NpAsyncTaskExecption;
import com.jiuqi.np.asynctask.impl.service.AsyncTaskManagerImpl;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Service
@RestController
public class AsyncTaskManagerPubImpl
implements AsyncTaskManagerPub,
ApplicationEventPublisherAware {
    @Autowired
    private AsyncTaskManagerImpl manager;
    @Autowired
    private AsyncTaskDao dao;
    private ApplicationEventPublisher eventPublisher;

    @PostMapping(value={"/asynctask/manager/publishTaskWithDepend"}, consumes={"application/json;charset=UTF-8"}, produces={"application/json;charset=UTF-8"})
    public String publishTaskWithDepend(@RequestBody byte[] bytes, @RequestParam(value="taskPoolType") String taskPoolType) throws NpAsyncTaskExecption {
        return this.manager.publishTaskWithDependImpl(SimpleParamConverter.SerializationUtils.deserialize(bytes), taskPoolType, null);
    }

    @PostMapping(value={"/asynctask/manager/publishTaskWithDependImpl"}, consumes={"application/json;charset=UTF-8"}, produces={"application/json;charset=UTF-8"})
    public String publishTaskWithDependImpl(byte[] bytes, String taskPoolType, String dependedTaskId) throws NpAsyncTaskExecption {
        return this.manager.publishTaskWithDependImpl(bytes, taskPoolType, dependedTaskId);
    }

    public TaskState queryTaskState(String taskId) {
        TaskState taskState = this.dao.queryState(taskId);
        if (taskState != null) {
            return taskState;
        }
        return TaskState.NONE;
    }

    public Integer queryLocation(String taskId) {
        AsyncTask asyncTask = this.queryTask(taskId);
        return this.queryLocation(asyncTask);
    }

    public Integer queryLocation(AsyncTask asyncTask) {
        if (null == asyncTask) {
            return Integer.MAX_VALUE;
        }
        if ("immediately".equals(asyncTask.getPublishType())) {
            return 0;
        }
        Integer location = this.dao.queryLocation(asyncTask.getTaskId(), asyncTask);
        return Objects.isNull(location) ? 0 : location + 1;
    }

    public Double queryProcess(String taskId) {
        AsyncTask task = this.queryTask(taskId);
        if (null != task) {
            return task.getProcess();
        }
        return null;
    }

    public String queryResult(String taskId) {
        AsyncTask task = this.queryTask(taskId);
        if (null != task) {
            return task.getResult();
        }
        return null;
    }

    public Object queryDetail(String taskId) {
        return this.dao.queryDetail(taskId);
    }

    public String queryDetailString(String taskId) {
        return this.dao.queryDetailString(taskId);
    }

    public String queryArgs(String taskId) {
        return this.dao.queryArgs(taskId);
    }

    public AsyncTask queryTask(String taskId) {
        return this.dao.query(taskId);
    }

    public AsyncTask querySimpleTask(String taskId) {
        return this.dao.querySimpleTask(taskId);
    }

    public List<AsyncTask> queryTaskToDo() {
        return this.dao.queryToDo();
    }

    public List<AsyncTask> queryTaskToDoWithoutClob() {
        return this.dao.queryTaskToDoWithoutClob();
    }

    public void cancelTask(String taskId) {
        if (taskId.startsWith("nr-")) {
            AsyncTask task = this.queryTask(taskId);
            if (task.getState().equals((Object)TaskState.WAITING) || task.getServeId() == null && task.getState().equals((Object)TaskState.PROCESSING)) {
                this.dao.updateState(TaskState.CANCELED, taskId);
            } else if (task.getState().equals((Object)TaskState.PROCESSING)) {
                this.dao.updateState(TaskState.CANCELING, taskId);
            }
            TaskCancelEvent taskCancelEvent = new TaskCancelEvent();
            taskCancelEvent.setTaskPoolType(task.getTaskPoolType());
            taskCancelEvent.setTaskId(taskId);
            this.eventPublisher.publishEvent((ApplicationEvent)taskCancelEvent);
        } else {
            try {
                ImmediatelyJobInfo immediatelyJobInfo = ImmediatelyJobRunner.getInstance().getJobInfo(taskId);
                if (immediatelyJobInfo != null) {
                    ImmediatelyJobRunner.getInstance().cancel(taskId);
                } else {
                    RealTimeJobManager.getInstance().cancel(taskId);
                }
            }
            catch (JobsException e) {
                throw new NpAsyncTaskExecption((Throwable)e);
            }
        }
    }

    @PostMapping(value={"/asynctask/manager/publishAndExecuteTask"}, consumes={"application/json;charset=UTF-8"}, produces={"application/json;charset=UTF-8"})
    public String publishAndExecuteTask(byte[] bytes, String taskPoolType) throws NpAsyncTaskExecption {
        return this.manager.publishAndExecuteTaskProxy(SimpleParamConverter.SerializationUtils.deserialize(bytes), taskPoolType, null);
    }

    @PostMapping(value={"/asynctask/manager/publishToSplitQueue"}, consumes={"application/json;charset=UTF-8"}, produces={"application/json;charset=UTF-8"})
    public String publishToSplitQueue(@RequestBody byte[] bytes, @RequestParam(value="taskPoolType") String taskPoolType) throws NpAsyncTaskExecption {
        return this.manager.publishToSplitQueueProxy(SimpleParamConverter.SerializationUtils.deserialize(bytes), taskPoolType, null);
    }

    @PostMapping(value={"/asynctask/manager/publishUniqueTask"}, consumes={"application/json;charset=UTF-8"}, produces={"application/json;charset=UTF-8"})
    public String publishUniqueTask(byte[] bytes, String taskPoolType, String dimensionIdentity) {
        return this.manager.publishUniqueProxy(SimpleParamConverter.SerializationUtils.deserialize(bytes), taskPoolType, dimensionIdentity);
    }

    public int completeTask(String taskId) {
        return this.dao.updateCompleteTask(taskId);
    }

    public Map<String, Object> queryDetails(List<String> taskIds) {
        return this.dao.queryDetails(taskIds);
    }

    public Map<String, String> queryDetailStrings(List<String> taskIds) {
        return this.dao.queryDetailStrings(taskIds);
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.eventPublisher = applicationEventPublisher;
    }
}

