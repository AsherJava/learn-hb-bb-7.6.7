/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.query.sql.vo;

import java.util.List;
import java.util.Map;

public class QueryRelationDTO {
    private List<String> relationIds;
    private Map<String, Object> row;

    public List<String> getRelationIds() {
        return this.relationIds;
    }

    public void setRelationIds(List<String> relationIds) {
        this.relationIds = relationIds;
    }

    public Map<String, Object> getRow() {
        return this.row;
    }

    public void setRow(Map<String, Object> row) {
        this.row = row;
    }
}

