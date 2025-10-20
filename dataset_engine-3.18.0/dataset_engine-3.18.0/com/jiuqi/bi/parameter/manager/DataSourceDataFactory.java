/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.parameter.manager;

import com.jiuqi.bi.parameter.manager.IDataSourceDataProvider;
import com.jiuqi.bi.parameter.model.datasource.DataSourceModel;

public abstract class DataSourceDataFactory {
    public abstract IDataSourceDataProvider createDataProvider(DataSourceModel var1);
}

