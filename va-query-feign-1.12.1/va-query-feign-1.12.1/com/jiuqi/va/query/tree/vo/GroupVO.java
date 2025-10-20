/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.query.tree.vo;

public class GroupVO {
    private String id;
    private String code;
    private String title;
    private String description;
    private Integer sortOrder;
    private String parentId;

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

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getSortOrder() {
        return this.sortOrder;
    }

    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getParentId() {
        return this.parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String toString() {
        return "GroupVO [id=" + this.id + ", code=" + this.code + ", title=" + this.title + ", description=" + this.description + ", sortOrder=" + this.sortOrder + ", parentId=" + this.parentId + "]";
    }
}

