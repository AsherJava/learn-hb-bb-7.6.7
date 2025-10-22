/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskPool
 *  com.jiuqi.nr.common.constant.AsynctaskPoolType
 */
package com.jiuqi.nr.dataentry.asynctask;

import com.jiuqi.np.asynctask.AsyncTaskPool;
import com.jiuqi.nr.common.constant.AsynctaskPoolType;
import org.springframework.stereotype.Component;

@Component
public class BatchClearAsyncTaskPool
implements AsyncTaskPool {
    public String getType() {
        return AsynctaskPoolType.ASYNCTASK_BATCHCLEAR.getName();
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

