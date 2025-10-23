/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.mapping2.web.vo;

import com.jiuqi.nr.mapping2.provider.TypeOption;
import com.jiuqi.nr.mapping2.web.vo.SelectOptionVO;
import java.util.List;

public class TaskFormSchemeVO {
    private String taskKey;
    private String taskTitle;
    private String orgName;
    private String formShemeKey;
    private String formShemeTitle;
    private List<SelectOptionVO> formSchemes;
    private List<SelectOptionVO> orgLinks;
    private TypeOption typeOption;

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

    public String getOrgName() {
        return this.orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
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

    public List<SelectOptionVO> getOrgLinks() {
        return this.orgLinks;
    }

    public void setOrgLinks(List<SelectOptionVO> orgLinks) {
        this.orgLinks = orgLinks;
    }

    public TypeOption getTypeOption() {
        return this.typeOption;
    }

    public void setTypeOption(TypeOption typeOption) {
        this.typeOption = typeOption;
    }
}

