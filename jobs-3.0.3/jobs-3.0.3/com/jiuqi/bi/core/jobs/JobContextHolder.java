/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.core.jobs;

import com.jiuqi.bi.core.jobs.JobContext;

public class JobContextHolder {
    private static final ThreadLocal<JobContext> jobContext = new ThreadLocal();

    public static JobContext getJobContext() {
        return jobContext.get();
    }

    public static void setJobContext(JobContext context) {
        jobContext.set(context);
    }

    public static void clear() {
        jobContext.remove();
    }
}

