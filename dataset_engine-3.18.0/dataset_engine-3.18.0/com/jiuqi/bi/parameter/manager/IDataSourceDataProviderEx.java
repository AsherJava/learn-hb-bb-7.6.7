/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.MemoryDataSet
 */
package com.jiuqi.bi.parameter.manager;

import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.parameter.DataSourceException;
import com.jiuqi.bi.parameter.engine.ParameterEngineEnv;
import com.jiuqi.bi.parameter.manager.IDataSourceDataProvider;
import com.jiuqi.bi.parameter.model.ParameterColumnInfo;
import com.jiuqi.bi.parameter.model.ParameterModel;

public interface IDataSourceDataProviderEx
extends IDataSourceDataProvider {
    public MemoryDataSet<ParameterColumnInfo> filterChildrenValue(String var1, int var2, boolean var3, String var4, ParameterEngineEnv var5) throws DataSourceException;

    public MemoryDataSet<ParameterColumnInfo> filterAllChoiceValues(ParameterModel var1, ParameterEngineEnv var2) throws DataSourceException;

    public MemoryDataSet<ParameterColumnInfo> filterChildrenValue(String var1, int var2, String var3, ParameterEngineEnv var4, boolean var5) throws DataSourceException;
}

