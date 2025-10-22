/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dafafill.owner.web.vo;

import com.jiuqi.nr.dafafill.model.enums.ModelType;

public class DefinitionVO {
    private String title;
    private String group;
    private String model;
    private ModelType sourceType;
    private String task;

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGroup() {
        return this.group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getModel() {
        return this.model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public ModelType getSourceType() {
        return this.sourceType;
    }

    public void setSourceType(ModelType sourceType) {
        this.sourceType = sourceType;
    }

    public String getTask() {
        return this.task;
    }

    public void setTask(String task) {
        this.task = task;
    }
}

