/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 */
package com.jiuqi.dc.base.common.intf.impl;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(value=JsonInclude.Include.NON_NULL)
public class TreeDTO {
    private String id;
    private String code;
    private String name;
    private String title;
    private Boolean leaf;
    private String parentId;
    private String parentCode;
    private String nodeType;
    private List<TreeDTO> children = new ArrayList<TreeDTO>();
    private Map<String, Object> attributes = new HashMap<String, Object>();

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

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getLeaf() {
        return this.leaf;
    }

    public void setLeaf(Boolean leaf) {
        this.leaf = leaf;
    }

    public List<TreeDTO> getChildren() {
        return this.children;
    }

    public void setChildren(List<TreeDTO> children) {
        this.children = children;
    }

    public void addChild(TreeDTO child) {
        this.children.add(child);
    }

    public Map<String, Object> getAttributes() {
        return this.attributes;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public String getParentId() {
        return this.parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getParentCode() {
        return this.parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public String getNodeType() {
        return this.nodeType;
    }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }

    public String toString() {
        return "TreeDTO [id=" + this.id + ", code=" + this.code + ", title=" + this.title + ", leaf=" + this.leaf + ", parentId=" + this.parentId + ", parentCode=" + this.parentCode + ", children=" + this.children + ", attributes=" + this.attributes + "]";
    }
}

