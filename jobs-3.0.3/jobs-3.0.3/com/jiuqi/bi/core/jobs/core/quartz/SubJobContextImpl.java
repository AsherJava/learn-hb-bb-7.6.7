/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.quartz.JobExecutionContext
 */
package com.jiuqi.bi.core.jobs.core.quartz;

import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.bi.core.jobs.JobExecutionException;
import com.jiuqi.bi.core.jobs.JobExecutor;
import com.jiuqi.bi.core.jobs.core.SubJobProgressMonitor;
import com.jiuqi.bi.core.jobs.core.quartz.JobContextImpl;
import java.util.HashMap;
import java.util.Map;
import org.quartz.JobExecutionContext;

public class SubJobContextImpl
extends JobContextImpl {
    private String instanceId;
    private String parentInstanceId;
    private SubJobProgressMonitor monitor;
    private JobExecutionContext quartzContext;

    public SubJobContextImpl(String instanceId, String userguid, String username, String parentInstanceId, String rootInstanceId, JobExecutionContext quartzContext) {
        super(null, userguid, username, instanceId, quartzContext);
        this.instanceId = instanceId;
        this.parentInstanceId = parentInstanceId;
        this.quartzContext = quartzContext;
        this.monitor = new SubJobProgressMonitor(instanceId, parentInstanceId);
    }

    @Override
    public SubJobProgressMonitor getMonitor() {
        return this.monitor;
    }

    @Override
    public String getInstanceId() {
        return this.instanceId;
    }

    @Override
    public String executeSubJob(String subJobTitle, Class<? extends JobExecutor> subExecutor, Map<String, String> params) throws JobExecutionException {
        throw new JobExecutionException("\u5b50\u4efb\u52a1\u65e0\u6cd5\u542f\u52a8\u5b50\u4efb\u52a1");
    }

    @Override
    public boolean waitForSubJob(int waitSecond) throws JobExecutionException {
        return false;
    }

    @Override
    public String getParameterValue(String name) {
        Object value = this.quartzContext.getMergedJobDataMap().get((Object)name);
        return value == null ? null : value.toString();
    }

    @Override
    public Map<String, JobContext.SubJobStatus> getSubJobStatus() throws JobExecutionException {
        return new HashMap<String, JobContext.SubJobStatus>();
    }
}

