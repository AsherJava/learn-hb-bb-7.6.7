/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.nr.dataservice.core.asynctask.IAsyncTaskFeture
 */
package com.jiuqi.nr.data.gather.service;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nr.data.gather.bean.NodeCheckParam;
import com.jiuqi.nr.data.gather.bean.NodeCheckResultInfo;
import com.jiuqi.nr.dataservice.core.asynctask.IAsyncTaskFeture;

public interface INodeCheckService
extends IAsyncTaskFeture<NodeCheckResultInfo> {
    public NodeCheckResultInfo nodeCheck(NodeCheckParam var1);

    public NodeCheckResultInfo asyncNodeCheck(NodeCheckParam var1, AsyncTaskMonitor var2);

    public NodeCheckResultInfo batchNodeCheck(NodeCheckParam var1, AsyncTaskMonitor var2);

    public NodeCheckResultInfo getNodeCheckResult(String var1);
}

