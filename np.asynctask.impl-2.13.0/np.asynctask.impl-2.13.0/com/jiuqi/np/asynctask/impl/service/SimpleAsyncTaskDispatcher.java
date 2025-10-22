/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTask
 *  com.jiuqi.np.asynctask.AsyncTaskBufferQueue
 *  com.jiuqi.np.asynctask.AsyncTaskDispatcher
 *  com.jiuqi.np.asynctask.AsyncTaskTypeCollecter
 *  com.jiuqi.np.asynctask.TaskState
 *  com.jiuqi.np.asynctask.event.AsyncTaskReadyEvent
 *  com.jiuqi.np.asynctask.event.FinishTaskEvent
 *  com.jiuqi.np.asynctask.event.PublishTaskEvent
 *  com.jiuqi.np.asynctask.exception.NpAsyncTaskExecption
 *  com.jiuqi.np.cache.internal.redis.IRedisLock
 *  com.jiuqi.np.core.application.ApplicationInitialization
 *  org.quartz.CronScheduleBuilder
 *  org.quartz.JobBuilder
 *  org.quartz.JobDetail
 *  org.quartz.ScheduleBuilder
 *  org.quartz.Scheduler
 *  org.quartz.SchedulerException
 *  org.quartz.Trigger
 *  org.quartz.TriggerBuilder
 */
package com.jiuqi.np.asynctask.impl.service;

import com.jiuqi.np.asynctask.AsyncTask;
import com.jiuqi.np.asynctask.AsyncTaskBufferQueue;
import com.jiuqi.np.asynctask.AsyncTaskDispatcher;
import com.jiuqi.np.asynctask.AsyncTaskTypeCollecter;
import com.jiuqi.np.asynctask.TaskState;
import com.jiuqi.np.asynctask.dao.AsyncTaskDao;
import com.jiuqi.np.asynctask.event.AsyncTaskReadyEvent;
import com.jiuqi.np.asynctask.event.FinishTaskEvent;
import com.jiuqi.np.asynctask.event.PublishTaskEvent;
import com.jiuqi.np.asynctask.exception.NpAsyncTaskExecption;
import com.jiuqi.np.asynctask.impl.service.AsyncTaskDispatcherThread;
import com.jiuqi.np.asynctask.impl.service.DeleteHistoryDataPlanTaskRunner;
import com.jiuqi.np.cache.internal.redis.IRedisLock;
import com.jiuqi.np.core.application.ApplicationInitialization;
import java.util.List;
import java.util.UUID;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.ScheduleBuilder;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;

