/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.JobExecutor
 *  com.jiuqi.bi.core.jobs.JobFactory
 *  com.jiuqi.bi.core.jobs.JobsException
 */
package com.jiuqi.nr.workflow2.schedule.bi.jobs;

import com.jiuqi.bi.core.jobs.JobExecutor;
import com.jiuqi.bi.core.jobs.JobFactory;
import com.jiuqi.bi.core.jobs.JobsException;
import com.jiuqi.nr.workflow2.schedule.bi.jobs.executor.CheckInstancesJobExecutor;
import org.springframework.stereotype.Component;

@Component
public class ProcessCheckStartupJobFactory
extends JobFactory {
    public static final String CATEGORY_ID = "NR-WORKFLOW2-SCHEDULE-FOR#startup_restitution_check";
    public static final String CATEGORY_TITLE = "\u6d41\u7a0b2.0-\u8ba1\u5212\u4efb\u52a1-\u8865\u507f\u68c0\u67e5";
    public static final String JOB_GUID = "CEC163BF-B8AC-D719-5884-64E0C8286307";
    public static final String JOB_TITLE = "\u6d41\u7a0b2.0-\u542f\u52a8\u68c0\u67e5\u4efb\u52a1";

    public String getJobCategoryId() {
        return CATEGORY_ID;
    }

    public String getJobCategoryTitle() {
        return CATEGORY_TITLE;
    }

    public boolean configable() {
        return false;
    }

    public JobExecutor createJobExecutor(String jobId) throws JobsException {
        return new CheckInstancesJobExecutor();
    }
}

