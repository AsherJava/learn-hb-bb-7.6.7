/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.fasterxml.jackson.annotation.JsonProperty
 */
package com.jiuqi.nr.task.api.tree;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jiuqi.nr.task.api.tree.TreeData;
import java.util.ArrayList;
import java.util.List;

public class UITreeNode<T extends TreeData> {
    private String key;
    private String parent;
    private String title;
    private boolean expand;
    private String order;
    private boolean selected;
    private boolean disabled;
    private boolean checked;
    private T data;
    private String icon;
    private boolean isLeaf = true;
    private List<UITreeNode<T>> children;

    public UITreeNode() {
    }

    public UITreeNode(T data) {
        this.data = data;
        if (data != null) {
            this.key = data.getKey();
            this.title = data.getTitle();
            this.parent = data.getParent();
        }
    }

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

    public String getTitle() {
        if (null == this.title) {
            this.title = null == this.getData() ? null : this.getData().getTitle();
        }
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isExpand() {
        return this.expand;
    }

    public void setExpand(boolean expand) {
        this.expand = expand;
    }

    @JsonIgnore
    public String getOrder() {
        return this.order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public boolean isSelected() {
        return this.selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isDisabled() {
        return this.disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public boolean isChecked() {
        return this.checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getIcon() {
        return this.icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @JsonProperty(value="isLeaf")
    public boolean isLeaf() {
        return this.isLeaf;
    }

    public void setLeaf(boolean leaf) {
        this.isLeaf = leaf;
    }

    public List<UITreeNode<T>> getChildren() {
        return this.children;
    }

    public void setChildren(List<UITreeNode<T>> children) {
        this.children = children;
    }

    public void addChildren(UITreeNode<T> children) {
        if (this.children == null) {
            this.children = new ArrayList<UITreeNode<T>>(16);
        }
        this.children.add(children);
    }
}

