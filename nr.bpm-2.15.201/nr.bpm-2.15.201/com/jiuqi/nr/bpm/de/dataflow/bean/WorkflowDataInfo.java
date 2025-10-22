/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 */
package com.jiuqi.nr.bpm.de.dataflow.bean;

import com.jiuqi.nr.bpm.de.dataflow.bean.WorkflowAction;
import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import java.util.List;

public class WorkflowDataInfo {
    private String taskId;
    private String taskCode;
    private WorkFlowType workFlowType;
    private List<WorkflowAction> actions;
    private boolean disabled;

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public List<WorkflowAction> getActions() {
        return this.actions;
    }

    public void setActions(List<WorkflowAction> actions) {
        this.actions = actions;
    }

    public String getTaskCode() {
        return this.taskCode;
    }

    public void setTaskCode(String taskCode) {
        this.taskCode = taskCode;
    }

    public boolean isDisabled() {
        return this.disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public WorkFlowType getWorkFlowType() {
        return this.workFlowType;
    }

    public void setWorkFlowType(WorkFlowType workFlowType) {
        this.workFlowType = workFlowType;
    }
}

