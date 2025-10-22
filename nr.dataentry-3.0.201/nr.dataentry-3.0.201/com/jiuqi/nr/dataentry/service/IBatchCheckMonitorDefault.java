/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.nr.parallel.BatchParallelExeTask
 *  com.jiuqi.nr.parallel.impl.BatchParallelMonitor
 */
package com.jiuqi.nr.dataentry.service;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nr.dataentry.service.IDataEntryServiceDefault;
import com.jiuqi.nr.parallel.BatchParallelExeTask;
import com.jiuqi.nr.parallel.impl.BatchParallelMonitor;

@Deprecated
public interface IBatchCheckMonitorDefault
extends IDataEntryServiceDefault {
    public BatchParallelMonitor getMonitor(BatchParallelExeTask var1, AsyncTaskMonitor var2);

    public BatchParallelMonitor getMonitor(BatchParallelExeTask var1);
}

