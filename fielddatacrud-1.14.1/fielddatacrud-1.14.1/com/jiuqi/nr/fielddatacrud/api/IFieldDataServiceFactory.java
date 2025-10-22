/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.common.IProviderStore
 */
package com.jiuqi.nr.fielddatacrud.api;

import com.jiuqi.nr.dataservice.core.common.IProviderStore;
import com.jiuqi.nr.fielddatacrud.api.IFieldDataService;
import com.jiuqi.nr.fielddatacrud.spi.IParamDataProvider;

public interface IFieldDataServiceFactory {
    public IFieldDataService getFieldDataFileService();

    public IFieldDataService getFieldDataFileService(IProviderStore var1);

    public IFieldDataService getFieldDataFileService(IParamDataProvider var1);

    public IFieldDataService getFieldDataFileService(IProviderStore var1, IParamDataProvider var2);
}

