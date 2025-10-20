/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.dataset;

import com.jiuqi.bi.dataset.model.field.AggregationType;

public class AggrMeasureItem {
    private String fieldName;
    private String alias;
    private String title;
    private AggregationType aggrType;

    public AggrMeasureItem() {
    }

    public AggrMeasureItem(String fieldName, String alias, String title, AggregationType aggrType) {
        this.fieldName = fieldName;
        this.alias = alias;
        this.title = title;
        this.aggrType = aggrType;
    }

    public String getFieldName() {
        return this.fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getAlias() {
        return this.alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }

    public AggregationType getAggrType() {
        return this.aggrType;
    }

    public void setAggrType(AggregationType aggrType) {
        this.aggrType = aggrType;
    }

    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("[").append(this.fieldName).append(",").append(this.title);
        buf.append(",").append(this.aggrType == null ? "null" : this.aggrType.title());
        buf.append("]");
        return buf.toString();
    }
}

