/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.asynctask;

import com.jiuqi.np.asynctask.AsyncTask;
import com.jiuqi.np.asynctask.NpRealTimeTaskInfo;
import com.jiuqi.np.asynctask.TaskState;
import java.util.List;

public interface AsyncThreadExecutor {
    public String executeTask(NpRealTimeTaskInfo var1);

    public List<String> executorTasks(List<NpRealTimeTaskInfo> var1);

    public void cancelTask(String var1);

    public AsyncTask queryTask(String var1);

    public Double queryProcess(String var1);

    public TaskState queryTaskState(String var1);

    public String queryResult(String var1);
}

