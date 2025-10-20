/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  org.quartz.DisallowConcurrentExecution
 *  org.quartz.InterruptableJob
 *  org.quartz.JobDataMap
 *  org.quartz.JobExecutionContext
 *  org.quartz.JobExecutionException
 *  org.quartz.UnableToInterruptJobException
 */
package com.jiuqi.bi.core.jobs.realtime.quartz;

import com.jiuqi.bi.core.jobs.JobsException;
import com.jiuqi.bi.core.jobs.core.MDCUtil;
import com.jiuqi.bi.core.jobs.manager.ConfigManager;
import com.jiuqi.bi.core.jobs.manager.JobOperationManager;
import com.jiuqi.bi.core.jobs.message.MessageSender;
import com.jiuqi.bi.core.jobs.message.SubJobFinishedNotifierFactory;
import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.bi.core.jobs.realtime.IRealTimePostProcessor;
import com.jiuqi.bi.core.jobs.realtime.RealTimeJobManager;
import com.jiuqi.bi.core.jobs.realtime.RealTimePostProcessManager;
import com.jiuqi.bi.core.jobs.realtime.core.Executor;
import com.jiuqi.bi.core.jobs.realtime.quartz.RealTimeQuartzJobContextImpl;
import com.jiuqi.bi.core.jobs.realtime.quartz.RealTimeQuartzSubJobContextImpl;
import com.jiuqi.bi.util.StringUtils;
import java.sql.SQLException;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.InterruptableJob;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.UnableToInterruptJobException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@DisallowConcurrentExecution
public class RealTimeQuartzJob
implements InterruptableJob {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private RealTimeQuartzJobContextImpl jobContext;
    private JobOperationManager jobOperationManager = new JobOperationManager();
    private boolean isCanceled = false;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDataMap jobData = context.getMergedJobDataMap();
        MDCUtil.generateMDC(jobData);
        for (IRealTimePostProcessor processor : RealTimePostProcessManager.getInstance().getProcessors()) {
            processor.afterPost(jobData);
        }
        String jobId = (String)jobData.get((Object)"_SYS_INSTANCE_ID");
        String parentInstanceId = (String)jobData.get((Object)"_SYS_PARENT_INSTANCE_ID");
        AbstractRealTimeJob realTimeJob = (AbstractRealTimeJob)jobData.get((Object)"_SYS_JOB_ENTITY");
        realTimeJob.getParams().put("_SYS_INSTANCE_ID", jobId);
        if (RealTimeJobManager.getInstance().isLocalRealTimeJobMode()) {
            try {
                ConfigManager.getInstance().deleteJobOnlyInNode(jobId);
            }
            catch (JobsException e) {
                this.logger.warn("\u5220\u9664\u4efb\u52a1[" + realTimeJob.getTitle() + "]\u4ec5\u53ef\u6267\u884c\u7684\u8282\u70b9\u914d\u7f6e\u5931\u8d25", e);
            }
        }
        try {
            if (this.jobOperationManager.instanceStateIsCanceled(jobId, realTimeJob.getTitle())) {
                this.logger.info("\u4efb\u52a1[{}]\u5df2\u88ab\u8bbe\u7f6e\u4e3a\u5df2\u53d6\u6d88\uff0c\u53d6\u6d88\u6267\u884c", (Object)realTimeJob.getTitle());
                return;
            }
        }
        catch (JobsException e) {
            this.logger.error(e.getMessage(), e);
            return;
        }
        boolean isSubJob = StringUtils.isNotEmpty((String)parentInstanceId);
        if (isSubJob) {
            this.jobContext = new RealTimeQuartzSubJobContextImpl(realTimeJob, context);
        } else {
            this.jobContext = new RealTimeQuartzJobContextImpl(realTimeJob, context);
            SubJobFinishedNotifierFactory.getInstance().putSubjobNotifier(jobId, subInstanceId -> {
                RealTimeQuartzJobContextImpl realTimeQuartzJobContextImpl = this.jobContext;
                synchronized (realTimeQuartzJobContextImpl) {
                    this.jobContext.removeSubJobInstanceId(subInstanceId);
                    this.jobContext.notifyAll();
                }
            });
        }
        try {
            if (this.isCanceled) {
                this.logger.debug("\u5373\u65f6\u4efb\u52a1[{}]\u5df2\u53d6\u6d88", (Object)realTimeJob.getTitle());
                Executor.jobCanceled(jobId);
                return;
            }
            Executor.doExec(realTimeJob, this.jobContext);
        }
        finally {
            if (isSubJob) {
                try {
                    MessageSender.sendSubJobFinishedMessage(this.jobContext.getInstanceId(), parentInstanceId);
                }
                catch (Exception e) {
                    this.logger.error("\u53d1\u9001\u6d88\u606f\u5931\u8d25", e);
                }
            } else {
                SubJobFinishedNotifierFactory.getInstance().removeSubjobNotifier(this.jobContext.getInstanceId());
            }
        }
    }

    public void interrupt() throws UnableToInterruptJobException {
        this.isCanceled = true;
        try {
            this.jobOperationManager.cancelJob(this.jobContext.getInstanceId());
            this.jobContext.getMonitor().cancel();
        }
        catch (SQLException e) {
            this.logger.error(String.format("\u5373\u65f6\u4efb\u52a1[%s]\u53d6\u6d88\u5931\u8d25", this.jobContext.getRealTimeJob().getTitle()), e);
        }
    }
}

