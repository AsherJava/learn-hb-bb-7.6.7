/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 */
package com.jiuqi.gcreport.billextract.client.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(value=JsonInclude.Include.NON_NULL)
public class TreeVO
implements Serializable {
    private static final long serialVersionUID = -9034255639657227697L;
    private String id;
    private String code;
    private String name;
    private String title;
    private Boolean leaf;
    private String parentId;
    private String parentCode;
    private String nodeType;
    private Boolean expand;
    private List<TreeVO> children = new ArrayList<TreeVO>();
    private Map<String, Serializable> attributes = new HashMap<String, Serializable>();

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

    public List<TreeVO> getChildren() {
        return this.children;
    }

    public void setChildren(List<TreeVO> children) {
        this.children = children;
    }

    public void addChild(TreeVO child) {
        this.children.add(child);
    }

    public Map<String, Serializable> getAttributes() {
        return this.attributes;
    }

    public void setAttributes(Map<String, Serializable> attributes) {
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

    public Boolean getExpand() {
        return this.expand;
    }

    public void setExpand(Boolean expand) {
        this.expand = expand;
    }

    public String toString() {
        return "TreeVO [id=" + this.id + ", code=" + this.code + ", title=" + this.title + ", leaf=" + this.leaf + ", parentId=" + this.parentId + ", parentCode=" + this.parentCode + ", children=" + this.children + ", attributes=" + this.attributes + "]";
    }
}

