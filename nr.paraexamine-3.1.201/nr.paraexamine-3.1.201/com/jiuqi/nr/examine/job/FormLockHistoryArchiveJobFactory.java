/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.JobExecutor
 *  com.jiuqi.bi.core.jobs.JobFactory
 *  com.jiuqi.bi.core.jobs.JobsException
 *  com.jiuqi.np.definition.common.SpringBeanProvider
 *  com.jiuqi.nvwa.jobmanager.config.IJobAdvanceConfiguration
 */
package com.jiuqi.nr.examine.job;

import com.jiuqi.bi.core.jobs.JobExecutor;
import com.jiuqi.bi.core.jobs.JobFactory;
import com.jiuqi.bi.core.jobs.JobsException;
import com.jiuqi.np.definition.common.SpringBeanProvider;
import com.jiuqi.nr.examine.job.FormLockHistoryArchiveJobExecutor;
import com.jiuqi.nvwa.jobmanager.config.IJobAdvanceConfiguration;
import org.springframework.stereotype.Component;

@Component
public class FormLockHistoryArchiveJobFactory
extends JobFactory
implements IJobAdvanceConfiguration {
    private static final String JOB_ID = "61c63e99-0c70-4428-a944-2315ad630873";
    private static final String JOB_TITLE = "\u9501\u5b9a\u5386\u53f2\u72b6\u6001\u8868\u6570\u636e\u5f52\u6863";
    private static final String JOB_CONFIG_PAGE = "job-history-archive";

    public JobExecutor createJobExecutor(String s) throws JobsException {
        return (JobExecutor)SpringBeanProvider.getBean(FormLockHistoryArchiveJobExecutor.class);
    }

    public String getJobCategoryId() {
        return JOB_ID;
    }

    public String getJobCategoryTitle() {
        return JOB_TITLE;
    }

    public String getModelName() {
        return JOB_CONFIG_PAGE;
    }
}

