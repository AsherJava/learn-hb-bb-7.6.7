/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.JobManager
 *  com.jiuqi.bi.core.jobs.JobsException
 *  com.jiuqi.bi.core.jobs.model.AbstractScheduleMethod
 *  com.jiuqi.bi.core.jobs.model.JobModel
 *  com.jiuqi.bi.core.jobs.model.schedulemethod.DailyScheduleMethod
 *  com.jiuqi.bi.core.jobs.model.schedulemethod.DayHour
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.event.ParamChangeEvent$ChangeParam
 *  com.jiuqi.nr.definition.event.ParamChangeEvent$ChangeType
 *  com.jiuqi.nr.definition.event.TaskChangeEvent
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.workflow2.engine.core.applicationevent.WorkflowSettingsSaveEvent
 *  com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsDO
 *  com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsService
 *  com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsServiceImpl
 *  com.jiuqi.nr.workflow2.engine.core.settings.entity.FillInStartTimeConfig
 *  com.jiuqi.nr.workflow2.engine.core.settings.entity.WorkflowOtherSettings
 *  com.jiuqi.nr.workflow2.engine.core.settings.entity.WorkflowSelfControl
 *  org.json.JSONObject
 *  org.quartz.SchedulerException
 */
package com.jiuqi.nr.workflow2.schedule.listener;

