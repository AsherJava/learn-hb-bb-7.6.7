/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.quartz.JobExecutionContext
 */
package com.jiuqi.bi.core.jobs.simpleschedule;

import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.bi.core.jobs.JobExecutionException;
import com.jiuqi.bi.core.jobs.JobExecutor;
import com.jiuqi.bi.core.jobs.base.AbstractBaseJobContext;
import com.jiuqi.bi.core.jobs.core.MainJobProgressMonitor;
import com.jiuqi.bi.core.jobs.defaultlog.AsyncLogger;
import com.jiuqi.bi.core.jobs.model.JobModel;
import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.bi.core.jobs.simpleschedule.AbstractSimpleScheduleJob;
import com.jiuqi.bi.core.jobs.simpleschedule.SimpleJobManager;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Map;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleScheduleJobContext
extends AbstractBaseJobContext {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private AbstractSimpleScheduleJob simpleScheduleJob;
    private JobExecutionContext context;

    public SimpleScheduleJobContext(AbstractSimpleScheduleJob simpleScheduleJob, JobExecutionContext context) {
        this.simpleScheduleJob = simpleScheduleJob;
        this.context = context;
        this.setUserguid(simpleScheduleJob.getUserGuid());
        this.setUsername(simpleScheduleJob.getUserName());
        this.setInstanceId(this.getParameterValue("_SCHEDULE_INSTANCE_ID"));
        this.setMonitor(new MainJobProgressMonitor(this.getInstanceId()));
        this.setDefaultLogger(new AsyncLogger(this));
    }

    @Override
    public JobModel getJob() {
        String jobId = this.simpleScheduleJob.getJobId();
        SimpleJobManager manager = new SimpleJobManager();
        try {
            return manager.getSimpleJob(jobId);
        }
        catch (Exception e) {
            this.logger.error("\u67e5\u8be2\u7b80\u5355\u4efb\u52a1\u6a21\u578b\u5931\u8d25\uff1a" + e.getMessage(), e);
            return null;
        }
    }

    @Override
    public AbstractRealTimeJob getRealTimeJob() {
        return null;
    }

    @Override
    public String getParameterValue(String name) {
        Map<String, String> params = this.simpleScheduleJob.getParams();
        if (params != null) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                if (!entry.getKey().equals(name)) continue;
                return entry.getValue();
            }
        }
        return null;
    }

    @Override
    public String executeSubJob(String subJobName, Class<? extends JobExecutor> subExecutor, Map<String, String> params) throws JobExecutionException {
        throw new JobExecutionException("\u4e0d\u652f\u6301\u5b50\u4efb\u52a1\u6267\u884c");
    }

    @Override
    public String executeRealTimeSubJob(AbstractRealTimeJob subJob) throws JobExecutionException {
        return null;
    }

    @Override
    public boolean waitForSubJob(int waitSecond) throws JobExecutionException {
        return false;
    }

    @Override
    public boolean waitForSubJob() throws JobExecutionException {
        return false;
    }

    @Override
    public Map<String, JobContext.SubJobStatus> getSubJobStatus() throws JobExecutionException {
        return Collections.emptyMap();
    }

    @Override
    public void updateProgress(int progress) throws JobExecutionException {
        try {
            this.jobOperationManager.updateJobProgress(this.getInstanceId(), progress);
        }
        catch (SQLException e) {
            throw new JobExecutionException(e);
        }
    }

    @Override
    public void updateProgress(int progress, String prompt) throws JobExecutionException {
        try {
            this.jobOperationManager.updateJobProgress(this.getInstanceId(), progress, prompt);
        }
        catch (SQLException throwables) {
            throw new JobExecutionException(throwables);
        }
    }

    @Override
    public String getFireInstanceId() {
        return this.context.getFireInstanceId();
    }

    @Override
    public String getAuthenticatedUsername() {
        return this.getUsername();
    }
}

