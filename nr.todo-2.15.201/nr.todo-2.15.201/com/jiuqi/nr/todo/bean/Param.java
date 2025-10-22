/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.todo.bean;

import com.jiuqi.nr.todo.bean.Locate;

public class Param {
    private String taskId;
    private String formSchemeId;
    private String period;
    private Locate locate;
    private String[] unitViewEntityRange;

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getFormSchemeId() {
        return this.formSchemeId;
    }

    public void setFormSchemeId(String formSchemeId) {
        this.formSchemeId = formSchemeId;
    }

    public String getPeriod() {
        return this.period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public Locate getLocate() {
        return this.locate;
    }

    public void setLocate(Locate locate) {
        this.locate = locate;
    }

    public String[] getUnitViewEntityRange() {
        return this.unitViewEntityRange;
    }

    public void setUnitViewEntityRange(String[] unitViewEntityRange) {
        this.unitViewEntityRange = unitViewEntityRange;
    }
}

