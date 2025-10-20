/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.core.jobs.combination;

import com.jiuqi.bi.core.jobs.JobExecutor;
import com.jiuqi.bi.core.jobs.JobFactory;
import com.jiuqi.bi.core.jobs.JobsException;

public class CombinationJobFactory
extends JobFactory {
    public static final String TYPE = "combination_job";
    public static final String TITLE = "\u7ec4\u5408\u4efb\u52a1";

    @Override
    public String getJobCategoryId() {
        return TYPE;
    }

    @Override
    public String getJobCategoryTitle() {
        return TITLE;
    }

    @Override
    public JobExecutor createJobExecutor(String jobId) throws JobsException {
        return null;
    }
}

