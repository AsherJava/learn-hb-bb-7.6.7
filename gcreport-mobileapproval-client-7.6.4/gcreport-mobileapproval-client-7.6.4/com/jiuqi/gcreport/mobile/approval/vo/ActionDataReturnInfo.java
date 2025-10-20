/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.bpm.de.dataflow.bean.WorkflowDataInfo
 *  com.jiuqi.nr.common.params.DimensionValue
 */
package com.jiuqi.gcreport.mobile.approval.vo;

import com.jiuqi.nr.bpm.de.dataflow.bean.WorkflowDataInfo;
import com.jiuqi.nr.common.params.DimensionValue;
import java.util.List;
import java.util.Map;

public class ActionDataReturnInfo {
    private List<WorkflowDataInfo> workflowDataInfos;
    private Map<String, DimensionValue> dimensionSet;
    private String formulaSchemeKey;

    public List<WorkflowDataInfo> getWorkflowDataInfos() {
        return this.workflowDataInfos;
    }

    public void setWorkflowDataInfos(List<WorkflowDataInfo> workflowDataInfos) {
        this.workflowDataInfos = workflowDataInfos;
    }

    public Map<String, DimensionValue> getDimensionSet() {
        return this.dimensionSet;
    }

    public void setDimensionSet(Map<String, DimensionValue> dimensionSet) {
        this.dimensionSet = dimensionSet;
    }

    public String getFormulaSchemeKey() {
        return this.formulaSchemeKey;
    }

    public void setFormulaSchemeKey(String formulaSchemeKey) {
        this.formulaSchemeKey = formulaSchemeKey;
    }
}

