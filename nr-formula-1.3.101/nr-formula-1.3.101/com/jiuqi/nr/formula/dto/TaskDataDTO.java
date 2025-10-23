/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.task.api.tree.TreeData
 */
package com.jiuqi.nr.formula.dto;

import com.jiuqi.nr.task.api.tree.TreeData;
import java.util.List;

public class TaskDataDTO
implements TreeData {
    private String key;
    private String parent;
    private String title;
    private String code;
    private String parentTitle;
    private List<TaskDataDTO> children;

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getParent() {
        return this.parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getParentTitle() {
        return this.parentTitle;
    }

    public void setParentTitle(String parentTitle) {
        this.parentTitle = parentTitle;
    }

    public List<TaskDataDTO> getChildren() {
        return this.children;
    }

    public void setChildren(List<TaskDataDTO> children) {
        this.children = children;
    }
}

