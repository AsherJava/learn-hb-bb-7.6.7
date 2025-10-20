/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.nodekeeper.DistributionManager
 *  com.jiuqi.bi.core.nodekeeper.Node
 *  com.jiuqi.bi.util.Guid
 *  com.jiuqi.bi.util.StringUtils
 *  org.quartz.JobBuilder
 *  org.quartz.JobDataMap
 *  org.quartz.JobDetail
 *  org.quartz.ScheduleBuilder
 *  org.quartz.SimpleScheduleBuilder
 *  org.quartz.SimpleTrigger
 *  org.quartz.TriggerBuilder
 */
package com.jiuqi.bi.core.jobs.realtime.quartz;

import com.jiuqi.bi.core.jobs.JobsException;
import com.jiuqi.bi.core.jobs.core.SchedulerCommitPool;
import com.jiuqi.bi.core.jobs.core.quartz.KeyBuilder;
import com.jiuqi.bi.core.jobs.extension.JobDispatchControlManager;
import com.jiuqi.bi.core.jobs.manager.ConfigManager;
import com.jiuqi.bi.core.jobs.manager.JobOperationManager;
import com.jiuqi.bi.core.jobs.message.MessageSender;
import com.jiuqi.bi.core.jobs.monitor.JobType;
import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.bi.core.jobs.realtime.IRealTimePostProcessor;
import com.jiuqi.bi.core.jobs.realtime.RealTimeJob;
import com.jiuqi.bi.core.jobs.realtime.RealTimeJobManager;
import com.jiuqi.bi.core.jobs.realtime.RealTimePostProcessManager;
import com.jiuqi.bi.core.jobs.realtime.core.RealTimeJobFactory;
import com.jiuqi.bi.core.jobs.realtime.core.RealTimeJobRunner;
import com.jiuqi.bi.core.jobs.realtime.quartz.RealTimeQuartzJob;
import com.jiuqi.bi.core.nodekeeper.DistributionManager;
import com.jiuqi.bi.core.nodekeeper.Node;
import com.jiuqi.bi.util.Guid;
import com.jiuqi.bi.util.StringUtils;
import java.sql.SQLException;
import java.util.Arrays;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.ScheduleBuilder;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.SimpleTrigger;
import org.quartz.TriggerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RealTimeQuartzJobRunner
implements RealTimeJobRunner {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final JobOperationManager jobOperationManager = new JobOperationManager();

    @Override
    public String commit(AbstractRealTimeJob job) throws JobsException {
        return this.commit(null, null, job);
    }

    @Override
    public String commit(String parentJobGuid, String rootJobGuid, AbstractRealTimeJob job) throws JobsException {
        String jobId = Guid.newGuid();
        job.getParams().put("_SYS_INSTANCE_ID", jobId);
        JobOperationManager operationManager = new JobOperationManager();
        operationManager.setInstanceParams(jobId, job.getParams());
        boolean isSubJob = StringUtils.isNotEmpty((String)parentJobGuid);
        JobDataMap jobData = new JobDataMap();
        jobData.put("_SYS_JOB_ENTITY", (Object)job);
        jobData.put("_SYS_INSTANCE_ID", jobId);
        jobData.put("_SYS_PARENT_INSTANCE_ID", parentJobGuid);
        jobData.put("_SYS_ROOT_INSTANCE_ID", rootJobGuid);
        String realTimeJobGroup = RealTimeJobFactory.getInstance().getRealTimeJobGroup(job);
        jobData.put("_SYS_JOB_GROUP", realTimeJobGroup);
        boolean isolateJobGroup = RealTimeJobFactory.getInstance().isIsolateJobGroup(job);
        if (isolateJobGroup && this.jobOperationManager.isLinkSourcesRunning(job.getLinkSource())) {
            throw new JobsException("\u5b58\u5728\u6b63\u5728\u6267\u884c\u7684\u8d44\u6e90\uff0c\u7981\u6b62\u5e76\u53d1\u6267\u884c");
        }
        jobData.put("_SYS_JOB_ISOLATE", String.valueOf(isolateJobGroup));
        for (IRealTimePostProcessor processor : RealTimePostProcessManager.getInstance().getProcessors()) {
            processor.beforePost(jobData);
        }
        JobDetail jobDetail = JobBuilder.newJob(RealTimeQuartzJob.class).withIdentity(jobId, RealTimeJobFactory.getRealTimeJobCategoryId(realTimeJobGroup)).usingJobData(jobData).build();
        SimpleTrigger trigger = (SimpleTrigger)TriggerBuilder.newTrigger().forJob(jobDetail).withIdentity(jobId, KeyBuilder.buildTriggerGroup(jobDetail)).withSchedule((ScheduleBuilder)SimpleScheduleBuilder.simpleSchedule().withMisfireHandlingInstructionNextWithRemainingCount().withRepeatCount(0)).startNow().build();
        try {
            if (isSubJob) {
                this.jobOperationManager.subJobAdded(jobId, parentJobGuid, parentJobGuid, jobId, job.getTitle(), job.getUserGuid(), job.getUserName(), job.getLinkSource());
            } else {
                String jobGroup = realTimeJobGroup;
                this.jobOperationManager.mainJobAdded(jobId, jobId, job.getUserGuid(), job.getUserName(), job.getTitle(), JobType.REALTIME_JOB, RealTimeJobFactory.getRealTimeJobCategoryId(jobGroup), RealTimeJobFactory.getInstance().getRealTimeJobGroupTitle(jobGroup), false, job.getLinkSource(), job.getQueryField1(), job.getQueryField2());
            }
        }
        catch (Exception e) {
            throw new JobsException(e);
        }
        if (RealTimeJobManager.getInstance().isLocalRealTimeJobMode()) {
            this.logger.info("[JQBI.LOCALREALJOB-\u5373\u65f6\u4efb\u52a1\u672c\u5730\u6a21\u5f0f]\u5df2\u5f00\u542f\uff0c\u5373\u65f6\u4efb\u52a1[" + job.getTitle() + "]\u5c06\u5728\u672c\u673a\u8fd0\u884c");
            String jobDispatchType = ConfigManager.getInstance().getJobDispatchType();
            String jobMatchType = ConfigManager.getInstance().getJobMatchType();
            JobDispatchControlManager jobDispatchControlManager = new JobDispatchControlManager();
            jobDispatchControlManager.load(jobMatchType, jobDispatchType);
            boolean allowed = true;
            RealTimeJob annotation = job.getClass().getAnnotation(RealTimeJob.class);
            if (annotation != null) {
                allowed = jobDispatchType.equalsIgnoreCase("BY_TYPE") ? jobDispatchControlManager.isAllowed(annotation.group()) : jobDispatchControlManager.isAllowed(Arrays.asList(annotation.tags()));
            }
            if (!allowed) {
                this.logger.error("\u5f53\u524d\u8282\u70b9\u7981\u6b62\u6267\u884c\u5373\u65f6\u4efb\u52a1\uff0c\u8bf7\u5728CONSOLE\u8fdb\u884c\u8bbe\u7f6e\uff01");
                try {
                    this.jobOperationManager.jobExcepted(jobId, "\u5f53\u524d\u8282\u70b9\u7981\u6b62\u6267\u884c\u5373\u65f6\u4efb\u52a1\uff0c\u8bf7\u5728CONSOLE\u8fdb\u884c\u8bbe\u7f6e\uff01");
                }
                catch (SQLException e) {
                    this.logger.error(e.getMessage(), e);
                }
                throw new JobsException("\u5f53\u524d\u8282\u70b9\u7981\u6b62\u6267\u884c\u5373\u65f6\u4efb\u52a1\uff0c\u8bf7\u5728CONSOLE\u8fdb\u884c\u8bbe\u7f6e\uff01");
            }
            Node node = DistributionManager.getInstance().self();
            String nodeName = node.getName();
            ConfigManager configManager = ConfigManager.getInstance();
            configManager.setJobOnlyInNode(jobId, new String[]{nodeName});
        }
        if (isSubJob) {
            SchedulerCommitPool.getInstance().addSubRealTimeTrigger(jobDetail, trigger);
        } else {
            SchedulerCommitPool.getInstance().addRealTimeTrigger(jobDetail, trigger);
        }
        this.logger.debug("\u4efb\u52a1[" + job.getTitle() + "]\u5df2\u6dfb\u52a0\u8fdb\u8c03\u5ea6\u5668\u7b49\u5f85\u8c03\u5ea6");
        return jobId;
    }

    @Override
    public void cancel(String instanceGuid) throws JobsException {
        try {
            MessageSender.sendJobCanceledMessage(instanceGuid);
        }
        catch (Exception e) {
            throw new JobsException("\u53d1\u9001\u4efb\u52a1\u53d6\u6d88\u6d88\u606f\u5931\u8d25", e);
        }
    }

    @Override
    public void restartAll() throws JobsException {
    }
}

