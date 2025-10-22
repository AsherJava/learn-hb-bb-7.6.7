/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.common.IProviderStore
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 */
package com.jiuqi.nr.data.logic.internal.impl;

import com.jiuqi.nr.data.logic.api.param.BaseFmlProviderParam;
import com.jiuqi.nr.data.logic.api.param.FmlExecInfo;
import com.jiuqi.nr.data.logic.internal.impl.DefFmlProviderFactory;
import com.jiuqi.nr.data.logic.spi.IFmlExecInfoProvider;
import com.jiuqi.nr.dataservice.core.common.IProviderStore;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import java.util.List;

public class FmlExecInfoProvider
implements IFmlExecInfoProvider {
    private final BaseFmlProviderParam baseFmlProviderParam;
    private final DefFmlProviderFactory defFmlProviderFactory;
    private IProviderStore providerStore;
    private IFmlExecInfoProvider baseFmlProvider;

    public FmlExecInfoProvider(BaseFmlProviderParam baseFmlProviderParam, DefFmlProviderFactory defFmlProviderFactory) {
        this.baseFmlProviderParam = baseFmlProviderParam;
        this.defFmlProviderFactory = defFmlProviderFactory;
        this.providerStore = defFmlProviderFactory.getProviderStore();
    }

    private void init() {
        this.baseFmlProvider = this.defFmlProviderFactory.getProvider(this.baseFmlProviderParam, this.providerStore);
    }

    @Override
    public String getFormulaSchemeKey() {
        return this.getBaseFmlProvider().getFormulaSchemeKey();
    }

    @Override
    public DimensionCollection getDimensionCollection() {
        return this.getBaseFmlProvider().getDimensionCollection();
    }

    @Override
    public List<FmlExecInfo> getFmlExecInfo() {
        return this.getBaseFmlProvider().getFmlExecInfo();
    }

    public void setProviderStore(IProviderStore providerStore) {
        this.providerStore = providerStore;
    }

    public IFmlExecInfoProvider getBaseFmlProvider() {
        if (this.baseFmlProvider == null) {
            this.init();
        }
        return this.baseFmlProvider;
    }
}

