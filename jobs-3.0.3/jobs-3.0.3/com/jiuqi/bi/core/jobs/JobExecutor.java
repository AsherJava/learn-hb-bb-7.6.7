/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.core.jobs;

import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.bi.core.jobs.JobExecutionException;

public abstract class JobExecutor {
    public static final int UNCOUNTABLE_SUBJOB_COUNT = -1;
    public static final int DEFAULT_SUBJOB_CONCURRENT_NUM = 5;

    public abstract void execute(JobContext var1) throws JobExecutionException;

    public boolean hasSubJob() {
        return this.getSubJobCount() != 0;
    }

    public int subJobConcurrentNum() {
        return 5;
    }

    public int getSubJobCount() {
        return 0;
    }
}

