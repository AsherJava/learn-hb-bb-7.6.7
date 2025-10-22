/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataentry.copydes.web.vo;

public class FormulaSchemeVO {
    private String key;
    private String title;
    private String formSchemeKey;
    private String formSchemeTitle;
    private String formSchemeCode;
    private String taskKey;
    private String taskTitle;
    private String taskCode;
    private boolean defaultFls;

    public String getFormSchemeCode() {
        return this.formSchemeCode;
    }

    public void setFormSchemeCode(String formSchemeCode) {
        this.formSchemeCode = formSchemeCode;
    }

    public String getTaskCode() {
        return this.taskCode;
    }

    public void setTaskCode(String taskCode) {
        this.taskCode = taskCode;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFormSchemeTitle() {
        return this.formSchemeTitle;
    }

    public void setFormSchemeTitle(String formSchemeTitle) {
        this.formSchemeTitle = formSchemeTitle;
    }

    public String getTaskTitle() {
        return this.taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public boolean isDefaultFls() {
        return this.defaultFls;
    }

    public void setDefaultFls(boolean defaultFls) {
        this.defaultFls = defaultFls;
    }
}

