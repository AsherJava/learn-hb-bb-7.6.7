/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.JobManager
 *  com.jiuqi.bi.core.jobs.model.JobModel
 *  com.jiuqi.np.logging.LogFactory
 *  com.jiuqi.np.logging.Logger
 *  com.jiuqi.nvwa.jobmanager.sysjob.AbstractSysJob
 *  com.jiuqi.nvwa.jobmanager.sysjob.SysJobManager
 *  com.jiuqi.nvwa.sf.models.ModuleInitiator
 *  javax.servlet.ServletContext
 */
package com.jiuqi.nr.io.record;

import com.jiuqi.bi.core.jobs.JobManager;
import com.jiuqi.bi.core.jobs.model.JobModel;
import com.jiuqi.np.logging.LogFactory;
import com.jiuqi.np.logging.Logger;
import com.jiuqi.nr.io.record.ImportHistoryPlanTask;
import com.jiuqi.nvwa.jobmanager.sysjob.AbstractSysJob;
import com.jiuqi.nvwa.jobmanager.sysjob.SysJobManager;
import com.jiuqi.nvwa.sf.models.ModuleInitiator;
import javax.servlet.ServletContext;

public class ImportHistoryCleanInit
implements ModuleInitiator {
    private final Logger logger = LogFactory.getLogger(ImportHistoryCleanInit.class);

    public void init(ServletContext context) throws Exception {
        try {
            SysJobManager sysJobManager = SysJobManager.getInstance();
            ImportHistoryPlanTask importHistoryPlanTask = new ImportHistoryPlanTask();
            sysJobManager.register((AbstractSysJob)importHistoryPlanTask, ImportHistoryPlanTask.getDefaultConf());
            JobManager jobManager = JobManager.getInstance((String)"com.jiuqi.bi.sysjob");
            JobModel job = jobManager.getJob("DELETE_IMPORT_RECORD");
            if (!job.isEnable()) {
                jobManager.jobEnable(importHistoryPlanTask.getId(), true);
            }
            this.logger.info("\u521b\u5efa\u201cNRDX\u5bfc\u5165\u5386\u53f2\u8bb0\u5f55\u6e05\u7406\u201d\u8ba1\u5212\u4efb\u52a1\u6210\u529f");
        }
        catch (Exception e) {
            this.logger.error("\u521b\u5efa\u201cNRDX\u5bfc\u5165\u5386\u53f2\u8bb0\u5f55\u6e05\u7406\u201d\u8ba1\u5212\u4efb\u52a1\u5f02\u5e38\uff1a{}", (Object)e.getMessage());
        }
    }

    public void initWhenStarted(ServletContext context) throws Exception {
    }
}

