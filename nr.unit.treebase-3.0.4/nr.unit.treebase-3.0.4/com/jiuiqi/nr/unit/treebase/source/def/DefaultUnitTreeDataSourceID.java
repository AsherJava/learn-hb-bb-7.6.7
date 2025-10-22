/*
 * Decompiled with CFR 0.152.
 */
package com.jiuiqi.nr.unit.treebase.source.def;

import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext;
import com.jiuiqi.nr.unit.treebase.source.IUnitTreeDataSourceID;

public class DefaultUnitTreeDataSourceID
implements IUnitTreeDataSourceID {
    @Override
    public String getDataSourceID(IUnitTreeContext context) {
        return context.getITreeContext().getDataSourceId();
    }

    @Override
    public String getContextMenuSourceID(IUnitTreeContext context) {
        return context.getITreeContext().getDataSourceId();
    }
}

