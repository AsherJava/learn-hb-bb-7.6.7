/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.JobExecutor
 *  com.jiuqi.bi.core.jobs.JobFactory
 *  com.jiuqi.bi.core.jobs.JobsException
 *  com.jiuqi.nvwa.jobmanager.config.IJobAdvanceConfiguration
 */
package com.jiuqi.nr.datascheme.internal.job;

import com.jiuqi.bi.core.jobs.JobExecutor;
import com.jiuqi.bi.core.jobs.JobFactory;
import com.jiuqi.bi.core.jobs.JobsException;
import com.jiuqi.nr.datascheme.internal.job.CalZbJobExecutor;
import com.jiuqi.nvwa.jobmanager.config.IJobAdvanceConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CalZbJobFactory
extends JobFactory
implements IJobAdvanceConfiguration {
    public static final String JOB_TITLE = "\u5206\u6790\u6307\u6807\u5904\u7406";
    public static final String JOB_ID = "analyse-zb-job";
    @Autowired
    private CalZbJobExecutor jobExecutor;

    public JobExecutor createJobExecutor(String s) throws JobsException {
        return this.jobExecutor;
    }

    public String getJobCategoryId() {
        return JOB_ID;
    }

    public String getJobCategoryTitle() {
        return JOB_TITLE;
    }

    public String getModelName() {
        return JOB_ID;
    }
}

