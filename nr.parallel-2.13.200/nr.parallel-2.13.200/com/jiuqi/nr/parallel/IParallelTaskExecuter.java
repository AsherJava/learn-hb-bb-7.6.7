/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.intf.IMonitor
 */
package com.jiuqi.nr.parallel;

import com.jiuqi.np.dataengine.intf.IMonitor;
import com.jiuqi.nr.parallel.BatchParallelExeTask;

public interface IParallelTaskExecuter {
    public void doExecute(BatchParallelExeTask var1, IMonitor var2);
}

