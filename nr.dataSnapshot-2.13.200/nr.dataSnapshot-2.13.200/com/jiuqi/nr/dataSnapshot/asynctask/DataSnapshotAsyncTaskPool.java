/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskPool
 */
package com.jiuqi.nr.dataSnapshot.asynctask;

import com.jiuqi.np.asynctask.AsyncTaskPool;
import org.springframework.stereotype.Component;

@Component
public class DataSnapshotAsyncTaskPool
implements AsyncTaskPool {
    public String getType() {
        return "ASYNCTASK_DATASNAPSHOT";
    }

    public Integer getQueueSize() {
        return 100;
    }

    public Integer getParallelSize() {
        return 30;
    }

    public Boolean isConfig() {
        return true;
    }
}

