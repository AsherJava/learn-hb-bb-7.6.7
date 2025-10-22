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
 *  com.jiuqi.bi.oss.Bucket
 *  com.jiuqi.bi.oss.BucketService
 *  com.jiuqi.bi.oss.ObjectStorageManager
 *  com.jiuqi.nvwa.jobmanager.entity.PlanTaskGroupEO
 *  com.jiuqi.nvwa.jobmanager.service.PlanTaskGroupService
 *  com.jiuqi.nvwa.sf.models.ModuleInitiator
 *  javax.servlet.ServletContext
 */
package com.jiuqi.nr.attachment.init;

import com.jiuqi.bi.core.jobs.JobFactory;
import com.jiuqi.bi.core.jobs.JobFactoryManager;
import com.jiuqi.bi.core.jobs.JobManager;
import com.jiuqi.bi.core.jobs.model.AbstractScheduleMethod;
import com.jiuqi.bi.core.jobs.model.JobModel;
import com.jiuqi.bi.core.jobs.model.schedulemethod.DailyScheduleMethod;
import com.jiuqi.bi.core.jobs.model.schedulemethod.DayHour;
import com.jiuqi.bi.oss.Bucket;
import com.jiuqi.bi.oss.BucketService;
import com.jiuqi.bi.oss.ObjectStorageManager;
import com.jiuqi.nr.attachment.job.DeleteFileFactory;
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
public class UpgradeOSSBucketLinkInitiator
implements ModuleInitiator {
    private static final Logger logger = LoggerFactory.getLogger(UpgradeOSSBucketLinkInitiator.class);
    @Autowired
    private PlanTaskGroupService planTaskGroupService;

    public void init(ServletContext context) throws Exception {
        JobFactoryManager.getInstance().regJobFactory((JobFactory)new DeleteFileFactory());
        this.createDeleteFileJob();
    }

    public void initWhenStarted(ServletContext context) throws Exception {
        Bucket bucket;
        BucketService bucketService = ObjectStorageManager.getInstance().createBucketService();
        boolean exist = bucketService.existBucket("JTABLEAREA");
        if (exist && !(bucket = bucketService.getBucket("JTABLEAREA")).isLinkWhenExist()) {
            ObjectStorageManager.getInstance().makeObjectLinkEnable("JTABLEAREA");
        }
        bucketService.close();
    }

    private void createDeleteFileJob() {
        JobManager jobManager = JobManager.getInstance((String)"attachment_delete_file_job");
        JobModel jobModel = null;
        try {
            jobModel = jobManager.getJob("attachment_delete_file_job");
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        try {
            if (jobModel == null) {
                jobModel = jobManager.createJob("\u6e05\u9664\u88ab\u6807\u8bb0\u5220\u9664\u7684\u9644\u4ef6", "attachment_delete_file_job");
                jobModel.setTitle("\u6e05\u9664\u88ab\u6807\u8bb0\u5220\u9664\u7684\u9644\u4ef6");
                PlanTaskGroupEO rootGroup = this.planTaskGroupService.getRootGroup();
                jobModel.setFolderGuid(rootGroup.getId());
                DailyScheduleMethod dailyScheduleMethod = new DailyScheduleMethod();
                DayHour dayHour = new DayHour();
                dayHour.setHour(2);
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
            logger.info("\u6e05\u9664\u88ab\u6807\u8bb0\u5220\u9664\u7684\u9644\u4ef6\u8ba1\u5212\u4efb\u52a1\u9ed8\u8ba4\u6ce8\u518c\u6210\u529f");
        }
        catch (Exception e) {
            logger.info("\u6e05\u9664\u88ab\u6807\u8bb0\u5220\u9664\u7684\u9644\u4ef6\u8ba1\u5212\u4efb\u52a1\u9ed8\u8ba4\u6ce8\u518c\u5931\u8d25");
            logger.error(e.getMessage(), e);
        }
    }
}

