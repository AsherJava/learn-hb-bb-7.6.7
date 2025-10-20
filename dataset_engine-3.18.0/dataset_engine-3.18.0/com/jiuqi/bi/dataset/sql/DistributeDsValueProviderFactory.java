/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.distributeds.IDistributeDsValueProvider
 *  com.jiuqi.nvwa.framework.parameter.IParameterEnv
 */
package com.jiuqi.bi.dataset.sql;

import com.jiuqi.bi.distributeds.IDistributeDsValueProvider;
import com.jiuqi.bi.parameter.engine.EnhancedParameterEnvAdapter;
import com.jiuqi.bi.parameter.engine.IParameterEnv;

public abstract class DistributeDsValueProviderFactory {
    public abstract IDistributeDsValueProvider createDistributeDsValueProvider(com.jiuqi.nvwa.framework.parameter.IParameterEnv var1);

    @Deprecated
    public IDistributeDsValueProvider createDistributeDsValueProvider(IParameterEnv env) {
        return this.createDistributeDsValueProvider(new EnhancedParameterEnvAdapter(env));
    }
}

