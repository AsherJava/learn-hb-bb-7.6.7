/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.query.tree.vo;

import java.util.ArrayList;
import java.util.List;

public class MenuTreeVO {
    private String id;
    private String code;
    private String title;
    private String nodeType;
    private String description;
    private String parentId;
    private String datasourceCode;
    private String type;
    private Integer sortOrder;
    private String parents;
    private List<MenuTreeVO> children;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNodeType() {
        return this.nodeType;
    }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getParentId() {
        return this.parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getDatasourceCode() {
        return this.datasourceCode;
    }

    public void setDatasourceCode(String datasourceCode) {
        this.datasourceCode = datasourceCode;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getSortOrder() {
        return this.sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public List<MenuTreeVO> getChildren() {
        return this.children;
    }

    public void setChildren(List<MenuTreeVO> children) {
        this.children = children;
    }

    public String toString() {
        return "MenuTreeVO [id=" + this.id + ", code=" + this.code + ", title=" + this.title + ", nodeType=" + this.nodeType + ", description=" + this.description + ", parentId=" + this.parentId + ", datasourceCode=" + this.datasourceCode + ", type=" + this.type + ", sortOrder=" + this.sortOrder + ", children=" + this.children + "]";
    }

    public void addChildren(MenuTreeVO vo) {
        if (this.children == null) {
            this.children = new ArrayList<MenuTreeVO>();
        }
        this.children.add(vo);
    }

    public String getParents() {
        return this.parents;
    }

    public void setParents(String parents) {
        this.parents = parents;
    }
}

