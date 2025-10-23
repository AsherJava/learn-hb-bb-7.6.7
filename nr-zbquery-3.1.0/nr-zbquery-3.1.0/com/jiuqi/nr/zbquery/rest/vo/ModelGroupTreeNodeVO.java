/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.zbquery.rest.vo;

import java.util.List;

public class ModelGroupTreeNodeVO {
    private String id;
    private String label;
    private List<ModelGroupTreeNodeVO> children;
    private boolean isDefaultExpanded;

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

    public List<ModelGroupTreeNodeVO> getChildren() {
        return this.children;
    }

    public void setChildren(List<ModelGroupTreeNodeVO> children) {
        this.children = children;
    }

    public boolean getIsDefaultExpanded() {
        return this.isDefaultExpanded;
    }

    public void setIsDefaultExpanded(boolean isDefaultExpanded) {
        this.isDefaultExpanded = isDefaultExpanded;
    }
}

