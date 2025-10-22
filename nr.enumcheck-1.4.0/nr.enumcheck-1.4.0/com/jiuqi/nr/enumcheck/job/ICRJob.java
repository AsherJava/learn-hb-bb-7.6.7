/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.JobFactory
 *  com.jiuqi.bi.core.jobs.JobFactoryManager
 *  com.jiuqi.bi.core.jobs.JobManager
 *  com.jiuqi.bi.core.jobs.model.AbstractScheduleMethod
 *  com.jiuqi.bi.core.jobs.model.JobModel
 *  com.jiuqi.bi.core.jobs.model.schedulemethod.DailyScheduleMethod
 *  com.jiuqi.bi.core.jobs.model.schedulemethod.DayHour
 *  com.jiuqi.nr.integritycheck.job.ICRClearJobFactory
 *  com.jiuqi.nvwa.jobmanager.entity.PlanTaskGroupEO
 *  com.jiuqi.nvwa.jobmanager.service.PlanTaskGroupService
 *  com.jiuqi.nvwa.sf.models.ModuleInitiator
 *  javax.servlet.ServletContext
 */
package com.jiuqi.nr.enumcheck.job;

import com.jiuqi.bi.core.jobs.JobFactory;
import com.jiuqi.bi.core.jobs.JobFactoryManager;
import com.jiuqi.bi.core.jobs.JobManager;
import com.jiuqi.bi.core.jobs.model.AbstractScheduleMethod;
import com.jiuqi.bi.core.jobs.model.JobModel;
import com.jiuqi.bi.core.jobs.model.schedulemethod.DailyScheduleMethod;
import com.jiuqi.bi.core.jobs.model.schedulemethod.DayHour;
import com.jiuqi.nr.enumcheck.job.ECRClearJobFactory;
import com.jiuqi.nr.integritycheck.job.ICRClearJobFactory;
import com.jiuqi.nvwa.jobmanager.entity.PlanTaskGroupEO;
import com.jiuqi.nvwa.jobmanager.service.PlanTaskGroupService;
import com.jiuqi.nvwa.sf.models.ModuleInitiator;
import java.util.Calendar;
import javax.servlet.ServletContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ICRJob
implements ModuleInitiator {
    private static final Logger logger = LoggerFactory.getLogger(ICRJob.class);
    @Autowired
    private PlanTaskGroupService planTaskGroupService;

    public void init(ServletContext context) throws Exception {
        JobFactoryManager.getInstance().regJobFactory((JobFactory)new ICRClearJobFactory());
        this.createDelIcrJob();
        JobFactoryManager.getInstance().regJobFactory((JobFactory)new ECRClearJobFactory());
        this.createDelEcrJob();
    }

    public void initWhenStarted(ServletContext context) throws Exception {
    }

    private void createDelIcrJob() {
        JobManager jobManager = JobManager.getInstance((String)"icr_clear_job");
        JobModel jobModel = null;
        try {
            jobModel = jobManager.getJob("icr_clear_job");
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        try {
            if (jobModel == null) {
                jobModel = jobManager.createJob("\u8868\u5b8c\u6574\u6027\u68c0\u67e5\u7ed3\u679c\u8868\u6570\u636e\u6e05\u9664", "icr_clear_job");
                jobModel.setTitle("\u8868\u5b8c\u6574\u6027\u68c0\u67e5\u7ed3\u679c\u8868\u6570\u636e\u6e05\u9664");
                PlanTaskGroupEO rootGroup = this.planTaskGroupService.getRootGroup();
                jobModel.setFolderGuid(rootGroup.getId());
                DailyScheduleMethod dailyScheduleMethod = new DailyScheduleMethod();
                DayHour dayHour = new DayHour();
                dayHour.setHour(0);
                dayHour.setMinute(0);
                dailyScheduleMethod.setExecuteTimeInDay(dayHour);
                jobModel.setScheduleMethod((AbstractScheduleMethod)dailyScheduleMethod);
                jobModel.setUser("admin");
                Calendar calendar = Calendar.getInstance();
                calendar.set(2099, 1, 1);
                long endTime = calendar.getTimeInMillis();
                jobModel.setStartTime(System.currentTimeMillis());
                jobModel.setEndTime(endTime);
                jobManager.addJobModel(jobModel);
            }
            logger.info("\u6e05\u9664\u8868\u5b8c\u6574\u6027\u68c0\u67e5\u7ed3\u679c\u8ba1\u5212\u4efb\u52a1\u9ed8\u8ba4\u6ce8\u518c\u6210\u529f");
        }
        catch (Exception e) {
            logger.info("\u6e05\u9664\u8868\u5b8c\u6574\u6027\u68c0\u67e5\u7ed3\u679c\u8ba1\u5212\u4efb\u52a1\u9ed8\u8ba4\u6ce8\u518c\u5931\u8d25");
            logger.error(e.getMessage(), e);
        }
    }

    private void createDelEcrJob() {
        JobManager jobManager = JobManager.getInstance((String)"ecr_clear_job");
        JobModel jobModel = null;
        try {
            jobModel = jobManager.getJob("ecr_clear_job");
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        try {
            if (jobModel == null) {
                jobModel = jobManager.createJob("\u679a\u4e3e\u5b57\u5178\u68c0\u67e5\u7ed3\u679c\u8868\u6570\u636e\u6e05\u9664", "ecr_clear_job");
                jobModel.setTitle("\u679a\u4e3e\u5b57\u5178\u68c0\u67e5\u7ed3\u679c\u8868\u6570\u636e\u6e05\u9664");
                PlanTaskGroupEO rootGroup = this.planTaskGroupService.getRootGroup();
                jobModel.setFolderGuid(rootGroup.getId());
                DailyScheduleMethod dailyScheduleMethod = new DailyScheduleMethod();
                DayHour dayHour = new DayHour();
                dayHour.setHour(0);
                dayHour.setMinute(0);
                dailyScheduleMethod.setExecuteTimeInDay(dayHour);
                jobModel.setScheduleMethod((AbstractScheduleMethod)dailyScheduleMethod);
                jobModel.setUser("admin");
                Calendar calendar = Calendar.getInstance();
                calendar.set(2099, 1, 1);
                long endTime = calendar.getTimeInMillis();
                jobModel.setStartTime(System.currentTimeMillis());
                jobModel.setEndTime(endTime);
                jobManager.addJobModel(jobModel);
            }
            logger.info("\u6e05\u9664\u679a\u4e3e\u5b57\u5178\u68c0\u67e5\u7ed3\u679c\u8ba1\u5212\u4efb\u52a1\u9ed8\u8ba4\u6ce8\u518c\u6210\u529f");
        }
        catch (Exception e) {
            logger.info("\u6e05\u9664\u679a\u4e3e\u5b57\u5178\u68c0\u67e5\u7ed3\u679c\u8ba1\u5212\u4efb\u52a1\u9ed8\u8ba4\u6ce8\u518c\u5931\u8d25");
            logger.error(e.getMessage(), e);
        }
    }
}

