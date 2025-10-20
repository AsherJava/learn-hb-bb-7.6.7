/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.sql.IConnectionProvider
 */
package com.jiuqi.bi.database.temp;

import com.jiuqi.bi.database.temp.DefaultTempTableProvider;
import com.jiuqi.bi.database.temp.ITempTableProvider;
import com.jiuqi.bi.sql.IConnectionProvider;

public class TempTableProviderFactory {
    private static final TempTableProviderFactory instance = new TempTableProviderFactory();
    private ITempTableProvider tempTableProvider = new DefaultTempTableProvider();
    private IConnectionProvider connectionProvider;

    public static final TempTableProviderFactory getInstance() {
        return instance;
    }

    public ITempTableProvider getTempTableProvider() {
        return this.tempTableProvider;
    }

    public void setTempTableProvider(ITempTableProvider tempTableProvider) {
        this.tempTableProvider = tempTableProvider;
    }

    public IConnectionProvider getConnectionProvider() {
        return this.connectionProvider;
    }

    public void setConnectionProvider(IConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
    }
}

