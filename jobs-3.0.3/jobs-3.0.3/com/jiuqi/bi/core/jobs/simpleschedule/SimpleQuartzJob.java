/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.Guid
 *  org.quartz.DisallowConcurrentExecution
 *  org.quartz.InterruptableJob
 *  org.quartz.JobDataMap
 *  org.quartz.JobExecutionContext
 *  org.quartz.JobExecutionException
 */
package com.jiuqi.bi.core.jobs.simpleschedule;

import com.jiuqi.bi.core.jobs.JobsException;
import com.jiuqi.bi.core.jobs.core.MDCUtil;
import com.jiuqi.bi.core.jobs.manager.JobOperationManager;
import com.jiuqi.bi.core.jobs.monitor.JobType;
import com.jiuqi.bi.core.jobs.simpleschedule.AbstractSimpleScheduleJob;
import com.jiuqi.bi.core.jobs.simpleschedule.SimpleScheduleJobContext;
import com.jiuqi.bi.util.Guid;
import java.sql.SQLException;
import java.util.ArrayList;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.InterruptableJob;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@DisallowConcurrentExecution
public class SimpleQuartzJob
implements InterruptableJob {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private SimpleScheduleJobContext jobContext;
    private JobOperationManager jobOperationManager = new JobOperationManager();
    private boolean isCanceled = false;

    public void interrupt() {
        this.isCanceled = true;
        try {
            this.jobOperationManager.cancelJob(this.jobContext.getInstanceId());
            this.jobContext.getMonitor().cancel();
        }
        catch (SQLException e) {
            this.logger.error(String.format("\u8c03\u5ea6\u4efb\u52a1[%s]\u53d6\u6d88\u5931\u8d25", this.jobContext.getJob().getTitle()), e);
        }
    }

    public void execute(JobExecutionContext context) throws JobExecutionException {
        String instanceId = Guid.newGuid();
        JobDataMap jobData = context.getMergedJobDataMap();
        MDCUtil.generateMDC(jobData);
        String jobId = (String)jobData.get((Object)"_SCHEDULE_SIMPLE_JOB_ID");
        AbstractSimpleScheduleJob simpleScheduleJob = (AbstractSimpleScheduleJob)jobData.get((Object)"_SCHEDULE_JOB_ENTITY");
        simpleScheduleJob.getParams().put("_SCHEDULE_INSTANCE_ID", instanceId);
        String quartzInstance = context.getFireInstanceId();
        try {
            String userGuid = simpleScheduleJob.getUserGuid();
            String userName = simpleScheduleJob.getUserName();
            String jobTitle = simpleScheduleJob.getTitle();
            this.jobOperationManager.mainJobAdded(instanceId, jobId, userGuid, userName, jobTitle, JobType.SCHEDULED_JOB, simpleScheduleJob.getJobGroupId(), simpleScheduleJob.getJobGroupTitle(), true, new ArrayList<String>(), null, null);
        }
        catch (Exception e) {
            throw new JobExecutionException((Throwable)e);
        }
        this.jobFired(instanceId, quartzInstance);
        try {
            if (this.jobOperationManager.instanceStateIsCanceled(instanceId, simpleScheduleJob.getTitle())) {
                this.logger.info("\u4efb\u52a1[{}]\u5df2\u88ab\u8bbe\u7f6e\u4e3a\u5df2\u53d6\u6d88\uff0c\u53d6\u6d88\u6267\u884c", (Object)simpleScheduleJob.getTitle());
                return;
            }
        }
        catch (JobsException e) {
            this.logger.error(e.getMessage(), e);
            return;
        }
        this.jobContext = new SimpleScheduleJobContext(simpleScheduleJob, context);
        try {
            if (this.isCanceled) {
                this.logger.debug("\u8c03\u5ea6\u4efb\u52a1[{}]\u5df2\u53d6\u6d88", (Object)simpleScheduleJob.getTitle());
                this.jobCanceled(instanceId);
                return;
            }
            simpleScheduleJob.execute(this.jobContext);
        }
        catch (Throwable e) {
            this.logger.error(String.format("\u540e\u53f0\u4efb\u52a1[%s]\u6267\u884c\u5f02\u5e38", simpleScheduleJob.getTitle()), e);
            this.jobExcepted(this.jobContext.getInstanceId(), "\u6267\u884c\u5f02\u5e38");
            return;
        }
        int jobResult = this.jobContext.getResult();
        String jobResultMessage = this.jobContext.getResultMesage();
        if ((jobResult == 0 || jobResult == 2) && this.jobContext.getMonitor().isCanceled()) {
            this.jobCanceled(this.jobContext.getInstanceId());
        } else {
            if (jobResult == 0) {
                jobResult = 100;
            }
            if (jobResultMessage == null) {
                jobResultMessage = "\u6267\u884c\u5b8c\u6bd5";
            }
            this.jobFinished(this.jobContext.getInstanceId(), jobResult, jobResultMessage);
        }
    }

    private void jobFired(String instanceId, String quartzInstance) {
        try {
            JobOperationManager operation = new JobOperationManager();
            operation.mainJobFired(instanceId, quartzInstance);
        }
        catch (Exception e) {
            this.logger.error("\u542f\u52a8\u8ba1\u5212\u4efb\u52a1\u51fa\u9519", e);
        }
    }

    public void jobExcepted(String instanceId, String message) {
        try {
            this.jobOperationManager.jobExcepted(instanceId, message);
        }
        catch (Exception e) {
            this.logger.error(String.format("\u5373\u65f6\u4efb\u52a1[%s]\u72b6\u6001\u8bbe\u7f6e\u4e3a\u6267\u884c\u5f02\u5e38\u5931\u8d25", instanceId), e);
        }
    }

    private void jobCanceled(String instanceId) throws JobExecutionException {
        try {
            this.jobOperationManager.jobCanceled(instanceId);
        }
        catch (SQLException e) {
            throw new JobExecutionException("\u8c03\u5ea6\u4efb\u52a1" + instanceId + "\u53d6\u6d88\u5931\u8d25", (Throwable)e);
        }
    }

    public void jobFinished(String instanceId, int jobResult, String jobResultMessage) {
        try {
            this.jobOperationManager.jobFinished(instanceId, jobResult, jobResultMessage);
        }
        catch (Exception e) {
            this.logger.error(String.format("\u5373\u65f6\u4efb\u52a1[%s]\u72b6\u6001\u8bbe\u7f6e\u4e3a\u6267\u884c\u5b8c\u6bd5\u5931\u8d25", instanceId), e);
        }
    }
}

