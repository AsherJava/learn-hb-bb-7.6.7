/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.multcheck2.service.IMCMonitor
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.IProcessAsyncMonitor
 */
package com.jiuqi.nr.workflow2.events.monitor;

import com.jiuqi.nr.multcheck2.service.IMCMonitor;
import com.jiuqi.nr.workflow2.engine.core.event.runtime.IProcessAsyncMonitor;

public class ProcessMCRunMonitor
implements IMCMonitor {
    private IProcessAsyncMonitor monitor;

    public ProcessMCRunMonitor(IProcessAsyncMonitor monitor) {
        this.monitor = monitor;
    }

    public void progressAndMessage(double progress, String message) {
        this.monitor.setJobProgress(progress, message);
    }

    public boolean isCancel() {
        return this.monitor.isCancel();
    }
}

