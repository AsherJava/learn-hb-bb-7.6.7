/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.instance.bean;

import java.util.List;

public class WorkflowTreeNodeInfo {
    private String key;
    private String code;
    private String title;
    private boolean isLeaf;
    private List<WorkflowTreeNodeInfo> children;

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

    public boolean isLeaf() {
        return this.isLeaf;
    }

    public void setLeaf(boolean isLeaf) {
        this.isLeaf = isLeaf;
    }

    public List<WorkflowTreeNodeInfo> getChildren() {
        return this.children;
    }

    public void setChildren(List<WorkflowTreeNodeInfo> children) {
        this.children = children;
    }
}

