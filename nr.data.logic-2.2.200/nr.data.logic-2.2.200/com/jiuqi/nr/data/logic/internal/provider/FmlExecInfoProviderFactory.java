/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.logic.internal.provider;

import com.jiuqi.nr.data.logic.facade.extend.IFmlExecInfoProvider;
import com.jiuqi.nr.data.logic.facade.extend.IFmlExecInfoProviderFactory;
import com.jiuqi.nr.data.logic.facade.extend.param.BaseFmlFactoryParam;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FmlExecInfoProviderFactory {
    @Autowired
    private List<IFmlExecInfoProviderFactory> fmlExecInfoProviderFactories;

    public IFmlExecInfoProvider getProvider(BaseFmlFactoryParam param) {
        for (IFmlExecInfoProviderFactory fmlExecInfoProviderFactory : this.fmlExecInfoProviderFactories) {
            IFmlExecInfoProvider fmlExecInfoProvider;
            if (!param.getProviderFactoryName().equals(fmlExecInfoProviderFactory.getFactoryName()) || (fmlExecInfoProvider = fmlExecInfoProviderFactory.getProvider(param)) == null) continue;
            return fmlExecInfoProvider;
        }
        throw new IllegalArgumentException("\u672a\u627e\u5230\u516c\u5f0f\u6267\u884c\u4fe1\u606f\u63d0\u4f9b\u5668" + param.getProviderFactoryName() + "-" + param.getClass());
    }
}

