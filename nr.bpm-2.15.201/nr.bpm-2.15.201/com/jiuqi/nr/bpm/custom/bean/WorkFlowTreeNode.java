/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.custom.bean;

import java.util.List;

public class WorkFlowTreeNode {
    private Object data;
    private boolean expand = false;
    private String key;
    private String title;
    private boolean isGroup = false;
    private List<WorkFlowTreeNode> children;
    private boolean hasChildern = false;
    private boolean selected = false;
    private boolean disabled = false;
    private boolean checked = false;
    private String parentKey;

    public boolean isSelected() {
        return this.selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isHasChildern() {
        return this.hasChildern;
    }

    public void setHasChildern(boolean hasChildern) {
        this.hasChildern = hasChildern;
    }

    public boolean isGroup() {
        return this.isGroup;
    }

    public void setGroup(boolean isGroup) {
        this.isGroup = isGroup;
    }

    public Object getData() {
        return this.data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<WorkFlowTreeNode> getChildren() {
        return this.children;
    }

    public void setChildren(List<WorkFlowTreeNode> children) {
        this.children = children;
    }

    public boolean isExpand() {
        return this.expand;
    }

    public void setExpand(boolean expand) {
        this.expand = expand;
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

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getParentKey() {
        return this.parentKey;
    }

    public void setParentKey(String parentKey) {
        this.parentKey = parentKey;
    }
}

