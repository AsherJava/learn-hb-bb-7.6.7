/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.settings.dto;

import java.io.Serializable;

public class WorkflowSettingsManipulationContext
implements Serializable {
    private String taskId;
    private Object otherSettings;
    private Object workflowSettings;

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public Object getOtherSettings() {
        return this.otherSettings;
    }

    public void setOtherSettings(Object otherSettings) {
        this.otherSettings = otherSettings;
    }

    public Object getWorkflowSettings() {
        return this.workflowSettings;
    }

    public void setWorkflowSettings(Object workflowSettings) {
        this.workflowSettings = workflowSettings;
    }
}

