/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 */
package com.jiuqi.nr.data.access.api;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.data.access.common.WorkflowState;
import java.util.List;
import java.util.Map;

@Deprecated
public interface IStateWorkFlowService {
    public void stateSave(String var1, DimensionValueSet var2, WorkflowState var3);

    public void stateBatchSave(String var1, List<DimensionValueSet> var2, WorkflowState var3, DimensionValueSet var4);

    public void stateBatchSave(String var1, Map<DimensionValueSet, WorkflowState> var2, DimensionValueSet var3);
}

