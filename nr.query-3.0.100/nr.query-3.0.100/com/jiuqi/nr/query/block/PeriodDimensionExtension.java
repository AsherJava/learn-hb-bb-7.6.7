/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.ObjectMapper
 */
package com.jiuqi.nr.query.block;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.nr.query.block.DimensionExtension;
import com.jiuqi.nr.query.common.QuerySelectionType;
import org.springframework.beans.factory.annotation.Autowired;

public class PeriodDimensionExtension
extends DimensionExtension {
    @Autowired
    private ObjectMapper objectMapper;

    public PeriodDimensionExtension() {
    }

    public PeriodDimensionExtension(String queryGridPropertyStr) {
        if (queryGridPropertyStr == null || "".equals(queryGridPropertyStr)) {
            this.setQuerySelectionType(QuerySelectionType.RANGE);
            this.setStatisticsDimensions("");
            return;
        }
        try {
            PeriodDimensionExtension item = (PeriodDimensionExtension)this.objectMapper.readValue(queryGridPropertyStr, PeriodDimensionExtension.class);
            this.setQuerySelectionType(item.getQuerySelectionType());
            this.setQuerySelectionType(item.getQuerySelectionType());
            this.setStatisticsDimensions(item.getStatisticsDimensions());
        }
        catch (Exception e) {
            this.setQuerySelectionType(QuerySelectionType.RANGE);
            this.setStatisticsDimensions("");
        }
    }
}

