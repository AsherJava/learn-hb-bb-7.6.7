/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.settings.message.dto;

public class MessageInstanceBaseContext {
    private String taskId;
    private String workflowNode;
    private String actionCode;
    private String actionTitle;
    private String workflowDefineTemplate;
    private String submitMode;
    private boolean isTodoEnabled;

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
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

    public String getActionTitle() {
        return this.actionTitle;
    }

    public void setActionTitle(String actionTitle) {
        this.actionTitle = actionTitle;
    }

    public String getWorkflowDefineTemplate() {
        return this.workflowDefineTemplate;
    }

    public void setWorkflowDefineTemplate(String workflowDefineTemplate) {
        this.workflowDefineTemplate = workflowDefineTemplate;
    }

    public String getSubmitMode() {
        return this.submitMode;
    }

    public void setSubmitMode(String submitMode) {
        this.submitMode = submitMode;
    }

    public boolean isIsTodoEnabled() {
        return this.isTodoEnabled;
    }

    public void setIsTodoEnabled(boolean isTodoEnabled) {
        this.isTodoEnabled = isTodoEnabled;
    }
}

