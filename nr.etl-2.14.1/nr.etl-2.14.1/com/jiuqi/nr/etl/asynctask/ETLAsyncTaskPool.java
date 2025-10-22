/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskPool
 *  com.jiuqi.nr.common.constant.AsynctaskPoolType
 */
package com.jiuqi.nr.etl.asynctask;

import com.jiuqi.np.asynctask.AsyncTaskPool;
import com.jiuqi.nr.common.constant.AsynctaskPoolType;
import org.springframework.stereotype.Component;

@Component
public class ETLAsyncTaskPool
implements AsyncTaskPool {
    public String getType() {
        return AsynctaskPoolType.ASYNCTASK_ETL.getName();
    }

    public Integer getQueueSize() {
        return 100;
    }

    public Integer getParallelSize() {
        return 10;
    }
}

