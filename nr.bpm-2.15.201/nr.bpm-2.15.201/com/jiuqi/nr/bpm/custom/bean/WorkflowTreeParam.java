/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.custom.bean;

import java.util.List;

public class WorkflowTreeParam {
    private String taskKey;
    private List<String> selectedKeys;

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public List<String> getSelectedKeys() {
        return this.selectedKeys;
    }

    public void setSelectedKeys(List<String> selectedKeys) {
        this.selectedKeys = selectedKeys;
    }
}

