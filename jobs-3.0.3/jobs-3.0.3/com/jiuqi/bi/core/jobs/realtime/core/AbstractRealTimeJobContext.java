/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.core.jobs.realtime.core;

import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.bi.core.jobs.JobExecutionException;
import com.jiuqi.bi.core.jobs.JobExecutor;
import com.jiuqi.bi.core.jobs.JobsException;
import com.jiuqi.bi.core.jobs.base.AbstractBaseJobContext;
import com.jiuqi.bi.core.jobs.bean.JobInstanceBean;
import com.jiuqi.bi.core.jobs.core.MainJobProgressMonitor;
import com.jiuqi.bi.core.jobs.defaultlog.AsyncLogger;
import com.jiuqi.bi.core.jobs.model.JobModel;
import com.jiuqi.bi.core.jobs.monitor.JobMonitorManager;
import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.bi.core.jobs.realtime.core.RealTimeJobRunner;
import com.jiuqi.bi.core.jobs.realtime.core.RealTimeJobRunnerFactory;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractRealTimeJobContext
extends AbstractBaseJobContext {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private AbstractRealTimeJob realTimeJob;
    private List<String> subJobInstanceIdList = new ArrayList<String>();

    protected AbstractRealTimeJobContext(AbstractRealTimeJob realTimeJob) {
        this.realTimeJob = realTimeJob;
        this.setUserguid(realTimeJob.getUserGuid());
        this.setUsername(realTimeJob.getUserName());
        this.setInstanceId(this.getParameterValue("_SYS_INSTANCE_ID"));
        this.setMonitor(new MainJobProgressMonitor(this.getInstanceId()));
        this.setDefaultLogger(new AsyncLogger(this));
    }

    @Override
    public JobModel getJob() {
        return null;
    }

    @Override
    public AbstractRealTimeJob getRealTimeJob() {
        return this.realTimeJob;
    }

    @Override
    public String getParameterValue(String name) {
        Map<String, String> params = this.realTimeJob.getParams();
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
        throw new JobExecutionException("\u521b\u5efa\u5373\u65f6\u4efb\u52a1\u5b50\u4efb\u52a1\u8bf7\u8c03\u7528\u65b9\u6cd5\uff1acom.jiuqi.bi.core.jobs.realtime.quartz.RealTimeJobContextImpl.executeRealTimeSubJob");
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public String executeRealTimeSubJob(AbstractRealTimeJob subJob) throws JobExecutionException {
        String instanceId;
        RealTimeJobRunner runner = RealTimeJobRunnerFactory.getInstance().getDefaultRunner();
        subJob.setUserGuid(this.getUserguid());
        subJob.setUserName(this.getUsername());
        Map<String, String> parentParams = this.getRealTimeJob().getParams();
        Map<String, String> childParams = subJob.getParams();
        HashMap<String, String> combineParams = new HashMap<String, String>();
        combineParams.putAll(parentParams);
        combineParams.putAll(childParams);
        subJob.setParams(combineParams);
        if (this.getMonitor().isCanceled()) {
            return null;
        }
        try {
            instanceId = runner.commit(this.getInstanceId(), this.getInstanceId(), subJob);
        }
        catch (JobsException e) {
            throw new JobExecutionException(e);
        }
        AbstractRealTimeJobContext abstractRealTimeJobContext = this;
        synchronized (abstractRealTimeJobContext) {
            this.addSubJobInstanceId(instanceId);
        }
        return instanceId;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean waitForSubJob(int waitSecond) throws JobExecutionException {
        if (waitSecond < 1) {
            throw new JobExecutionException("\u7b49\u5f85\u7684\u79d2\u6570\u4e0d\u80fd\u5c0f\u4e8e1");
        }
        int count = 0;
        AbstractRealTimeJobContext abstractRealTimeJobContext = this;
        synchronized (abstractRealTimeJobContext) {
            while (!this.getMonitor().isCanceled() && !this.subJobInstanceIdList.isEmpty()) {
                try {
                    this.wait(1000L);
                }
                catch (InterruptedException e) {
                    this.logger.error(e.getMessage(), e);
                    Thread.currentThread().interrupt();
                    return false;
                }
                if (count++ <= waitSecond) continue;
                return true;
            }
            return false;
        }
    }

    @Override
    public boolean waitForSubJob() throws JobExecutionException {
        return this.waitForSubJob(Integer.MAX_VALUE);
    }

    @Override
    public Map<String, JobContext.SubJobStatus> getSubJobStatus() throws JobExecutionException {
        HashMap<String, JobContext.SubJobStatus> map = new HashMap<String, JobContext.SubJobStatus>();
        try {
            List<JobInstanceBean> list = JobMonitorManager.getSubJobInstance(this.getInstanceId());
            for (JobInstanceBean instance : list) {
                JobContext.SubJobStatus status = new JobContext.SubJobStatus();
                status.setSubJobInstanceId(instance.getInstanceId());
                status.setSubJobName(instance.getInstanceName());
                status.setProgress(instance.getProgress());
                status.setPrompt(instance.getPrompt());
                status.setResult(instance.getResult());
                status.setResultMessage(instance.getResultMessage());
                map.put(status.getSubJobName(), status);
            }
        }
        catch (JobsException e) {
            throw new JobExecutionException(e.getMessage(), e);
        }
        return map;
    }

    @Override
    public void updateProgress(int progress) throws JobExecutionException {
        try {
            this.jobOperationManager.updateJobProgress(this.getInstanceId(), progress);
        }
        catch (SQLException throwables) {
            throw new JobExecutionException(throwables);
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
        return this.getUsername();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void removeSubJobInstanceId(String subInstanceId) {
        List<String> list = this.subJobInstanceIdList;
        synchronized (list) {
            this.subJobInstanceIdList.remove(subInstanceId);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void addSubJobInstanceId(String subInstanceId) {
        List<String> list = this.subJobInstanceIdList;
        synchronized (list) {
            this.subJobInstanceIdList.add(subInstanceId);
        }
    }

    protected List<String> getSubJobInstanceIdList() {
        ArrayList<String> instanceIds = new ArrayList<String>();
        for (String s : this.subJobInstanceIdList) {
            instanceIds.add(s);
        }
        return instanceIds;
    }
}

