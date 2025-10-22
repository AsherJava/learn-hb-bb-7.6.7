/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 */
package com.jiuqi.nr.datastatus.facade.obj;

import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import java.util.List;

public interface ICopySetting {
    public String getFormSchemeKey();

    public List<String> getFormKeys();

    public DimensionCollection getTargetDimension();

    public DimensionCombination getSourceDimension(DimensionCombination var1);
}

