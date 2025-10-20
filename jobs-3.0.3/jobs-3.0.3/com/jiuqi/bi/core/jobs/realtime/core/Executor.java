/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.log.comm.LogTraceIDUtil
 */
package com.jiuqi.bi.core.jobs.realtime.core;

import com.jiuqi.bi.core.jobs.JobContextHolder;
import com.jiuqi.bi.core.jobs.JobsException;
import com.jiuqi.bi.core.jobs.base.AbstractBaseJobContext;
import com.jiuqi.bi.core.jobs.base.IJobExecuteAble;
import com.jiuqi.bi.core.jobs.bean.JobExecResultBean;
import com.jiuqi.bi.core.jobs.manager.JobExecResultManager;
import com.jiuqi.bi.core.jobs.manager.JobOperationManager;
import com.jiuqi.bi.core.jobs.message.usermessage.IUserMessageSender;
import com.jiuqi.bi.core.jobs.message.usermessage.UserMessageSenderFactory;
import com.jiuqi.bi.core.jobs.monitor.JobResult;
import com.jiuqi.bi.core.jobs.monitor.JobType;
import com.jiuqi.bi.core.jobs.realtime.RealTimeJob;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.log.comm.LogTraceIDUtil;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Executor {
    private static Logger logger = LoggerFactory.getLogger(Executor.class);
    private static JobOperationManager jobOperationManager = new JobOperationManager();
    private static JobExecResultManager jobExecResultManager = new JobExecResultManager();

    private Executor() {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Loose catch block
     */
    public static void doExec(IJobExecuteAble realTimeJob, AbstractBaseJobContext jobContext) {
        block23: {
            block22: {
                String parentInstanceId;
                if (LogTraceIDUtil.getLogTraceId() == null) {
                    LogTraceIDUtil.setLogTraceId((String)LogTraceIDUtil.newLogTraceId());
                }
                Executor.jobFired(jobContext.getInstanceId(), jobContext.getFireInstanceId());
                RealTimeJob annotation = realTimeJob.getClass().getAnnotation(RealTimeJob.class);
                String group = "";
                group = annotation != null ? annotation.group() : realTimeJob.getClass().getSimpleName();
                jobOperationManager.insertRunning(jobContext.getInstanceId(), JobType.REALTIME_JOB.getValue(), group);
                JobContextHolder.setJobContext(jobContext);
                try {
                    jobContext.getDefaultLogger().doLog(20, "\u6267\u884c\u65e5\u5fd7\u5f00\u59cb\u52a0\u8f7d", null);
                    realTimeJob.execute(jobContext);
                }
                finally {
                    JobContextHolder.clear();
                }
                int jobResult = jobContext.getResult();
                String jobResultMessage = jobContext.getResultMesage();
                if ((jobResult == 0 || jobResult == 2) && jobContext.getMonitor().isCanceled()) {
                    Executor.jobCanceled(jobContext.getInstanceId());
                    break block22;
                }
                if (jobResult == 0) {
                    jobResult = 100;
                }
                if (jobResultMessage == null) {
                    jobResultMessage = "\u6267\u884c\u5b8c\u6bd5";
                }
                if (StringUtils.isEmpty((String)(parentInstanceId = jobContext.getParameterValue("_SYS_PARENT_INSTANCE_ID")))) {
                    try {
                        IUserMessageSender messageSender = UserMessageSenderFactory.getInstance().getMessageSender();
                        if (messageSender != null) {
                            List<JobExecResultBean> fileBeans = jobExecResultManager.getAllResultByRootJobInstance(jobContext.getInstanceId());
                            String messageTitle = realTimeJob.getTitle() + JobResult.getResultTitle(jobResult);
                            messageSender.send(jobContext.getUserguid(), jobContext.getInstanceId(), "com.jiuqi.bi.jobs.realtime", messageTitle, jobResultMessage, fileBeans);
                        }
                    }
                    catch (Exception e) {
                        logger.error("\u53d1\u9001\u6d88\u606f\u5931\u8d25" + realTimeJob.getTitle(), e);
                    }
                }
                Executor.jobFinished(jobContext.getInstanceId(), jobResult, jobResultMessage);
            }
            try {
                jobOperationManager.deleteRunning(jobContext.getInstanceId());
            }
            catch (JobsException e) {
                logger.error(String.format("\u5373\u65f6\u4efb\u52a1[%s]\u5220\u9664\u4efb\u52a1\u6b63\u5728\u6267\u884c\u72b6\u6001\u51fa\u73b0\u5f02\u5e38", realTimeJob.getTitle()), e);
            }
            jobContext.getDefaultLogger().doLog(29, "\u6267\u884c\u65e5\u5fd7\u52a0\u8f7d\u5b8c\u6210", null);
            break block23;
            catch (Throwable e) {
                try {
                    logger.error(String.format("\u5373\u65f6\u4efb\u52a1[%s]\u6267\u884c\u5f02\u5e38", realTimeJob.getTitle()), e);
                    Executor.jobExcepted(jobContext.getInstanceId(), "\u6267\u884c\u5f02\u5e38");
                }
                catch (Throwable throwable) {
                    try {
                        jobOperationManager.deleteRunning(jobContext.getInstanceId());
                    }
                    catch (JobsException e2) {
                        logger.error(String.format("\u5373\u65f6\u4efb\u52a1[%s]\u5220\u9664\u4efb\u52a1\u6b63\u5728\u6267\u884c\u72b6\u6001\u51fa\u73b0\u5f02\u5e38", realTimeJob.getTitle()), e2);
                    }
                    jobContext.getDefaultLogger().doLog(29, "\u6267\u884c\u65e5\u5fd7\u52a0\u8f7d\u5b8c\u6210", null);
                    throw throwable;
                }
                try {
                    jobOperationManager.deleteRunning(jobContext.getInstanceId());
                }
                catch (JobsException e3) {
                    logger.error(String.format("\u5373\u65f6\u4efb\u52a1[%s]\u5220\u9664\u4efb\u52a1\u6b63\u5728\u6267\u884c\u72b6\u6001\u51fa\u73b0\u5f02\u5e38", realTimeJob.getTitle()), e3);
                }
                jobContext.getDefaultLogger().doLog(29, "\u6267\u884c\u65e5\u5fd7\u52a0\u8f7d\u5b8c\u6210", null);
            }
        }
    }

    public static void jobFired(String instanceId, String quartzInstance) {
        try {
            jobOperationManager.jobFired(instanceId, quartzInstance);
        }
        catch (Exception e) {
            logger.error(String.format("\u5373\u65f6\u4efb\u52a1[%s]\u72b6\u6001\u8bbe\u7f6e\u4e3a\u6b63\u5728\u8fd0\u884c\u5931\u8d25", instanceId), e);
        }
    }

    public static void jobExcepted(String instanceId, String message) {
        try {
            jobOperationManager.jobExcepted(instanceId, message);
        }
        catch (Exception e) {
            logger.error(String.format("\u5373\u65f6\u4efb\u52a1[%s]\u72b6\u6001\u8bbe\u7f6e\u4e3a\u6267\u884c\u5f02\u5e38\u5931\u8d25", instanceId), e);
        }
    }

    public static void jobFinished(String instanceId, int jobResult, String jobResultMessage) {
        try {
            jobOperationManager.jobFinished(instanceId, jobResult, jobResultMessage);
        }
        catch (Exception e) {
            logger.error(String.format("\u5373\u65f6\u4efb\u52a1[%s]\u72b6\u6001\u8bbe\u7f6e\u4e3a\u6267\u884c\u5b8c\u6bd5\u5931\u8d25", instanceId), e);
        }
    }

    public static void jobCanceled(String instanceId) {
        try {
            jobOperationManager.jobCanceled(instanceId);
        }
        catch (Exception e) {
            logger.error(String.format("\u5373\u65f6\u4efb\u52a1[%s]\u72b6\u6001\u8bbe\u7f6e\u4e3a\u5df2\u53d6\u6d88\u5931\u8d25", instanceId), e);
        }
    }
}

