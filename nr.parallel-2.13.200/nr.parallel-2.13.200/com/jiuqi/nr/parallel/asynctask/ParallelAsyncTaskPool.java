/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskPool
 */
package com.jiuqi.nr.parallel.asynctask;

import com.jiuqi.np.asynctask.AsyncTaskPool;
import org.springframework.stereotype.Component;

@Component
public class ParallelAsyncTaskPool
implements AsyncTaskPool {
    public static final String TYPE = "ASYNCTASK_POOL_TYPE_PARALLEL";

    public String getType() {
        return TYPE;
    }

    public Integer getParallelSize() {
        return 20;
    }
}

