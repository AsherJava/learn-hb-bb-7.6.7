/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.init.IBdeModuleItemInitiator
 *  com.jiuqi.bi.core.jobs.JobManager
 *  com.jiuqi.bi.core.jobs.JobsException
 *  com.jiuqi.bi.core.jobs.manager.JobStorageManager
 *  com.jiuqi.bi.core.jobs.model.IScheduleMethod
 *  com.jiuqi.bi.core.jobs.model.JobModel
 *  com.jiuqi.bi.core.jobs.model.schedulemethod.DailyScheduleMethod
 *  com.jiuqi.bi.core.jobs.model.schedulemethod.DayHour
 *  com.jiuqi.nvwa.jobmanager.sysjob.AbstractSysJob
 *  com.jiuqi.nvwa.jobmanager.sysjob.SysJobManager
 *  javax.servlet.ServletContext
 */
package com.jiuqi.bde.fetch.impl.job;

import com.jiuqi.bde.common.init.IBdeModuleItemInitiator;
import com.jiuqi.bde.fetch.impl.job.CleanResultTableJob;
import com.jiuqi.bi.core.jobs.JobManager;
import com.jiuqi.bi.core.jobs.JobsException;
import com.jiuqi.bi.core.jobs.manager.JobStorageManager;
import com.jiuqi.bi.core.jobs.model.IScheduleMethod;
import com.jiuqi.bi.core.jobs.model.JobModel;
import com.jiuqi.bi.core.jobs.model.schedulemethod.DailyScheduleMethod;
import com.jiuqi.bi.core.jobs.model.schedulemethod.DayHour;
import com.jiuqi.nvwa.jobmanager.sysjob.AbstractSysJob;
import com.jiuqi.nvwa.jobmanager.sysjob.SysJobManager;
import javax.servlet.ServletContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CleanResultTableItemInitiator
implements IBdeModuleItemInitiator {
    private static Logger logger = LoggerFactory.getLogger(CleanResultTableItemInitiator.class);

    public String getName() {
        return "\u521d\u59cb\u6e05\u7406\u7ed3\u679c\u8868\u8ba1\u5212\u4efb\u52a1";
    }

    public void init(ServletContext context) throws Exception {
    }

    private void registerJob() throws Exception {
        JobManager jobManager = JobManager.getInstance((String)"com.jiuqi.bi.sysjob");
        try {
            JobModel jobModel = new JobStorageManager().getJob("com.jiuqi.bde.result.clean");
            if (jobModel != null) {
                CleanResultTableJob job = new CleanResultTableJob();
                SysJobManager.getInstance().register((AbstractSysJob)job, null);
                logger.info("\u521d\u59cb\u6e05\u7406\u7ed3\u679c\u8868\u8ba1\u5212\u4efb\u52a1\u5df2\u7ecf\u5b58\u5728\uff0c\u8df3\u8fc7\u6ce8\u518c");
                return;
            }
        }
        catch (JobsException e) {
            logger.info(String.format("\u521d\u59cb\u6e05\u7406\u7ed3\u679c\u8868\u8ba1\u5212\u4efb\u52a1%1$s\uff0c\u8df3\u8fc7\u6ce8\u518c", e.getMessage()));
            return;
        }
        CleanResultTableJob job = new CleanResultTableJob();
        SysJobManager.getInstance().register((AbstractSysJob)job, null);
        jobManager.jobEnable("com.jiuqi.bde.result.clean", true);
        long endTime = 7226582400000L;
        DailyScheduleMethod scheduleMethod = new DailyScheduleMethod();
        DayHour dayHour = new DayHour();
        dayHour.setHour(23);
        dayHour.setMinute(30);
        scheduleMethod.setExecuteTimeInDay(dayHour);
        jobManager.updateJobScheduleConf("com.jiuqi.bde.result.clean", 0L, endTime, (IScheduleMethod)scheduleMethod);
    }

    public void initWhenStarted(ServletContext context) throws Exception {
        this.registerJob();
    }

    public int getOrder() {
        return Integer.MAX_VALUE;
    }
}

