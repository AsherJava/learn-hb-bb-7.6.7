/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.setting.pojo;

public class DataParam {
    private String taskKey;
    private String formSchemeKey;
    private String workflowId;
    private boolean defaultWorkflow;

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public String getWorkflowId() {
        return this.workflowId;
    }

    public void setWorkflowId(String workflowId) {
        this.workflowId = workflowId;
    }

    public boolean isDefaultWorkflow() {
        return this.defaultWorkflow;
    }

    public void setDefaultWorkflow(boolean defaultWorkflow) {
        this.defaultWorkflow = defaultWorkflow;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }
}

