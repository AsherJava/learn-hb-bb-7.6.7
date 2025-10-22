/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.logic.facade.extend;

import com.jiuqi.nr.data.logic.facade.extend.IFmlExecInfoProvider;
import com.jiuqi.nr.data.logic.facade.extend.param.BaseFmlFactoryParam;

public interface IFmlExecInfoProviderFactory {
    public String getFactoryName();

    public IFmlExecInfoProvider getProvider(BaseFmlFactoryParam var1);
}

