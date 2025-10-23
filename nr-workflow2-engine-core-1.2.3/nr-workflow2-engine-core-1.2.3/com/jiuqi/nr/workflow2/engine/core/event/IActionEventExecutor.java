/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.engine.core.event;

import com.jiuqi.nr.workflow2.engine.core.event.runtime.IEventFinishedResult;
import com.jiuqi.nr.workflow2.engine.core.event.runtime.IOperateResultSet;
import com.jiuqi.nr.workflow2.engine.core.event.runtime.IProcessAsyncMonitor;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IActionArgs;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKey;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKeyCollection;

public interface IActionEventExecutor {
    public IEventFinishedResult executionEvent(IProcessAsyncMonitor var1, IOperateResultSet var2, IActionArgs var3, IBusinessKey var4) throws Exception;

    public IEventFinishedResult executionEvent(IProcessAsyncMonitor var1, IOperateResultSet var2, IActionArgs var3, IBusinessKeyCollection var4) throws Exception;
}

