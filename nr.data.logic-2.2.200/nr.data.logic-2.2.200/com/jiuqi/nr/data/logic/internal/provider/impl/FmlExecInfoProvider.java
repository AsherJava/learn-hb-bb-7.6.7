/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 */
package com.jiuqi.nr.data.logic.internal.provider.impl;

import com.jiuqi.nr.data.logic.facade.extend.IFmlExecInfoProvider;
import com.jiuqi.nr.data.logic.facade.extend.param.FmlExecInfo;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import java.util.List;

class FmlExecInfoProvider
implements IFmlExecInfoProvider {
    private final List<FmlExecInfo> fmlExecInfos;
    private final String formulaSchemeKey;
    private final DimensionCollection dimensionCollection;

    public FmlExecInfoProvider(List<FmlExecInfo> fmlExecInfos, String formulaSchemeKey, DimensionCollection dimensionCollection) {
        this.fmlExecInfos = fmlExecInfos;
        this.formulaSchemeKey = formulaSchemeKey;
        this.dimensionCollection = dimensionCollection;
    }

    @Override
    public String getFormulaSchemeKey() {
        return this.formulaSchemeKey;
    }

    @Override
    public DimensionCollection getDimensionCollection() {
        return this.dimensionCollection;
    }

    @Override
    public List<FmlExecInfo> getFmlExecInfo() {
        return this.fmlExecInfos;
    }
}

