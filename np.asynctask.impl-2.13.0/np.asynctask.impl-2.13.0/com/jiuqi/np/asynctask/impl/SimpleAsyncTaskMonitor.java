/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.asynctask.TaskState
 *  com.jiuqi.np.asynctask.event.FinishTaskEvent
 */
package com.jiuqi.np.asynctask.impl;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.asynctask.TaskState;
import com.jiuqi.np.asynctask.dao.AsyncTaskDao;
import com.jiuqi.np.asynctask.event.FinishTaskEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;

@Deprecated
public class SimpleAsyncTaskMonitor
implements AsyncTaskMonitor {
    private String taskId;
    private String taskPoolType;
    private AsyncTaskDao dao;
    private boolean isCancel = false;
    private double lastProgress = 0.0;
    private long lastProcessTime = 0L;
    private double MIN_UPDATE_PROCESS_SPACE = 0.05;
    private long MIN_UPDATE_TIME_SPACE = 1000L;
    private ApplicationEventPublisher eventPublisher;
    private String LOG_NAME = "NP_ASYNCTASK_MONITOR_FOR_";
    private Logger logger;
    private boolean finish = false;

    public SimpleAsyncTaskMonitor(AsyncTaskDao dao, ApplicationEventPublisher eventPublisher, String taskId, String taskPoolType) {
        this.dao = dao;
        this.eventPublisher = eventPublisher;
        this.taskId = taskId;
        this.taskPoolType = taskPoolType;
        this.logger = LoggerFactory.getLogger(this.LOG_NAME + taskPoolType);
    }

    public void progressAndMessage(double progress, String message) {
        long currentTime = System.currentTimeMillis();
        if (this.lastProgress == 0.0) {
            this.lastProgress = progress;
            this.lastProcessTime = currentTime;
            this.updateProgressAndMessage(this.taskId, progress, message);
        } else if ((currentTime - this.lastProcessTime > this.MIN_UPDATE_TIME_SPACE || progress - this.lastProgress >= this.MIN_UPDATE_PROCESS_SPACE) && progress > this.lastProgress) {
            this.lastProgress = progress;
            this.lastProcessTime = currentTime;
            this.updateProgressAndMessage(this.taskId, progress, message);
        }
    }

    public boolean isCancel() {
        TaskState state;
        if (!this.isCancel && null != (state = this.dao.queryState(this.taskId)) && state.equals((Object)TaskState.CANCELING)) {
            this.isCancel = true;
        }
        return this.isCancel;
    }

    public void finish(String result, Object detail) {
        this.finish = true;
        this.updateResultAndDetail(TaskState.FINISHED, this.taskId, result, detail);
        this.logger.trace("\u3010\u5f02\u6b65\u4efb\u52a1\u7ed3\u675f\u3011: " + this.taskPoolType + "_" + this.taskId + result);
        this.publishFinishEvent();
    }

    public void finished(String result, Object detail) {
        this.finish = true;
        this.updateResultAndDetail(TaskState.FINISHED, this.taskId, result, detail);
        this.logger.trace("\u3010\u5f02\u6b65\u4efb\u52a1\u7ed3\u675f\u3011: " + this.taskPoolType + "_" + this.taskId + result);
    }

    public void canceling(String result, Object detail) {
        this.finish = true;
        this.updateResultAndDetail(TaskState.CANCELING, this.taskId, result, detail);
        this.logger.trace("\u3010\u5f02\u6b65\u4efb\u52a1\u53d6\u6d88\u4e2d\u3011: " + this.taskPoolType + "_" + this.taskId + result);
    }

    public void canceled(String result, Object detail) {
        this.finish = true;
        this.updateResultAndDetail(TaskState.CANCELED, this.taskId, result, detail);
        this.logger.trace("\u3010\u5f02\u6b65\u4efb\u52a1\u5df2\u53d6\u6d88\u3011: " + this.taskPoolType + "_" + this.taskId + result);
        this.publishFinishEvent();
    }

    public String getTaskId() {
        return this.taskId;
    }

    public String getTaskPoolTask() {
        return this.taskPoolType;
    }

    private void publishFinishEvent() {
        FinishTaskEvent event = new FinishTaskEvent();
        event.setTaskPoolType(this.taskPoolType);
        this.eventPublisher.publishEvent((ApplicationEvent)event);
    }

    private void updateProgressAndMessage(String taskId, double progress, String message) {
        this.dao.updateProgressAndMessage(taskId, progress, message);
    }

    private void updateResultAndDetail(TaskState state, String taskId, String result, Object detail) {
        this.dao.updateResultAndDetail(state, taskId, result, detail);
    }

    public void error(String cause, Throwable t) {
        this.finish = true;
        this.dao.updateErrorInfo(this.taskId, cause, t, null);
        this.logger.error("\u3010\u5f02\u6b65\u4efb\u52a1\u5f02\u5e38\u3011: " + this.taskPoolType + "_" + this.taskId + cause, t);
        this.publishFinishEvent();
    }

    public void error(String result, Throwable t, String detail) {
        this.finish = true;
        this.dao.updateErrorInfo(this.taskId, result, t, detail);
        this.logger.error("\u3010\u5f02\u6b65\u4efb\u52a1\u5f02\u5e38\u3011: " + this.taskPoolType + "_" + this.taskId + result, t);
        this.publishFinishEvent();
    }

    public boolean isFinish() {
        return this.finish;
    }
}

