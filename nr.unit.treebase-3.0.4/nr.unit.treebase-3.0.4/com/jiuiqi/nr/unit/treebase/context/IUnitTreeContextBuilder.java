/*
 * Decompiled with CFR 0.152.
 */
package com.jiuiqi.nr.unit.treebase.context;

import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext;
import com.jiuiqi.nr.unit.treebase.context.impl.UnitTreeContextData;
import com.jiuiqi.nr.unit.treebase.source.IUnitTreeDataSource;

public interface IUnitTreeContextBuilder {
    public IUnitTreeContext createTreeContext(UnitTreeContextData var1);

    public IUnitTreeContext createTreeContext(IUnitTreeContext var1, IUnitTreeDataSource var2);
}

