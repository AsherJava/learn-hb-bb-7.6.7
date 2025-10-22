/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.query.block;

import com.jiuqi.nr.query.block.ShowType;
import com.jiuqi.nr.query.common.QuerySelectionType;

class DimensionExtension {
    private String filter;
    private QuerySelectionType querySelectionType = QuerySelectionType.RANGE;
    private String statisticsDimensions;
    private ShowType showType;
    public static final int ALLCHILDREN = -1;
    public static final int DIRECTCHILDREN = 0;
    private Boolean isNum;
    private String FieldConditionType;

    public void setFilter(String fl) {
        this.filter = fl;
    }

    public String getFilter() {
        return this.filter;
    }

    public QuerySelectionType getQuerySelectionType() {
        return this.querySelectionType;
    }

    public void setQuerySelectionType(QuerySelectionType querySelectionType) {
        this.querySelectionType = querySelectionType;
    }

    public String getStatisticsDimensions() {
        return this.statisticsDimensions;
    }

    public void setStatisticsDimensions(String statisticsDimensions) {
        this.statisticsDimensions = statisticsDimensions;
    }

    public void setShowType(ShowType type) {
        this.showType = type;
    }

    public ShowType getShowType() {
        return this.showType;
    }

    public Boolean getNum() {
        return this.isNum;
    }

    public void setNum(Boolean num) {
        this.isNum = num;
    }

    public String getFieldConditionType() {
        return this.FieldConditionType;
    }

    public void setFieldConditionType(String fieldConditionType) {
        this.FieldConditionType = fieldConditionType;
    }
}

