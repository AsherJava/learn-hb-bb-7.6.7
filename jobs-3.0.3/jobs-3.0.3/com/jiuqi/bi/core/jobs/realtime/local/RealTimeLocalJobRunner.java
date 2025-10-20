/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.Guid
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.bi.core.jobs.realtime.local;

import com.jiuqi.bi.core.jobs.JobsException;
import com.jiuqi.bi.core.jobs.bean.JobInstanceBean;
import com.jiuqi.bi.core.jobs.manager.JobOperationManager;
import com.jiuqi.bi.core.jobs.monitor.JobMonitorManager;
import com.jiuqi.bi.core.jobs.monitor.JobType;
import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.bi.core.jobs.realtime.IRealTimePostProcessor;
import com.jiuqi.bi.core.jobs.realtime.RealTimeJobManager;
import com.jiuqi.bi.core.jobs.realtime.RealTimePostProcessManager;
import com.jiuqi.bi.core.jobs.realtime.core.RealTimeJobFactory;
import com.jiuqi.bi.core.jobs.realtime.core.RealTimeJobRunner;
import com.jiuqi.bi.core.jobs.realtime.local.LocalScheduler;
import com.jiuqi.bi.core.jobs.realtime.local.LocalSchedulerManager;
import com.jiuqi.bi.core.jobs.realtime.local.RealTimeLocalJobContextImpl;
import com.jiuqi.bi.core.jobs.realtime.local.RealTimeLocalSubJobContextImpl;
import com.jiuqi.bi.util.Guid;
import com.jiuqi.bi.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RealTimeLocalJobRunner
implements RealTimeJobRunner {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private JobOperationManager jobOperationManager = new JobOperationManager();

    @Override
    public String commit(AbstractRealTimeJob job) throws JobsException {
        return this.commit(null, null, job);
    }

    @Override
    public String commit(String parentJobGuid, String rootJobGuid, AbstractRealTimeJob job) throws JobsException {
        RealTimeLocalJobContextImpl context;
        LocalScheduler scheduler;
        String jobId = Guid.newGuid();
        job.getParams().put("_SYS_INSTANCE_ID", jobId);
        job.getParams().put("_SYS_JOB_GROUP", RealTimeJobFactory.getInstance().getRealTimeJobGroup(job));
        JobOperationManager operationManager = new JobOperationManager();
        operationManager.setInstanceParams(jobId, job.getParams());
        boolean isolateJobGroup = RealTimeJobFactory.getInstance().isIsolateJobGroup(job);
        if (isolateJobGroup && this.jobOperationManager.isLinkSourcesRunning(job.getLinkSource())) {
            throw new JobsException("\u5b58\u5728\u6b63\u5728\u6267\u884c\u7684\u8d44\u6e90\uff0c\u7981\u6b62\u5e76\u53d1\u6267\u884c");
        }
        job.getParams().put("_SYS_JOB_ISOLATE", String.valueOf(isolateJobGroup));
        for (IRealTimePostProcessor processor : RealTimePostProcessManager.getInstance().getProcessors()) {
            processor.beforePost(job);
        }
        boolean isSubJob = StringUtils.isNotEmpty((String)parentJobGuid);
        try {
            if (isSubJob) {
                scheduler = LocalSchedulerManager.getInstance().getSubScheduler();
                job.getParams().put("_SYS_PARENT_INSTANCE_ID", parentJobGuid);
                job.getParams().put("_SYS_ROOT_INSTANCE_ID", rootJobGuid);
                this.jobOperationManager.subRealTimeJobAdded(jobId, parentJobGuid, null, jobId, job.getTitle(), job.getUserGuid(), job.getUserName(), job.getLinkSource());
                context = new RealTimeLocalSubJobContextImpl(job);
            } else {
                String jobGroup = RealTimeJobFactory.getInstance().getRealTimeJobGroup(job);
                this.jobOperationManager.mainRealTimeJobAdded(jobId, jobId, job.getUserGuid(), job.getUserName(), job.getTitle(), JobType.REALTIME_JOB, RealTimeJobFactory.getRealTimeJobCategoryId(jobGroup), RealTimeJobFactory.getInstance().getRealTimeJobGroupTitle(jobGroup), false, job.getLinkSource(), job.getQueryField1(), job.getQueryField2());
                context = new RealTimeLocalJobContextImpl(job);
                scheduler = LocalSchedulerManager.getInstance().getScheduler();
            }
        }
        catch (Exception e) {
            throw new JobsException(e);
        }
        return scheduler.commit(job, context);
    }

    @Override
    public void cancel(String instanceGuid) throws JobsException {
        JobMonitorManager monitorManager = RealTimeJobManager.getInstance().getMonitorManager();
        JobInstanceBean instanceBean = monitorManager.getJobInstanceByGuid(instanceGuid);
        if (instanceBean == null) {
            this.logger.warn("\u4efb\u52a1[{}]\u5e76\u6ca1\u6709\u5728\u8fd0\u884c\u72b6\u6001\uff0c\u5ffd\u7565\u53d6\u6d88\u8bf7\u6c42", (Object)instanceGuid);
            return;
        }
        int level = instanceBean.getLevel();
        LocalScheduler scheduler = LocalSchedulerManager.getInstance().getScheduler(level);
        scheduler.cancel(instanceGuid);
    }

    @Override
    public void restartAll() throws JobsException {
        LocalSchedulerManager.getInstance().restartAll();
    }
}

