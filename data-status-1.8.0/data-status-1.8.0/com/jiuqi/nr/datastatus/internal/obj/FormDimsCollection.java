/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 */
package com.jiuqi.nr.datastatus.internal.obj;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import java.util.HashSet;
import java.util.Set;

public class FormDimsCollection {
    private final Set<String> formKeys = new HashSet<String>();
    private final Set<DimensionValueSet> dimensionValueSets = new HashSet<DimensionValueSet>();

    public Set<String> getFormKeys() {
        return this.formKeys;
    }

    public Set<DimensionValueSet> getDimensionValueSets() {
        return this.dimensionValueSets;
    }
}

