/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.asynctask;

import com.jiuqi.np.asynctask.NpAsyncTaskExecutor;
import java.util.List;

public interface AsyncTaskTypeCollecter {
    public List<String> getTaskPoolTypes();

    public Integer getQueueSize(String var1);

    public Integer getParallelSize(String var1);

    public String getTitle(String var1);

    public boolean isConfig(String var1);

    public NpAsyncTaskExecutor getExecutorByType(String var1);

    public List<String> getTaskToolTypeList();

    public List<String> getClearDataByTypeTasks();

    public List<String> getClearDataByTaskIdTasks();
}

