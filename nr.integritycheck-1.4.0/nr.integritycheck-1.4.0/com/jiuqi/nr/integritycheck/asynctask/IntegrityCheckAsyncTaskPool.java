/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskPool
 */
package com.jiuqi.nr.integritycheck.asynctask;

import com.jiuqi.np.asynctask.AsyncTaskPool;
import org.springframework.stereotype.Component;

@Component
public class IntegrityCheckAsyncTaskPool
implements AsyncTaskPool {
    public String getType() {
        return "ASYNCTASK_TABLE_INTEGRITYCHECK";
    }

    public Integer getQueueSize() {
        return 100;
    }

    public Integer getParallelSize() {
        return 10;
    }
}

