/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.core.jobs.base;

import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.bi.core.jobs.JobExecutionException;

public interface IJobExecuteAble<T extends JobContext> {
    public String getTitle();

    public void execute(T var1) throws JobExecutionException;
}

