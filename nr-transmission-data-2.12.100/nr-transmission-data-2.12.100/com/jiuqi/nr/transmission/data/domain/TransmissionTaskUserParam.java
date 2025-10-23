/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.transmission.data.domain;

import java.util.List;

public class TransmissionTaskUserParam {
    private List<String> selectedTask;
    private String selectedUser;

    public List<String> getSelectedTask() {
        return this.selectedTask;
    }

    public void setSelectedTask(List<String> selectedTask) {
        this.selectedTask = selectedTask;
    }

    public String getSelectedUser() {
        return this.selectedUser;
    }

    public void setSelectedUser(String selectedUser) {
        this.selectedUser = selectedUser;
    }
}

