/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskPool
 *  com.jiuqi.nr.common.constant.AsynctaskPoolType
 */
package com.jiuqi.nr.examine.web.service;

import com.jiuqi.np.asynctask.AsyncTaskPool;
import com.jiuqi.nr.common.constant.AsynctaskPoolType;

public class ParamExamineAsyncTaskPool
implements AsyncTaskPool {
    public String getType() {
        return AsynctaskPoolType.ASYNCTASK_PARAMEXAMIE.getName();
    }

    public Integer getQueueSize() {
        return 0;
    }

    public Integer getParallelSize() {
        return 1;
    }
}

