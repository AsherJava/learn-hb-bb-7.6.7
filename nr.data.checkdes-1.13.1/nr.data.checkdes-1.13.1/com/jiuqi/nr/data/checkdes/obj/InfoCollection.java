/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 */
package com.jiuqi.nr.data.checkdes.obj;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import java.util.HashSet;
import java.util.Set;

public class InfoCollection {
    private final Set<String> formulaSchemes = new HashSet<String>();
    private final Set<String> forms = new HashSet<String>();
    private final Set<DimensionValueSet> dimensionValueSets = new HashSet<DimensionValueSet>();
    private final Set<String> dwSet = new HashSet<String>();

    public Set<String> getFormulaSchemes() {
        return this.formulaSchemes;
    }

    public Set<String> getForms() {
        return this.forms;
    }

    public Set<DimensionValueSet> getDimensionValueSets() {
        return this.dimensionValueSets;
    }

    public Set<String> getDwSet() {
        return this.dwSet;
    }
}

