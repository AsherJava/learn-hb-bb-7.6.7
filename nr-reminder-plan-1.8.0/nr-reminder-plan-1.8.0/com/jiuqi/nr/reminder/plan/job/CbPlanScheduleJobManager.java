/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.JobsException
 *  com.jiuqi.bi.core.jobs.core.SchedulerManager
 *  com.jiuqi.np.asynctask.util.SimpleParamConverter$SerializationUtils
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  org.quartz.JobBuilder
 *  org.quartz.JobDataMap
 *  org.quartz.JobDetail
 *  org.quartz.JobKey
 *  org.quartz.Scheduler
 *  org.quartz.SchedulerException
 *  org.quartz.Trigger
 *  org.quartz.spi.OperableTrigger
 */
package com.jiuqi.nr.reminder.plan.job;

import com.jiuqi.bi.core.jobs.JobsException;
import com.jiuqi.bi.core.jobs.core.SchedulerManager;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.reminder.plan.CbPlanDTO;
import com.jiuqi.nr.reminder.plan.common.ErrorEnumImpl;
import com.jiuqi.nr.reminder.plan.job.CbPlanJob;
import com.jiuqi.nr.reminder.plan.job.CbTriggerBuilder;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.spi.OperableTrigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CbPlanScheduleJobManager {
    @Autowired
    private CbTriggerBuilder cbTriggerBuilder;
    private final Logger logger = LoggerFactory.getLogger(CbPlanScheduleJobManager.class);

    public void schedule(CbPlanDTO cbPlanDTO) throws JQException {
        try {
            String jobId = cbPlanDTO.getPlanId();
            Scheduler scheduler = SchedulerManager.getInstance().getScheduler();
            JobDetail jobDetail = JobBuilder.newJob(CbPlanJob.class).withIdentity(jobId, "com.jiuqi.nr.cb.job").build();
            List<Trigger> triggers = this.cbTriggerBuilder.build(cbPlanDTO);
            for (Trigger trigger : triggers) {
                OperableTrigger trig = (OperableTrigger)trigger;
                Date date = trig.computeFirstFireTime(null);
                if (date != null) continue;
                throw new JQException((ErrorEnum)new ErrorEnumImpl("\u6307\u5b9a\u65f6\u95f4\u4e0d\u5728\u542f\u7528\u65f6\u95f4\u8303\u56f4\u5185"));
            }
            JobDataMap jobDataMap = jobDetail.getJobDataMap();
            NpContext context = NpContextHolder.getContext();
            String contextStr = SimpleParamConverter.SerializationUtils.serializeToString((Object)context);
            jobDataMap.put("context", contextStr);
            scheduler.scheduleJob(jobDetail, new HashSet<Trigger>(triggers), true);
        }
        catch (JobsException | SchedulerException e) {
            this.logger.error("\u8bbe\u7f6e\u50ac\u62a5\u5931\u8d25\uff0c\u8bf7\u8054\u7cfb\u7ba1\u7406\u5458", e);
            throw new JQException((ErrorEnum)new ErrorEnumImpl("\u8bbe\u7f6e\u50ac\u62a5\u5931\u8d25\uff0c\u8bf7\u8054\u7cfb\u7ba1\u7406\u5458"));
        }
    }

    public void deleteJob(String jobId) throws JQException {
        JobKey jobKey = new JobKey(jobId, "com.jiuqi.nr.cb.job");
        try {
            Scheduler scheduler = SchedulerManager.getInstance().getScheduler();
            if (scheduler.checkExists(jobKey)) {
                scheduler.deleteJob(jobKey);
            }
        }
        catch (JobsException | SchedulerException e) {
            this.logger.error("\u8bbe\u7f6e\u50ac\u62a5\u5931\u8d25\uff0c\u8bf7\u8054\u7cfb\u7ba1\u7406\u5458", e);
            throw new JQException((ErrorEnum)new ErrorEnumImpl("\u8bbe\u7f6e\u50ac\u62a5\u5931\u8d25\uff0c\u8bf7\u8054\u7cfb\u7ba1\u7406\u5458"));
        }
    }
}

