/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext
 */
package com.jiuqi.nr.unit.uselector.source;

import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext;
import com.jiuqi.nr.unit.uselector.source.IUSelectorEntityRowProvider;

public interface IUSelectorDataSource {
    public String getSourceId();

    public IUSelectorEntityRowProvider getUSelectorEntityRowProvider(IUnitTreeContext var1);
}

