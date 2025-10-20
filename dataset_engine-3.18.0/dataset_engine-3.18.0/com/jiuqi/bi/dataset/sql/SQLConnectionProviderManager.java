/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.sql.IConnectionProvider
 *  com.jiuqi.bi.syntax.function.IFunctionProvider
 */
package com.jiuqi.bi.dataset.sql;

import com.jiuqi.bi.sql.IConnectionProvider;
import com.jiuqi.bi.syntax.function.IFunctionProvider;

public class SQLConnectionProviderManager {
    private static final SQLConnectionProviderManager instance = new SQLConnectionProviderManager();
    private IFunctionProvider functionProvider;
    private IConnectionProvider provider;

    public static final SQLConnectionProviderManager getInstance() {
        return instance;
    }

    public void registConnectionProvider(IConnectionProvider provider) {
        this.provider = provider;
    }

    public IConnectionProvider getConnectionProvider() {
        return this.provider;
    }

    public void registFunctionProvider(IFunctionProvider provider) {
        this.functionProvider = provider;
    }

    public IFunctionProvider getFunctionProvider() {
        return this.functionProvider;
    }
}

