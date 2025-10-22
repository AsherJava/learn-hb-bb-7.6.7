/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.gather.refactor.service;

import com.jiuqi.nr.data.gather.bean.NodeCheckParam;
import com.jiuqi.nr.data.gather.refactor.bean.NodeCheckResult;
import com.jiuqi.nr.data.gather.refactor.monitor.IGatherServiceMonitor;
import java.util.List;

public interface NodeCheckService {
    public NodeCheckResult nodeCheck(NodeCheckParam var1);

    public NodeCheckResult nodeCheck(NodeCheckParam var1, IGatherServiceMonitor var2);

    public List<NodeCheckResult> batchNodeCheck(NodeCheckParam var1);

    public List<NodeCheckResult> batchNodeCheck(NodeCheckParam var1, IGatherServiceMonitor var2);
}

