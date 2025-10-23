/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.JobManager
 *  com.jiuqi.bi.core.jobs.model.JobModel
 *  com.jiuqi.nvwa.jobmanager.sysjob.AbstractSysJob
 *  com.jiuqi.nvwa.jobmanager.sysjob.SysJobManager
 *  com.jiuqi.nvwa.sf.models.ModuleInitiator
 *  javax.servlet.ServletContext
 */
package com.jiuqi.nr.multcheck2.config;

import com.jiuqi.bi.core.jobs.JobManager;
import com.jiuqi.bi.core.jobs.model.JobModel;
import com.jiuqi.nr.multcheck2.job.MultcheckCleanJob;
import com.jiuqi.nvwa.jobmanager.sysjob.AbstractSysJob;
import com.jiuqi.nvwa.jobmanager.sysjob.SysJobManager;
import com.jiuqi.nvwa.sf.models.ModuleInitiator;
import javax.servlet.ServletContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MultcheckInitiator
implements ModuleInitiator {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public void init(ServletContext context) throws Exception {
        try {
            JobModel job = null;
            JobManager jobManager = null;
            try {
                jobManager = JobManager.getInstance((String)"com.jiuqi.bi.sysjob");
                job = jobManager.getJob("NR_MULT_CHECK_CLEAN_RES_JOB");
            }
            catch (Exception e) {
                this.logger.error(e.getMessage(), e);
            }
            if (job == null) {
                MultcheckCleanJob cleanJob = new MultcheckCleanJob();
                SysJobManager.getInstance().register((AbstractSysJob)cleanJob, "{}");
                jobManager.jobEnable(cleanJob.getId(), true);
            } else {
                MultcheckCleanJob cleanJob = new MultcheckCleanJob();
                SysJobManager.getInstance().register((AbstractSysJob)cleanJob, "{}");
            }
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), e);
        }
    }

    public void initWhenStarted(ServletContext context) throws Exception {
    }
}

