/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.transmission.data.domain;

import java.util.List;

public class TaskNodeParam {
    private String key;
    private String title;
    private boolean expand;
    private boolean checked;
    private List<TaskNodeParam> children;

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

    public List<TaskNodeParam> getChildren() {
        return this.children;
    }

    public void setChildren(List<TaskNodeParam> children) {
        this.children = children;
    }
}

