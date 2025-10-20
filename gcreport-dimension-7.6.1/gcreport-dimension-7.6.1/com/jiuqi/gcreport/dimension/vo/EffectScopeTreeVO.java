/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.dimension.vo;

import java.util.List;

public class EffectScopeTreeVO {
    private String id;
    private String label;
    private String des;
    private List<EffectScopeTreeVO> children;
    private Boolean isLeaf;
    private Boolean isDisabled;

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

    public String getDes() {
        return this.des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public List<EffectScopeTreeVO> getChildren() {
        return this.children;
    }

    public void setChildren(List<EffectScopeTreeVO> children) {
        this.children = children;
    }

    public Boolean getLeaf() {
        return this.isLeaf;
    }

    public void setLeaf(Boolean leaf) {
        this.isLeaf = leaf;
    }

    public Boolean getDisabled() {
        return this.isDisabled;
    }

    public void setDisabled(Boolean disabled) {
        this.isDisabled = disabled;
    }
}

