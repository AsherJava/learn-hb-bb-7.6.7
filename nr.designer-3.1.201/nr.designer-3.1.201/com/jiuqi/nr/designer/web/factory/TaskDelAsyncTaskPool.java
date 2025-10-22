/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskPool
 *  com.jiuqi.nr.common.constant.AsynctaskPoolType
 */
package com.jiuqi.nr.designer.web.factory;

import com.jiuqi.np.asynctask.AsyncTaskPool;
import com.jiuqi.nr.common.constant.AsynctaskPoolType;

public class TaskDelAsyncTaskPool
implements AsyncTaskPool {
    public String getType() {
        return AsynctaskPoolType.ASYNCTASK_DELETETASK.getName();
    }

    public Integer getQueueSize() {
        return 100;
    }

    public Integer getParallelSize() {
        return 10;
    }
}

