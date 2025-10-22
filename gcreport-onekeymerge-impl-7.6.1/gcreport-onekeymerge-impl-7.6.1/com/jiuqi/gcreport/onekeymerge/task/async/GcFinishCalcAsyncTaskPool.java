/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskPool
 */
package com.jiuqi.gcreport.onekeymerge.task.async;

import com.jiuqi.np.asynctask.AsyncTaskPool;
import org.springframework.stereotype.Component;

@Component
public class GcFinishCalcAsyncTaskPool
implements AsyncTaskPool {
    public static final String TASK_POOL_TYPE = "GC_ASYNCTASK_FINISHCALC";

    public String getType() {
        return TASK_POOL_TYPE;
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

