/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskPool
 */
package com.jiuqi.gcreport.conversion.asynctask;

import com.jiuqi.np.asynctask.AsyncTaskPool;
import org.springframework.stereotype.Component;

@Component
public class GcConversionAsyncTaskPool
implements AsyncTaskPool {
    public static final String TASK_POOL_TYPE = "GC_ASYNCTASK_CONVERSION";

    public String getType() {
        return TASK_POOL_TYPE;
    }

    public Integer getQueueSize() {
        return 100;
    }

    public Integer getParallelSize() {
        return 5;
    }

    public Boolean isConfig() {
        return true;
    }
}

