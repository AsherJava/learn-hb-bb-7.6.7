/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.settings.vo.source;

import java.util.List;

public class CustomWorkflowDefine {
    private String key;
    private String title;
    private boolean isLeaf;
    private List<CustomWorkflowDefine> children;

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

    public boolean getIsLeaf() {
        return this.isLeaf;
    }

    public void setIsLeaf(boolean leaf) {
        this.isLeaf = leaf;
    }

    public List<CustomWorkflowDefine> getChildren() {
        return this.children;
    }

    public void setChildren(List<CustomWorkflowDefine> children) {
        this.children = children;
    }
}

