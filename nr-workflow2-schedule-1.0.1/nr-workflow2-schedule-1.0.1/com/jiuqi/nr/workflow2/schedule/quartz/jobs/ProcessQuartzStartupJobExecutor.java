/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.quartz.Job
 *  org.quartz.JobDataMap
 *  org.quartz.JobExecutionContext
 *  org.quartz.Trigger
 */
package com.jiuqi.nr.workflow2.schedule.quartz.jobs;

import java.util.HashMap;
import java.util.Map;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.Trigger;

public class ProcessQuartzStartupJobExecutor
implements Job {
    public static final String JOB_GROUP = "workflow2-startup-schedule-job-group";
    public static final String TRIGGER_GROUP = "workflow2-startup-schedule-trigger-group";
    public static final String NP_CONTEXT = "np-context";

    public void execute(JobExecutionContext context) {
    }

    private static Map<String, String> getParams(Trigger trigger) {
        HashMap<String, String> params = new HashMap<String, String>();
        JobDataMap jobDataMap = trigger.getJobDataMap();
        String taskKey = jobDataMap.getString("taskKey");
        String period = jobDataMap.getString("period");
        params.put("taskKey", taskKey);
        params.put("period", period);
        return params;
    }
}

