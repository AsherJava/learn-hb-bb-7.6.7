/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datacrud.impl;

import java.util.ArrayList;
import java.util.List;

public class EntityData {
    private String id;
    private String rowCaption;
    private String code;
    private String title;
    private double order;
    private boolean leaf;
    private int childrenCount;
    private String parentId;
    private List<String> parents = new ArrayList<String>();
    private List<String> data = new ArrayList<String>();
    private List<EntityData> children = new ArrayList<EntityData>();

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRowCaption() {
        return this.rowCaption;
    }

    public void setRowCaption(String rowCaption) {
        this.rowCaption = rowCaption;
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

    public double getOrder() {
        return this.order;
    }

    public void setOrder(double order) {
        this.order = order;
    }

    public boolean isLeaf() {
        return this.leaf;
    }

    public void setLeaf(boolean leaf) {
        this.leaf = leaf;
    }

    public int getChildrenCount() {
        return this.childrenCount;
    }

    public void setChildrenCount(int childrenCount) {
        this.childrenCount = childrenCount;
    }

    public String getParentId() {
        return this.parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public List<String> getParents() {
        return this.parents;
    }

    public void setParents(List<String> parents) {
        this.parents = parents;
    }

    public List<String> getData() {
        return this.data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }

    public List<EntityData> getChildren() {
        return this.children;
    }

    public void setChildren(List<EntityData> children) {
        this.children = children;
    }
}

