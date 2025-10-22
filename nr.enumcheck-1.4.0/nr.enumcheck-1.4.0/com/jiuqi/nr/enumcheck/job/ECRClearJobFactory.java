/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.JobExecutor
 *  com.jiuqi.bi.core.jobs.JobFactory
 *  com.jiuqi.bi.core.jobs.JobsException
 */
package com.jiuqi.nr.enumcheck.job;

import com.jiuqi.bi.core.jobs.JobExecutor;
import com.jiuqi.bi.core.jobs.JobFactory;
import com.jiuqi.bi.core.jobs.JobsException;
import com.jiuqi.nr.enumcheck.job.ECRClearJobExecutor;

public class ECRClearJobFactory
extends JobFactory {
    public static final String ID = "ecr_clear_job";
    public static final String TITLE = "\u679a\u4e3e\u5b57\u5178\u68c0\u67e5\u7ed3\u679c\u8868\u6570\u636e\u6e05\u9664";

    public JobExecutor createJobExecutor(String s) throws JobsException {
        return new ECRClearJobExecutor();
    }

    public String getJobCategoryId() {
        return ID;
    }

    public String getJobCategoryTitle() {
        return TITLE;
    }

    public String getModelName() {
        return "job-cleanCheckRes";
    }
}

