/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.parameter.manager;

import com.jiuqi.bi.parameter.engine.ParameterEngineEnv;
import com.jiuqi.bi.parameter.engine.cascade.MetaBuffer;
import com.jiuqi.bi.parameter.manager.ICascadeAnalyzer;
import com.jiuqi.bi.parameter.model.datasource.DataSourceModel;

public abstract class DataSourceModelFactory {
    public abstract DataSourceModel createDataSourceModel();

    public abstract ICascadeAnalyzer createCascadeAnalyzer(ParameterEngineEnv var1, MetaBuffer var2);

    public abstract String getTitle();

    public abstract String getType();
}

