/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.events.helper;

import java.util.List;

public class UnitFilterCondition {
    private List<String> unitRangeKeys;
    private FilterType filterType = FilterType.by_self;

    public FilterType getFilterType() {
        return this.filterType;
    }

    public void setFilterType(FilterType filterType) {
        this.filterType = filterType;
    }

    public List<String> getUnitRangeKeys() {
        return this.unitRangeKeys;
    }

    public void setUnitRangeKeys(List<String> unitRangeKeys) {
        this.unitRangeKeys = unitRangeKeys;
    }

    public static enum FilterType {
        by_unit_range,
        by_leaf_node,
        by_self;

    }
}

