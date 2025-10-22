/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.logic.spi;

import com.jiuqi.nr.data.logic.api.param.CalEvent;
import com.jiuqi.nr.data.logic.facade.monitor.IFmlMonitor;

public interface ICalculateMonitor
extends IFmlMonitor {
    public void executeBefore(CalEvent var1);

    public void executeAfter(CalEvent var1);
}

