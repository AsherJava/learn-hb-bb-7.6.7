/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.IEventFinishedResult
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.IOperateResultSet
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.IProcessAsyncMonitor
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IActionArgs
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKey
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKeyCollection
 */
package com.jiuqi.nr.workflow2.form.reject.ext.event;

import com.jiuqi.nr.workflow2.engine.core.event.runtime.IEventFinishedResult;
import com.jiuqi.nr.workflow2.engine.core.event.runtime.IOperateResultSet;
import com.jiuqi.nr.workflow2.engine.core.event.runtime.IProcessAsyncMonitor;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IActionArgs;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKey;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKeyCollection;

public interface IFormRejectEventExecutor {
    public IEventFinishedResult executionEvent(IProcessAsyncMonitor var1, IOperateResultSet var2, IActionArgs var3, IBusinessKey var4) throws Exception;

    public IEventFinishedResult executionEvent(IProcessAsyncMonitor var1, IOperateResultSet var2, IActionArgs var3, IBusinessKeyCollection var4) throws Exception;
}

