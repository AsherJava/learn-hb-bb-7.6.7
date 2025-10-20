/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.planpublish.dao.TaskPlanPublishDao
 *  org.quartz.Job
 *  org.quartz.JobDataMap
 *  org.quartz.JobExecutionContext
 *  org.quartz.JobExecutionException
 */
package com.jiuqi.nr.designer.planpublish.job;

import com.jiuqi.nr.definition.planpublish.dao.TaskPlanPublishDao;
import com.jiuqi.nr.designer.planpublish.service.SendMessageService;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

public class PlanPublishSendMessageJob
implements Job {
    @Autowired
    private TaskPlanPublishDao innerTaskPlanPublishDao;
    @Autowired
    private SendMessageService sendMessageService;

    public void execute(JobExecutionContext context) throws JobExecutionException {
        try {
            JobDataMap jobDataMap = context.getMergedJobDataMap();
            String taskKey = (String)jobDataMap.get("taskKey");
            String taskPlanPublishKey = this.innerTaskPlanPublishDao.queryWorkPlanKey(taskKey);
            String planKey = (String)jobDataMap.get("planKey");
            if (taskPlanPublishKey != null && taskPlanPublishKey.equals(planKey)) {
                String message = (String)jobDataMap.get("message");
                this.sendMessageService.sendMessageToUser(message, taskKey);
            }
        }
        catch (Exception e) {
            throw new JobExecutionException(e.getMessage());
        }
    }
}

