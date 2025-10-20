/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.quartz.JobExecutionContext
 */
package com.jiuqi.bi.core.jobs.base.quartz;

import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.bi.core.jobs.JobExecutionException;
import com.jiuqi.bi.core.jobs.JobExecutor;
import com.jiuqi.bi.core.jobs.base.AbstractBaseJob;
import com.jiuqi.bi.core.jobs.base.AbstractBaseJobContext;
import com.jiuqi.bi.core.jobs.core.MainJobProgressMonitor;
import com.jiuqi.bi.core.jobs.model.JobModel;
import com.jiuqi.bi.core.jobs.model.JobParameter;
import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import org.quartz.JobExecutionContext;

public class BaseQuartzJobContextImpl
extends AbstractBaseJobContext {
    private static final String NOT_SUPPORTED_OPERATION = "\u4e0d\u652f\u6301\u7684\u64cd\u4f5c\u6307\u4ee4";
    private AbstractBaseJob baseJob;
    private final JobExecutionContext quartzContext;

    public BaseQuartzJobContextImpl(AbstractBaseJob baseJob, JobExecutionContext quartzContext) {
        this.baseJob = baseJob;
        this.setUsername(baseJob.getUserName());
        this.setUserguid(baseJob.getUserGuid());
        this.setInstanceId(baseJob.getParams().get("__sys_instanceid"));
        this.setMonitor(new MainJobProgressMonitor(this.getInstanceId()));
        this.quartzContext = quartzContext;
    }

    @Override
    public String getParameterValue(String name) {
        Object value;
        if (this.quartzContext != null && (value = this.quartzContext.getMergedJobDataMap().get((Object)name)) != null) {
            return value.toString();
        }
        Map<String, String> params = this.baseJob.getParams();
        if (params != null) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                if (!entry.getKey().equals(name)) continue;
                return entry.getValue();
            }
        }
        return null;
    }

    @Override
    public void updateProgress(int progress) throws JobExecutionException {
        try {
            this.jobOperationManager.updateJobProgress(this.getInstanceId(), progress);
        }
        catch (SQLException throwable) {
            throw new JobExecutionException(throwable);
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
    public String getAuthenticatedUsername() {
        return this.baseJob.getUserName();
    }

    @Override
    public String getFireInstanceId() {
        return this.quartzContext.getFireInstanceId();
    }

    @Override
    public JobModel getJob() {
        JobModel jobModel = new JobModel();
        jobModel.setGuid(this.getParameterValue("__sys_jobid"));
        jobModel.setCategory(this.getParameterValue("__sys_categoryid"));
        List<JobParameter> parameters = jobModel.getParameters();
        this.baseJob.getParams().forEach((key, value) -> {
            if (key.startsWith("__sys")) {
                return;
            }
            JobParameter parameter = new JobParameter();
            parameter.setName((String)key);
            parameter.setDefaultValue((String)value);
            parameter.setTitle((String)key);
            parameters.add(parameter);
        });
        return jobModel;
    }

    @Override
    public AbstractRealTimeJob getRealTimeJob() {
        return null;
    }

    @Override
    public String executeSubJob(String subJobName, Class<? extends JobExecutor> subExecutor, Map<String, String> params) throws JobExecutionException {
        throw new UnsupportedOperationException(NOT_SUPPORTED_OPERATION);
    }

    @Override
    public String executeRealTimeSubJob(AbstractRealTimeJob subJob) throws JobExecutionException {
        throw new UnsupportedOperationException(NOT_SUPPORTED_OPERATION);
    }

    @Override
    public boolean waitForSubJob(int waitSecond) throws JobExecutionException {
        throw new UnsupportedOperationException(NOT_SUPPORTED_OPERATION);
    }

    @Override
    public boolean waitForSubJob() throws JobExecutionException {
        throw new UnsupportedOperationException(NOT_SUPPORTED_OPERATION);
    }

    @Override
    public Map<String, JobContext.SubJobStatus> getSubJobStatus() throws JobExecutionException {
        throw new UnsupportedOperationException(NOT_SUPPORTED_OPERATION);
    }
}

