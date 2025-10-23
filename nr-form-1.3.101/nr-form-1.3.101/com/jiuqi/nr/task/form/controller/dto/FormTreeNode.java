/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.form.controller.dto;

import java.util.List;

public class FormTreeNode {
    private String key;
    private String title;
    private List<FormTreeNode> children;
    private boolean disabled = false;
    private String nodeType;

    public String getNodeType() {
        return this.nodeType;
    }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
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

    public List<FormTreeNode> getChildren() {
        return this.children;
    }

    public void setChildren(List<FormTreeNode> children) {
        this.children = children;
    }

    public boolean isDisabled() {
        return this.disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }
}

