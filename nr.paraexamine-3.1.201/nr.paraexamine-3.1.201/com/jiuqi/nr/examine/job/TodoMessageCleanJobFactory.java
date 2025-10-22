/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.JobExecutor
 *  com.jiuqi.bi.core.jobs.JobFactory
 *  com.jiuqi.bi.core.jobs.JobsException
 *  com.jiuqi.np.definition.common.SpringBeanProvider
 */
package com.jiuqi.nr.examine.job;

import com.jiuqi.bi.core.jobs.JobExecutor;
import com.jiuqi.bi.core.jobs.JobFactory;
import com.jiuqi.bi.core.jobs.JobsException;
import com.jiuqi.np.definition.common.SpringBeanProvider;
import com.jiuqi.nr.examine.job.TodoMessageCleanJobExecutor;
import org.springframework.stereotype.Component;

@Component
public class TodoMessageCleanJobFactory
extends JobFactory {
    private static final String JOB_ID = "61c63e99-0c70-4428-a944-2315ad630871";
    private static final String JOB_TITLE = "\u5f85\u529e\u6d88\u606f\u76f8\u5173\u8868\u6570\u636e\u6e05\u9664";

    public JobExecutor createJobExecutor(String s) throws JobsException {
        return (JobExecutor)SpringBeanProvider.getBean(TodoMessageCleanJobExecutor.class);
    }

    public String getJobCategoryId() {
        return JOB_ID;
    }

    public String getJobCategoryTitle() {
        return JOB_TITLE;
    }
}

