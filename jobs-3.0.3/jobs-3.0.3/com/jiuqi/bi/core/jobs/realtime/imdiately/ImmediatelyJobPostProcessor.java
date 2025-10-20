/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.core.jobs.realtime.imdiately;

import com.jiuqi.bi.core.jobs.realtime.imdiately.ImmediatelyJobExecuteThread;

public interface ImmediatelyJobPostProcessor {
    public void beforeExecute(ImmediatelyJobExecuteThread var1);

    public void afterExecute(ImmediatelyJobExecuteThread var1);
}

