/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.carryover.enums.CarryOverAsyncTaskPoolType
 *  com.jiuqi.np.asynctask.AsyncTaskPool
 */
package com.jiuqi.gcreport.offsetitem.init.carryover.asynctask;

import com.jiuqi.gcreport.carryover.enums.CarryOverAsyncTaskPoolType;
import com.jiuqi.np.asynctask.AsyncTaskPool;
import org.springframework.stereotype.Component;

@Component
public class GcCarryOverOffsetAsyncTaskPool
implements AsyncTaskPool {
    public String getType() {
        return CarryOverAsyncTaskPoolType.ASYNCTASK_OFFSET.getName();
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

