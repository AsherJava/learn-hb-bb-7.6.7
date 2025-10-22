/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.etl.common;

import com.jiuqi.nr.etl.common.UniversalTask;

public class ETLTask
extends UniversalTask {
    private String taskGuid;
    private String taskName;
    private String taskDescription;

    @Override
    public String getTaskGuid() {
        return this.taskGuid;
    }

    @Override
    public void setTaskGuid(String taskGuid) {
        this.taskGuid = taskGuid;
    }

    @Override
    public String getTaskName() {
        return this.taskName;
    }

    @Override
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    @Override
    public String getTaskDescription() {
        return this.taskDescription;
    }

    @Override
    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }
}