import com.jiuqi.bi.core.jobs.JobManager;
import com.jiuqi.bi.core.jobs.JobsException;
import com.jiuqi.bi.core.jobs.model.AbstractScheduleMethod;
import com.jiuqi.bi.core.jobs.model.JobModel;
import com.jiuqi.bi.core.jobs.model.schedulemethod.DailyScheduleMethod;
import com.jiuqi.bi.core.jobs.model.schedulemethod.DayHour;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.event.ParamChangeEvent;
import com.jiuqi.nr.definition.event.TaskChangeEvent;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.workflow2.engine.core.applicationevent.WorkflowSettingsSaveEvent;
import com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsDO;
import com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsService;
import com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsServiceImpl;
import com.jiuqi.nr.workflow2.engine.core.settings.entity.FillInStartTimeConfig;
import com.jiuqi.nr.workflow2.engine.core.settings.entity.WorkflowOtherSettings;
import com.jiuqi.nr.workflow2.engine.core.settings.entity.WorkflowSelfControl;
import com.jiuqi.nr.workflow2.schedule.common.ProcessScheduleDateUtil;
import com.jiuqi.nr.workflow2.schedule.service.IProcessStartupScheduleManager;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import org.json.JSONObject;
import org.quartz.SchedulerException;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class IWFStartupScheduleListener {
    @Autowired
    protected IRunTimeViewController runTimeViewController;
    @Autowired
    protected WorkflowSettingsService workflowSettingsService;
    @Autowired
    protected IProcessStartupScheduleManager startupScheduleManager;

    @EventListener
    public void workflowSettingsSaveEventHandler(WorkflowSettingsSaveEvent event) {
        if (this.isSettingChanged(event)) {
            TaskDefine taskDefine = this.runTimeViewController.getTask(event.getTaskId());
            this.createOrUpdateTaskStartup(taskDefine);
            this.startupCheckSchedule();
        }
    }

    private boolean isSettingChanged(WorkflowSettingsSaveEvent event) {
        WorkflowSettingsDO originalSettingsDO = event.getOriginalSettingsDO();
        WorkflowOtherSettings oriWorkflowOtherSettings = WorkflowSettingsServiceImpl.parseJSONObjectToWorkflowOtherSettings((JSONObject)new JSONObject(originalSettingsDO.getOtherConfig()));
        FillInStartTimeConfig oriFillInStartTimeConfig = oriWorkflowOtherSettings.getFillInStartTimeConfig();
        WorkflowSelfControl oriWorkflowSelfControl = oriWorkflowOtherSettings.getWorkflowSelfControl();
        WorkflowSettingsDO targetSettingsDO = event.getTargetSettingsDO();
        WorkflowOtherSettings tarWorkflowOtherSettings = WorkflowSettingsServiceImpl.parseJSONObjectToWorkflowOtherSettings((JSONObject)new JSONObject(targetSettingsDO.getOtherConfig()));
        FillInStartTimeConfig tarFillInStartTimeConfig = tarWorkflowOtherSettings.getFillInStartTimeConfig();
        WorkflowSelfControl tarWorkflowSelfControl = tarWorkflowOtherSettings.getWorkflowSelfControl();
        return !oriFillInStartTimeConfig.equals(tarFillInStartTimeConfig) || !oriWorkflowSelfControl.equals(tarWorkflowSelfControl);
    }

    @EventListener
    public void TaskChangeEventHandler(TaskChangeEvent event) {
        ParamChangeEvent.ChangeType type = event.getType();
        if (ParamChangeEvent.ChangeType.DELETE == type) {
            List tasks = event.getTasks();
            for (TaskDefine taskDefine : tasks) {
                try {
                    this.startupScheduleManager.removeStartupSchedule(taskDefine);
                }
                catch (JobsException | ParseException | SchedulerException e) {
                    LoggerFactory.getLogger(this.getClass()).error(e.getMessage(), e);
                }
            }
        }
        if (ParamChangeEvent.ChangeType.UPDATE == type) {
            List changeParams = event.getChangeParams();
            for (ParamChangeEvent.ChangeParam changeParam : changeParams) {
                WorkflowOtherSettings workflowOtherSettings;
                WorkflowSelfControl startupConfig;
                TaskDefine oldValue = (TaskDefine)changeParam.getOldValue();
                TaskDefine newValue = (TaskDefine)changeParam.getNewValue();
                if (oldValue.getTaskPeriodOffset() == newValue.getTaskPeriodOffset() || (startupConfig = (workflowOtherSettings = this.workflowSettingsService.queryTaskWorkflowOtherSettings(newValue.getKey())).getWorkflowSelfControl()) == null || !startupConfig.isEnable()) continue;
                try {
                    this.startupScheduleManager.updateStartupSchedule(newValue);
                }
                catch (JobsException | ParseException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    protected void createOrUpdateTaskStartup(TaskDefine taskDefine) {
        try {
            WorkflowOtherSettings workflowOtherSettings = this.workflowSettingsService.queryTaskWorkflowOtherSettings(taskDefine.getKey());
            WorkflowSelfControl startupConfig = workflowOtherSettings.getWorkflowSelfControl();
            if (startupConfig != null && startupConfig.isEnable()) {
                this.startupScheduleManager.updateStartupSchedule(taskDefine);
            } else {
                this.startupScheduleManager.deleteStartupSchedule(taskDefine);
            }
        }
        catch (JobsException | ParseException | SchedulerException e) {
            LoggerFactory.getLogger(IWFStartupScheduleListener.class).error(e.getMessage(), e);
        }
    }

    protected void startupCheckSchedule() {
        JobManager jobManager = JobManager.getInstance((String)"NR-WORKFLOW2-SCHEDULE-FOR#startup_restitution_check");
        JobModel jobModel = this.getJobModel(jobManager, "CEC163BF-B8AC-D719-5884-64E0C8286307");
        if (jobModel == null) {
            Date[] rangeFromNow = ProcessScheduleDateUtil.getDateRangeFromNow(20);
            jobModel = jobManager.createJob("\u6d41\u7a0b2.0-\u542f\u52a8\u68c0\u67e5\u4efb\u52a1", "CEC163BF-B8AC-D719-5884-64E0C8286307");
            jobModel.setFolderGuid("workflow2-schedule-startup-job-group");
            jobModel.setEnable(true);
            jobModel.setStartTime(rangeFromNow[0].getTime());
            jobModel.setEndTime(rangeFromNow[1].getTime());
            DailyScheduleMethod dailyScheduleMethod = new DailyScheduleMethod();
            DayHour executeTime = new DayHour();
            executeTime.setHour(3);
            executeTime.setMinute(0);
            executeTime.setSecond(0);
            dailyScheduleMethod.setExecuteTimeInDay(executeTime);
            jobModel.setScheduleMethod((AbstractScheduleMethod)dailyScheduleMethod);
            try {
                jobManager.addJobModel(jobModel);
            }
            catch (JobsException e) {
                LoggerFactory.getLogger(IWFStartupScheduleListener.class).error(e.getMessage(), e);
            }
        }
    }

    protected JobModel getJobModel(JobManager jobManager, String jobId) {
        try {
            return jobManager.getJob(jobId);
        }
        catch (JobsException e) {
            return null;
        }
    }

    protected void deleteCheckSchedule() throws JobsException {
        JobManager jobManager = JobManager.getInstance((String)"NR-WORKFLOW2-SCHEDULE-FOR#startup_restitution_check");
        JobModel jobModel = this.getJobModel(jobManager, "CEC163BF-B8AC-D719-5884-64E0C8286307");
        if (jobModel != null) {
            jobManager.jobEnable(jobModel.getGuid(), false);
        }
    }
}

