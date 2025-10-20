/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.query.sql.vo;

public class QueryRelationCheckResultVO {
    private String relationId;
    private String warningText;

    public QueryRelationCheckResultVO(String relationId, String warningText) {
        this.relationId = relationId;
        this.warningText = warningText;
    }

    public String getRelationId() {
        return this.relationId;
    }

    public void setRelationId(String relationId) {
        this.relationId = relationId;
    }

    public String getWarningText() {
        return this.warningText;
    }

    public void setWarningText(String warningText) {
        this.warningText = warningText;
    }
}

