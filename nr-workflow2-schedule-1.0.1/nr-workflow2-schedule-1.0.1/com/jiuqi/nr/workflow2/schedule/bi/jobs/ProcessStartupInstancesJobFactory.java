/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.JobExecutor
 *  com.jiuqi.bi.core.jobs.JobFactory
 *  com.jiuqi.bi.core.jobs.JobFactoryCustomPrivilege
 *  com.jiuqi.bi.core.jobs.JobsException
 */
package com.jiuqi.nr.workflow2.schedule.bi.jobs;

import com.jiuqi.bi.core.jobs.JobExecutor;
import com.jiuqi.bi.core.jobs.JobFactory;
import com.jiuqi.bi.core.jobs.JobFactoryCustomPrivilege;
import com.jiuqi.bi.core.jobs.JobsException;
import com.jiuqi.nr.workflow2.schedule.bi.jobs.executor.StartInstancesJobExecutor;
import org.springframework.stereotype.Component;

@Component
public class ProcessStartupInstancesJobFactory
extends JobFactory {
    public static final String CATEGORY_ID = "NR-WORKFLOW2-SCHEDULE-FOR#start_instances";
    public static final String CATEGORY_TITLE = "\u6d41\u7a0b2.0-\u8ba1\u5212\u4efb\u52a1-\u542f\u52a8\u6d41\u7a0b\u5b9e\u4f8b";

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
        return new StartInstancesJobExecutor();
    }

    public JobFactoryCustomPrivilege getJobFactoryCustomPrivilege() {
        return new JobFactoryCustomPrivilege(false, false, true);
    }
}

