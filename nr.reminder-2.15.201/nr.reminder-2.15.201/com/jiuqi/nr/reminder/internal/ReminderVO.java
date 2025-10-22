/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.reminder.internal;

import com.jiuqi.nr.reminder.internal.Reminder;

public class ReminderVO
extends Reminder {
    private String taskTitle;
    private String formSchemeTitle;
    private String unitName;

    public String getTaskTitle() {
        return this.taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public String getFormSchemeTitle() {
        return this.formSchemeTitle;
    }

    public void setFormSchemeTitle(String formSchemeTitle) {
        this.formSchemeTitle = formSchemeTitle;
    }

    public String getUnitName() {
        return this.unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }
}

