/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.nodekeeper.DistributionManager
 *  com.jiuqi.bi.monitor.ProgressException
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.bi.util.type.GUID
 *  org.quartz.InterruptableJob
 *  org.quartz.JobDataMap
 *  org.quartz.JobDetail
 *  org.quartz.JobExecutionContext
 *  org.quartz.JobExecutionException
 *  org.quartz.JobKey
 *  org.quartz.UnableToInterruptJobException
 */
package com.jiuqi.bi.core.jobs.core.quartz;

import com.jiuqi.bi.core.jobs.JobExecutor;
import com.jiuqi.bi.core.jobs.JobFactory;
import com.jiuqi.bi.core.jobs.JobFactoryManager;
import com.jiuqi.bi.core.jobs.JobsException;
import com.jiuqi.bi.core.jobs.bean.JobInstanceBean;
import com.jiuqi.bi.core.jobs.certification.CertificationInfo;
import com.jiuqi.bi.core.jobs.certification.CertificationManager;
import com.jiuqi.bi.core.jobs.certification.Certifier;
import com.jiuqi.bi.core.jobs.combination.CombinationExtendStageConfig;
import com.jiuqi.bi.core.jobs.combination.CombinationJobContextImpl;
import com.jiuqi.bi.core.jobs.combination.CombinationJobTools;
import com.jiuqi.bi.core.jobs.core.MDCUtil;
import com.jiuqi.bi.core.jobs.core.quartz.JobContextImpl;
import com.jiuqi.bi.core.jobs.defaultlog.AsyncLogger;
import com.jiuqi.bi.core.jobs.extension.IJobClassifier;
import com.jiuqi.bi.core.jobs.extension.item.JobItem;
import com.jiuqi.bi.core.jobs.manager.JobOperationManager;
import com.jiuqi.bi.core.jobs.manager.JobStorageManager;
import com.jiuqi.bi.core.jobs.message.SubJobFinishedNotifierFactory;
import com.jiuqi.bi.core.jobs.model.JobModel;
import com.jiuqi.bi.core.nodekeeper.DistributionManager;
import com.jiuqi.bi.monitor.ProgressException;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.bi.util.type.GUID;
import java.sql.SQLException;
import java.util.List;
import org.quartz.InterruptableJob;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.UnableToInterruptJobException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommonQuartzJob
implements InterruptableJob {
    public static final String SUB_JOB_GROUP_PREFIX = "<sub>";
    private JobContextImpl context;
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private JobOperationManager operation = new JobOperationManager();
    private JobStorageManager storage = new JobStorageManager();
    private JobOperationManager jobOperationManager = new JobOperationManager();
    private String instanceId;
    private boolean isCanceled = false;
    private List<CombinationExtendStageConfig> stageConfigs;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void execute(JobExecutionContext quartzContext) throws JobExecutionException {
        block41: {
            JobFactory factory;
            JobModel job;
            JobDetail jobDetail = quartzContext.getJobDetail();
            JobKey key = jobDetail.getKey();
            String categoryId = key.getGroup();
            String jobId = key.getName();
            String quartzInstance = quartzContext.getFireInstanceId();
            JobDataMap jobDataMap = quartzContext.getMergedJobDataMap();
            MDCUtil.generateMDC(jobDataMap);
            this.instanceId = (String)jobDataMap.get((Object)"__sys_instanceid");
            String userguid = (String)jobDataMap.get((Object)"__sys_userguid");
            String username = (String)jobDataMap.get((Object)"__sys_username");
            String isManual = (String)jobDataMap.get((Object)"__sys_ismanual");
            if (StringUtils.isEmpty((String)this.instanceId)) {
                this.instanceId = GUID.newGUID();
                jobDataMap.put("__sys_instanceid", this.instanceId);
            }
            JobInstanceBean jobInstanceBean = null;
            try {
                IJobClassifier jobClassifier;
                job = this.storage.getJob(jobId);
                if (job == null) {
                    throw new JobsException("job\u4e0d\u5b58\u5728");
                }
                String jobTitle = job.getTitle();
                JobFactory jobFactory = JobFactoryManager.getInstance().getJobFactory(job.getCategory());
                jobInstanceBean = this.jobOperationManager.getInstanceById(this.instanceId);
                if ("true".equalsIgnoreCase(isManual)) {
                    try {
                        if (this.jobOperationManager.instanceStateIsCanceled(this.instanceId, job.getTitle())) {
                            this.logger.info("\u4efb\u52a1[{}]\u5df2\u88ab\u8bbe\u7f6e\u4e3a\u5df2\u53d6\u6d88\uff0c\u53d6\u6d88\u6267\u884c", (Object)job.getTitle());
                            return;
                        }
                    }
                    catch (JobsException e) {
                        this.logger.error(e.getMessage(), e);
                        return;
                    }
                    this.jobFired(this.instanceId, quartzInstance);
                    if (jobFactory == null) {
                        this.jobExcepted(this.instanceId, "\u8282\u70b9\u65e0\u6cd5\u5f53\u524d\u6267\u884c\u8ba1\u5212\u4efb\u52a1\u7c7b\u578b");
                        return;
                    }
                    if (CombinationJobTools.isCombinationType(categoryId) && jobInstanceBean.getStage() == 0) {
                        jobInstanceBean = this.jobOperationManager.getInstanceById(this.instanceId);
                        jobInstanceBean.setStage(jobInstanceBean.getStage() + 1);
                        this.jobOperationManager.updateJobStage(this.instanceId, jobInstanceBean.getStage());
                        CombinationJobTools.buildStageTrigger(job, jobInstanceBean, jobDataMap);
                        return;
                    }
                } else {
                    String categoryTitle = "";
                    if (jobFactory != null) {
                        categoryTitle = jobFactory.getJobCategoryTitle();
                    }
                    if (CombinationJobTools.isCombinationType(categoryId)) {
                        if (jobInstanceBean == null) {
                            this.jobStarted(this.instanceId, jobId, userguid, username, quartzInstance, job.getCategory(), categoryTitle, null, null, null);
                            jobInstanceBean = this.jobOperationManager.getInstanceById(this.instanceId);
                            this.stageConfigs = CombinationJobTools.getStageConfigs(job.getExtendedConfig());
                            jobInstanceBean.setStage(jobInstanceBean.getStage() + 1);
                            this.jobOperationManager.updateJobStage(this.instanceId, jobInstanceBean.getStage());
                            CombinationJobTools.buildStageTrigger(job, jobInstanceBean, jobDataMap);
                            return;
                        }
                    } else {
                        this.jobStarted(this.instanceId, jobId, userguid, username, quartzInstance, job.getCategory(), categoryTitle, null, null, null);
                    }
                    if (jobFactory == null) {
                        this.jobExcepted(this.instanceId, "\u8282\u70b9\u65e0\u6cd5\u5f53\u524d\u6267\u884c\u8ba1\u5212\u4efb\u52a1\u7c7b\u578b");
                        return;
                    }
                }
                if ((jobClassifier = jobFactory.getJobClassifier()) == null) {
                    throw new JobsException("\u4efb\u52a1category\u672a\u5b9a\u4e49IJobClassifier");
                }
                JobItem jobItem = jobClassifier.getJobItem(jobId);
                if (jobItem != null && StringUtils.isNotEmpty((String)jobItem.getJobTitle())) {
                    jobTitle = jobItem.getJobTitle();
                }
                this.updateJobInstanceTitle(this.instanceId, jobTitle);
            }
            catch (Exception e) {
                this.logger.error(String.format("\u65e0\u6cd5\u83b7\u53d6\u4efb\u52a1[%s@%s]", jobId, categoryId), e);
                this.jobExcepted(this.instanceId, "\u4efb\u52a1\u6267\u884c\u9519\u8bef:" + e.getMessage());
                return;
            }
            if (this.isCanceled) {
                this.jobCanceled(this.instanceId);
                return;
            }
            if (CombinationJobTools.isCombinationType(categoryId)) {
                this.context = new CombinationJobContextImpl(job, userguid, username, this.instanceId, quartzContext, jobInstanceBean.getStage());
                if (this.stageConfigs == null) {
                    this.stageConfigs = CombinationJobTools.getStageConfigs(job.getExtendedConfig());
                }
                CombinationExtendStageConfig combinationExtendStageConfig = this.stageConfigs.get(jobInstanceBean.getStage() - 1);
                factory = JobFactoryManager.getInstance().getJobFactory(combinationExtendStageConfig.getType());
                this.context.setDefaultLogger(new AsyncLogger(this.context));
                String name = DistributionManager.getInstance().self().getName();
                this.context.getJob().setExtendedConfig(combinationExtendStageConfig.getConfig());
                this.context.getDefaultLogger().info("\u5f00\u59cb\u5728[" + name + "]\u8282\u70b9\u4e0a\u6267\u884c[" + factory.getJobCategoryTitle() + "]\u7c7b\u578b\u8ba1\u5212\u4efb\u52a1");
            } else {
                this.context = new JobContextImpl(job, userguid, username, this.instanceId, quartzContext);
                factory = JobFactoryManager.getInstance().getJobFactory(categoryId);
                com.jiuqi.bi.core.jobs.defaultlog.Logger defaultLogger = factory.createLogger(this.context);
                this.context.setDefaultLogger(defaultLogger);
            }
            Certifier certifier = CertificationManager.getInstance().getCertifier();
            try {
                CertificationInfo certification = job.getCertification();
                if (factory.userRequire() && (certification == null || StringUtils.isEmpty((String)certification.getUsername()))) {
                    this.jobFinished(this.instanceId, -9999, "\u5f00\u542f\u5f3a\u5236\u6821\u9a8c\u7528\u6237\uff0c\u672a\u8bbe\u7f6e\u7528\u6237");
                    return;
                }
                boolean result = certifier.certify(this.context, job.getUser(), certification);
                if (!result) {
                    this.jobFinished(this.instanceId, -9999, "\u672a\u901a\u8fc7\u8ba4\u8bc1");
                    return;
                }
                if (certification != null) {
                    this.context.setAuthenticatedUsername(certification.getUsername());
                }
            }
            catch (Exception e1) {
                this.logger.error("\u4efb\u52a1\u8ba4\u8bc1\u5931\u8d25", e1);
                this.jobFinished(this.instanceId, -9999, "\u8ba4\u8bc1\u5931\u8d25\uff1a " + e1.getMessage());
                return;
            }
            SubJobFinishedNotifierFactory.getInstance().putSubjobNotifier(this.instanceId, subInstanceId -> {
                JobContextImpl jobContextImpl = this.context;
                synchronized (jobContextImpl) {
                    this.context.removeSubJobInstanceId(subInstanceId);
                    this.context.notifyAll();
                }
            });
            try {
                JobExecutor executor = factory.createJobExecutor(jobId);
                try {
                    executor.execute(this.context);
                    if (this.context.getMonitor().isCanceled()) {
                        this.jobCanceled(this.instanceId);
                        break block41;
                    }
                    int jobResult = this.context.getResult();
                    String jobResultMessage = this.context.getResultMesage();
                    if (jobResult == 0) {
                        jobResult = 100;
                    }
                    if (jobResultMessage == null) {
                        jobResultMessage = "\u4efb\u52a1\u6267\u884c\u5b8c\u6bd5";
                    }
                    if (CombinationJobTools.isCombinationType(categoryId)) {
                        if (this.stageConfigs.size() > jobInstanceBean.getStage()) {
                            jobInstanceBean.setStage(jobInstanceBean.getStage() + 1);
                            this.jobOperationManager.updateJobStage(this.instanceId, jobInstanceBean.getStage());
                            CombinationJobTools.buildStageTrigger(job, jobInstanceBean, jobDataMap);
                        } else {
                            this.jobFinished(this.instanceId, jobResult, jobResultMessage);
                        }
                        break block41;
                    }
                    this.jobFinished(this.instanceId, jobResult, jobResultMessage);
                }
                catch (ProgressException e) {
                    this.logger.error(String.format("\u4efb\u52a1[%s@%s]\u83b7\u53d6\u8fdb\u5ea6\u4fe1\u606f\u53d1\u751f\u9519\u8bef", job.getTitle(), categoryId), e);
                    this.jobExcepted(this.instanceId, "\u4efb\u52a1\u6267\u884c\u9519\u8bef\uff1a" + e.getMessage());
                }
                catch (Throwable e) {
                    this.logger.error(String.format("\u4efb\u52a1[%s@%s]\u6267\u884c\u5f02\u5e38", job.getTitle(), categoryId), e);
                    this.jobExcepted(this.instanceId, "\u4efb\u52a1\u6267\u884c\u9519\u8bef:" + e.getMessage());
                }
            }
            catch (Exception e) {
                this.logger.error(String.format("\u65e0\u6cd5\u521b\u5efa\u4efb\u52a1[%s@%s]\u7684\u6267\u884c\u5668", job.getTitle(), categoryId), e);
                this.jobExcepted(this.instanceId, "\u65e0\u6cd5\u521b\u5efa\u4efb\u52a1");
            }
            finally {
                SubJobFinishedNotifierFactory.getInstance().removeSubjobNotifier(this.instanceId);
            }
        }
    }

    private void updateJobInstanceTitle(String instanceId, String jobTitle) {
        try {
            this.operation.updateJobInstanceTitle(jobTitle, instanceId);
        }
        catch (Exception e) {
            this.logger.error("\u66f4\u65b0\u4efb\u52a1\u6807\u9898\u5931\u8d25", e);
        }
    }

    private void jobStarted(String instanceId, String jobId, String userguid, String username, String quartzInstance, String categoryId, String categoryTitle, List<String> linkSources, String queryField1, String queryField2) {
        try {
            this.operation.mainJobStart(instanceId, jobId, userguid, username, null, quartzInstance, categoryId, categoryTitle, true, linkSources, queryField1, queryField2);
        }
        catch (Exception e) {
            this.logger.error("\u542f\u52a8\u8ba1\u5212\u4efb\u52a1\u51fa\u9519", e);
        }
    }

    private void jobFired(String instanceId, String quartzInstance) {
        try {
            this.operation.mainJobFired(instanceId, quartzInstance);
        }
        catch (Exception e) {
            this.logger.error("\u542f\u52a8\u8ba1\u5212\u4efb\u52a1\u51fa\u9519", e);
        }
    }

    private void jobExcepted(String instanceId, String message) {
        try {
            this.operation.jobExcepted(instanceId, message);
        }
        catch (Exception e) {
            this.logger.error("\u8bb0\u5f55\u4efb\u52a1\u5b9e\u4f8b\u72b6\u6001\u51fa\u9519:" + e.getMessage(), e);
        }
    }

    private void jobFinished(String instanceId, int jobResult, String jobResultMessage) {
        try {
            this.operation.jobFinished(instanceId, jobResult, jobResultMessage);
        }
        catch (Exception e) {
            this.logger.error("[\u4efb\u52a1\u5b8c\u6210]\u8bb0\u5f55\u4efb\u52a1\u5b9e\u4f8b\u72b6\u6001\u51fa\u9519:" + e.getMessage(), e);
        }
    }

    private void jobCanceled(String instanceId) {
        try {
            this.operation.jobCanceled(instanceId);
        }
        catch (Exception e) {
            this.logger.error("[\u4efb\u52a1\u53d6\u6d88]\u8bb0\u5f55\u4efb\u52a1\u5b9e\u4f8b\u72b6\u6001\u51fa\u9519:" + e.getMessage(), e);
        }
    }

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
}

