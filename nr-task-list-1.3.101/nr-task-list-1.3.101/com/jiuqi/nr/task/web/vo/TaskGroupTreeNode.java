/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.DesignTaskGroupDefine
 *  com.jiuqi.nr.task.api.tree.TreeData
 */
package com.jiuqi.nr.task.web.vo;

import com.jiuqi.nr.definition.facade.DesignTaskGroupDefine;
import com.jiuqi.nr.task.api.tree.TreeData;
import com.jiuqi.nr.task.dto.TaskGroupDTO;

public class TaskGroupTreeNode
implements TreeData {
    private String key;
    private String title;
    private String description;
    private String parentKey;
    private String parentName;
    private boolean isFirst;
    private boolean isLast;

    public TaskGroupTreeNode() {
    }

    public TaskGroupTreeNode(DesignTaskGroupDefine designTaskGroupDefine) {
        if (designTaskGroupDefine != null) {
            this.key = designTaskGroupDefine.getKey();
            this.title = designTaskGroupDefine.getTitle();
            this.description = designTaskGroupDefine.getDescription();
            this.parentKey = designTaskGroupDefine.getParentKey();
        }
    }

    public TaskGroupTreeNode(TaskGroupDTO taskGroupDTO) {
        if (taskGroupDTO != null) {
            this.key = taskGroupDTO.getKey();
            this.title = taskGroupDTO.getTitle();
            this.description = taskGroupDTO.getDescription();
            this.parentKey = taskGroupDTO.getParentKey();
        }
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

    public boolean isFirst() {
        return this.isFirst;
    }

    public void setFirst(boolean first) {
        this.isFirst = first;
    }

    public boolean isLast() {
        return this.isLast;
    }

    public void setLast(boolean last) {
        this.isLast = last;
    }
}

