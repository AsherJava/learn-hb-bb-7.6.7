/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonProperty
 */
package com.jiuqi.gcreport.basedata.api.itree;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jiuqi.gcreport.basedata.api.itree.INode;
import java.util.ArrayList;
import java.util.List;

public class ITree<E extends INode> {
    @JsonProperty(value="key")
    private String key;
    @JsonProperty(value="code")
    private String code;
    @JsonProperty(value="title")
    private String title;
    @JsonProperty(value="isLeaf")
    private boolean isLeaf = false;
    @JsonProperty(value="icons")
    private String icons;
    @JsonProperty(value="level")
    private int level = 0;
    @JsonProperty(value="childCount")
    private int childCount = 0;
    @JsonProperty(value="selected")
    private boolean selected = false;
    @JsonProperty(value="expanded")
    private boolean expanded = false;
    @JsonProperty(value="checked")
    private boolean checked = false;
    @JsonProperty(value="noDrag")
    private boolean noDrag = false;
    @JsonProperty(value="noDrop")
    private boolean noDrop = false;
    @JsonProperty(value="disabled")
    private boolean disabled = false;
    private E data;
    private ITree<E> parent;
    private List<ITree<E>> children;

    public ITree(E data) {
        this.data = data;
        this.parent = null;
        this.children = null;
        this.init();
    }

    public ITree() {
        this(null);
    }

    private void init() {
        if (this.data != null) {
            this.key = this.data.getCode();
            this.code = this.data.getCode();
            this.title = this.data.getTitle();
            this.isLeaf = this.data.isLeaf();
        }
    }

    public ITree<E> appendChild(E child) {
        ITree<E> node = new ITree<E>(child);
        this.appendChild(node);
        return node;
    }

    public ITree<E> appendChild(int index, E child) {
        ITree<E> node = new ITree<E>(child);
        this.appendChild(index, node);
        return node;
    }

    public void appendChild(ITree<E> child) {
        if (this.children == null) {
            this.children = new ArrayList<ITree<E>>();
        }
        child.parent = this;
        child.level = this.level + 1;
        this.children.add(child);
    }

    public void appendChild(int index, ITree<E> child) {
        if (this.children == null) {
            this.children = new ArrayList<ITree<E>>();
        }
        child.parent = this;
        child.level = this.level + 1;
        this.children.add(index, child);
    }

    public boolean hasChildren() {
        return this.children != null && !this.children.isEmpty();
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

    public boolean isRoot() {
        return this.parent == null;
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

    public ITree<E> getParent() {
        return this.parent;
    }

    public void setParent(ITree<E> parent) {
        this.parent = parent;
    }

    public List<ITree<E>> getChildren() {
        return this.children;
    }

    public void setChildren(List<ITree<E>> children) {
        this.children = children;
    }

    public int getLevel() {
        return this.level;
    }

    public void setLevel(int level) {
        this.level = level;
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

