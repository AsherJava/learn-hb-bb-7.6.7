/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.zbquery.bean.impl;

import com.jiuqi.nr.zbquery.model.QueryDimensionType;
import com.jiuqi.nr.zbquery.model.ZBField;
import java.util.Map;

public class ZBFieldEx
extends ZBField {
    private Map<String, QueryDimensionType> relatedDimensionMap;

    public Map<String, QueryDimensionType> getRelatedDimensionMap() {
        return this.relatedDimensionMap;
    }

    public void setRelatedDimensionMap(Map<String, QueryDimensionType> relatedDimensionMap) {
        this.relatedDimensionMap = relatedDimensionMap;
    }
}

