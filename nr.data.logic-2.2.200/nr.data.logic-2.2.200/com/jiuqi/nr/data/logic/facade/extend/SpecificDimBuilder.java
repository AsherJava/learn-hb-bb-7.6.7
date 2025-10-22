/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollectionBuilder
 */
package com.jiuqi.nr.data.logic.facade.extend;

import com.jiuqi.nr.dataservice.core.dimension.DimensionCollectionBuilder;

public interface SpecificDimBuilder {
    public boolean isUse(String var1);

    public void setBuildInfo(DimensionCollectionBuilder var1, String var2, String var3);
}

