/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 */
package com.jiuqi.nr.dataentry.service;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nr.dataentry.paramInfo.BatchCheckInfo;

@Deprecated
public interface IBatchCheckService {
    @Deprecated
    public void batchCheckForm(BatchCheckInfo var1);

    @Deprecated
    public void batchCheckForm(BatchCheckInfo var1, AsyncTaskMonitor var2);
}

