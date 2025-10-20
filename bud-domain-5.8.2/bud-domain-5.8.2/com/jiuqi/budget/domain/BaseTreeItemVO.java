/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.budget.domain;

public abstract class BaseTreeItemVO {
    private String id;
    private String text;
    private boolean expand;
    private boolean checked;
    private boolean selected;
    private boolean leaf;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isExpand() {
        return this.expand;
    }

    public void setExpand(boolean expand) {
        this.expand = expand;
    }

    public boolean isChecked() {
        return this.checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean isSelected() {
        return this.selected;
    }

    public boolean isLeaf() {
        return this.leaf;
    }

    public void setLeaf(boolean leaf) {
        this.leaf = leaf;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String toString() {
        return "BaseTreeItemVO{id='" + this.id + '\'' + ", text='" + this.text + '\'' + ", expand=" + this.expand + ", checked=" + this.checked + ", selected=" + this.selected + ", leaf=" + this.leaf + '}';
    }
}

