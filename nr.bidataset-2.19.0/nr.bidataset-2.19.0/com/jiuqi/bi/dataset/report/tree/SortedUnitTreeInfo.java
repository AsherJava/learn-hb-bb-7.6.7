/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.dataset.report.tree;

import java.util.List;

public class SortedUnitTreeInfo {
    private String unitKey;
    private List<String> parents;
    private Object order;

    public String getUnitKey() {
        return this.unitKey;
    }

    public SortedUnitTreeInfo(String unitKey, Object order) {
        this.unitKey = unitKey;
        this.order = order;
    }

    public SortedUnitTreeInfo(String unitKey, List<String> parents, Object order) {
        this.unitKey = unitKey;
        this.parents = parents;
        this.order = order;
    }

    public List<String> getParents() {
        return this.parents;
    }

    public Object getOrder() {
        return this.order;
    }

    public void setUnitKey(String unitKey) {
        this.unitKey = unitKey;
    }

    public void setParents(List<String> parents) {
        this.parents = parents;
    }

    public void setOrder(Object order) {
        this.order = order;
    }

    public String toString() {
        return "ReportUnitTreeInfo [unitKey=" + this.unitKey + ", parents=" + this.parents + ", order=" + this.order + "]";
    }
}

