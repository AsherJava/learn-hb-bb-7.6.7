/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 */
package com.jiuqi.nr.datacrud.impl.crud.inner;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import java.util.List;

public interface KeyNode {
    public DimensionValueSet getKey();

    public int getCount();

    public List<Integer> getIndexes();
}

