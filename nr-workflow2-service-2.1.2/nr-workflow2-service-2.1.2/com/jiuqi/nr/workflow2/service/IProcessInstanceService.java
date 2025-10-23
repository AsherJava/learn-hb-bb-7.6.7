/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.IOperateResultSet
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.IProcessAsyncMonitor
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKeyCollection
 */
package com.jiuqi.nr.workflow2.service;

import com.jiuqi.nr.workflow2.engine.core.event.runtime.IOperateResultSet;
import com.jiuqi.nr.workflow2.engine.core.event.runtime.IProcessAsyncMonitor;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKeyCollection;
import com.jiuqi.nr.workflow2.service.para.IProcessRunPara;

public interface IProcessInstanceService {
    public void startInstances(IProcessRunPara var1, IBusinessKeyCollection var2, IProcessAsyncMonitor var3, IOperateResultSet var4);

    public void clearInstances(IProcessRunPara var1, IBusinessKeyCollection var2, IProcessAsyncMonitor var3, IOperateResultSet var4);

    public void refreshActors(IProcessRunPara var1, IBusinessKeyCollection var2, IProcessAsyncMonitor var3, IOperateResultSet var4);
}

