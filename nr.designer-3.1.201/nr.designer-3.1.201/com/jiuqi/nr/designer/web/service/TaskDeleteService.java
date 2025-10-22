/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.message.TodoCompleteEvent
 *  com.jiuqi.nr.definition.controller.IDesignTimeViewController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.message.NrTaskDeleteEvent
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.designer.web.service;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.message.TodoCompleteEvent;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.message.NrTaskDeleteEvent;
import com.jiuqi.nr.designer.common.NrDesignLogHelper;
import com.jiuqi.nr.designer.common.NrDesingerErrorEnum;
import com.jiuqi.nr.designer.web.service.ReportTaskService;
import com.jiuqi.nr.designer.web.service.TaskDeleteCondition;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

@Component
public class TaskDeleteService {
    @Resource
    private ReportTaskService reportTaskService;
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private IRunTimeViewController runtimeService;
    @Autowired
    private IDesignTimeViewController designtimeService;

    public void deleteTask(AsyncTaskMonitor asyncTaskMonitor, TaskDeleteCondition deleteCondition) throws Exception {
        String taskKey = deleteCondition.getTaskId();
        List formSchemeDefines = this.runtimeService.queryFormSchemeByTask(taskKey);
        DesignTaskDefine designTaskDefine = this.designtimeService.queryTaskDefine(taskKey);
        this.deleteReportTask(taskKey, deleteCondition.deleteData);
        this.designtimeService.deleteDimensionFilter(taskKey);
        ArrayList formSchemeKeys = new ArrayList();
        Optional.ofNullable(formSchemeDefines).orElse(Collections.emptyList()).forEach(formSchemeDefine -> formSchemeKeys.add(formSchemeDefine.getKey()));
        if (!formSchemeKeys.isEmpty()) {
            TodoCompleteEvent todoCompleteEvent = new TodoCompleteEvent();
            todoCompleteEvent.setFormSchemeKey(formSchemeKeys);
            todoCompleteEvent.setUserId(NpContextHolder.getContext().getUserId());
            this.applicationContext.publishEvent(todoCompleteEvent);
            NrTaskDeleteEvent nrTaskDeleteEvent = new NrTaskDeleteEvent((Object)this);
            nrTaskDeleteEvent.setTaskKey(taskKey);
            nrTaskDeleteEvent.setFormSchemeDefines(formSchemeDefines);
            nrTaskDeleteEvent.setUserId(NpContextHolder.getContext().getUserId());
            nrTaskDeleteEvent.setTaskdefine((TaskDefine)designTaskDefine);
            this.applicationContext.publishEvent((ApplicationEvent)nrTaskDeleteEvent);
        }
        asyncTaskMonitor.progressAndMessage(1.0, "\u4efb\u52a1\u5220\u9664\u6210\u529f");
        if (asyncTaskMonitor.isCancel()) {
            String retStr = "\u4efb\u52a1\u53d6\u6d88";
            asyncTaskMonitor.canceled(retStr, (Object)retStr);
        }
    }

    private void deleteReportTask(String taskId, boolean delLinkedParam) throws JQException {
        String taskTitle = "\u672a\u77e5";
        String logTitle = "\u5220\u9664\u4efb\u52a1";
        try {
            taskTitle = this.designtimeService.queryTaskDefine(taskId).getTitle();
            this.reportTaskService.deleteReportTask(taskId, delLinkedParam);
            NrDesignLogHelper.log(logTitle, taskTitle, NrDesignLogHelper.LOGLEVEL_INFO);
        }
        catch (JQException e) {
            NrDesignLogHelper.log(logTitle, taskTitle, NrDesignLogHelper.LOGLEVEL_ERROR);
            throw e;
        }
        catch (Exception e) {
            NrDesignLogHelper.log(logTitle, taskTitle, NrDesignLogHelper.LOGLEVEL_ERROR);
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_013);
        }
    }
}

