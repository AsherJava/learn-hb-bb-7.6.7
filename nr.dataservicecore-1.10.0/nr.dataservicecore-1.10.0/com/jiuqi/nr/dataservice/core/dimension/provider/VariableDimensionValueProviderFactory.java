/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataservice.core.dimension.provider;

import com.jiuqi.nr.dataservice.core.dimension.VariableDimensionValueProvider;
import com.jiuqi.nr.dataservice.core.dimension.provider.DimensionProviderData;

public interface VariableDimensionValueProviderFactory {
    public VariableDimensionValueProvider getProvider(DimensionProviderData var1);
}

