/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.JobManager
 *  com.jiuqi.bi.core.jobs.JobsException
 *  com.jiuqi.bi.core.jobs.manager.JobStorageManager
 *  com.jiuqi.bi.core.jobs.model.AbstractScheduleMethod
 *  com.jiuqi.bi.core.jobs.model.IScheduleMethod
 *  com.jiuqi.bi.core.jobs.model.JobModel
 *  com.jiuqi.bi.core.jobs.model.JobParameter
 *  com.jiuqi.bi.core.jobs.model.schedulemethod.SimpleScheduleMethod
 *  com.jiuqi.np.asynctask.util.SimpleParamConverter$SerializationUtils
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.workflow2.engine.core.settings.utils.JavaBeanUtils
 *  org.quartz.Trigger
 */
package com.jiuqi.nr.workflow2.schedule.bi.jobs;

import com.jiuqi.bi.core.jobs.JobManager;
import com.jiuqi.bi.core.jobs.JobsException;
import com.jiuqi.bi.core.jobs.manager.JobStorageManager;
import com.jiuqi.bi.core.jobs.model.AbstractScheduleMethod;
import com.jiuqi.bi.core.jobs.model.IScheduleMethod;
import com.jiuqi.bi.core.jobs.model.JobModel;
import com.jiuqi.bi.core.jobs.model.JobParameter;
import com.jiuqi.bi.core.jobs.model.schedulemethod.SimpleScheduleMethod;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.workflow2.engine.core.settings.utils.JavaBeanUtils;
import com.jiuqi.nr.workflow2.schedule.bi.jobs.param.ProcessStartupRunPara;
import com.jiuqi.nr.workflow2.schedule.common.ProcessScheduleDateUtil;
import com.jiuqi.nr.workflow2.schedule.dao.IWFSTriggerPlanDao;
import com.jiuqi.nr.workflow2.schedule.dao.WFSTriggerEntity;
import com.jiuqi.nr.workflow2.schedule.dao.impl.WFSTriggerEntityImpl;
import com.jiuqi.nr.workflow2.schedule.enumeration.WFSTriggerStatus;
import com.jiuqi.nr.workflow2.schedule.service.IProcessPeriodTriggerService;
import com.jiuqi.nr.workflow2.schedule.service.IProcessStartupScheduleManager;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProcessBiScheduleJobManager
implements IProcessStartupScheduleManager {
    public static final String NP_CONTEXT = "NP_CONTEXT";
    @Autowired
    protected IRunTimeViewController runTimeViewController;
    @Autowired
    protected IWFSTriggerPlanDao triggerPlanDao;
    @Autowired
    protected IProcessPeriodTriggerService periodTriggerService;

    @Override
    public void updateStartupSchedule(TaskDefine taskDefine) throws ParseException, JobsException {
        ArrayList<WFSTriggerEntityImpl> triggerEntities = new ArrayList<WFSTriggerEntityImpl>();
        List periodRangeList = this.runTimeViewController.listSchemePeriodLinkByTask(taskDefine.getKey());
        List validRangePeriods = periodRangeList.stream().map(SchemePeriodLinkDefine::getPeriodKey).sorted().collect(Collectors.toList());
        for (String period : validRangePeriods) {
            Trigger trigger = this.periodTriggerService.buildStartupTrigger(taskDefine, period);
            if (ProcessScheduleDateUtil.isNowAfter(trigger.getEndTime())) {
                triggerEntities.add(this.buildTriggerEntity(taskDefine, period, trigger));
                continue;
            }
            if (ProcessScheduleDateUtil.isNowInRange(trigger.getStartTime(), trigger.getEndTime())) {
                this.immediateStartupSchedule(taskDefine, period);
                break;
            }
            if (!ProcessScheduleDateUtil.isNowBefore(trigger.getStartTime())) continue;
            this.createOrUpdateStartupSchedule(taskDefine, period);
            break;
        }
        this.insertOrUpdateTriggerEntities(taskDefine, triggerEntities);
    }

    @Override
    public void deleteStartupSchedule(TaskDefine taskDefine) throws JobsException {
        JobManager jobManager = JobManager.getInstance((String)"NR-WORKFLOW2-SCHEDULE-FOR#start_instances");
        JobModel jobModel = this.queryJobModel(this.getJobGuid(taskDefine));
        if (jobModel != null) {
            jobManager.jobEnable(jobModel.getGuid(), false);
        }
    }

    @Override
    public void removeStartupSchedule(TaskDefine taskDefine) throws JobsException {
        JobManager jobManager = JobManager.getInstance((String)"NR-WORKFLOW2-SCHEDULE-FOR#start_instances");
        JobModel jobModel = this.queryJobModel(this.getJobGuid(taskDefine));
        if (jobModel != null) {
            jobManager.deleteJob(jobModel.getGuid());
        }
    }

    @Override
    public void immediateStartupSchedule(TaskDefine taskDefine, String period) throws JobsException, ParseException {
        Trigger trigger = this.periodTriggerService.buildStartupTrigger(taskDefine, period);
        JobManager jobManager = JobManager.getInstance((String)"NR-WORKFLOW2-SCHEDULE-FOR#start_instances");
        JobModel jobModel = this.createJobModel(jobManager, taskDefine, period, trigger);
        ProcessStartupRunPara startupRunPara = this.buildStartupRunPara(taskDefine, period);
        jobModel.setExtendedConfig(JavaBeanUtils.toJSONStr((Object)startupRunPara));
        SimpleScheduleMethod simpleScheduleMethod = new SimpleScheduleMethod();
        simpleScheduleMethod.setExecuteTime(System.currentTimeMillis());
        jobModel.setScheduleMethod((AbstractScheduleMethod)simpleScheduleMethod);
        List<JobParameter> jobParameters = this.getJobParameters(taskDefine);
        jobModel.getParameters().addAll(jobParameters);
        if (this.hasJobModel(jobModel.getGuid())) {
            this.updateJobModel(jobManager, jobModel, simpleScheduleMethod);
        } else {
            jobManager.addJobModel(jobModel);
        }
    }

    @Override
    public void createOrUpdateStartupSchedule(TaskDefine taskDefine, String period) throws JobsException, ParseException {
        Trigger trigger = this.periodTriggerService.buildStartupTrigger(taskDefine, period);
        JobManager jobManager = JobManager.getInstance((String)"NR-WORKFLOW2-SCHEDULE-FOR#start_instances");
        JobModel jobModel = this.createJobModel(jobManager, taskDefine, period, trigger);
        ProcessStartupRunPara startupRunPara = this.buildStartupRunPara(taskDefine, period);
        jobModel.setExtendedConfig(JavaBeanUtils.toJSONStr((Object)startupRunPara));
        SimpleScheduleMethod simpleScheduleMethod = new SimpleScheduleMethod();
        simpleScheduleMethod.setExecuteTime(trigger.getStartTime().getTime());
        jobModel.setScheduleMethod((AbstractScheduleMethod)simpleScheduleMethod);
        List<JobParameter> jobParameters = this.getJobParameters(taskDefine);
        jobModel.getParameters().addAll(jobParameters);
        if (this.hasJobModel(jobModel.getGuid())) {
            this.updateJobModel(jobManager, jobModel, simpleScheduleMethod);
        } else {
            jobManager.addJobModel(jobModel);
        }
    }

    protected WFSTriggerEntityImpl buildTriggerEntity(TaskDefine taskDefine, String period, Trigger trigger) {
        WFSTriggerEntityImpl entity = new WFSTriggerEntityImpl();
        entity.setTaskKey(taskDefine.getKey());
        entity.setPeriod(period);
        entity.setStatus(WFSTriggerStatus.UN_EXECUTED);
        entity.setPlanedStartTime(trigger.getStartTime());
        entity.setPlanedEndTime(trigger.getEndTime());
        return entity;
    }

    protected void insertOrUpdateTriggerEntities(TaskDefine taskDefine, List<WFSTriggerEntityImpl> triggerEntities) {
        List<WFSTriggerEntity> hiTriggerEntities = this.triggerPlanDao.queryRowsByTask(taskDefine.getKey());
        ArrayList<WFSTriggerEntity> insertEntities = new ArrayList<WFSTriggerEntity>();
        ArrayList<WFSTriggerEntity> updateEntities = new ArrayList<WFSTriggerEntity>();
        for (WFSTriggerEntityImpl triggerEntity : triggerEntities) {
            Optional<WFSTriggerEntity> findByPeriod = hiTriggerEntities.stream().filter(e -> e.getPeriod().equals(triggerEntity.getPeriod())).findFirst();
            if (findByPeriod.isPresent()) {
                triggerEntity.setExecCount(findByPeriod.get().getExecCount() + 1);
                updateEntities.add(triggerEntity);
                continue;
            }
            insertEntities.add(triggerEntity);
        }
        this.triggerPlanDao.updateRow(updateEntities);
        this.triggerPlanDao.insertRows(insertEntities);
    }

    protected ProcessStartupRunPara buildStartupRunPara(TaskDefine taskDefine, String period) {
        ProcessStartupRunPara startupRunPara = new ProcessStartupRunPara();
        startupRunPara.setTaskKey(taskDefine.getKey());
        startupRunPara.setPeriod(period);
        return startupRunPara;
    }

    protected String getJobGuid(TaskDefine taskDefine) {
        return taskDefine.getTaskCode();
    }

    protected String getJobTitle(TaskDefine taskDefine, String period) {
        return taskDefine.getTitle() + "[" + period + "]";
    }

    protected JobModel queryJobModel(String jobGuid) throws JobsException {
        JobStorageManager jobStorageManager = new JobStorageManager();
        return jobStorageManager.getJob(jobGuid);
    }

    protected boolean hasJobModel(String jobGuid) throws JobsException {
        return this.queryJobModel(jobGuid) != null;
    }

    protected JobModel createJobModel(JobManager jobManager, TaskDefine taskDefine, String period, Trigger trigger) {
        JobModel jobModel = jobManager.createJob(this.getJobTitle(taskDefine, period), this.getJobGuid(taskDefine));
        jobModel.setFolderGuid("workflow2-schedule-startup-job-group");
        jobModel.setEnable(true);
        jobModel.setStartTime(trigger.getStartTime().getTime());
        jobModel.setEndTime(trigger.getEndTime().getTime());
        return jobModel;
    }

    protected void updateJobModel(JobManager jobManager, JobModel jobModel, SimpleScheduleMethod simpleScheduleMethod) throws JobsException {
        jobManager.jobEnable(jobModel.getGuid(), true);
        jobManager.updateJobTitle(jobModel.getGuid(), jobModel.getTitle());
        jobManager.updateJobParameter(jobModel.getGuid(), jobModel.getParameters());
        jobManager.updateJobConfig(jobModel.getGuid(), jobModel.getExtendedConfig());
        jobManager.updateJobScheduleConf(jobModel.getGuid(), jobModel.getStartTime(), jobModel.getEndTime(), (IScheduleMethod)simpleScheduleMethod);
    }

    protected List<JobParameter> getJobParameters(TaskDefine taskDefine) {
        JobParameter jobParameter = new JobParameter();
        jobParameter.setName(NP_CONTEXT);
        jobParameter.setTitle("\u6267\u884c\u4e0a\u4e0b\u6587");
        jobParameter.setDefaultValue(SimpleParamConverter.SerializationUtils.serializeToString((Object)NpContextHolder.getContext()));
        ArrayList<JobParameter> parameters = new ArrayList<JobParameter>();
        parameters.add(jobParameter);
        return parameters;
    }
}

