/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.JobManager
 *  com.jiuqi.bi.core.jobs.model.JobModel
 *  com.jiuqi.nvwa.jobmanager.sysjob.AbstractSysJob
 *  com.jiuqi.nvwa.jobmanager.sysjob.SysJobManager
 */
package com.jiuqi.nr.dataentity_ext.init;

import com.jiuqi.bi.core.jobs.JobManager;
import com.jiuqi.bi.core.jobs.model.JobModel;
import com.jiuqi.nr.dataentity_ext.internal.TmpTableCleanJob;
import com.jiuqi.nvwa.jobmanager.sysjob.AbstractSysJob;
import com.jiuqi.nvwa.jobmanager.sysjob.SysJobManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Lazy(value=false)
@Component
public class ModuleInit {
    private static final Logger log = LoggerFactory.getLogger(ModuleInit.class);

    public void init() {
        try {
            SysJobManager sysJobManager = SysJobManager.getInstance();
            TmpTableCleanJob task = new TmpTableCleanJob();
            sysJobManager.register((AbstractSysJob)task, null);
            JobManager jobManager = JobManager.getInstance((String)"com.jiuqi.bi.sysjob");
            JobModel job = jobManager.getJob(task.getId());
            if (!job.isEnable()) {
                jobManager.jobEnable(task.getId(), true);
            }
            log.info("\u521b\u5efa\u201cNR\u81ea\u5b9a\u4e49\u5b9e\u4f53\u6570\u636e\u6269\u5c55\u4e34\u65f6\u8868\u5b9a\u671f\u6e05\u7406\u201d\u8ba1\u5212\u4efb\u52a1\u6210\u529f");
        }
        catch (Exception e) {
            log.error("\u521b\u5efa\u201cNR\u81ea\u5b9a\u4e49\u5b9e\u4f53\u6570\u636e\u6269\u5c55\u4e34\u65f6\u8868\u5b9a\u671f\u6e05\u7406\u201d\u8ba1\u5212\u4efb\u52a1\u5f02\u5e38\uff1a{}", (Object)e.getMessage());
        }
    }
}

