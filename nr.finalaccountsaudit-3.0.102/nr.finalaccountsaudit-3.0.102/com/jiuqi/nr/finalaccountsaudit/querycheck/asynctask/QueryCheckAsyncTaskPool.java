/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskPool
 */
package com.jiuqi.nr.finalaccountsaudit.querycheck.asynctask;

import com.jiuqi.np.asynctask.AsyncTaskPool;
import com.jiuqi.nr.finalaccountsaudit.common.AsynctaskPoolType;

public class QueryCheckAsyncTaskPool
implements AsyncTaskPool {
    public String getType() {
        return AsynctaskPoolType.ASYNCTASK_QUERYCHECK.getName();
    }

    public Integer getQueueSize() {
        return 100;
    }

    public Integer getParallelSize() {
        return 10;
    }
}

