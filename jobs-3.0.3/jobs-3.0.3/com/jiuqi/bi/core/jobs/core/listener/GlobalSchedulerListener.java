/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  org.quartz.JobDetail
 *  org.quartz.JobKey
 *  org.quartz.SchedulerException
 *  org.quartz.SchedulerListener
 *  org.quartz.Trigger
 *  org.quartz.TriggerKey
 */
package com.jiuqi.bi.core.jobs.core.listener;

import com.jiuqi.bi.core.jobs.JobsException;
import com.jiuqi.bi.core.jobs.manager.SchedulerStateManager;
import com.jiuqi.bi.core.jobs.simpleschedule.SimpleJobManager;
import com.jiuqi.bi.util.StringUtils;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.SchedulerException;
import org.quartz.SchedulerListener;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GlobalSchedulerListener
implements SchedulerListener {
    private static final Logger logger = LoggerFactory.getLogger(GlobalSchedulerListener.class);
    private String schedulerName;

    public GlobalSchedulerListener(String schedulerName) {
        this.schedulerName = schedulerName;
    }

    public void jobScheduled(Trigger trigger) {
        logger.debug("jobScheduled\u6267\u884c,Trigger:" + trigger.getKey().getName());
    }

    public void jobUnscheduled(TriggerKey triggerKey) {
        logger.debug("jobScheduled\u6267\u884c,TriggerName\uff1a\u3010" + triggerKey.getName() + "\u3011\uff0cTriggerGroup\uff1a\u3010" + triggerKey.getGroup() + "\u3011");
    }

    public void triggerFinalized(Trigger trigger) {
        logger.trace("triggerFinalized\u6267\u884c\uff0c\u8be5trigger\u4e0d\u4f1a\u88ab\u89e6\u53d1\u4e86,TriggerName\uff1a\u3010" + trigger.getKey().getName() + "\u3011\uff0cTriggerGroup\uff1a\u3010" + trigger.getKey().getGroup() + "\u3011");
        String jobId = trigger.getKey().getName();
        String group = trigger.getKey().getGroup();
        if (StringUtils.isNotEmpty((String)jobId) && group.indexOf("com.jiuqi.bi.jobs.simpleschedule") > -1) {
            SimpleJobManager simpleJobManager = new SimpleJobManager();
            try {
                simpleJobManager.setFinished(jobId);
            }
            catch (Exception e) {
                logger.error("\u66f4\u65b0\u7b80\u5355\u4efb\u52a1\u8c03\u5ea6\u5b8c\u6210\u5931\u8d25", e);
            }
        }
    }

    public void triggerPaused(TriggerKey triggerKey) {
        logger.trace("triggerPaused\u6267\u884c\uff0cTrigger\u88ab\u6682\u505c\uff0cTriggerName\uff1a\u3010" + triggerKey.getName() + "\u3011\uff0cTriggerGroup\uff1a\u3010" + triggerKey.getGroup() + "\u3011");
    }

    public void triggersPaused(String triggerGroup) {
        logger.trace("triggersPaused\u6267\u884c\uff0cTrigger\u7ec4\u88ab\u6682\u505c\uff0cTriggerGroup\uff1a\u3010" + triggerGroup + "\u3011");
    }

    public void triggerResumed(TriggerKey triggerKey) {
        logger.trace("triggerResumed\u6267\u884c\uff0cTrigger\u4ece\u6682\u505c\u6062\u590d\u6267\u884c\uff0cTriggerName\uff1a\u3010" + triggerKey.getName() + "\u3011\uff0cTriggerGroup\uff1a\u3010" + triggerKey.getGroup() + "\u3011");
    }

    public void triggersResumed(String triggerGroup) {
        logger.trace("triggersResumed\u6267\u884c\uff0cTrigger\u7ec4\u4ece\u6682\u505c\u6062\u590d\u6267\u884c\uff0cTriggerGroup\uff1a\u3010" + triggerGroup + "\u3011");
    }

    public void jobAdded(JobDetail jobDetail) {
        logger.trace("jobAdded\u6267\u884c\uff0c\u6dfb\u52a0\u4efb\u52a1\uff0cjobName\uff1a\u3010" + jobDetail.getKey().getName() + "\u3011");
    }

    public void jobDeleted(JobKey jobKey) {
        logger.trace("jobDeleted\u6267\u884c\uff0c\u5220\u9664\u4efb\u52a1\uff0cjobName\uff1a\u3010" + jobKey.getName() + "\u3011");
    }

    public void jobPaused(JobKey jobKey) {
        logger.trace("jobPaused\u6267\u884c\uff0c\u4efb\u52a1\u6682\u505c\uff0cjobGroup\uff1a\u3010" + jobKey.getGroup() + "\u3011jobName\uff1a\u3010" + jobKey.getName() + "\u3011");
    }

    public void jobsPaused(String jobGroup) {
        logger.trace("jobsPaused\u6267\u884c\uff0c\u4efb\u52a1\u7ec4\u6682\u505c\uff0cjobGroup\uff1a\u3010" + jobGroup + "\u3011");
    }

    public void jobResumed(JobKey jobKey) {
        logger.trace("jobResumed\u6267\u884c\uff0c\u4efb\u52a1\u4ece\u6682\u505c\u4e2d\u6062\u590d\uff0cjobGroup\uff1a\u3010" + jobKey.getGroup() + "\u3011jobName\uff1a\u3010" + jobKey.getName() + "\u3011");
    }

    public void jobsResumed(String jobGroup) {
        logger.debug("jobsResumed\u6267\u884c\uff0c\u4efb\u52a1\u7ec4\u4ece\u6682\u505c\u4e2d\u6062\u590d\uff0cjobGroup\uff1a\u3010" + jobGroup + "\u3011");
    }

    public void schedulerError(String msg, SchedulerException cause) {
        logger.warn("schedulerError\u6267\u884c\uff0cmsg\uff1a" + msg + "cause:" + cause.getMessage(), cause);
    }

    public void schedulerInStandbyMode() {
        logger.debug("Scheduler\u3010" + this.schedulerName + "\u3011\u5904\u4e8eStandBy\u6a21\u5f0f");
    }

    public void schedulerStarted() {
        logger.debug("schedulerStarted\u6267\u884c\uff0cScheduler\u5f00\u542f");
        try {
            SchedulerStateManager.getInstance().started(this.schedulerName);
        }
        catch (JobsException e) {
            logger.error("\u8bb0\u5f55\u8c03\u5ea6\u5668\u3010" + this.schedulerName + "\u3011\u72b6\u6001\u5931\u8d25", e);
        }
    }

    public void schedulerStarting() {
        logger.trace("schedulerStarting\u6267\u884c\uff0cScheduler\u5f00\u542fing");
        try {
            SchedulerStateManager.getInstance().starting(this.schedulerName);
        }
        catch (JobsException e) {
            logger.error("\u8bb0\u5f55\u8c03\u5ea6\u5668\u3010" + this.schedulerName + "\u3011\u72b6\u6001\u5931\u8d25", e);
        }
    }

    public void schedulerShutdown() {
        logger.trace("schedulerShutdown\u6267\u884c");
        try {
            SchedulerStateManager.getInstance().shutdown(this.schedulerName);
        }
        catch (JobsException e) {
            logger.error("\u8bb0\u5f55\u8c03\u5ea6\u5668\u3010" + this.schedulerName + "\u3011\u72b6\u6001\u5931\u8d25", e);
        }
    }

    public void schedulerShuttingdown() {
        logger.trace("schedulerShuttingdown\u6267\u884c");
        try {
            SchedulerStateManager.getInstance().shuttingdown(this.schedulerName);
        }
        catch (JobsException e) {
            logger.error("\u8bb0\u5f55\u8c03\u5ea6\u5668\u3010" + this.schedulerName + "\u3011\u72b6\u6001\u5931\u8d25", e);
        }
    }

    public void schedulingDataCleared() {
        logger.trace("Scheduler\u4e2d\u7684\u6570\u636e\u88ab\u6e05\u9664");
    }
}

