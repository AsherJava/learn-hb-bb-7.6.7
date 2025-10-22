/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.logic.spi;

import com.jiuqi.nr.data.logic.facade.extend.param.CheckEvent;
import com.jiuqi.nr.data.logic.facade.monitor.IFmlMonitor;

public interface ICheckMonitor
extends IFmlMonitor {
    public void executeBefore(CheckEvent var1);

    public void executeAfter(CheckEvent var1);
}

