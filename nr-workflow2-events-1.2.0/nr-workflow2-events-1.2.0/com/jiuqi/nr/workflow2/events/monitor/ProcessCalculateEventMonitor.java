/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.data.logic.facade.monitor.IFmlMonitor
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.AsyncJobResult
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.IProcessAsyncMonitor
 */
package com.jiuqi.nr.workflow2.events.monitor;

import com.jiuqi.nr.data.logic.facade.monitor.IFmlMonitor;
import com.jiuqi.nr.workflow2.engine.core.event.runtime.AsyncJobResult;
import com.jiuqi.nr.workflow2.engine.core.event.runtime.IProcessAsyncMonitor;

public class ProcessCalculateEventMonitor
implements IFmlMonitor {
    protected final IProcessAsyncMonitor monitor;

    public ProcessCalculateEventMonitor(IProcessAsyncMonitor monitor) {
        this.monitor = monitor;
    }

    public String getTaskId() {
        return this.monitor.getAsyncTaskId();
    }

    public void progressAndMessage(double currProgress, String message) {
        this.monitor.setJobProgress(currProgress, message);
    }

    public void error(String message, Throwable sender) {
        this.monitor.error(message, sender);
    }

    public void error(String message, Throwable sender, Object detail) {
        this.monitor.error(message, sender);
    }

    public void finish(String result, Object detail) {
    }

    public void cancel(String message, Object detail) {
        this.monitor.setJobResult(AsyncJobResult.CANCELED, message, detail);
    }

    public boolean isCancel() {
        return this.monitor.isCancel();
    }

    public void canceled(String result, Object detail) {
        this.monitor.setJobResult(AsyncJobResult.CANCELED, "", detail);
    }
}

