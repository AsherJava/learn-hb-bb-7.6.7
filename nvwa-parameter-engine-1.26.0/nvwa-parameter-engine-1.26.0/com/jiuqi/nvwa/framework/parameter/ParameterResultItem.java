/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.framework.parameter;

import java.io.Serializable;

public class ParameterResultItem
implements Serializable {
    private static final long serialVersionUID = 1L;
    private Object value;
    private String title;
    private String binding;
    private String parent = null;
    private String path = null;
    private int level = 0;
    private boolean leaf = true;

    public ParameterResultItem() {
    }

    public ParameterResultItem(Object value) {
        this.value = value;
    }

    public ParameterResultItem(Object value, String title) {
        this.value = value;
        this.title = title;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Object getValue() {
        return this.value;
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

    public int getLevel() {
        return this.level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean isLeaf() {
        return this.leaf;
    }

    public void setLeaf(boolean leaf) {
        this.leaf = leaf;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return this.path;
    }

    public void setBinding(String binding) {
        this.binding = binding;
    }

    public String getBinding() {
        return this.binding;
    }

    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("{ value:").append(this.value).append(", title:").append(this.title).append(", parent:").append(this.parent);
        buf.append(", leaf:").append(this.leaf);
        if (this.path != null) {
            buf.append(", path:").append(this.path);
        }
        return buf.toString();
    }
}

