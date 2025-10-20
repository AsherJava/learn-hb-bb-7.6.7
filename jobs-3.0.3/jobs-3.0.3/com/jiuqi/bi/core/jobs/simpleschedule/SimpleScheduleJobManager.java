/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.quartz.JobBuilder
 *  org.quartz.JobDataMap
 *  org.quartz.JobDetail
 *  org.quartz.JobKey
 *  org.quartz.ScheduleBuilder
 *  org.quartz.Scheduler
 *  org.quartz.SchedulerException
 *  org.quartz.Trigger
 *  org.quartz.TriggerBuilder
 *  org.quartz.impl.matchers.GroupMatcher
 */
package com.jiuqi.bi.core.jobs.simpleschedule;

import com.jiuqi.bi.core.jobs.JobsException;
import com.jiuqi.bi.core.jobs.core.SchedulerManager;
import com.jiuqi.bi.core.jobs.core.quartz.KeyBuilder;
import com.jiuqi.bi.core.jobs.model.AbstractScheduleMethod;
import com.jiuqi.bi.core.jobs.simpleschedule.AbstractSimpleScheduleJob;
import com.jiuqi.bi.core.jobs.simpleschedule.SimpleJobManager;
import com.jiuqi.bi.core.jobs.simpleschedule.SimpleQuartzJob;
import com.jiuqi.bi.core.jobs.simpleschedule.bean.SimpleJobBean;
import java.util.Date;
import java.util.Map;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.ScheduleBuilder;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleScheduleJobManager {
    private SimpleJobManager simpleJobManager = new SimpleJobManager();
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public String schedule(String jobTitle, AbstractSimpleScheduleJob scheduleJob, Long startTime, Long endTime, AbstractScheduleMethod method, String userGuid, String userName, Map<String, String> params) throws JobsException {
        String jobId = scheduleJob.getJobId();
        Scheduler scheduler = SchedulerManager.getInstance().getScheduler();
        JobDataMap jobData = new JobDataMap();
        jobData.put("_SCHEDULE_JOB_ENTITY", (Object)scheduleJob);
        jobData.put("_SCHEDULE_SIMPLE_JOB_ID", jobId);
        jobData.put("_SCHEDULE_JOB_GROUP", scheduleJob.getJobGroupId());
        jobData.put("_SCHEDULE_JOB_GROUP_TITLE", scheduleJob.getJobGroupTitle());
        if (params != null) {
            jobData.putAll(params);
        }
        JobDetail jobDetail = JobBuilder.newJob(SimpleQuartzJob.class).withIdentity(jobId, "com.jiuqi.bi.jobs.simpleschedule").usingJobData(jobData).build();
        ScheduleBuilder<? extends Trigger> builder = method.createQuartzScheduleBuilder();
        TriggerBuilder tb = TriggerBuilder.newTrigger().forJob(jobDetail).withIdentity(jobId, KeyBuilder.buildTriggerGroup(jobDetail));
        if (startTime > System.currentTimeMillis()) {
            tb.startAt(new Date(startTime));
        } else {
            tb.startNow();
        }
        if (endTime > 0L) {
            tb.endAt(new Date(endTime));
        }
        tb.withSchedule(builder);
        Trigger trigger = tb.build();
        method.afterScheduleBuilder(trigger);
        this.addSimpleJob(scheduleJob, method);
        try {
            scheduler.scheduleJob(jobDetail, trigger);
            this.logger.debug("\u4efb\u52a1[{}]\u5df2\u6dfb\u52a0\u8fdb\u8c03\u5ea6\u5668\u7b49\u5f85\u8c03\u5ea6", (Object)jobTitle);
        }
        catch (SchedulerException e) {
            throw new JobsException(e);
        }
        return jobId;
    }

    public void resumeJob(String jobId) throws JobsException {
        Scheduler scheduler = SchedulerManager.getInstance().getScheduler();
        JobKey jobKey = new JobKey("ANNO_" + jobId, "com.jiuqi.bi.jobs.simpleschedule");
        try {
            scheduler.resumeJob(jobKey);
            scheduler.resumeTriggers(GroupMatcher.triggerGroupEquals((String)KeyBuilder.buildTriggerGroup("ANNO_" + jobId, "com.jiuqi.bi.jobs.simpleschedule")));
        }
        catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

    public void pauseJob(String jobId) throws JobsException {
        Scheduler scheduler = SchedulerManager.getInstance().getScheduler();
        JobKey jobKey = new JobKey("ANNO_" + jobId, "com.jiuqi.bi.jobs.simpleschedule");
        try {
            scheduler.pauseJob(jobKey);
            scheduler.pauseTriggers(GroupMatcher.triggerGroupEquals((String)KeyBuilder.buildTriggerGroup("ANNO_" + jobId, "com.jiuqi.bi.jobs.simpleschedule")));
        }
        catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeSchedule(String jobId) throws JobsException {
        Scheduler scheduler = SchedulerManager.getInstance().getScheduler();
        JobKey jobKey = new JobKey("ANNO_" + jobId, "com.jiuqi.bi.jobs.simpleschedule");
        try {
            scheduler.deleteJob(jobKey);
        }
        catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteSimpleJob(AbstractSimpleScheduleJob simpleScheduleJob) throws JobsException {
        try {
            this.simpleJobManager.deleteSimpleJob(simpleScheduleJob.getJobId());
            this.removeSchedule(simpleScheduleJob.getJobId());
        }
        catch (Exception e) {
            throw new JobsException("\u5220\u9664\u7b80\u5355\u4efb\u52a1\u6a21\u578b\u5931\u8d25\uff1a" + e.getMessage(), e);
        }
    }

    private void addSimpleJob(AbstractSimpleScheduleJob simpleScheduleJob, AbstractScheduleMethod method) throws JobsException {
        SimpleJobBean bean = new SimpleJobBean();
        bean.setGuid(simpleScheduleJob.getJobId());
        bean.setGroupId(simpleScheduleJob.getJobGroupId());
        bean.setCategory("com.jiuqi.bi.jobs.simpleschedule");
        bean.setTitle(simpleScheduleJob.getTitle());
        bean.setUser(simpleScheduleJob.getUserName());
        bean.setScheduleMethod(method);
        bean.setFolderGuid("com.jiuqi.bi.jobs.simpleschedule");
        bean.setExecuteType(simpleScheduleJob.getExecuteType().getName());
        try {
            this.simpleJobManager.addSimpleJob(bean);
        }
        catch (Exception e) {
            throw new JobsException("\u6dfb\u52a0\u7b80\u5355\u4efb\u52a1\u6a21\u578b\u5931\u8d25\uff1a" + e.getMessage(), e);
        }
    }
}

