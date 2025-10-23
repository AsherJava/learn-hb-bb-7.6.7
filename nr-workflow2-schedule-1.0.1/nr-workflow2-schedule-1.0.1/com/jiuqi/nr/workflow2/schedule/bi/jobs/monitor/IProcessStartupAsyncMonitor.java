/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.AsyncJobResult
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.IProcessAsyncMonitor
 */
package com.jiuqi.nr.workflow2.schedule.bi.jobs.monitor;

import com.jiuqi.nr.workflow2.engine.core.event.runtime.AsyncJobResult;
import com.jiuqi.nr.workflow2.engine.core.event.runtime.IProcessAsyncMonitor;

public interface IProcessStartupAsyncMonitor
extends IProcessAsyncMonitor {
    public void setExecuteResult(AsyncJobResult var1, String var2);

    public void setExecuteResult(AsyncJobResult var1, String var2, Object var3);
}

