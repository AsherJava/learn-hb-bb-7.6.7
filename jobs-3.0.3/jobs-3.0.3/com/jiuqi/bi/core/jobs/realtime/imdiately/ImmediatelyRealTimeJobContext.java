/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.nodekeeper.GlobalConnectionProviderManager
 *  com.jiuqi.bi.monitor.IProgressMonitor
 */
package com.jiuqi.bi.core.jobs.realtime.imdiately;

import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.bi.core.jobs.JobExecutionException;
import com.jiuqi.bi.core.jobs.JobsException;
import com.jiuqi.bi.core.jobs.bean.JobExecResultBean;
import com.jiuqi.bi.core.jobs.dao.JobInstanceDetailDAO;
import com.jiuqi.bi.core.jobs.defaultlog.Logger;
import com.jiuqi.bi.core.jobs.oss.AbstractJobResult;
import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.bi.core.jobs.realtime.core.AbstractRealTimeJobContext;
import com.jiuqi.bi.core.jobs.realtime.imdiately.ImmediatelyJobInfo;
import com.jiuqi.bi.core.jobs.realtime.imdiately.ImmediatelyJobManager;
import com.jiuqi.bi.core.jobs.realtime.imdiately.ImmediatelyLogger;
import com.jiuqi.bi.core.jobs.realtime.imdiately.ImmediatelyProgressMonitor;
import com.jiuqi.bi.core.nodekeeper.GlobalConnectionProviderManager;
import com.jiuqi.bi.monitor.IProgressMonitor;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class ImmediatelyRealTimeJobContext
extends AbstractRealTimeJobContext {
    private final ImmediatelyJobInfo jobInfo;

    protected ImmediatelyRealTimeJobContext(AbstractRealTimeJob realTimeJob, ImmediatelyJobInfo jobInfo) {
        super(realTimeJob);
        this.jobInfo = jobInfo;
    }

    @Override
    public String getFireInstanceId() {
        return null;
    }

    @Override
    public IProgressMonitor getMonitor() {
        return new ImmediatelyProgressMonitor(this.jobInfo);
    }

    @Override
    public Logger getDefaultLogger() {
        return new ImmediatelyLogger(this);
    }

    @Override
    public List<JobExecResultBean> getResultFileBeans() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setInstanceDetail(String detail) throws JobsException {
        try (Connection conn = GlobalConnectionProviderManager.getConnection();){
            JobInstanceDetailDAO.updateImmediatelyInstanceDetail(conn, this.jobInfo.getInstanceId(), detail);
        }
        catch (SQLException e) {
            throw new JobsException("\u6dfb\u52a0\u4efb\u52a1\u8be6\u60c5\u5931\u8d25", e);
        }
    }

    @Override
    public List<JobExecResultBean> saveResultFile(List<AbstractJobResult> jobResults) throws JobsException {
        throw new UnsupportedOperationException();
    }

    @Override
    public String executeRealTimeSubJob(AbstractRealTimeJob subJob) throws JobExecutionException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean waitForSubJob(int waitSecond) throws JobExecutionException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Map<String, JobContext.SubJobStatus> getSubJobStatus() throws JobExecutionException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateProgress(int progress) throws JobExecutionException {
        this.jobInfo.setProgress(progress);
        ImmediatelyJobManager.getInstance().getStorageMiddleware().patch(this.jobInfo);
    }

    @Override
    public void updateProgress(int progress, String prompt) throws JobExecutionException {
        this.jobInfo.setProgress(progress);
        this.jobInfo.setPrompt(prompt);
        ImmediatelyJobManager.getInstance().getStorageMiddleware().patch(this.jobInfo);
    }

    @Override
    public void removeSubJobInstanceId(String subInstanceId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void addSubJobInstanceId(String subInstanceId) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected List<String> getSubJobInstanceIdList() {
        throw new UnsupportedOperationException();
    }
}

