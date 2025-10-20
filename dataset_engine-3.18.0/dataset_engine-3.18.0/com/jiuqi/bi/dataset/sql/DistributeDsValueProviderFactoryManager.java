/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.distributeds.IDistributeDsValueProvider
 *  com.jiuqi.nvwa.framework.parameter.IParameterEnv
 */
package com.jiuqi.bi.dataset.sql;

import com.jiuqi.bi.dataset.sql.DefaultDistributeDsValueProviderFactory;
import com.jiuqi.bi.dataset.sql.DistributeDsValueProviderFactory;
import com.jiuqi.bi.distributeds.IDistributeDsValueProvider;
import com.jiuqi.bi.parameter.engine.IParameterEnv;

public class DistributeDsValueProviderFactoryManager {
    private DistributeDsValueProviderFactory factory = new DefaultDistributeDsValueProviderFactory();
    private static final DistributeDsValueProviderFactoryManager instance = new DistributeDsValueProviderFactoryManager();

    private DistributeDsValueProviderFactoryManager() {
    }

    public static DistributeDsValueProviderFactoryManager getInstance() {
        return instance;
    }

    public void regFactory(DistributeDsValueProviderFactory factory) {
        this.factory = factory;
    }

    public DistributeDsValueProviderFactory getFactory() {
        return this.factory;
    }

    public IDistributeDsValueProvider createProvider(com.jiuqi.nvwa.framework.parameter.IParameterEnv env) {
        return this.factory.createDistributeDsValueProvider(env);
    }

    @Deprecated
    public IDistributeDsValueProvider createProvider(IParameterEnv env) {
        return this.factory.createDistributeDsValueProvider(env);
    }
}

