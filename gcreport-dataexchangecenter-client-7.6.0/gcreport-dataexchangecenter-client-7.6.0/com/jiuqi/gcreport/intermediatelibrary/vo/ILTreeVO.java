/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.intermediatelibrary.vo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ILTreeVO {
    private String title;
    private String id;
    private List<ILTreeVO> children = new ArrayList<ILTreeVO>();
    private String taskId;
    private String parentId;
    private Set<String> fieldIdSet = new HashSet<String>();
    private String code;

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Set<String> getFieldIdSet() {
        return this.fieldIdSet;
    }

    public void setFieldIdSet(Set<String> fieldIdSet) {
        this.fieldIdSet = fieldIdSet;
    }

    public String getParentId() {
        return this.parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<ILTreeVO> getChildren() {
        return this.children;
    }

    public void setChildren(List<ILTreeVO> children) {
        this.children = children;
    }
}

