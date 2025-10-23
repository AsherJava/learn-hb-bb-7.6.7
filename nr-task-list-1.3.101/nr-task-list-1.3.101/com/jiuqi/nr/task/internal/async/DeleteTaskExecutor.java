/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.JobContext
 *  com.jiuqi.bi.core.jobs.JobExecutionException
 *  com.jiuqi.bi.core.jobs.realtime.RealTimeJob
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.asynctask.NpRealTimeTaskExecutor
 *  com.jiuqi.np.asynctask.impl.RealTimeTaskMonitor
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.message.TodoCompleteEvent
 *  com.jiuqi.nr.definition.api.IDesignTimeViewController
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.controller.IViewDeployController
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.controller2.DesignTimeViewController
 *  com.jiuqi.nr.definition.message.NrTaskDeleteEvent
 */
package com.jiuqi.nr.task.internal.async;

import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.bi.core.jobs.JobExecutionException;
import com.jiuqi.bi.core.jobs.realtime.RealTimeJob;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.asynctask.NpRealTimeTaskExecutor;
import com.jiuqi.np.asynctask.impl.RealTimeTaskMonitor;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.message.TodoCompleteEvent;
import com.jiuqi.nr.definition.api.IDesignTimeViewController;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.controller.IViewDeployController;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.controller2.DesignTimeViewController;
import com.jiuqi.nr.definition.message.NrTaskDeleteEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;

@RealTimeJob(group="ASYNC_TASK_DELETE", groupTitle="\u8868\u6837\u5bfc\u51fa")
public class DeleteTaskExecutor
extends NpRealTimeTaskExecutor {
    private static final Logger log = LoggerFactory.getLogger(DeleteTaskExecutor.class);
    public static final String TYPE = "ASYNC_TASK_DELETE";

    public void executeWithNpContext(JobContext jobContext) throws JobExecutionException {
        DesignTimeViewController designTimeViewController = (DesignTimeViewController)SpringBeanUtils.getBean(DesignTimeViewController.class);
        String taskKey = jobContext.getRealTimeJob().getQueryField1();
        RealTimeTaskMonitor asyncTaskMonitor = new RealTimeTaskMonitor(jobContext.getInstanceId(), TYPE, jobContext);
        DesignTaskDefine task = designTimeViewController.getTask(taskKey);
        if ("1.0".equals(task.getVersion())) {
            this.deleteOldVersionTask(taskKey, (AsyncTaskMonitor)asyncTaskMonitor);
        } else {
            this.deleteTask(taskKey, (AsyncTaskMonitor)asyncTaskMonitor);
        }
    }

    private void deleteTask(String taskKey, AsyncTaskMonitor asyncTaskMonitor) {
        DesignTimeViewController designTimeViewController = (DesignTimeViewController)SpringBeanUtils.getBean(DesignTimeViewController.class);
        try {
            asyncTaskMonitor.progressAndMessage(0.1, "\u5220\u9664\u4efb\u52a1");
            designTimeViewController.deleteTask(new String[]{taskKey});
            asyncTaskMonitor.progressAndMessage(0.7, "\u5220\u9664\u751f\u6548\u65f6\u671f");
            designTimeViewController.deleteSchemePeriodLinkByTask(taskKey);
            asyncTaskMonitor.progressAndMessage(0.8, "\u5220\u9664\u8fc7\u6ee4\u6a21\u677f");
            designTimeViewController.deleteIDimensionFilterByTask(taskKey);
            asyncTaskMonitor.progressAndMessage(0.9, "\u5220\u9664\u591a\u53e3\u5f84\u5173\u8054");
            designTimeViewController.deleteTaskOrgLinkByTask(taskKey);
            asyncTaskMonitor.progressAndMessage(1.0, "\u4efb\u52a1\u5220\u9664\u6210\u529f");
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            asyncTaskMonitor.error("\u4efb\u52a1\u51fa\u9519", (Throwable)e);
        }
    }

    private void deleteOldVersionTask(String taskKey, AsyncTaskMonitor asyncTaskMonitor) {
        IRunTimeViewController runTimeViewController = (IRunTimeViewController)SpringBeanUtils.getBean(IRunTimeViewController.class);
        IDesignTimeViewController designTimeViewController = (IDesignTimeViewController)SpringBeanUtils.getBean(IDesignTimeViewController.class);
        List formSchemeDefines = runTimeViewController.listFormSchemeByTask(taskKey);
        DesignTaskDefine designTaskDefine = designTimeViewController.getTask(taskKey);
        asyncTaskMonitor.progressAndMessage(0.1, "\u5220\u9664\u4efb\u52a1");
        this.deleteReportTask(taskKey);
        asyncTaskMonitor.progressAndMessage(0.6, "\u5220\u9664\u8fc7\u6ee4\u6a21\u677f");
        designTimeViewController.deleteIDimensionFilterByTask(taskKey);
        ArrayList formSchemeKeys = new ArrayList();
        Optional.ofNullable(formSchemeDefines).orElse(Collections.emptyList()).forEach(formSchemeDefine -> formSchemeKeys.add(formSchemeDefine.getKey()));
        asyncTaskMonitor.progressAndMessage(0.7, "\u5f85\u529e");
        if (!formSchemeKeys.isEmpty()) {
            ApplicationContext applicationContext = SpringBeanUtils.getApplicationContext();
            TodoCompleteEvent todoCompleteEvent = new TodoCompleteEvent();
            todoCompleteEvent.setFormSchemeKey(formSchemeKeys);
            todoCompleteEvent.setUserId(NpContextHolder.getContext().getUserId());
            applicationContext.publishEvent(todoCompleteEvent);
            NrTaskDeleteEvent nrTaskDeleteEvent = new NrTaskDeleteEvent((Object)this);
            nrTaskDeleteEvent.setTaskKey(taskKey);
            nrTaskDeleteEvent.setFormSchemeDefines(formSchemeDefines);
            nrTaskDeleteEvent.setUserId(NpContextHolder.getContext().getUserId());
            nrTaskDeleteEvent.setTaskdefine((TaskDefine)designTaskDefine);
            applicationContext.publishEvent((ApplicationEvent)nrTaskDeleteEvent);
        }
        asyncTaskMonitor.progressAndMessage(1.0, "\u4efb\u52a1\u5220\u9664\u6210\u529f");
        if (asyncTaskMonitor.isCancel()) {
            String retStr = "\u4efb\u52a1\u53d6\u6d88";
            asyncTaskMonitor.canceled(retStr, (Object)retStr);
        }
    }

    private void deleteReportTask(String taskId) {
        String taskTitle = "\u672a\u77e5";
        IDesignTimeViewController designTimeViewController = (IDesignTimeViewController)SpringBeanUtils.getBean(IDesignTimeViewController.class);
        IViewDeployController deployController = (IViewDeployController)SpringBeanUtils.getBean(IViewDeployController.class);
        try {
            taskTitle = designTimeViewController.getTask(taskId).getTitle();
            designTimeViewController.deleteTask(new String[]{taskId});
            deployController.deployTask(taskId, false);
        }
        catch (Exception e) {
            throw new RuntimeException("\u5220\u9664\u4efb\u52a1" + taskTitle + "\u65f6\u53d1\u751f\u5f02\u5e38\uff1a" + e.getMessage(), e);
        }
    }
}

