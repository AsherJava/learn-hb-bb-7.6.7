/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.core.jobs.extension;

import com.jiuqi.bi.core.jobs.extension.JobListenerContext;

public interface IJobListener {
    public void beforeJobDelete(JobListenerContext var1) throws Exception;

    public void afterJobDelete(JobListenerContext var1) throws Exception;
}

