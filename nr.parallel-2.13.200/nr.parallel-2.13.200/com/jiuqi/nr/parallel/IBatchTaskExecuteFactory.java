/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 */
package com.jiuqi.nr.parallel;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nr.parallel.BatchParallelExeTask;
import com.jiuqi.nr.parallel.IParallelTaskExecuter;
import com.jiuqi.nr.parallel.impl.BatchParallelMonitor;

public interface IBatchTaskExecuteFactory {
    public String getType();

    public BatchParallelMonitor getMonitor(BatchParallelExeTask var1);

    public BatchParallelMonitor getMonitor(BatchParallelExeTask var1, AsyncTaskMonitor var2);

    public IParallelTaskExecuter getIParallelTaskExecuter();
}

