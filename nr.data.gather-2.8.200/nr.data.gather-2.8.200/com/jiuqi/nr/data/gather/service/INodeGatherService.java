/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.nr.dataservice.core.asynctask.IAsyncTaskFeture
 */
package com.jiuqi.nr.data.gather.service;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nr.data.gather.bean.NodeGatherParam;
import com.jiuqi.nr.dataservice.core.asynctask.IAsyncTaskFeture;

public interface INodeGatherService
extends IAsyncTaskFeture {
    public void batchNodeGather(NodeGatherParam var1);

    public void asyncBatchDataGather(NodeGatherParam var1, AsyncTaskMonitor var2);
}

