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
import com.jiuqi.bi.parameter.model.ParameterModel;

public interface IDataSourceTreeHierarchyProvider {
    public MemoryDataSet<String> getChoiceTreeHierarchies(ParameterModel var1, ParameterEngineEnv var2) throws DataSourceException;
}

