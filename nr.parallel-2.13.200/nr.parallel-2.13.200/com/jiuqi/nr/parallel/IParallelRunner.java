/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.intf.IMonitor
 */
package com.jiuqi.nr.parallel;

import com.jiuqi.np.dataengine.intf.IMonitor;
import com.jiuqi.nr.parallel.BatchParallelExeTask;

public interface IParallelRunner {
    public void run(BatchParallelExeTask var1, IMonitor var2) throws Exception;

    public void run(BatchParallelExeTask var1, IMonitor var2, boolean var3) throws Exception;
}

