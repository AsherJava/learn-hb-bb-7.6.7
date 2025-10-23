/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.IOperateResultSet
 */
package com.jiuqi.nr.workflow2.schedule.service;

import com.jiuqi.nr.workflow2.engine.core.event.runtime.IOperateResultSet;
import com.jiuqi.nr.workflow2.schedule.bi.jobs.monitor.IProcessStartupAsyncMonitor;
import com.jiuqi.nr.workflow2.schedule.bi.jobs.param.IProcessStartupRunPara;

public interface IProcessStartupInstanceService {
    public void startInstances(IProcessStartupRunPara var1, IOperateResultSet var2, IProcessStartupAsyncMonitor var3);
}

