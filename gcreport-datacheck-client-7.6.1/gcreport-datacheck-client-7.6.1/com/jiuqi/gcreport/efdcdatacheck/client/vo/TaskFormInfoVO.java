/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.efdcdatacheck.client.vo;

import java.util.List;

public class TaskFormInfoVO {
    private String id;
    private String title;
    private String code;
    private String name;
    private String description;
    private String groupId;
    private List<TaskFormInfoVO> children;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGroupId() {
        return this.groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public List<TaskFormInfoVO> getChildren() {
        return this.children;
    }

    public void setChildren(List<TaskFormInfoVO> children) {
        this.children = children;
    }
}

