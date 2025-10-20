/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.nodekeeper.DistributionManager
 *  com.jiuqi.bi.core.nodekeeper.Node
 *  org.quartz.JobBuilder
 *  org.quartz.JobDataMap
 *  org.quartz.JobDetail
 *  org.quartz.JobKey
 *  org.quartz.ScheduleBuilder
 *  org.quartz.Scheduler
 *  org.quartz.SchedulerException
 *  org.quartz.Trigger
 *  org.quartz.TriggerBuilder
 *  org.quartz.impl.JobDetailImpl
 *  org.quartz.impl.matchers.GroupMatcher
 *  org.quartz.spi.OperableTrigger
 */
package com.jiuqi.bi.core.jobs.core.quartz;

import com.jiuqi.bi.core.jobs.IJobFactory;
import com.jiuqi.bi.core.jobs.JobFactory;
import com.jiuqi.bi.core.jobs.JobFactoryManager;
import com.jiuqi.bi.core.jobs.JobsException;
import com.jiuqi.bi.core.jobs.core.SchedulerManager;
import com.jiuqi.bi.core.jobs.core.bridge.AbstractJobBridge;
import com.jiuqi.bi.core.jobs.core.bridge.JobBridgeFactory;
import com.jiuqi.bi.core.jobs.core.quartz.ConcurrentMainQuartzJob;
import com.jiuqi.bi.core.jobs.core.quartz.KeyBuilder;
import com.jiuqi.bi.core.jobs.core.quartz.MainQuartzJob;
import com.jiuqi.bi.core.jobs.extension.JobDispatchControlManager;
import com.jiuqi.bi.core.jobs.manager.ConfigManager;
import com.jiuqi.bi.core.jobs.model.JobModel;
import com.jiuqi.bi.core.jobs.model.JobParameter;
import com.jiuqi.bi.core.nodekeeper.DistributionManager;
import com.jiuqi.bi.core.nodekeeper.Node;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.Random;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.ScheduleBuilder;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.matchers.GroupMatcher;
import org.quartz.spi.OperableTrigger;

