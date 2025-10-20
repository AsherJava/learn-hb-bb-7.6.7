/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.query.sql.vo;

import java.util.List;
import java.util.Map;

public class TreeRow {
    private Map<String, Object> rowValues;
    private List<TreeRow> children;

    public Map<String, Object> getRowValues() {
        return this.rowValues;
    }

    public void setRowValues(Map<String, Object> rowValues) {
        this.rowValues = rowValues;
    }

    public List<TreeRow> getChildren() {
        return this.children;
    }

    public void setChildren(List<TreeRow> children) {
        this.children = children;
    }
}

