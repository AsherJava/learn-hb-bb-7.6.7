/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.gather.refactor.service;

import com.jiuqi.nr.data.gather.bean.SelectDataGatherParam;
import com.jiuqi.nr.data.gather.refactor.monitor.IGatherServiceMonitor;

public interface SelectGatherService {
    public void selectNodeGather(SelectDataGatherParam var1);

    public void selectNodeGather(SelectDataGatherParam var1, IGatherServiceMonitor var2);
}

