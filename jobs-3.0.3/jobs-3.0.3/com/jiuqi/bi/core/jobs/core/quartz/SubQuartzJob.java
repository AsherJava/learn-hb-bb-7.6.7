/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.quartz.InterruptableJob
 *  org.quartz.Job
 *  org.quartz.JobDataMap
 *  org.quartz.JobExecutionContext
 *  org.quartz.JobExecutionException
 *  org.quartz.UnableToInterruptJobException
 */
package com.jiuqi.bi.core.jobs.core.quartz;

import com.jiuqi.bi.core.jobs.JobExecutor;
import com.jiuqi.bi.core.jobs.JobFactory;
import com.jiuqi.bi.core.jobs.JobFactoryManager;
import com.jiuqi.bi.core.jobs.JobsException;
import com.jiuqi.bi.core.jobs.bean.JobInstanceBean;
import com.jiuqi.bi.core.jobs.core.MDCUtil;
import com.jiuqi.bi.core.jobs.core.quartz.SubJobContextImpl;
import com.jiuqi.bi.core.jobs.defaultlog.AsyncLogger;
import com.jiuqi.bi.core.jobs.manager.JobOperationManager;
import com.jiuqi.bi.core.jobs.message.MessageSender;
import com.jiuqi.bi.core.jobs.monitor.State;
import java.sql.SQLException;
import org.quartz.InterruptableJob;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.UnableToInterruptJobException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SubQuartzJob
implements Job,
InterruptableJob {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private JobOperationManager operation = new JobOperationManager();
    private SubJobContextImpl context;
    private String instanceId;
    private boolean isCanceled = false;

    public void interrupt() throws UnableToInterruptJobException {
        try {
            this.operation.cancelJob(this.instanceId);
            this.isCanceled = true;
            if (this.context != null) {
                this.context.jobCancel();
            }
        }
        catch (SQLException e) {
            this.logger.error("\u53d6\u6d88\u4efb\u52a1\u5931\u8d25", e);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void execute(JobExecutionContext quartzContext) throws JobExecutionException {
        JobExecutor executor;
        String executorClassName;
        String username;
        String userguid;
        String rootInstanceId;
        String parentInstanceId;
        String quartzInstance;
        block34: {
            quartzInstance = quartzContext.getFireInstanceId();
            JobDataMap jobDataMap = quartzContext.getMergedJobDataMap();
            MDCUtil.generateMDC(jobDataMap);
            this.instanceId = (String)jobDataMap.get((Object)"__sys_instanceid");
            String jobTitle = (String)jobDataMap.get((Object)"__sys_jobtitle");
            parentInstanceId = (String)jobDataMap.get((Object)"__sys_parentinstanceid");
            rootInstanceId = (String)jobDataMap.get((Object)"__sys_rootinstanceid");
            userguid = (String)jobDataMap.get((Object)"__sys_userguid");
            username = (String)jobDataMap.get((Object)"__sys_username");
            executorClassName = (String)jobDataMap.get((Object)"__sys_subexe_class");
            try {
                JobOperationManager jobOperationManager = new JobOperationManager();
                if (jobOperationManager.instanceStateIsCanceled(this.instanceId, jobTitle)) {
                    this.logger.info("\u4efb\u52a1[{}]\u5df2\u88ab\u8bbe\u7f6e\u4e3a\u5df2\u53d6\u6d88\uff0c\u53d6\u6d88\u6267\u884c", (Object)jobTitle);
                    return;
                }
                if (executorClassName != null) break block34;
            }
            catch (JobsException e) {
                this.logger.error(e.getMessage(), e);
                return;
            }
            this.jobExcepted(this.instanceId, "\u672a\u6307\u5b9a\u5b50\u4efb\u52a1\u6267\u884c\u5668");
            return;
        }
        try {
            executor = (JobExecutor)Class.forName(executorClassName).newInstance();
        }
        catch (Exception e) {
            this.logger.error("\u521d\u59cb\u5316\u5b50\u4efb\u52a1\u6267\u884c\u5668\u51fa\u9519", e);
            this.jobExcepted(this.instanceId, "\u65e0\u6cd5\u521d\u59cb\u5316\u5b50\u4efb\u52a1\u6267\u884c\u5668");
            try {
                MessageSender.sendSubJobFinishedMessage(this.instanceId, parentInstanceId);
                return;
            }
            catch (Exception e2) {
                this.logger.error("\u53d1\u9001\u6d88\u606f\u5931\u8d25", e2);
            }
            return;
        }
        this.jobFired(this.instanceId, quartzInstance);
        JobInstanceBean parentInstance = this.getJobInstance(parentInstanceId);
        if (this.isCanceled || parentInstance != null && (parentInstance.getState() == State.CANCELING.getValue() || parentInstance.getResult() == 2)) {
            this.jobCanceled(this.instanceId);
            return;
        }
        this.context = this.createContext(this.instanceId, userguid, username, parentInstanceId, rootInstanceId, quartzContext);
        if (parentInstance != null) {
            JobFactory jobFactory = JobFactoryManager.getInstance().getJobFactory(parentInstance.getCategoryId());
            com.jiuqi.bi.core.jobs.defaultlog.Logger defaultLogger = jobFactory.createLogger(this.context);
            this.context.setDefaultLogger(defaultLogger);
        } else {
            this.context.setDefaultLogger(new AsyncLogger(this.context));
        }
        try {
            executor.execute(this.context);
            if (this.context.getMonitor().isCanceled()) {
                this.jobCanceled(this.instanceId);
                return;
            }
            int jobResult = this.context.getResult();
            String jobResultMessage = this.context.getResultMesage();
            if (jobResult == 0) {
                jobResult = 100;
            }
            if (jobResultMessage == null) {
                jobResultMessage = "\u4efb\u52a1\u6267\u884c\u5b8c\u6bd5";
            }
            this.jobFinished(this.instanceId, jobResult, jobResultMessage);
            return;
        }
        catch (Throwable e) {
            this.logger.error("\u5b50\u4efb\u52a1\u6267\u884c\u5f02\u5e38", e);
            this.jobExcepted(this.instanceId, "\u4efb\u52a1\u6267\u884c\u9519\u8bef");
            return;
        }
        finally {
            try {
                MessageSender.sendSubJobFinishedMessage(this.instanceId, parentInstanceId);
            }
            catch (Exception e) {
                this.logger.error("\u53d1\u9001\u6d88\u606f\u5931\u8d25", e);
            }
        }
    }

    private void jobExcepted(String instanceId, String message) {
        try {
            this.operation.jobExcepted(instanceId, message);
        }
        catch (Exception e) {
            this.logger.error("\u8bb0\u5f55\u4efb\u52a1\u5b9e\u4f8b\u72b6\u6001\u51fa\u9519", e);
        }
    }

    private void jobFired(String instanceId, String quartzInstance) {
        try {
            this.operation.subJobFired(instanceId, quartzInstance);
        }
        catch (Exception e) {
            this.logger.error("\u542f\u52a8\u8ba1\u5212\u4efb\u52a1\u51fa\u9519\u3002", e);
        }
    }

    private void jobFinished(String instanceId, int jobResult, String jobResultMessage) {
        try {
            this.operation.jobFinished(instanceId, jobResult, jobResultMessage);
        }
        catch (Exception e) {
            this.logger.error("\u8bb0\u5f55\u4efb\u52a1\u5b9e\u4f8b\u72b6\u6001\u51fa\u9519", e);
        }
    }

    private void jobCanceled(String instanceId) {
        try {
            this.operation.jobCanceled(instanceId);
        }
        catch (Exception e) {
            this.logger.error("\u8bb0\u5f55\u4efb\u52a1\u5b9e\u4f8b\u72b6\u6001\u51fa\u9519", e);
        }
    }

    private JobInstanceBean getJobInstance(String instanceId) {
        try {
            return this.operation.getInstanceById(instanceId);
        }
        catch (Exception e) {
            this.logger.error("\u83b7\u53d6\u4efb\u52a1\u5b9e\u4f8b\u51fa\u9519", e);
            return null;
        }
    }

    private SubJobContextImpl createContext(String instanceId, String userguid, String username, String parentInstanceId, String rootInstanceId, JobExecutionContext quartzContext) {
        return new SubJobContextImpl(instanceId, userguid, username, parentInstanceId, rootInstanceId, quartzContext);
    }
}

