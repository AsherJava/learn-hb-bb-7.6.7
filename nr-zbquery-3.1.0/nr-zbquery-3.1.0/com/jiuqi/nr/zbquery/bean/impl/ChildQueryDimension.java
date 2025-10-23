/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.zbquery.bean.impl;

import com.jiuqi.nr.zbquery.model.DimensionAttributeField;
import com.jiuqi.nr.zbquery.model.QueryDimension;
import com.jiuqi.nr.zbquery.model.QueryDimensionType;

public class ChildQueryDimension
extends QueryDimension {
    private DimensionAttributeField dimAttribute;

    public ChildQueryDimension() {
        this.setDimensionType(QueryDimensionType.CHILD);
    }

    public DimensionAttributeField getDimAttribute() {
        return this.dimAttribute;
    }

    public void setDimAttribute(DimensionAttributeField dimAttribute) {
        this.dimAttribute = dimAttribute;
    }
}

