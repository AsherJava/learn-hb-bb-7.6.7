/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskPool
 */
package com.jiuqi.nr.enumcheck.asynctask;

import com.jiuqi.np.asynctask.AsyncTaskPool;

public class EnumDataCheckAsyncTaskPool
implements AsyncTaskPool {
    public String getType() {
        return "ASYNCTASK_ENUMCHECK";
    }

    public Integer getQueueSize() {
        return 100;
    }

    public Integer getParallelSize() {
        return 10;
    }
}

