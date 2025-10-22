/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.instance.bean;

import java.util.List;

public class WorkflowDefineResult {
    private String id;
    private String title;
    private boolean grouped;
    private List<WorkflowDefineResult> children;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<WorkflowDefineResult> getChildren() {
        return this.children;
    }

    public void setChildren(List<WorkflowDefineResult> children) {
        this.children = children;
    }

    public boolean isGrouped() {
        return this.grouped;
    }

    public void setGrouped(boolean grouped) {
        this.grouped = grouped;
    }
}

