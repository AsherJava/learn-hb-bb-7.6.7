/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.JobManager
 *  com.jiuqi.bi.core.jobs.JobsException
 *  com.jiuqi.bi.core.jobs.manager.JobStorageManager
 *  com.jiuqi.bi.core.jobs.model.IScheduleMethod
 *  com.jiuqi.bi.core.jobs.model.JobModel
 *  com.jiuqi.bi.core.jobs.model.schedulemethod.CronScheduleMethod
 *  com.jiuqi.nvwa.jobmanager.sysjob.AbstractSysJob
 *  com.jiuqi.nvwa.jobmanager.sysjob.SysJobManager
 *  com.jiuqi.nvwa.sf.models.ModuleInitiator
 *  javax.servlet.ServletContext
 */
package com.jiuqi.dc.integration.execute.impl.basedatasync.job;

import com.jiuqi.bi.core.jobs.JobManager;
import com.jiuqi.bi.core.jobs.JobsException;
import com.jiuqi.bi.core.jobs.manager.JobStorageManager;
import com.jiuqi.bi.core.jobs.model.IScheduleMethod;
import com.jiuqi.bi.core.jobs.model.JobModel;
import com.jiuqi.bi.core.jobs.model.schedulemethod.CronScheduleMethod;
import com.jiuqi.dc.integration.execute.impl.basedatasync.job.DcBaseDataSyncJob;
import com.jiuqi.nvwa.jobmanager.sysjob.AbstractSysJob;
import com.jiuqi.nvwa.jobmanager.sysjob.SysJobManager;
import com.jiuqi.nvwa.sf.models.ModuleInitiator;
import javax.servlet.ServletContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class DcBaseDataSyncJobInitiator
implements ModuleInitiator {
    private static Logger logger = LoggerFactory.getLogger(DcBaseDataSyncJobInitiator.class);

    public void init(ServletContext context) throws Exception {
    }

    private void registerJob() throws Exception {
        JobManager jobManager = JobManager.getInstance((String)"com.jiuqi.bi.sysjob");
        try {
            JobModel jobModel = new JobStorageManager().getJob("com.jiuqi.dc.basedatasync");
            if (jobModel != null) {
                DcBaseDataSyncJob job = new DcBaseDataSyncJob();
                SysJobManager.getInstance().register((AbstractSysJob)job, null);
                logger.info("\u3010\u5408\u5e76\u591a\u7ef4\u3011\u57fa\u7840\u6570\u636e\u540c\u6b65\u8ba1\u5212\u4efb\u52a1\u5df2\u7ecf\u5b58\u5728\uff0c\u8df3\u8fc7\u6ce8\u518c");
                return;
            }
        }
        catch (JobsException e) {
            logger.info("\u3010\u5408\u5e76\u591a\u7ef4\u3011\u57fa\u7840\u6570\u636e\u540c\u6b65\u8ba1\u5212\u4efb\u52a1\uff0c\u8df3\u8fc7\u6ce8\u518c", e);
            return;
        }
        DcBaseDataSyncJob job = new DcBaseDataSyncJob();
        SysJobManager.getInstance().register((AbstractSysJob)job, null);
        jobManager.jobEnable("com.jiuqi.dc.basedatasync", true);
        long endTime = 7226582400000L;
        CronScheduleMethod cronScheduleMethod = new CronScheduleMethod();
        cronScheduleMethod.setCronExpression("0,30 * * * *");
        jobManager.updateJobScheduleConf("com.jiuqi.dc.basedatasync", 0L, endTime, (IScheduleMethod)cronScheduleMethod);
    }

    public void initWhenStarted(ServletContext context) throws Exception {
        this.registerJob();
    }
}

