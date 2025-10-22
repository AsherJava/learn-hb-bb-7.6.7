/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.common.IProviderStore
 */
package com.jiuqi.nr.datacopy.factory;

import com.jiuqi.nr.datacopy.param.DataCopyParamProvider;
import com.jiuqi.nr.datacopy.service.IDataCopyService;
import com.jiuqi.nr.dataservice.core.common.IProviderStore;

public interface IDataCopyServiceFactory {
    public IDataCopyService getDataCopyService(DataCopyParamProvider var1);

    public IDataCopyService getDataCopyService(DataCopyParamProvider var1, IProviderStore var2);
}

