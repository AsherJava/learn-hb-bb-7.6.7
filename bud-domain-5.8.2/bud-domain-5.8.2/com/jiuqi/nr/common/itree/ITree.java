/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.annotation.JsonSerialize
 */
package com.jiuqi.nr.common.itree;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jiuqi.nr.common.itree.INode;
import com.jiuqi.nr.common.itree.ITreeBreadthFirstIterator;
import com.jiuqi.nr.common.itree.ITreeDepthFirstIterator;
import com.jiuqi.nr.common.itree.ITreeJsonSerializer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@JsonSerialize(using=ITreeJsonSerializer.class)
public class ITree<E extends INode> {
    private String key;
    private String code;
    private String title;
    private String[] icons;
    private String color;
    private int level = 0;
    private boolean isLeaf = false;
    private boolean selected = false;
    private boolean expanded = false;
    private boolean checked = false;
    private boolean noDrag = false;
    private boolean noDrop = false;
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
            this.key = this.data.getKey();
            this.code = this.data.getCode();
            this.title = this.data.getTitle();
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

    public Iterator<ITree<E>> iterator(traverPloy ploy) {
        Iterator iterator = new ITreeDepthFirstIterator(this);
        ploy = ploy != null ? ploy : traverPloy.DEPTH_FIRST;
        switch (ploy) {
            case DEPTH_FIRST: {
                break;
            }
            case BREADTH_FIRST: {
                iterator = new ITreeBreadthFirstIterator(this);
                break;
            }
        }
        return iterator;
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

    public String[] getIcons() {
        return this.icons;
    }

    public void setIcons(String[] icons) {
        this.icons = icons;
    }

    public String getColor() {
        return this.color;
    }

    public void setColor(String color) {
        this.color = color;
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

    public static enum traverPloy {
        DEPTH_FIRST,
        BREADTH_FIRST;

    }
}

