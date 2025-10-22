/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.fielddatacrud.ImpMode
 */
package com.jiuqi.nr.data.common.service;

import com.jiuqi.nr.data.common.service.dto.CompletionDim;
import com.jiuqi.nr.data.common.service.dto.FilterDim;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.fielddatacrud.ImpMode;
import java.util.List;

public interface ImpSettings {
    public DimensionCollection getMasterKeys();

    public List<String> getNonexistentUnits();

    public ImpMode getImpMode(String var1);

    public void resetMasterKeys(DimensionCollection var1);

    public FilterDim getFilterDims();

    public CompletionDim getCompletionDims();
}

