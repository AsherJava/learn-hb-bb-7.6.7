/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.common.IProviderStore
 */
package com.jiuqi.nr.data.text.api;

import com.jiuqi.nr.data.text.service.ExpFieldDataService;
import com.jiuqi.nr.data.text.service.ImpFieldDataService;
import com.jiuqi.nr.data.text.spi.IParamDataFileProvider;
import com.jiuqi.nr.dataservice.core.common.IProviderStore;

public interface IFieldDataFileServiceFactory {
    public ExpFieldDataService getExpFieldDataService(IParamDataFileProvider var1);

    public ExpFieldDataService getExpFieldDataService(IProviderStore var1, IParamDataFileProvider var2);

    public ImpFieldDataService getImpFieldDataService();

    public ImpFieldDataService getImpFieldDataService(IProviderStore var1);

    public ImpFieldDataService getImpFieldDataService(IParamDataFileProvider var1);

    public ImpFieldDataService getImpFieldDataService(IProviderStore var1, IParamDataFileProvider var2);
}

