/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.data.logic.facade.extend.param.CheckEvent
 *  com.jiuqi.nr.data.logic.spi.ICheckMonitor
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.IProcessAsyncMonitor
 */
package com.jiuqi.nr.workflow2.events.monitor;

import com.jiuqi.nr.data.logic.facade.extend.param.CheckEvent;
import com.jiuqi.nr.data.logic.spi.ICheckMonitor;
import com.jiuqi.nr.workflow2.engine.core.event.runtime.IProcessAsyncMonitor;
import com.jiuqi.nr.workflow2.events.monitor.ProcessCalculateEventMonitor;

public class ProcessReviewEventMonitor
extends ProcessCalculateEventMonitor
implements ICheckMonitor {
    public ProcessReviewEventMonitor(IProcessAsyncMonitor monitor) {
        super(monitor);
    }

    public void executeBefore(CheckEvent checkEvent) {
    }

    public void executeAfter(CheckEvent checkEvent) {
    }
}

