/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonProperty
 */
package com.jiuqi.nr.summary.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;

public class TreeNode {
    private String key;
    private String title;
    @JsonProperty(value="isLeaf")
    private boolean leaf;
    private boolean disabled;
    private boolean expand;
    private boolean selected;
    private List<TreeNode> children;
    private String icon;
    private boolean draggable;
    private Object data;

    public TreeNode() {
    }

    public TreeNode(String key, String title) {
        this.key = key;
        this.title = title;
    }

    public TreeNode(String key, String title, boolean leaf, boolean disabled, boolean expand) {
        this.key = key;
        this.title = title;
        this.leaf = leaf;
        this.disabled = disabled;
        this.expand = expand;
    }

    public void addChild(TreeNode treeNode) {
        if (this.children == null) {
            this.children = new ArrayList<TreeNode>();
        }
        this.children.add(treeNode);
    }

    public void addAllChild(List<TreeNode> treeNodes) {
        if (this.children == null) {
            this.children = new ArrayList<TreeNode>();
        }
        this.children.addAll(treeNodes);
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

    public boolean isLeaf() {
        return this.leaf;
    }

    public void setLeaf(boolean leaf) {
        this.leaf = leaf;
    }

    public boolean isDisabled() {
        return this.disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public boolean isExpand() {
        return this.expand;
    }

    public void setExpand(boolean expand) {
        this.expand = expand;
    }

    public boolean isSelected() {
        return this.selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public List<TreeNode> getChildren() {
        return this.children;
    }

    public void setChildren(List<TreeNode> children) {
        this.children = children;
    }

    public String getIcon() {
        return this.icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public boolean isDraggable() {
        return this.draggable;
    }

    public void setDraggable(boolean draggable) {
        this.draggable = draggable;
    }

    public Object getData() {
        return this.data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}

