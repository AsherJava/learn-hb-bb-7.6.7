/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskPool
 */
package com.jiuqi.nr.summary.executor.deploy.async;

import com.jiuqi.np.asynctask.AsyncTaskPool;

public class SummaryDeployAsyncTaskPool
implements AsyncTaskPool {
    private static final int TASK_QUEUE_SIZE = 100;
    private static final int TASK_PARALLEL_SIZE = 30;

    public String getType() {
        return "ASYNCSUMMARY_DEPLOY";
    }

    public Integer getQueueSize() {
        return 100;
    }

    public Integer getParallelSize() {
        return 30;
    }
}

