/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.gather.refactor.service;

import com.jiuqi.nr.data.gather.bean.NodeGatherParam;
import com.jiuqi.nr.data.gather.refactor.monitor.IGatherServiceMonitor;

public interface NodeGatherService {
    public void nodeGather(NodeGatherParam var1);

    public void nodeGather(NodeGatherParam var1, IGatherServiceMonitor var2);

    public void nodeGatherByDim(NodeGatherParam var1);

    public void nodeGatherByDim(NodeGatherParam var1, IGatherServiceMonitor var2);
}

