/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.IProcessAsyncMonitor
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKey
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKeyCollection
 */
package com.jiuqi.nr.workflow2.service;

import com.jiuqi.nr.workflow2.engine.core.event.runtime.IProcessAsyncMonitor;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKey;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKeyCollection;
import com.jiuqi.nr.workflow2.service.execute.runtime.IEventOperateResult;
import com.jiuqi.nr.workflow2.service.para.IProcessExecutePara;
import com.jiuqi.nr.workflow2.service.result.IProcessExecuteResult;

public interface IProcessExecuteService {
    public IProcessExecuteResult executeProcess(IProcessExecutePara var1, IBusinessKey var2, IProcessAsyncMonitor var3, IEventOperateResult var4) throws Exception;

    public IProcessExecuteResult executeProcess(IProcessExecutePara var1, IBusinessKeyCollection var2, IProcessAsyncMonitor var3, IEventOperateResult var4) throws Exception;
}

