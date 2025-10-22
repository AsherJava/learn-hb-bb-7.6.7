/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuiqi.nr.unit.treebase.enumeration;

import com.jiuqi.bi.util.StringUtils;

public enum UnitTreeRangeDisplay {
    RANGE_WITH_ROOTS("range-with-roots"),
    RANGE_WITH_TREE("range-with-tree"),
    RANGE_WITH_LIST("range-with-list");

    public String value;

    private UnitTreeRangeDisplay(String value) {
        this.value = value;
    }

    public static UnitTreeRangeDisplay toDisplay(String value) {
        if (StringUtils.isNotEmpty((String)value)) {
            for (UnitTreeRangeDisplay rd : UnitTreeRangeDisplay.values()) {
                if (!value.equals(rd.value)) continue;
                return rd;
            }
        }
        return null;
    }
}

