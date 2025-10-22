/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.TaskDefine
 */
package com.jiuqi.nr.examine.web.bean;

import com.jiuqi.nr.definition.facade.TaskDefine;

public class TaskInfo {
    private String taskKey;
    private String taskName;
    private String taskCode;

    public TaskInfo() {
    }

    public TaskInfo(TaskDefine taskDefine) {
        this.taskKey = taskDefine.getKey();
        this.taskName = taskDefine.getTitle();
        this.taskCode = taskDefine.getTaskCode();
    }

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public String getTaskName() {
        return this.taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskCode() {
        return this.taskCode;
    }

    public void setTaskCode(String taskCode) {
        this.taskCode = taskCode;
    }
}

