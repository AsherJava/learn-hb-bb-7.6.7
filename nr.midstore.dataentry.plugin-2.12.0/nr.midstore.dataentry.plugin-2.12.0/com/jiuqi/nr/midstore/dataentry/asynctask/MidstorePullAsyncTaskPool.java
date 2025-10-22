/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskPool
 */
package com.jiuqi.nr.midstore.dataentry.asynctask;

import com.jiuqi.np.asynctask.AsyncTaskPool;
import org.springframework.stereotype.Component;

@Component
public class MidstorePullAsyncTaskPool
implements AsyncTaskPool {
    public String getType() {
        return "ASYNCTASK_MIDSTORE_PULL";
    }

    public Integer getQueueSize() {
        return 100;
    }

    public Integer getParallelSize() {
        return 10;
    }

    public Boolean isConfig() {
        return true;
    }
}

