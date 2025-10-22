/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.np.asynctask.AsyncTask
 *  com.jiuqi.np.asynctask.AsyncTaskManager
 *  com.jiuqi.np.asynctask.TaskState
 *  com.jiuqi.nr.common.asynctask.AsyncTaskFuture
 *  com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo
 *  com.jiuqi.nr.common.exception.ExceptionCodeCost
 *  com.jiuqi.nr.common.exception.NotFoundAsyncTaskException
 */
package com.jiuqi.np.asynctask.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.np.asynctask.AsyncTask;
import com.jiuqi.np.asynctask.AsyncTaskManager;
import com.jiuqi.np.asynctask.TaskState;
import com.jiuqi.nr.common.asynctask.AsyncTaskFuture;
import com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo;
import com.jiuqi.nr.common.exception.ExceptionCodeCost;
import com.jiuqi.nr.common.exception.NotFoundAsyncTaskException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class AsyncTaskFutureImpl<R>
implements AsyncTaskFuture<R> {
    @Autowired
    private AsyncTaskManager asyncTaskManager;
    private static final Logger loggger = LoggerFactory.getLogger(AsyncTaskFutureImpl.class);

    public String getResult(String taskId) {
        Assert.notNull((Object)taskId, "taskId is must not be null!");
        return this.asyncTaskManager.queryResult(taskId);
    }

    public R getDetail(String taskId, Class<? extends R> clazz) {
        Assert.notNull((Object)taskId, "taskId is must not be null!");
        ObjectMapper mapper = new ObjectMapper();
        Object detail = this.asyncTaskManager.queryDetail(taskId);
        if (Objects.isNull(detail)) {
            return null;
        }
        Object detailObj = null;
        try {
            detailObj = mapper.readValue(detail.toString(), clazz);
        }
        catch (JsonProcessingException e) {
            loggger.error("\u67e5\u8be2\u5f02\u6b65\u4efb\u52a1\u7ed3\u679c-\u89e3\u6790json\u5f02\u5e38", e);
        }
        return (R)detailObj;
    }

    protected String addTask(Object param, String taskType, boolean runNow) {
        Assert.notNull((Object)taskType, "taskType is must not be null!");
        if (runNow) {
            return this.asyncTaskManager.publishAndExecuteTask(param, taskType);
        }
        return this.asyncTaskManager.publishTask(param, taskType);
    }

    public AsyncTaskInfo getTaskInfo(String taskId) {
        Assert.notNull((Object)taskId, "taskId is must not be null!");
        AsyncTaskInfo asyncTaskInfo = new AsyncTaskInfo();
        asyncTaskInfo.setId(taskId);
        AsyncTask queryTask = this.asyncTaskManager.queryTask(taskId);
        if (null == queryTask) {
            throw new NotFoundAsyncTaskException(ExceptionCodeCost.NOTFOUND_ASYNCTASK_IDISNULL, null);
        }
        asyncTaskInfo.setProcess(queryTask.getProcess());
        asyncTaskInfo.setResult(queryTask.getResult());
        asyncTaskInfo.setDetail(queryTask.getDetail());
        TaskState state = queryTask.getState();
        asyncTaskInfo.setState(state);
        if (state == TaskState.WAITING) {
            asyncTaskInfo.setLocation(this.asyncTaskManager.queryLocation(taskId));
        } else if (state == TaskState.FINISHED || state == TaskState.ERROR) {
            Object queryDetail = this.asyncTaskManager.queryDetail(taskId);
            asyncTaskInfo.setDetail(queryDetail);
        }
        return asyncTaskInfo;
    }

    private Class<R> getTClaszz() {
        Type[] types = ((ParameterizedType)this.getClass().getGenericSuperclass()).getActualTypeArguments();
        Class claszz = (Class)types[0];
        return claszz;
    }
}

