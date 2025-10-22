/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 */
package com.jiuqi.nr.bpm.de.dataflow.bean;

import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import java.util.List;

public class WorkflowConfig {
    private boolean flowStarted;
    private WorkFlowType workFlowType;
    private List<String> workflowEntities;
    public boolean calculateBefor = true;
    public String calculateFormulaSchemeKey;
    public boolean checkBefor = true;
    public String checkFormulaSchemeKey;

    public WorkFlowType getWorkFlowType() {
        return this.workFlowType;
    }

    public void setWorkFlowType(WorkFlowType workFlowType) {
        this.workFlowType = workFlowType;
    }

    public boolean isFlowStarted() {
        return this.flowStarted;
    }

    public void setFlowStarted(boolean flowStarted) {
        this.flowStarted = flowStarted;
    }

    public boolean isCalculateBefor() {
        return this.calculateBefor;
    }

    public void setCalculateBefor(boolean calculateBefor) {
        this.calculateBefor = calculateBefor;
    }

    public String getCalculateFormulaSchemeKey() {
        return this.calculateFormulaSchemeKey;
    }

    public void setCalculateFormulaSchemeKey(String calculateFormulaSchemeKey) {
        this.calculateFormulaSchemeKey = calculateFormulaSchemeKey;
    }

    public boolean isCheckBefor() {
        return this.checkBefor;
    }

    public void setCheckBefor(boolean checkBefor) {
        this.checkBefor = checkBefor;
    }

    public String getCheckFormulaSchemeKey() {
        return this.checkFormulaSchemeKey;
    }

    public void setCheckFormulaSchemeKey(String checkFormulaSchemeKey) {
        this.checkFormulaSchemeKey = checkFormulaSchemeKey;
    }

    public List<String> getWorkflowEntities() {
        return this.workflowEntities;
    }

    public void setWorkflowEntities(List<String> workflowEntities) {
        this.workflowEntities = workflowEntities;
    }
}

