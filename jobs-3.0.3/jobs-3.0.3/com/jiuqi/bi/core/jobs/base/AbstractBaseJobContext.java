/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.monitor.IProgressMonitor
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.bi.core.jobs.base;

import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.bi.core.jobs.JobsException;
import com.jiuqi.bi.core.jobs.bean.JobExecResultBean;
import com.jiuqi.bi.core.jobs.core.MainJobProgressMonitor;
import com.jiuqi.bi.core.jobs.defaultlog.Logger;
import com.jiuqi.bi.core.jobs.manager.JobExecResultManager;
import com.jiuqi.bi.core.jobs.manager.JobOperationManager;
import com.jiuqi.bi.core.jobs.oss.AbstractJobResult;
import com.jiuqi.bi.monitor.IProgressMonitor;
import com.jiuqi.bi.util.StringUtils;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractBaseJobContext
implements JobContext {
    private int result;
    private String resultMesage;
    private MainJobProgressMonitor monitor;
    private String instanceId;
    private String username;
    private String userguid;
    private Logger defaultLogger;
    private List<JobExecResultBean> resultFileBeans;
    protected JobOperationManager jobOperationManager = new JobOperationManager();

    @Override
    public IProgressMonitor getMonitor() {
        return this.monitor;
    }

    @Override
    public Logger getDefaultLogger() {
        return this.defaultLogger;
    }

    public List<JobExecResultBean> getResultFileBeans() {
        return this.resultFileBeans;
    }

    @Override
    public String getInstanceId() {
        return this.instanceId;
    }

    public abstract String getFireInstanceId();

    public int getResult() {
        return this.result;
    }

    public String getResultMesage() {
        return this.resultMesage;
    }

    @Override
    public void setResult(int result, String resultMessage) {
        this.result = result;
        this.resultMesage = resultMessage;
    }

    @Override
    public void setInstanceDetail(String detail) throws JobsException {
        this.jobOperationManager.setInstanceDetail(this.instanceId, detail);
    }

    @Override
    public String getUserguid() {
        return StringUtils.isEmpty((String)this.userguid) ? "_system_" : this.userguid;
    }

    @Override
    public String getUsername() {
        return StringUtils.isEmpty((String)this.username) ? "_system_" : this.username;
    }

    @Override
    public List<JobExecResultBean> saveResultFile(List<AbstractJobResult> jobResults) throws JobsException {
        if (jobResults == null || jobResults.isEmpty()) {
            return new ArrayList<JobExecResultBean>();
        }
        for (AbstractJobResult jobResult : jobResults) {
            jobResult.setUserGuid(this.getUserguid());
        }
        JobExecResultManager resultFileManager = new JobExecResultManager();
        this.resultFileBeans = resultFileManager.saveResultFile(this.instanceId, jobResults);
        return this.resultFileBeans;
    }

    protected void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    protected void setUsername(String username) {
        this.username = username;
    }

    protected void setUserguid(String userguid) {
        this.userguid = userguid;
    }

    protected void setMonitor(MainJobProgressMonitor monitor) {
        this.monitor = monitor;
    }

    public void setDefaultLogger(Logger defaultLogger) {
        this.defaultLogger = defaultLogger;
    }
}

