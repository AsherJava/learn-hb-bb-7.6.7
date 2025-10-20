/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.type.GUID
 *  org.quartz.JobBuilder
 *  org.quartz.JobDataMap
 *  org.quartz.JobDetail
 *  org.quartz.ScheduleBuilder
 *  org.quartz.Scheduler
 *  org.quartz.SimpleScheduleBuilder
 *  org.quartz.SimpleTrigger
 *  org.quartz.TriggerBuilder
 */
package com.jiuqi.bi.core.jobs.base;

import com.jiuqi.bi.core.jobs.JobsException;
import com.jiuqi.bi.core.jobs.base.AbstractBaseJob;
import com.jiuqi.bi.core.jobs.base.BaseJobFactory;
import com.jiuqi.bi.core.jobs.base.BaseJobFactoryManager;
import com.jiuqi.bi.core.jobs.base.quartz.BaseQuartzJob;
import com.jiuqi.bi.core.jobs.core.MDCUtil;
import com.jiuqi.bi.core.jobs.core.SchedulerCommitPool;
import com.jiuqi.bi.core.jobs.core.SchedulerManager;
import com.jiuqi.bi.core.jobs.core.quartz.KeyBuilder;
import com.jiuqi.bi.core.jobs.manager.JobOperationManager;
import com.jiuqi.bi.core.jobs.monitor.JobType;
import com.jiuqi.bi.util.type.GUID;
import java.util.ArrayList;
import java.util.Map;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.ScheduleBuilder;
import org.quartz.Scheduler;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.SimpleTrigger;
import org.quartz.TriggerBuilder;

public class BaseJobManager {
    private final BaseJobFactory factory;

    public BaseJobManager(BaseJobFactory factory) {
        this.factory = factory;
    }

    public static BaseJobManager getInstance(String categoryId) {
        return BaseJobFactoryManager.getInstance().getBaseJobManager(categoryId);
    }

    public String executeJob(String jobId, Map<String, String> params, String userGuid, String userName) throws JobsException {
        String instanceId = GUID.newGUID();
        Scheduler scheduler = SchedulerManager.getInstance().getScheduler();
        JobDataMap jobData = new JobDataMap();
        jobData.put("__sys_jobid", jobId);
        jobData.put("__sys_instanceid", instanceId);
        jobData.put("__sys_categoryid", this.factory.getJobCategoryId());
        jobData.put("__sys_userguid", userGuid);
        jobData.put("__sys_username", userName);
        jobData.put("__sys_parammap", params);
        MDCUtil.generateMDC(jobData);
        AbstractBaseJob jobExecutor = this.factory.createJob(jobId);
        jobExecutor.setUserGuid(userGuid);
        jobExecutor.setUserName(userName);
        jobExecutor.getParams().putAll(params);
        String jobTitle = jobExecutor.getTitle();
        jobData.put("__sys_jobtitle", jobTitle);
        JobDetail jobDetail = JobBuilder.newJob(BaseQuartzJob.class).withIdentity(instanceId, this.factory.getJobCategoryId()).usingJobData(jobData).build();
        SimpleTrigger trigger = (SimpleTrigger)TriggerBuilder.newTrigger().forJob(jobDetail).withIdentity(jobId, KeyBuilder.buildTriggerGroup(jobDetail)).withSchedule((ScheduleBuilder)SimpleScheduleBuilder.simpleSchedule().withMisfireHandlingInstructionNowWithRemainingCount().withRepeatCount(0)).startNow().build();
        JobOperationManager om = new JobOperationManager();
        try {
            om.mainJobAdded(instanceId, jobId, userGuid, userName, jobTitle, JobType.MANUAL_JOB, this.factory.getJobCategoryId(), this.factory.getJobCategoryTitle(), true, new ArrayList<String>(), null, null);
            SchedulerCommitPool.getInstance().addMainTrigger(jobDetail, trigger);
            return instanceId;
        }
        catch (Exception e) {
            throw new JobsException(e);
        }
    }
}

