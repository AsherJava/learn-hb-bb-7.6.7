/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.engine.core.event.runtime;

import com.jiuqi.nr.workflow2.engine.core.event.runtime.IEventOperateInfo;
import com.jiuqi.nr.workflow2.engine.core.event.runtime.WFMonitorCheckResult;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObject;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IOperateResultSet {
    public void setOperateResult(Object var1);

    public void setOperateResult(IBusinessObject var1, IEventOperateInfo var2);

    public void setOtherOperateResult(IBusinessObject var1, IEventOperateInfo var2);

    public void setLevel(IBusinessObject var1, Integer var2);

    public void setParentRowIndex(IBusinessObject var1, Integer var2);

    public boolean containsBusinessObject(IBusinessObject var1);

    public boolean hasCheckPassed(IBusinessObject var1);

    public Integer findBusinessObjectIndex(IBusinessObject var1);

    public Integer getParentRowIndex(IBusinessObject var1);

    public WFMonitorCheckResult getCheckStatus(IBusinessObject var1);

    public IEventOperateInfo getOperateResult(IBusinessObject var1);

    public IBusinessObject findBusinessObject(Integer var1);

    public List<IBusinessObject> allCheckPassBusinessObjects();

    public Map<Integer, Set<Integer>> getLevel2RowIndexes();

    public void removeBusinessObjects(List<IBusinessObject> var1);
}

