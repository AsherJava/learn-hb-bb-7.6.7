/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.designer.web.service;

import java.io.Serializable;

public class TaskDeleteCondition
implements Serializable {
    private static final long serialVersionUID = 1L;
    String taskId;
    boolean deleteData;

    public TaskDeleteCondition(String taskId) {
        this(taskId, true);
    }

    public TaskDeleteCondition(String taskId, boolean deleteData) {
        this.taskId = taskId;
        this.deleteData = deleteData;
    }

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public boolean isDeleteData() {
        return this.deleteData;
    }

    public void setDeleteData(boolean deleteData) {
        this.deleteData = deleteData;
    }
}

