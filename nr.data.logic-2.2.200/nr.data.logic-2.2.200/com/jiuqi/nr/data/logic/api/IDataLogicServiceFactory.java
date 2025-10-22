/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.common.IProviderStore
 */
package com.jiuqi.nr.data.logic.api;

import com.jiuqi.nr.data.logic.api.ICKDCopyService;
import com.jiuqi.nr.data.logic.api.ICalculateService;
import com.jiuqi.nr.data.logic.api.ICheckService;
import com.jiuqi.nr.data.logic.spi.ICKDCopyOptionProvider;
import com.jiuqi.nr.data.logic.spi.ICalOptionProvider;
import com.jiuqi.nr.data.logic.spi.ICheckOptionProvider;
import com.jiuqi.nr.data.logic.spi.IFmlExecInfoProvider;
import com.jiuqi.nr.data.logic.spi.IUnsupportedDesHandler;
import com.jiuqi.nr.dataservice.core.common.IProviderStore;
import java.util.Map;

public interface IDataLogicServiceFactory {
    public ICalculateService getCalculateService(IFmlExecInfoProvider var1);

    public ICalculateService getCalculateService(IFmlExecInfoProvider var1, IProviderStore var2);

    public ICalculateService getCalculateService(IFmlExecInfoProvider var1, ICalOptionProvider var2);

    public ICalculateService getCalculateService(IFmlExecInfoProvider var1, IProviderStore var2, ICalOptionProvider var3);

    public ICheckService getCheckService();

    public ICheckService getCheckService(IProviderStore var1);

    public ICheckService getCheckService(ICheckOptionProvider var1);

    public ICheckService getCheckService(IProviderStore var1, ICheckOptionProvider var2);

    public ICKDCopyService getCKDCopyService(IProviderStore var1, ICKDCopyOptionProvider var2);

    public IUnsupportedDesHandler getUnsupportedDesHandler(Map<String, String> var1);
}

