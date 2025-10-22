/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.logic.api;

import com.jiuqi.nr.data.logic.api.param.BaseFmlProviderParam;
import com.jiuqi.nr.data.logic.spi.IFmlExecInfoProvider;

public interface IFmlExecInfoProviderFactory {
    public IFmlExecInfoProvider getProvider(BaseFmlProviderParam var1);
}

