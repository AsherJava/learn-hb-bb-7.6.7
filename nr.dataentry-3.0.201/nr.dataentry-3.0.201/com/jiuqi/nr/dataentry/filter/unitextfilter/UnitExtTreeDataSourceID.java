/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext
 *  com.jiuiqi.nr.unit.treebase.source.IUnitTreeDataSourceID
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.nr.dataentry.filter.unitextfilter;

import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext;
import com.jiuiqi.nr.unit.treebase.source.IUnitTreeDataSourceID;
import com.jiuqi.bi.util.StringUtils;

public class UnitExtTreeDataSourceID
implements IUnitTreeDataSourceID {
    public static final String SOURCE_ID = "unitext-selector-tree-data-source";

    public String getDataSourceID(IUnitTreeContext context) {
        if (StringUtils.isNotEmpty((String)context.getDataSourceId()) && context.getITreeContext().getDataSourceId().equals(SOURCE_ID)) {
            return SOURCE_ID;
        }
        return "unit-selector-tree-data-source";
    }

    public String getContextMenuSourceID(IUnitTreeContext context) {
        return "unit-selector-tree-data-source";
    }
}

