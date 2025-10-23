/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.mapping.web.vo;

import com.jiuqi.nr.mapping.web.vo.SelectOptionVO;
import java.util.List;

public class TaskFormSchemeVO {
    private String taskKey;
    private String taskTitle;
    private String formShemeKey;
    private String formShemeTitle;
    private List<SelectOptionVO> formSchemes;

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

    public String getFormShemeKey() {
        return this.formShemeKey;
    }

    public void setFormShemeKey(String formShemeKey) {
        this.formShemeKey = formShemeKey;
    }

    public String getFormShemeTitle() {
        return this.formShemeTitle;
    }

    public void setFormShemeTitle(String formShemeTitle) {
        this.formShemeTitle = formShemeTitle;
    }

    public List<SelectOptionVO> getFormSchemes() {
        return this.formSchemes;
    }

    public void setFormSchemes(List<SelectOptionVO> formSchemes) {
        this.formSchemes = formSchemes;
    }
}