public class QuartzBridge
extends AbstractJobBridge {
    public static final String TYPE = "QUARTZ";
    private static Random random = new Random();
    private volatile boolean ready = false;

    @Override
    public String getBridgeType() {
        return TYPE;
    }

    @Override
    public void init() throws JobsException {
        try {
            Scheduler scheduler = SchedulerManager.getInstance().getScheduler();
            Scheduler subScheduler = SchedulerManager.getInstance().getSubScheduler();
            Scheduler realTimeScheduler = SchedulerManager.getInstance().getRealTimeScheduler();
            Scheduler subRealTimeScheduler = SchedulerManager.getInstance().getSubRealTimeScheduler();
            if (!scheduler.isStarted()) {
                scheduler.startDelayed(80);
            }
            if (!subScheduler.isStarted()) {
                subScheduler.startDelayed(60);
            }
            if (!realTimeScheduler.isStarted()) {
                realTimeScheduler.startDelayed(60);
            }
            if (!subRealTimeScheduler.isStarted()) {
                subRealTimeScheduler.startDelayed(60);
            }
            this.ready = true;
        }
        catch (SchedulerException e) {
            throw new JobsException("\u521d\u59cb\u5316quartz\u8c03\u5ea6\u5668\u5931\u8d25\uff0c" + e.getMessage(), e);
        }
    }

    @Override
    public void restartAll() throws JobsException {
        this.ready = false;
        SchedulerManager.getInstance().restartAll();
        this.ready = true;
    }

    @Override
    public void shutdownAll() throws JobsException {
        this.ready = false;
        SchedulerManager.getInstance().shutdownAll();
    }

    @Override
    public void scheduleJob(JobModel job) throws JobsException {
        Scheduler scheduler = SchedulerManager.getInstance().getScheduler();
        JobKey key = new JobKey(job.getGuid(), job.getCategory());
        JobDetail jd = job.isConcurrency() ? JobBuilder.newJob(ConcurrentMainQuartzJob.class).withIdentity(key).storeDurably().build() : JobBuilder.newJob(MainQuartzJob.class).withIdentity(key).storeDurably().build();
        for (JobParameter p : job.getParameters()) {
            jd.getJobDataMap().put(p.getName(), p.getDefaultValue());
        }
        try {
            scheduler.deleteJob(key);
            scheduler.addJob(jd, true);
        }
        catch (SchedulerException e) {
            throw new JobsException(e.getMessage(), e);
        }
        ScheduleBuilder<? extends Trigger> builder = job.getScheduleMethod().createQuartzScheduleBuilder();
        if (builder == null) {
            return;
        }
        TriggerBuilder tb = TriggerBuilder.newTrigger().forJob(jd).withIdentity(job.getGuid() + ".t1", KeyBuilder.buildTriggerGroup(job));
        if (job.getStartTime() > System.currentTimeMillis()) {
            tb.startAt(new Date(job.getStartTime()));
        } else {
            tb.startNow();
        }
        if (job.getEndTime() > 0L) {
            tb.endAt(new Date(job.getEndTime()));
        }
        tb.withSchedule(builder);
        try {
            Trigger trigger = tb.build();
            job.getScheduleMethod().afterScheduleBuilder(trigger);
            scheduler.unscheduleJob(trigger.getKey());
            scheduler.scheduleJob(trigger);
        }
        catch (Exception e) {
            String errorMsg = e.getMessage();
            if (errorMsg == null) {
                errorMsg = "";
            }
            if (errorMsg.contains("will never fire.")) {
                return;
            }
            if (errorMsg.contains("cannot be before start time")) {
                return;
            }
            throw new JobsException(errorMsg, e);
        }
    }

    @Override
    public void unscheduleJob(String jobId, String category) throws JobsException {
        Scheduler scheduler = SchedulerManager.getInstance().getScheduler();
        try {
            scheduler.deleteJob(new JobKey(jobId, category));
        }
        catch (SchedulerException e) {
            throw new JobsException(e.getMessage(), e);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void manualExecuteJob(JobModel job, String instanceId, Map<String, String> params, String userguid, String username) throws JobsException {
        Scheduler scheduler = SchedulerManager.getInstance().getScheduler();
        JobKey jobKey = new JobKey(job.getGuid(), job.getCategory());
        JobDataMap map = new JobDataMap();
        if (params != null) {
            map.putAll(params);
        }
        map.put("__sys_userguid", userguid);
        map.put("__sys_username", username);
        map.put("__sys_instanceid", instanceId);
        map.put("__sys_rootinstanceid", instanceId);
        map.put("__sys_jobid", job.getGuid());
        map.put("__sys_jobtitle", job.getTitle());
        map.put("__sys_ismanual", "true");
        try {
            String triggerId = this.newTriggerId();
            OperableTrigger trig = (OperableTrigger)TriggerBuilder.newTrigger().withIdentity(triggerId, job.getCategory() + triggerId).forJob(jobKey).build();
            trig.computeFirstFireTime(null);
            trig.setJobDataMap(map);
            String jobDispatchType = ConfigManager.getInstance().getJobDispatchType();
            String jobMatchType = ConfigManager.getInstance().getJobMatchType();
            JobDispatchControlManager jobDispatchControlManager = new JobDispatchControlManager();
            jobDispatchControlManager.load(jobMatchType, jobDispatchType);
            IJobFactory jobFactory = JobFactoryManager.getInstance().getJobFactory(job.getCategory(), true);
            boolean allowed = jobDispatchType.equalsIgnoreCase("BY_TYPE") ? jobDispatchControlManager.isAllowed(jobFactory.getJobCategoryId()) : jobDispatchControlManager.isAllowed(Arrays.asList(jobFactory.getTags()));
            if (allowed && JobBridgeFactory.getInstance().getDefaultBridge().isReady()) {
                JobFactory curJobFactory = JobFactoryManager.getInstance().getJobFactory(job.getCategory());
                if (null != curJobFactory) {
                    Node node = DistributionManager.getInstance().self();
                    String nodeName = node.getName();
                    ConfigManager configManager = ConfigManager.getInstance();
                    configManager.setJobOnlyInNode(job.getGuid(), new String[]{nodeName});
                }
            } else {
                ConfigManager.getInstance().deleteJobOnlyInNode(job.getGuid());
            }
            Scheduler scheduler2 = scheduler;
            synchronized (scheduler2) {
                if (job.isConcurrency()) {
                    JobDetail jobDetail = scheduler.getJobDetail(jobKey);
                    if (jobDetail instanceof JobDetailImpl) {
                        if (jobDetail.getJobClass() == MainQuartzJob.class) {
                            ((JobDetailImpl)jobDetail).setJobClass(ConcurrentMainQuartzJob.class);
                            scheduler.deleteJob(jobDetail.getKey());
                            scheduler.scheduleJob(jobDetail, (Trigger)trig);
                        } else {
                            scheduler.scheduleJob((Trigger)trig);
                        }
                    }
                } else {
                    scheduler.scheduleJob((Trigger)trig);
                }
            }
        }
        catch (SchedulerException e) {
            throw new JobsException("\u624b\u52a8\u89e6\u53d1\u4efb\u52a1\u5931\u8d25", e);
        }
    }

    @Override
    public void enableJob(String jobId, String category) throws JobsException {
        try {
            Scheduler scheduler = SchedulerManager.getInstance().getScheduler();
            JobKey jobKey = new JobKey(jobId, category);
            scheduler.resumeJob(jobKey);
            scheduler.resumeTriggers(GroupMatcher.triggerGroupEquals((String)KeyBuilder.buildTriggerGroup(jobId, category)));
        }
        catch (Exception e) {
            throw new JobsException(e.getMessage(), e);
        }
    }

    @Override
    public void disableJob(String jobId, String category) throws JobsException {
        try {
            Scheduler scheduler = SchedulerManager.getInstance().getScheduler();
            JobKey jobKey = new JobKey(jobId, category);
            scheduler.pauseJob(jobKey);
            scheduler.pauseTriggers(GroupMatcher.triggerGroupEquals((String)KeyBuilder.buildTriggerGroup(jobId, category)));
        }
        catch (Exception e) {
            throw new JobsException(e.getMessage(), e);
        }
    }

    private String newTriggerId() {
        long r = random.nextLong();
        if (r < 0L) {
            r = -r;
        }
        return "MT_" + Long.toString(r, 30 + (int)(System.currentTimeMillis() % 7L));
    }

    @Override
    public boolean isReady() {
        return this.ready;
    }
}

