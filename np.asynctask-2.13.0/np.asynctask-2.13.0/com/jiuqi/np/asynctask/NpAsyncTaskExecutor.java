/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.asynctask;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import java.util.List;

@Deprecated
public interface NpAsyncTaskExecutor {
    public static final Integer CLEAN_TYPE_NONE = 0;
    public static final Integer CLEAN_TYPE_BYTYPE = 1;
    public static final Integer CLEAN_TYPE_BYID = 2;

    public void execute(Object var1, AsyncTaskMonitor var2);

    public String getTaskPoolType();

    default public Integer getCleanDataType() {
        return CLEAN_TYPE_NONE;
    }

    default public void cleanHistoryData() {
    }

    default public void cleanHistoryDataByTaskId(List<String> taskIds) {
    }
}

