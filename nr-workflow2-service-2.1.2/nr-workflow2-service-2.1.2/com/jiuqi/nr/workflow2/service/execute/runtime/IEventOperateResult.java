/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.AsyncJobResult
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.IOperateResultSet
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObject
 */
package com.jiuqi.nr.workflow2.service.execute.runtime;

import com.jiuqi.nr.workflow2.engine.core.event.runtime.AsyncJobResult;
import com.jiuqi.nr.workflow2.engine.core.event.runtime.IOperateResultSet;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObject;
import com.jiuqi.nr.workflow2.service.execute.runtime.IEventOperateColumn;
import java.util.List;

public interface IEventOperateResult {
    public IOperateResultSet getOperateResultSet(IEventOperateColumn var1);

    public Integer getLevel(IBusinessObject var1);

    public Integer getParentRowIndex(IBusinessObject var1);

    public IBusinessObject findBusinessObject(Integer var1);

    public List<IBusinessObject> allCheckPassBusinessObjects();

    public List<IEventOperateColumn> allEventOperateColumns();

    public Object toOutputDetail();

    public String toResultMessage();

    public AsyncJobResult toAsyncJobResult();
}

