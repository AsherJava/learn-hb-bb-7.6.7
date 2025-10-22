/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskPool
 */
package com.jiuqi.gcreport.nr.impl.asynctask;

import com.jiuqi.gcreport.nr.impl.constant.GcAsyncTaskPoolType;
import com.jiuqi.np.asynctask.AsyncTaskPool;
import org.springframework.stereotype.Component;

@Component
public class GcFormulaCheckDesTaskPool
implements AsyncTaskPool {
    public String getType() {
        return GcAsyncTaskPoolType.ASYNCTASK_CHECKDESCOPYINFO.getName();
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

