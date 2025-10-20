/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.nr.impl.constant.GcAsyncTaskPoolType
 *  com.jiuqi.np.asynctask.AsyncTaskPool
 */
package com.jiuqi.gcreport.inputdata.dataentryext.inputdata.batchoffset.asynctask;

import com.jiuqi.gcreport.nr.impl.constant.GcAsyncTaskPoolType;
import com.jiuqi.np.asynctask.AsyncTaskPool;
import org.springframework.stereotype.Component;

@Component
public class OffsetAsyncTaskPool
implements AsyncTaskPool {
    public String getType() {
        return GcAsyncTaskPoolType.OFFSET.getName();
    }

    public Integer getQueueSize() {
        return 100;
    }

    public Integer getParallelSize() {
        return 5;
    }

    public Boolean isConfig() {
        return true;
    }
}

