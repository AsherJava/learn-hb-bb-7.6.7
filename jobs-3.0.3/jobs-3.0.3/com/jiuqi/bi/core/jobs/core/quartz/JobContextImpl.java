/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.type.GUID
 *  org.quartz.JobBuilder
 *  org.quartz.JobDetail
 *  org.quartz.JobExecutionContext
 *  org.quartz.ScheduleBuilder
 *  org.quartz.Scheduler
 *  org.quartz.SchedulerException
 *  org.quartz.SimpleScheduleBuilder
 *  org.quartz.SimpleTrigger
 *  org.quartz.Trigger
 *  org.quartz.TriggerBuilder
 */
package com.jiuqi.bi.core.jobs.core.quartz;

import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.bi.core.jobs.JobExecutionException;
import com.jiuqi.bi.core.jobs.JobExecutor;
import com.jiuqi.bi.core.jobs.JobsException;
import com.jiuqi.bi.core.jobs.base.AbstractBaseJobContext;
import com.jiuqi.bi.core.jobs.bean.JobInstanceBean;
import com.jiuqi.bi.core.jobs.core.MainJobProgressMonitor;
import com.jiuqi.bi.core.jobs.core.SchedulerManager;
import com.jiuqi.bi.core.jobs.core.quartz.KeyBuilder;
import com.jiuqi.bi.core.jobs.core.quartz.SubQuartzJob;
import com.jiuqi.bi.core.jobs.defaultlog.Logger;
import com.jiuqi.bi.core.jobs.model.JobModel;
import com.jiuqi.bi.core.jobs.model.JobParameter;
import com.jiuqi.bi.core.jobs.monitor.JobMonitorManager;
import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.bi.util.type.GUID;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.ScheduleBuilder;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;

public class JobContextImpl
extends AbstractBaseJobContext {
    private JobModel job;
    private JobExecutionContext quartzContext;
    private int subjobConcurrentNum;
    private String authenticatedUsername;
    private List<String> subJobInstanceIdList = new ArrayList<String>();

    @Override
    public void setDefaultLogger(Logger defaultLogger) {
        super.setDefaultLogger(defaultLogger);
    }

    public JobContextImpl(JobModel job, String userguid, String username, String instanceId, JobExecutionContext quartzContext) {
        this.job = job;
        this.quartzContext = quartzContext;
        this.setUserguid(userguid);
        this.setUsername(username);
        this.setInstanceId(instanceId);
        this.setMonitor(new MainJobProgressMonitor(instanceId));
    }

    @Override
    public String getAuthenticatedUsername() {
        return this.authenticatedUsername;
    }

    public void setAuthenticatedUsername(String authenticatedUsername) {
        this.authenticatedUsername = authenticatedUsername;
    }

    public void jobCancel() {
        this.getMonitor().cancel();
    }

    @Override
    public String getFireInstanceId() {
        return this.quartzContext.getFireInstanceId();
    }

    @Override
    public JobModel getJob() {
        return this.job;
    }

    @Override
    public AbstractRealTimeJob getRealTimeJob() {
        return null;
    }

    @Override
    public String getParameterValue(String name) {
        Object value = this.quartzContext.getMergedJobDataMap().get((Object)name);
        if (value != null) {
            return value.toString();
        }
        List<JobParameter> params = this.job.getParameters();
        JobParameter param = null;
        for (JobParameter p : params) {
            if (!p.getName().equals(name)) continue;
            param = p;
            break;
        }
        if (param == null) {
            return null;
        }
        return param.getDefaultValue();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public String executeSubJob(String subJobName, Class<? extends JobExecutor> subExecutor, Map<String, String> params) throws JobExecutionException {
        try {
            Scheduler scheduler = SchedulerManager.getInstance().getSubScheduler();
            String subGuid = GUID.newGUID();
            JobBuilder jb = JobBuilder.newJob(SubQuartzJob.class).withIdentity(subGuid, "<sub>" + this.getJob().getCategory());
            jb.usingJobData("__sys_instanceid", subGuid).usingJobData("__sys_parentinstanceid", this.getInstanceId()).usingJobData("__sys_rootinstanceid", this.getInstanceId()).usingJobData("__sys_userguid", this.getUserguid()).usingJobData("__sys_username", this.getUsername()).usingJobData("__sys_subexe_class", subExecutor.getName()).usingJobData("__sys_jobtitle", subJobName).usingJobData("__sys_mdc_traceid", (String)this.quartzContext.get((Object)"__sys_mdc_traceid"));
            if (params != null) {
                for (String key : params.keySet()) {
                    jb.usingJobData(key, params.get(key));
                }
            }
            JobDetail jd = jb.build();
            SimpleTrigger trigger = (SimpleTrigger)TriggerBuilder.newTrigger().forJob(jd).withIdentity(subGuid, KeyBuilder.buildTriggerGroup(jd)).startNow().withSchedule((ScheduleBuilder)SimpleScheduleBuilder.simpleSchedule().withMisfireHandlingInstructionNowWithRemainingCount().withRepeatCount(0)).build();
            this.jobOperationManager.subJobAdded(subGuid, this.getInstanceId(), this.getInstanceId(), this.job.getGuid(), subJobName, this.getUserguid(), this.getUsername(), null);
            try {
                scheduler.scheduleJob(jd, (Trigger)trigger);
            }
            catch (SchedulerException e) {
                this.jobOperationManager.subJobExcepted(subGuid, "\u5b50\u4efb\u52a1\u65e0\u6cd5\u88ab\u6b63\u786e\u8c03\u5ea6");
                throw new JobExecutionException(e.getMessage(), e);
            }
            JobContextImpl jobContextImpl = this;
            synchronized (jobContextImpl) {
                this.subJobInstanceIdList.add(subGuid);
            }
            return subGuid;
        }
        catch (JobsException | SQLException e) {
            throw new JobExecutionException(e.getMessage(), e);
        }
    }

    @Override
    public String executeRealTimeSubJob(AbstractRealTimeJob subJob) throws JobExecutionException {
        throw new JobExecutionException("\u521b\u5efa\u8ba1\u5212\u4efb\u52a1\u5b50\u4efb\u52a1\u8bf7\u8c03\u7528\u65b9\u6cd5\uff1acom.jiuqi.bi.core.jobs.core.quartz.JobContextImpl.executeSubJob");
    }

    @Override
    public boolean waitForSubJob() throws JobExecutionException {
        return this.waitForSubJob(Integer.MAX_VALUE);
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
        JobContextImpl jobContextImpl = this;
        synchronized (jobContextImpl) {
            while (!this.getMonitor().isCanceled() && !this.subJobInstanceIdList.isEmpty()) {
                try {
                    this.wait(1000L);
                }
                catch (InterruptedException e) {
                    return false;
                }
                if (count++ <= waitSecond) continue;
                return true;
            }
            return false;
        }
    }

    public void setSubjobConcurrentNum(int subjobConcurrentNum) {
        this.subjobConcurrentNum = subjobConcurrentNum;
    }

    public int getSubjobConcurrentNum() {
        return this.subjobConcurrentNum;
    }

    void removeSubJobInstanceId(String subInstanceId) {
        this.subJobInstanceIdList.remove(subInstanceId);
    }

    List<String> getSubJobInstanceIdList() {
        ArrayList<String> instanceIds = new ArrayList<String>();
        for (String s : this.subJobInstanceIdList) {
            instanceIds.add(s);
        }
        return instanceIds;
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
}

