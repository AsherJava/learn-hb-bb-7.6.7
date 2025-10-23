/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 */
package com.jiuqi.nr.data.access.api.response;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.data.access.common.WorkflowState;
import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import java.util.HashMap;
import java.util.Map;

public class WorkFlowStateInfo {
    private Map<DimensionValueSet, Map<String, WorkflowState>> formStateMap = new HashMap<DimensionValueSet, Map<String, WorkflowState>>();
    private Map<DimensionValueSet, WorkflowState> unitState;
    private WorkFlowType workFlowType;

    public Map<DimensionValueSet, Map<String, WorkflowState>> getFormStateMap() {
        return this.formStateMap;
    }

    public void setFormStateMap(Map<DimensionValueSet, Map<String, WorkflowState>> formStateMap) {
        this.formStateMap = formStateMap;
    }

    public Map<DimensionValueSet, WorkflowState> getUnitState() {
        return this.unitState;
    }

    public void setUnitState(Map<DimensionValueSet, WorkflowState> unitState) {
        this.unitState = unitState;
    }

    public WorkFlowType getWorkFlowType() {
        return this.workFlowType;
    }

    public void setWorkFlowType(WorkFlowType workFlowType) {
        this.workFlowType = workFlowType;
    }
}

