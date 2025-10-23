/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonProperty
 */
package com.jiuqi.nr.summary.tree.core;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class TreeNode {
    private String key;
    private String code;
    private String title;
    private String icon;
    private List<TreeNode> children;
    private boolean expand;
    private boolean selected;
    private boolean checked;
    private boolean disabled;
    @JsonProperty(value="isLeaf")
    private boolean leaf;
    private boolean draggable;
    private Object data;

    public TreeNode() {
    }

    public TreeNode(String key, String code, String title) {
        this.key = key;
        this.code = code;
        this.title = title;
    }

    public TreeNode(String key, String code, String title, String icon) {
        this(key, code, title);
        this.icon = icon;
    }

    public TreeNode(String key, String code, String title, String icon, Object data) {
        this(key, code, title, icon);
        this.data = data;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
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

    public String getIcon() {
        return this.icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public List<TreeNode> getChildren() {
        return this.children;
    }

    public void setChildren(List<TreeNode> children) {
        this.children = children;
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

    public boolean isChecked() {
        return this.checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean isDisabled() {
        return this.disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public boolean isLeaf() {
        return this.leaf;
    }

    public void setLeaf(boolean leaf) {
        this.leaf = leaf;
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

