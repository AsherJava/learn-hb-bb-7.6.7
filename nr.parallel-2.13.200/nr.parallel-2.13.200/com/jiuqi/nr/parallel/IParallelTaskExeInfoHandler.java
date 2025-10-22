/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.parallel;

import com.jiuqi.nr.parallel.BatchParallelExeTask;
import com.jiuqi.nr.parallel.ParallelExeInfo;
import java.util.List;

public interface IParallelTaskExeInfoHandler {
    public List<ParallelExeInfo> initTasks(List<BatchParallelExeTask> var1, String var2);

    public ParallelExeInfo queryTaskInfo(String var1, String var2);

    public void taskRunning(String var1, String var2);

    public void updateTask(ParallelExeInfo var1);
}

