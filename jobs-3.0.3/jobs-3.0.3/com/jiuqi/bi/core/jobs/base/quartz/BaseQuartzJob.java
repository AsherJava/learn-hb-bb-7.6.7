/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.quartz.InterruptableJob
 *  org.quartz.JobDataMap
 *  org.quartz.JobExecutionContext
 *  org.quartz.JobExecutionException
 *  org.quartz.UnableToInterruptJobException
 */
package com.jiuqi.bi.core.jobs.base.quartz;

import com.jiuqi.bi.core.jobs.JobsException;
import com.jiuqi.bi.core.jobs.base.AbstractBaseJob;
import com.jiuqi.bi.core.jobs.base.AbstractBaseJobContext;
import com.jiuqi.bi.core.jobs.base.BaseJobFactory;
import com.jiuqi.bi.core.jobs.base.BaseJobFactoryManager;
import com.jiuqi.bi.core.jobs.base.quartz.BaseQuartzJobContextImpl;
import com.jiuqi.bi.core.jobs.core.MDCUtil;
import com.jiuqi.bi.core.jobs.manager.JobOperationManager;
import com.jiuqi.bi.core.jobs.realtime.core.Executor;
import java.sql.SQLException;
import java.util.Map;
import org.quartz.InterruptableJob;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.UnableToInterruptJobException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseQuartzJob
implements InterruptableJob {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private boolean isCanceled = false;
    private AbstractBaseJobContext baseJobContext = null;
    private AbstractBaseJob baseJob = null;
    private JobOperationManager jobOperationManager = new JobOperationManager();

    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDataMap jobData = context.getMergedJobDataMap();
        MDCUtil.generateMDC(jobData);
        String jobId = (String)jobData.get((Object)"__sys_jobid");
        String jobTitle = (String)jobData.get((Object)"__sys_jobtitle");
        String instanceId = (String)jobData.get((Object)"__sys_instanceid");
        String categoryId = (String)jobData.get((Object)"__sys_categoryid");
        String userGuid = (String)jobData.get((Object)"__sys_userguid");
        String userName = (String)jobData.get((Object)"__sys_username");
        Map paramMap = (Map)jobData.get((Object)"__sys_parammap");
        BaseJobFactory jobFactory = BaseJobFactoryManager.getInstance().getJobFactory(categoryId);
        try {
            this.baseJob = jobFactory.createJob(jobId);
        }
        catch (JobsException e) {
            this.logger.error(e.getMessage(), e);
            return;
        }
        this.baseJob.setUserGuid(userGuid);
        this.baseJob.setUserName(userName);
        this.baseJob.getParams().putAll(paramMap);
        this.baseJob.getParams().put("__sys_jobid", jobId);
        this.baseJob.getParams().put("__sys_instanceid", instanceId);
        this.baseJob.getParams().put("__sys_categoryid", categoryId);
        this.baseJob.getParams().put("__sys_rootinstanceid", instanceId);
        this.baseJob.getParams().put("__sys_jobtitle", jobTitle);
        try {
            if (this.jobOperationManager.instanceStateIsCanceled(instanceId, this.baseJob.getTitle())) {
                this.logger.info("\u4efb\u52a1[{}]\u5df2\u88ab\u8bbe\u7f6e\u4e3a\u5df2\u53d6\u6d88\uff0c\u53d6\u6d88\u6267\u884c", (Object)this.baseJob.getTitle());
                return;
            }
        }
        catch (JobsException e) {
            this.logger.error(e.getMessage(), e);
            return;
        }
        if (this.isCanceled) {
            this.logger.debug("\u5373\u65f6\u4efb\u52a1[{}]\u5df2\u53d6\u6d88", (Object)this.baseJob.getTitle());
            Executor.jobCanceled(instanceId);
            return;
        }
        this.baseJobContext = new BaseQuartzJobContextImpl(this.baseJob, context);
        this.baseJobContext.setDefaultLogger(jobFactory.createLogger(this.baseJobContext));
        Executor.doExec(this.baseJob, this.baseJobContext);
    }

    public void interrupt() throws UnableToInterruptJobException {
        this.isCanceled = true;
        try {
            if (this.baseJob != null && this.baseJobContext != null) {
                this.jobOperationManager.cancelJob(this.baseJobContext.getInstanceId());
                this.baseJobContext.getMonitor().cancel();
            }
        }
        catch (SQLException e) {
            this.logger.error(String.format("\u57fa\u7840\u4efb\u52a1[%s]\u53d6\u6d88\u5931\u8d25", this.baseJob.getTitle()), e);
        }
    }
}

