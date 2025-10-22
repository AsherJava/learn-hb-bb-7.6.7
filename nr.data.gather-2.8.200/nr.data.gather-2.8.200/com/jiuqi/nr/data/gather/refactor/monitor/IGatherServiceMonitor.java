/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 */
package com.jiuqi.nr.data.gather.refactor.monitor;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nr.data.gather.refactor.monitor.MonitorEventParam;

public interface IGatherServiceMonitor
extends AsyncTaskMonitor {
    public void executeBefore(MonitorEventParam var1);

    public void executeAfter(MonitorEventParam var1);
}

