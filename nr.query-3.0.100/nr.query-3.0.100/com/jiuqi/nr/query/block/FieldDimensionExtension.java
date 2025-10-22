/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.ObjectMapper
 */
package com.jiuqi.nr.query.block;

import com.fasterxml.jackson.databind.ObjectMapper;

public class FieldDimensionExtension {
    private int placement;

    public int getPlacement() {
        return this.placement;
    }

    public void setPlacement(int placement) {
        this.placement = placement;
    }

    public FieldDimensionExtension() {
    }

    public FieldDimensionExtension(String dimensionPropertyStr) {
        if (dimensionPropertyStr == null || "".equals(dimensionPropertyStr)) {
            this.placement = 0;
            return;
        }
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            FieldDimensionExtension item = (FieldDimensionExtension)objectMapper.readValue(dimensionPropertyStr, FieldDimensionExtension.class);
            this.setPlacement(item.placement);
        }
        catch (Exception e) {
            this.placement = 0;
        }
    }
}