public class SimpleAsyncTaskDispatcher
implements AsyncTaskDispatcher,
ApplicationEventPublisherAware,
ApplicationInitialization {
    private final String SERVE_ID;
    private ApplicationEventPublisher eventPublisher;
    @Autowired
    private IRedisLock loca;
    @Autowired
    private AsyncTaskDao dao;
    @Autowired
    private AsyncTaskTypeCollecter collecter;
    @Autowired
    private AsyncTaskBufferQueue bufferQueue;
    @Autowired
    private Scheduler scheduler;
    @Autowired
    AsyncTaskDispatcherThread asyncTaskDispatcherThread;
    @Value(value="${jiuqi.np.asynctask.nr-asynctask-frame.enable:false}")
    private boolean enableOldFrame;
    private final String SYSTEM_NAME;
    private static String LOG_NAME = "NP_ASYNCTASK_PROCESS_DISPATCHER";
    private static String PLANTASK = "NP_ASYNCTASK_DELETEHISTORYDATA";
    private final Logger logger = LoggerFactory.getLogger(LOG_NAME);
    private static String METHOD_INITDISPATCHER = "_initDispatch";
    private static String METHOD_DISPATCHER = "_Dispatch_";
    private static Integer WARNING_WAITING_SIZE = 50;

    public SimpleAsyncTaskDispatcher(String systemName, String serveId) {
        this.SYSTEM_NAME = systemName;
        this.SERVE_ID = serveId;
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.eventPublisher = applicationEventPublisher;
    }

    private void publishAsyncTaskReadyEvent() {
        AsyncTaskReadyEvent event = new AsyncTaskReadyEvent();
        event.setServeCode(this.SERVE_ID);
        this.eventPublisher.publishEvent((ApplicationEvent)event);
    }

    private void handleOvertimeTask() {
        this.dao.updateOverTimeState();
    }

    private void initDispatch() {
        while (null == this.loca.lock(this.SYSTEM_NAME + METHOD_INITDISPATCHER)) {
        }
        try {
            for (String taskPoolType : this.collecter.getTaskPoolTypes()) {
                this.dispatch(taskPoolType);
            }
        }
        catch (Exception e) {
            this.logger.error("\u5f02\u6b65\u4efb\u52a1\u6d41\u7a0b\u8c03\u5ea6\u521d\u59cb\u5316\u9519\u8bef: " + e.getMessage(), e);
        }
        finally {
            this.loca.unLock(this.SYSTEM_NAME + METHOD_INITDISPATCHER);
        }
    }

    private Integer getParallelQueueFreeSize(String taskPoolType) {
        Integer parallelSize = this.collecter.getParallelSize(taskPoolType);
        if (null == parallelSize) {
            throw new NpAsyncTaskExecption("\u4efb\u52a1\u6c60\"" + taskPoolType + "\"\u65e0\u6267\u884c\u5668");
        }
        List<AsyncTask> taskList = this.dao.queryByTaskPool(taskPoolType, TaskState.PROCESSING);
        return parallelSize - taskList.size();
    }

    @EventListener
    protected void onPublishTask(PublishTaskEvent event) {
        this.asyncTaskDispatcherThread.notifyDispather(event.getTaskPoolType());
    }

    @EventListener
    protected void onFinishTask(FinishTaskEvent event) {
        this.asyncTaskDispatcherThread.notifyDispather(event.getTaskPoolType());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void dispatch(String taskPoolType) {
        long endTime = System.currentTimeMillis() + 30000L;
        while (null == this.loca.lock(this.SYSTEM_NAME + METHOD_DISPATCHER + taskPoolType)) {
            if (System.currentTimeMillis() < endTime) continue;
            this.logger.error("\u4efb\u52a1\u8c03\u5ea6\u83b7\u53d6\u5206\u5e03\u5f0f\u9501\u5931\u8d25\uff01");
            return;
        }
        try {
            Integer size = this.getParallelQueueFreeSize(taskPoolType);
            if (size > 0) {
                List<AsyncTask> taskList = this.queryByTaskPool(taskPoolType, TaskState.WAITING);
                if (taskList.size() > WARNING_WAITING_SIZE) {
                    this.logger.error("\u3010\u9884\u8b66\u3011\u5f02\u6b65\u4efb\u52a1\u7c7b\u578b: " + taskPoolType + "\u6392\u961f\u6570\u8d85\u8fc7" + WARNING_WAITING_SIZE + "!");
                }
                for (int i = 0; i < size && i < taskList.size(); ++i) {
                    AsyncTask task = taskList.get(i);
                    this.logger.trace("\u3010\u6d41\u7a0b\u8c03\u5ea6_\u5f02\u6b65\u4efb\u52a1\u53d1\u5e03\u3011: " + taskPoolType + "_" + task.getTaskId());
                    this.dao.updateState(TaskState.PROCESSING, task.getTaskId());
                    this.bufferQueue.publish("np_asynctask_simple_queue", task.getTaskId(), taskPoolType, task.getPriority());
                }
            }
        }
        catch (Exception e) {
            this.logger.error("\u5f02\u6b65\u4efb\u52a1\u6d41\u7a0b\u8c03\u5ea6\u521d\u59cb\u5316\u9519\u8bef: ", e);
        }
        finally {
            this.loca.unLock(this.SYSTEM_NAME + METHOD_DISPATCHER + taskPoolType);
        }
    }

    @Scheduled(cron="0 0/2 *  * * ? ")
    protected void updateEffectTime() {
        if (this.enableOldFrame) {
            this.dao.updateEffectTime(this.SERVE_ID);
        }
    }

    @Scheduled(cron="0 0/6 *  * * ? ")
    protected void scheduledDispatcher() {
        if (this.enableOldFrame) {
            this.handleOvertimeTask();
            for (String taskPoolType : this.collecter.getTaskPoolTypes()) {
                this.dispatch(taskPoolType);
            }
        }
    }

    private List<AsyncTask> queryByTaskPool(String taskPoolType, TaskState state) {
        List<AsyncTask> taskList = this.dao.queryByTaskPool(taskPoolType, state);
        return taskList;
    }

    public void init() {
        this.dao.updateOverTimeState(this.SERVE_ID);
        this.initDispatch();
        this.deleteHistoryData();
        this.publishAsyncTaskReadyEvent();
    }

    public void deleteHistoryData() {
        String jobId = UUID.randomUUID().toString();
        String jobGroupId = UUID.randomUUID().toString();
        JobDetail jobDetail = JobBuilder.newJob(DeleteHistoryDataPlanTaskRunner.class).withIdentity(jobId, jobGroupId).build();
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity(jobId, jobGroupId).startNow().withSchedule((ScheduleBuilder)CronScheduleBuilder.cronSchedule((String)"0 0 0 1/1 * ? *")).build();
        try {
            this.scheduler.scheduleJob(jobDetail, trigger);
            if (!this.scheduler.isShutdown()) {
                this.scheduler.start();
            }
        }
        catch (SchedulerException e) {
            this.logger.error(e.getMessage(), e);
        }
    }

    public void init(boolean isSysTenant) {
        if (this.enableOldFrame) {
            this.init();
        }
    }

    public String getSERVE_ID() {
        return this.SERVE_ID;
    }

    public String getSYSTEM_NAME() {
        return this.SYSTEM_NAME;
    }
}

