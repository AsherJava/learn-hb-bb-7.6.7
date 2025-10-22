/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.TaskLinkDefine
 */
package com.jiuqi.nr.finalaccountsaudit.multcheck.config.common;

import com.jiuqi.nr.definition.facade.TaskLinkDefine;

public class TaskShortInfo {
    private String taskKey;
    private String taskTitle;
    private String formSchemeKey;
    private String formSchemeTitle;
    private TaskLinkDefine taskLinkDefine;

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public String getTaskTitle() {
        return this.taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public String getFormSchemeTitle() {
        return this.formSchemeTitle;
    }

    public void setFormSchemeTitle(String formSchemeTitle) {
        this.formSchemeTitle = formSchemeTitle;
    }

    public TaskLinkDefine getTaskLinkDefine() {
        return this.taskLinkDefine;
    }

    public void setTaskLinkDefine(TaskLinkDefine taskLinkDefine) {
        this.taskLinkDefine = taskLinkDefine;
    }
}

