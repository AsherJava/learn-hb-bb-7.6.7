/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataservice.core.dimension;

import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.dataservice.core.dimension.FixedDimensionValue;

interface DimensionCombinationSetter
extends DimensionCombination {
    public void setValue(FixedDimensionValue var1);

    public void setValue(String var1, String var2, Object var3);

    public void setDWValue(FixedDimensionValue var1);

    public void setDWValue(String var1, String var2, Object var3);
}

