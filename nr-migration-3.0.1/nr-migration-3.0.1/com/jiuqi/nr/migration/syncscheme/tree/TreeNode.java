/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonProperty
 */
package com.jiuqi.nr.migration.syncscheme.tree;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jiuqi.nr.migration.syncscheme.tree.ITreeNodeData;
import java.util.List;

public class TreeNode<T extends ITreeNodeData> {
    private String key;
    private String title;
    private String parent;
    private T data;
    private boolean selected;
    private boolean expand;
    private boolean isLeaf;
    private String icon;
    private List<TreeNode<T>> children;

    public TreeNode() {
    }

    public TreeNode(T data) {
        this.data = data;
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

    public String getParent() {
        return this.parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSelected() {
        return this.selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isExpand() {
        return this.expand;
    }

    public void setExpand(boolean expand) {
        this.expand = expand;
    }

    @JsonProperty(value="isLeaf")
    public boolean isLeaf() {
        return this.isLeaf;
    }

    public void setLeaf(boolean leaf) {
        this.isLeaf = leaf;
    }

    public String getIcon() {
        return this.icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public List<TreeNode<T>> getChildren() {
        return this.children;
    }

    public void setChildren(List<TreeNode<T>> children) {
        this.children = children;
    }
}

