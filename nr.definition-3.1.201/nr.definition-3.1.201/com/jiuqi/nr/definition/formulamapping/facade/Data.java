/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.formulamapping.facade;

public class Data {
    private String title;
    private String code;
    private String parentKey;
    private String key;
    private String icon;
    private boolean expended = false;
    private boolean noDrag;
    private boolean isLeaf = false;
    private boolean selected = false;

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getParentKey() {
        return this.parentKey;
    }

    public void setParentKey(String parentKey) {
        this.parentKey = parentKey;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getIcon() {
        return this.icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public boolean isExpended() {
        return this.expended;
    }

    public void setExpended(boolean expended) {
        this.expended = expended;
    }

    public boolean getIsLeaf() {
        return this.isLeaf;
    }

    public void setIsLeaf(boolean isLeaf) {
        this.isLeaf = isLeaf;
    }

    public boolean isSelected() {
        return this.selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}

