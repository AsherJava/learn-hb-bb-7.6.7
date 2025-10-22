/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext
 *  com.jiuiqi.nr.unit.treebase.source.IUnitTreeDataSourceID
 */
package com.jiuqi.nr.dataentry.filter.checkResultUnitFilter;

import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext;
import com.jiuiqi.nr.unit.treebase.source.IUnitTreeDataSourceID;
import org.springframework.stereotype.Component;

@Component
public class CheckResultUnitTreeDataSourceID
implements IUnitTreeDataSourceID {
    public static final String SOURCE_ID = "checkResult-unit-selector-tree-data-source";

    public String getDataSourceID(IUnitTreeContext context) {
        if (context.getCustomVariable() != null && context.getCustomVariable().toMap().containsKey("checkAsyncTaskKey")) {
            return SOURCE_ID;
        }
        if (context.getCustomVariable() != null && context.getCustomVariable().toMap().containsKey("dataSourceId") && context.getCustomVariable().toMap().get("dataSourceId").equals("unitext-selector-tree-data-source")) {
            return "unitext-selector-tree-data-source";
        }
        return context.getITreeContext().getDataSourceId();
    }

    public String getContextMenuSourceID(IUnitTreeContext context) {
        return context.getITreeContext().getDataSourceId();
    }
}

