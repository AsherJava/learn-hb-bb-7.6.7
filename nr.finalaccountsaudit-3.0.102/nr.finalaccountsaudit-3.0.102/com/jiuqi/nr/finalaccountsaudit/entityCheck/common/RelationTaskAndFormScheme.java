/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.finalaccountsaudit.entityCheck.common;

public class RelationTaskAndFormScheme {
    private String taskKey;
    private String taskTitle;
    private String formSchemeKey;
    private String formSchemenTitle;
    private String period;

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

    public String getFormSchemenTitle() {
        return this.formSchemenTitle;
    }

    public void setFormSchemenTitle(String formSchemenTitle) {
        this.formSchemenTitle = formSchemenTitle;
    }

    public String getPeriod() {
        return this.period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + (this.formSchemeKey == null ? 0 : this.formSchemeKey.hashCode());
        result = 31 * result + (this.formSchemenTitle == null ? 0 : this.formSchemenTitle.hashCode());
        result = 31 * result + (this.period == null ? 0 : this.period.hashCode());
        result = 31 * result + (this.taskKey == null ? 0 : this.taskKey.hashCode());
        result = 31 * result + (this.taskTitle == null ? 0 : this.taskTitle.hashCode());
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        RelationTaskAndFormScheme other = (RelationTaskAndFormScheme)obj;
        if (this.formSchemeKey == null ? other.formSchemeKey != null : !this.formSchemeKey.equals(other.formSchemeKey)) {
            return false;
        }
        if (this.formSchemenTitle == null ? other.formSchemenTitle != null : !this.formSchemenTitle.equals(other.formSchemenTitle)) {
            return false;
        }
        if (this.period == null ? other.period != null : !this.period.equals(other.period)) {
            return false;
        }
        if (this.taskKey == null ? other.taskKey != null : !this.taskKey.equals(other.taskKey)) {
            return false;
        }
        return !(this.taskTitle == null ? other.taskTitle != null : !this.taskTitle.equals(other.taskTitle));
    }
}

