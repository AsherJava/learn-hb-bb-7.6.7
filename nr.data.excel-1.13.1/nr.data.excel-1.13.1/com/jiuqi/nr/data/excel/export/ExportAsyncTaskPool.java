/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskPool
 */
package com.jiuqi.nr.data.excel.export;

import com.jiuqi.np.asynctask.AsyncTaskPool;
import org.springframework.stereotype.Component;

@Component
public class ExportAsyncTaskPool
implements AsyncTaskPool {
    public static final String ASYNCTASK_DATAEXPORT = "ASYNCTASK_DATAEXPORT";

    public String getType() {
        return ASYNCTASK_DATAEXPORT;
    }

    public Integer getQueueSize() {
        return 100;
    }

    public Integer getParallelSize() {
        return 10;
    }

    public Boolean isConfig() {
        return super.isConfig();
    }
}

