/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 */
package com.jiuqi.nr.data.engine.gather;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public interface GatherEntityFilterProvider {
    public Set<String> getFilterChildren(List<String> var1);

    @Deprecated
    public boolean filterCurrentEntity(String var1);

    @Deprecated
    default public Set<String> getCanGatherForms(DimensionValueSet dimensionSet, String formSchemeKey) {
        return Collections.emptySet();
    }
}

