/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 */
package com.jiuqi.nr.data.access.filter;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.data.access.common.AccessLevel;

public interface DimensionFilter {
    public DimensionValueSet filter(DimensionValueSet var1, String var2, String var3, AccessLevel var4);

    public String getAccessName();
}

