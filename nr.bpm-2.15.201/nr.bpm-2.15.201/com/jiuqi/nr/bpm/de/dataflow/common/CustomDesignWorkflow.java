/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 */
package com.jiuqi.nr.bpm.de.dataflow.common;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.bpm.de.dataflow.bean.ActionState;
import com.jiuqi.nr.bpm.de.dataflow.bean.ActionStateBean;
import com.jiuqi.nr.bpm.de.dataflow.bean.WorkFlowTreeState;
import java.util.List;
import java.util.Map;

public interface CustomDesignWorkflow {
    public boolean isApply(String var1);

    public List<WorkFlowTreeState> queryWorkflowTreeState(String var1);

    public Map<DimensionValueSet, ActionStateBean> queryWorkflowTreeUploadState(DimensionValueSet var1, String var2, String var3, String var4);

    public ActionState getState(String var1, DimensionValueSet var2, String var3);
}

