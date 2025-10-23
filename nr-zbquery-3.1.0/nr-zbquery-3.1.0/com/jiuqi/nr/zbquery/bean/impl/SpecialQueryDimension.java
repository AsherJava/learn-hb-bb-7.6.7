/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.zbquery.bean.impl;

import com.jiuqi.nr.zbquery.model.DimensionAttributeField;
import com.jiuqi.nr.zbquery.model.QueryDimension;
import com.jiuqi.nr.zbquery.model.QueryDimensionType;
import java.util.Map;

public class SpecialQueryDimension
extends QueryDimension {
    private DimensionAttributeField dimAttribute;
    private Map<String, QueryDimensionType> relatedDimensionMap;

    public SpecialQueryDimension() {
    }

    public SpecialQueryDimension(QueryDimensionType dimensionType) {
        this.setDimensionType(dimensionType);
    }

    public DimensionAttributeField getDimAttribute() {
        return this.dimAttribute;
    }

    public void setDimAttribute(DimensionAttributeField dimAttribute) {
        this.dimAttribute = dimAttribute;
    }

    public Map<String, QueryDimensionType> getRelatedDimensionMap() {
        return this.relatedDimensionMap;
    }

    public void setRelatedDimensionMap(Map<String, QueryDimensionType> relatedDimensionMap) {
        this.relatedDimensionMap = relatedDimensionMap;
    }
}

