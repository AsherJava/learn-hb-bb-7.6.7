/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 *  org.quartz.CronScheduleBuilder
 *  org.quartz.JobBuilder
 *  org.quartz.JobDataMap
 *  org.quartz.JobDetail
 *  org.quartz.ScheduleBuilder
 *  org.quartz.Scheduler
 *  org.quartz.SchedulerException
 *  org.quartz.Trigger
 *  org.quartz.TriggerBuilder
 */
package com.jiuqi.nr.reminder.scheduler;

import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.reminder.internal.CreateReminderCommand;
import com.jiuqi.nr.reminder.scheduler.ReminderJob;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.ScheduleBuilder;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class SchedulerImpl {
    private static final Logger log = LoggerFactory.getLogger(SchedulerImpl.class);
    @Autowired
    private Scheduler scheduler;

    public boolean asyncBaseJob(ReminderJob autoReminderjob, CreateReminderCommand command) throws JQException {
        boolean succeed = false;
        Class<?> aClass = autoReminderjob.getClass();
        JobDataMap jobDataMap = new JobDataMap(autoReminderjob.getDataMap());
        String jobId = autoReminderjob.getId();
        String jobGroupId = autoReminderjob.getGroupId();
        String cronExpression = autoReminderjob.getCronExpression();
        Object[] argsCheck = new Object[]{jobId, jobGroupId, cronExpression};
        Assert.noNullElements(argsCheck, "\u53c2\u6570\u4e0d\u5b8c\u6574");
        SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ParsePosition pos1 = new ParsePosition(0);
        Date startDate = formatter1.parse(autoReminderjob.getStarTime(), pos1);
        try {
            Trigger trigger;
            JobDetail jobDetail = JobBuilder.newJob(aClass).withIdentity(jobId, jobGroupId).setJobData(jobDataMap).build();
            if ("regularTime".equals(command.getShowSendTime()) && command.getFrequencyMode() != 0 || command.getFrequencyMode() == 0 && autoReminderjob.getEndTime() == null) {
                if (command.getValidEndTime() != null && !command.getValidEndTime().equals("")) {
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date endDate = formatter.parse(command.getValidEndTime());
                    trigger = TriggerBuilder.newTrigger().withIdentity(jobId, jobGroupId).startAt(startDate).endAt(endDate).withSchedule((ScheduleBuilder)CronScheduleBuilder.cronSchedule((String)cronExpression)).build();
                } else {
                    trigger = TriggerBuilder.newTrigger().withIdentity(jobId, jobGroupId).startAt(startDate).withSchedule((ScheduleBuilder)CronScheduleBuilder.cronSchedule((String)cronExpression)).build();
                }
            } else {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                ParsePosition pos = new ParsePosition(0);
                Date endDate = formatter.parse(autoReminderjob.getEndTime(), pos);
                trigger = TriggerBuilder.newTrigger().withIdentity(jobId, jobGroupId).startAt(startDate).endAt(endDate).withSchedule((ScheduleBuilder)CronScheduleBuilder.cronSchedule((String)cronExpression)).build();
            }
            this.scheduler.scheduleJob(jobDetail, trigger);
            if (!this.scheduler.isShutdown()) {
                this.scheduler.start();
            }
            succeed = true;
        }
        catch (ParseException | SchedulerException e) {
            succeed = false;
            log.error(e.getMessage(), e);
        }
        return succeed;
    }
}

