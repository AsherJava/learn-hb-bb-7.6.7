/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dafafill.web.vo;

import java.util.List;

public class TreeSelectNode {
    private String id;
    private String label;
    private List<TreeSelectNode> children;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return this.label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<TreeSelectNode> getChildren() {
        return this.children;
    }

    public void setChildren(List<TreeSelectNode> children) {
        this.children = children;
    }
}

