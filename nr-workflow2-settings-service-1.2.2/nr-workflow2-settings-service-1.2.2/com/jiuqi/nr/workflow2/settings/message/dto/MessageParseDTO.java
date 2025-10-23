/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.settings.message.dto;

public class MessageParseDTO {
    private String taskKey;
    private String period;
    private String unitCode;
    private String formKey;
    private String formGroupKey;
    private String workflowNode;
    private String actionCode;
    private String type;

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public String getPeriod() {
        return this.period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getUnitCode() {
        return this.unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public String getFormGroupKey() {
        return this.formGroupKey;
    }

    public void setFormGroupKey(String formGroupKey) {
        this.formGroupKey = formGroupKey;
    }

    public String getWorkflowNode() {
        return this.workflowNode;
    }

    public void setWorkflowNode(String workflowNode) {
        this.workflowNode = workflowNode;
    }

    public String getActionCode() {
        return this.actionCode;
    }

    public void setActionCode(String actionCode) {
        this.actionCode = actionCode;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

