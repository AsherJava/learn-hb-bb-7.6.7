/*
 * Decompiled with CFR 0.152.
 */
package com.jiuiqi.nr.unit.treebase.context;

import com.jiuiqi.nr.unit.treebase.context.impl.UnitTreeContextData;

public interface IUnitTreeContextCache {
    public UnitTreeContextData getUnitTreeContextData(String var1);

    public void putContextData(String var1, UnitTreeContextData var2);
}

