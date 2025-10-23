/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 */
package com.jiuqi.nr.fmdm;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.fmdm.internal.check.FMDMCheckResult;
import java.util.List;
import java.util.Set;

public interface IFMDMUpdateResult {
    public String getSaveKey(String var1);

    public List<String> getUpdateKeys();

    public List<String> getUpdateCodes();

    public List<DimensionValueSet> getUpdateDimensions();

    public FMDMCheckResult getFMDMCheckResult();

    public Set<DimensionCombination> getNoAccessDims();
}

