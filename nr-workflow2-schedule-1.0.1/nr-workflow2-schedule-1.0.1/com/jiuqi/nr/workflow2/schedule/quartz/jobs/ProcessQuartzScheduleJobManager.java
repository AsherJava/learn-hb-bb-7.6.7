/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.JobsException
 *  com.jiuqi.bi.core.jobs.core.SchedulerManager
 *  com.jiuqi.np.asynctask.util.SimpleParamConverter$SerializationUtils
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  org.quartz.JobBuilder
 *  org.quartz.JobKey
 *  org.quartz.Scheduler
 *  org.quartz.SchedulerException
 *  org.quartz.Trigger
 */
package com.jiuqi.nr.workflow2.schedule.quartz.jobs;

import com.jiuqi.bi.core.jobs.JobsException;
import com.jiuqi.bi.core.jobs.core.SchedulerManager;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.workflow2.schedule.quartz.jobs.ProcessQuartzStartupJobExecutor;
import com.jiuqi.nr.workflow2.schedule.service.IProcessPeriodTriggerService;
import com.jiuqi.nr.workflow2.schedule.service.IProcessStartupScheduleManager;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import org.quartz.JobBuilder;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProcessQuartzScheduleJobManager
implements IProcessStartupScheduleManager {
    @Autowired
    protected IRunTimeViewController runTimeViewController;
    @Autowired
    protected IProcessPeriodTriggerService periodTriggerService;

    @Override
    public void updateStartupSchedule(TaskDefine taskDefine) throws ParseException, JobsException {
        List periodRangeList = this.runTimeViewController.listSchemePeriodLinkByTask(taskDefine.getKey());
        ArrayList<Trigger> triggers = new ArrayList<Trigger>();
        for (SchemePeriodLinkDefine periodLinkDefine : periodRangeList) {
            Trigger trigger = this.periodTriggerService.buildStartupTrigger(taskDefine, periodLinkDefine.getPeriodKey());
            triggers.add(trigger);
        }
        JobBuilder jobBuilder = JobBuilder.newJob(ProcessQuartzStartupJobExecutor.class);
        jobBuilder.withIdentity(taskDefine.getKey(), "workflow2-startup-schedule-job-group");
        jobBuilder.usingJobData("np-context", SimpleParamConverter.SerializationUtils.serializeToString((Object)NpContextHolder.getContext()));
        Scheduler scheduler = SchedulerManager.getInstance().getScheduler();
        try {
            scheduler.scheduleJob(jobBuilder.build(), new HashSet(triggers), true);
            scheduler.start();
        }
        catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteStartupSchedule(TaskDefine taskDefine) throws SchedulerException, JobsException {
        JobKey jobKey = new JobKey(taskDefine.getKey(), "workflow2-startup-schedule-job-group");
        Scheduler scheduler = SchedulerManager.getInstance().getScheduler();
        if (scheduler.checkExists(jobKey)) {
            scheduler.pauseJob(jobKey);
            scheduler.deleteJob(jobKey);
        }
    }

    @Override
    public void removeStartupSchedule(TaskDefine taskDefine) throws SchedulerException, JobsException, ParseException {
        this.deleteStartupSchedule(taskDefine);
    }

    @Override
    public void immediateStartupSchedule(TaskDefine taskDefine, String period) {
    }

    @Override
    public void createOrUpdateStartupSchedule(TaskDefine taskDefine, String period) throws JobsException, ParseException {
    }
}

