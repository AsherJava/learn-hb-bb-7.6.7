/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.nr.dataservice.core.asynctask.IAsyncTaskFeture
 */
package com.jiuqi.nr.data.gather.service;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nr.data.gather.bean.SelectDataGatherParam;
import com.jiuqi.nr.dataservice.core.asynctask.IAsyncTaskFeture;

public interface IDataSelectGatherService
extends IAsyncTaskFeture {
    public void selectNodeGather(SelectDataGatherParam var1);

    public void asyncDataSelectGather(SelectDataGatherParam var1, AsyncTaskMonitor var2);
}

