/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.todo.bean;

public class TodoParam {
    private String taskKey;
    private String taskTitle;
    private String formSchemeKey;
    private String period;
    private String detailResult;
    private String[] unitViewEntityRange;
    private String todoparam;

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

    public String getPeriod() {
        return this.period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getDetailResult() {
        return this.detailResult;
    }

    public void setDetailResult(String detailResult) {
        this.detailResult = detailResult;
    }

    public String getTodoparam() {
        return this.todoparam;
    }

    public void setTodoparam(String todoparam) {
        this.todoparam = todoparam;
    }

    public String[] getUnitViewEntityRange() {
        return this.unitViewEntityRange;
    }

    public void setUnitViewEntityRange(String[] unitViewEntityRange) {
        this.unitViewEntityRange = unitViewEntityRange;
    }
}

