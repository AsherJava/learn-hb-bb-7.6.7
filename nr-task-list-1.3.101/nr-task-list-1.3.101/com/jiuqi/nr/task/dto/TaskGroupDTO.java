/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.DesignTaskGroupDefine
 *  com.jiuqi.nr.definition.internal.impl.DesignTaskGroupDefineImpl
 */
package com.jiuqi.nr.task.dto;

import com.jiuqi.nr.definition.facade.DesignTaskGroupDefine;
import com.jiuqi.nr.definition.internal.impl.DesignTaskGroupDefineImpl;

public class TaskGroupDTO {
    private String key;
    private String title;
    private String description;
    private String parentKey;
    private String parentName;

    public TaskGroupDTO() {
    }

    public TaskGroupDTO(DesignTaskGroupDefine taskGroupDefine) {
        if (taskGroupDefine != null) {
            this.key = taskGroupDefine.getKey();
            this.title = taskGroupDefine.getTitle();
            this.description = taskGroupDefine.getDescription();
            this.parentKey = taskGroupDefine.getParentKey();
        }
    }

    public TaskGroupDTO(String key, String title, String description, String parentKey, String parentName) {
        this.key = key;
        this.title = title;
        this.description = description;
        this.parentKey = parentKey;
        this.parentName = parentName;
    }

    public DesignTaskGroupDefine toTaskGroupDefine() {
        DesignTaskGroupDefineImpl taskGroupDefine = new DesignTaskGroupDefineImpl();
        taskGroupDefine.setKey(this.key);
        taskGroupDefine.setTitle(this.title);
        taskGroupDefine.setDescription(this.description);
        taskGroupDefine.setParentKey(this.parentKey);
        return taskGroupDefine;
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

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getParentKey() {
        return this.parentKey;
    }

    public void setParentKey(String parentKey) {
        this.parentKey = parentKey;
    }

    public String getParentName() {
        return this.parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }
}

