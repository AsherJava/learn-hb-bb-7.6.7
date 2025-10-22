/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonProperty
 */
package com.jiuqi.gcreport.org.api.vo.tree;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jiuqi.gcreport.org.api.vo.tree.INode;

public class ITree<E extends INode> {
    public static final String tree_node_key = "key";
    public static final String tree_node_code = "code";
    public static final String tree_node_title = "title";
    public static final String tree_node_icon = "icons";
    public static final String tree_node_level = "level";
    public static final String tree_node_childCount = "childCount";
    public static final String tree_node_isleaf = "isLeaf";
    public static final String tree_node_selected = "selected";
    public static final String tree_node_expanded = "expanded";
    public static final String tree_node_checked = "checked";
    public static final String tree_node_children = "children";
    public static final String tree_node_data = "data";
    public static final String tree_node_noDrag = "noDrag";
    public static final String tree_node_noDrop = "noDrop";
    public static final String tree_node_disabled = "disabled";
    @JsonProperty(value="key", index=1)
    private String key;
    @JsonProperty(value="code", index=2)
    private String code;
    @JsonProperty(value="title", index=3)
    private String title;
    @JsonProperty(value="isLeaf", index=4)
    private boolean isLeaf = false;
    @JsonProperty(value="icons", index=5)
    private String icons;
    @JsonProperty(value="level", index=6)
    private int level = 0;
    @JsonProperty(value="childCount", index=7)
    private int childCount = 0;
    @JsonProperty(value="selected", index=8)
    private boolean selected = false;
    @JsonProperty(value="expanded", index=9)
    private boolean expanded = false;
    @JsonProperty(value="checked", index=10)
    private boolean checked = false;
    @JsonProperty(value="noDrag", index=11)
    private boolean noDrag = false;
    @JsonProperty(value="noDrop", index=12)
    private boolean noDrop = false;
    @JsonProperty(value="disabled", index=13)
    private boolean disabled = false;
    private E data;

    public ITree(E data) {
        this.data = data;
        this.init();
    }

    public ITree() {
        this(null);
    }

    private void init() {
        if (this.data != null) {
            this.key = this.data.getKey();
            this.code = this.data.getCode();
            this.title = this.data.getTitle();
            this.isLeaf = this.data.isLeaf();
        }
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

    public String getIcons() {
        return this.icons;
    }

    public void setIcons(String icons) {
        this.icons = icons;
    }

    public boolean isLeaf() {
        return this.isLeaf;
    }

    public void setLeaf(boolean isLeaf) {
        this.isLeaf = isLeaf;
    }

    public boolean isSelected() {
        return this.selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isExpanded() {
        return this.expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public boolean isChecked() {
        return this.checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public E getData() {
        return this.data;
    }

    public void setData(E data) {
        this.data = data;
    }

    public int getLevel() {
        return this.level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getChildCount() {
        return this.childCount;
    }

    public void setChildCount(int childCount) {
        this.childCount = childCount;
    }

    public boolean isNoDrag() {
        return this.noDrag;
    }

    public void setNoDrag(boolean noDrag) {
        this.noDrag = noDrag;
    }

    public boolean isNoDrop() {
        return this.noDrop;
    }

    public void setNoDrop(boolean noDrop) {
        this.noDrop = noDrop;
    }

    public boolean isDisabled() {
        return this.disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }
}

