/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskPool
 */
package com.jiuqi.nr.dataentry.asynctask;

import com.jiuqi.np.asynctask.AsyncTaskPool;
import com.jiuqi.nr.dataentry.internal.service.FmlMonitorAndDebugServiceImpl;
import org.springframework.stereotype.Component;

@Component
public class FmlMonitoringAsyncTaskPool
implements AsyncTaskPool {
    public String getType() {
        return FmlMonitorAndDebugServiceImpl.FmlMonitoringName;
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

