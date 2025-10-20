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
import java.util.List;

public interface IDataSourceDataQuickProvider
extends IDataSourceDataProvider {
    public static final int MAX_RETURN_RECORD_SIZE = 500;
    public static final String FLAG_HAS_MORE = "hasMore";

    public MemoryDataSet<ParameterColumnInfo> quickGetChoiceValues(ParameterModel var1, ParameterEngineEnv var2, int var3, boolean var4) throws DataSourceException;

    public MemoryDataSet<ParameterColumnInfo> fillDatasetByKey(MemoryDataSet<ParameterColumnInfo> var1, ParameterModel var2, ParameterEngineEnv var3) throws DataSourceException;

    public MemoryDataSet<ParameterColumnInfo> quickSearch(List<String> var1, ParameterEngineEnv var2, int var3, boolean var4) throws DataSourceException;
}

