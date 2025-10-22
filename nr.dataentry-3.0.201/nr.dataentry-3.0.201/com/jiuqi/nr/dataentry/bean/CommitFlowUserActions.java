/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.bpm.common.UserAction
 */
package com.jiuqi.nr.dataentry.bean;

import com.jiuqi.nr.bpm.common.UserAction;
import java.util.List;

public class CommitFlowUserActions {
    private String taskId;
    private List<UserAction> userActions;

    public List<UserAction> getUserActions() {
        return this.userActions;
    }

    public void setUserActions(List<UserAction> userActions) {
        this.userActions = userActions;
    }

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
}

