/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataentry.bean;

import com.jiuqi.nr.dataentry.bean.DWorkflowUserAction;
import java.util.List;

public class DWorkflowData {
    private String taskId;
    private String taskCode;
    private boolean disabled;
    private List<DWorkflowUserAction> userActions;

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public List<DWorkflowUserAction> getUserActions() {
        return this.userActions;
    }

    public void setUserActions(List<DWorkflowUserAction> userActions) {
        this.userActions = userActions;
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
}

