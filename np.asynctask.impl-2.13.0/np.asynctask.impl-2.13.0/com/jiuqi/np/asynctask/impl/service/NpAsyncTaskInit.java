/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.JobManager
 *  com.jiuqi.bi.core.jobs.manager.JobStorageManager
 *  com.jiuqi.bi.core.jobs.model.IScheduleMethod
 *  com.jiuqi.bi.core.jobs.model.JobModel
 *  com.jiuqi.nvwa.jobmanager.sysjob.AbstractSysJob
 *  com.jiuqi.nvwa.jobmanager.sysjob.SysJobManager
 *  com.jiuqi.nvwa.sf.models.ModuleInitiator
 *  javax.servlet.ServletContext
 */
package com.jiuqi.np.asynctask.impl.service;

import com.jiuqi.bi.core.jobs.JobManager;
import com.jiuqi.bi.core.jobs.manager.JobStorageManager;
import com.jiuqi.bi.core.jobs.model.IScheduleMethod;
import com.jiuqi.bi.core.jobs.model.JobModel;
import com.jiuqi.np.asynctask.impl.service.DeleteCompleteFlagPlanTaskJob;
import com.jiuqi.nvwa.jobmanager.sysjob.AbstractSysJob;
import com.jiuqi.nvwa.jobmanager.sysjob.SysJobManager;
import com.jiuqi.nvwa.sf.models.ModuleInitiator;
import javax.servlet.ServletContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NpAsyncTaskInit
implements ModuleInitiator {
    private static final Logger logger = LoggerFactory.getLogger(NpAsyncTaskInit.class);

    public void init(ServletContext context) throws Exception {
        JobModel realTimeJobModel = null;
        try {
            realTimeJobModel = new JobStorageManager().getJob("NVWA_UNION_CLEAN_SYS_JOB");
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        if (realTimeJobModel != null) {
            try {
                SysJobManager sysJobManager = SysJobManager.getInstance();
                sysJobManager.register((AbstractSysJob)new DeleteCompleteFlagPlanTaskJob(), null);
                JobManager jobManager = JobManager.getInstance((String)"com.jiuqi.bi.sysjob");
                AbstractSysJob deleteCompleteFlagPlanTaskJob = sysJobManager.getSysJob("DELETE_COMPLETEFLAG_JOB");
                jobManager.updateJobScheduleConf(deleteCompleteFlagPlanTaskJob.getId(), realTimeJobModel.getStartTime(), realTimeJobModel.getEndTime(), (IScheduleMethod)realTimeJobModel.getScheduleMethod());
                jobManager.jobEnable(deleteCompleteFlagPlanTaskJob.getId(), true);
                logger.info("\u521b\u5efa\u201c\u5f02\u6b65\u4efb\u52a1\u5df2\u8bfb\u6807\u8bb0\u6e05\u7406\u4efb\u52a1\u201d\u8ba1\u5212\u4efb\u52a1\u6210\u529f");
            }
            catch (Exception e) {
                logger.error("\u521b\u5efa\u201c\u5f02\u6b65\u4efb\u52a1\u5df2\u8bfb\u6807\u8bb0\u6e05\u7406\u4efb\u52a1\u201d\u8ba1\u5212\u4efb\u52a1\u5f02\u5e38\uff1a{}", (Object)e.getMessage());
            }
        } else {
            logger.error("\u521b\u5efa\u201c\u5f02\u6b65\u4efb\u52a1\u5df2\u8bfb\u6807\u8bb0\u6e05\u7406\u4efb\u52a1\u201d\u8ba1\u5212\u4efb\u52a1\u5931\u8d25");
        }
    }

    public void initWhenStarted(ServletContext context) throws Exception {
    }
}

